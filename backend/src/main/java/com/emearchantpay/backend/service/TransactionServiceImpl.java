package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.model.TransactionStatus;
import com.emearchantpay.backend.model.TransactionType;
import com.emearchantpay.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    MerchantService merchantService;

    @Autowired
    UserService userService;

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * handle 4 types of transactions: AUTHORIZE, CHARGE, REFUND & REVERSAL.
     * update transaction status accordingly,
     * if some logic is not as requested the transaction would be submitted with ERROR status.
     * @param transaction to be submitted
     * @param merchant_id the merchant that the transaction belongs to.
     * @return false only if transaction is not submitted: if the merchant is not valid or inactive.
     * other than that @return true.
     */
    @Override
    public boolean create(Transaction transaction,Long merchant_id) {
        Merchant merchant = merchantService.getById(merchant_id);

        if((transaction.getType() == TransactionType.REFUND
                || transaction.getType() == TransactionType.CHARGE ) && merchant == null
                && transaction.getReference()!=null){
            merchant = transaction.getReference().getMerchant();
        }

        if(merchant == null || !merchant.isActive()) return false;

        transaction.setMerchant(merchant);
        if(isReferencedTransactionWithValidStatus(transaction)){
            switch (transaction.getType()){
                case AUTHORIZE:
                    boolean held  = userService.holdAmount(transaction.getAmount());
                    if(held) transaction.setStatus(TransactionStatus.APPROVED);
                    else transaction.setStatus(TransactionStatus.ERROR);
                    break;
                case CHARGE:
                    if(transaction.getReference()!=null){
                        if(transaction.getReference().getAmount()==transaction.getAmount() &&
                                Objects.equals(transaction.getReference().getCustomer_email(), transaction.getCustomer_email())){
                            userService.unholdAmount(transaction.getReference().getAmount());
                        }
                    }
                    boolean charged = userService.charge(transaction.getAmount());
                    if(charged) {
                        merchantService.transferMoney(transaction.getAmount(),transaction.getMerchant());
                        transaction.setStatus(TransactionStatus.APPROVED);
                    } else transaction.setStatus(TransactionStatus.ERROR);
                    break;
                case REFUND:
                    if(transaction.getReference()!=null) {
                        userService.refund(transaction.getReference().getAmount());
                        merchantService.transferMoney(-transaction.getAmount(), merchant);
                        transaction.getReference().setStatus(TransactionStatus.REFUNDED);
                        transactionRepository.save(transaction.getReference());
                        transaction.setStatus(TransactionStatus.APPROVED);
                    }else transaction.setStatus(TransactionStatus.ERROR);
                    break;
                case REVERSAL:
                    if(transaction.getReference()!=null){
                        userService.unholdAmount(transaction.getReference().getAmount());
                        transaction.getReference().setStatus(TransactionStatus.REVERSED);
                        transactionRepository.save(transaction.getReference());
                        transaction.setStatus(TransactionStatus.APPROVED);
                    } else transaction.setStatus(TransactionStatus.ERROR);
                    break;
                default:
                    transaction.setStatus(TransactionStatus.ERROR);
            }
        }
        transactionRepository.save(transaction);
        return true;
    }

    /**
     * some transactions need reference transaction,
     * to validate if APPROVED or REFUNDED is the status of the reference transaction.
     * @param transaction to be validated.
     * @return true if not referenced or referenced to valid transaction
     */
    private boolean isReferencedTransactionWithValidStatus(Transaction transaction){
        if(transaction.getReference()!=null){
            TransactionStatus referencedTransactionStatus = transaction.getReference().getStatus();
            if(referencedTransactionStatus == TransactionStatus.ERROR ||
                    referencedTransactionStatus == TransactionStatus.REVERSED){
                transaction.setStatus(TransactionStatus.ERROR);
                return false;
            }
        }
        return true;
    }
    @Override
    public void delete(UUID transactin_id) {
        transactionRepository.deleteById(transactin_id);
    }

    /**
     * used for archiving old transactions.
     * @param timestamp the desired timestamp to fetch transactions before.
     * @return list of transactions with creation timestamp before one hour.
     */
    @Override
    public List<Transaction> getByCreationTimestampBefore(Timestamp timestamp) {
        return transactionRepository.findAllByCreationTimestampBefore(timestamp);
    }

    @Override
    public List<Transaction> getByMerchant(Merchant merchant) {
        return transactionRepository.findAllByMerchant(merchant);
    }

}
