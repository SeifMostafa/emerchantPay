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

    @Override
    public List<Transaction> getByCreationTimestampAfter(Timestamp timestamp) {
        return transactionRepository.findAllByCreationTimestampAfter(timestamp);
    }

    @Override
    public List<Transaction> getByMerchant(Merchant merchant) {
        return transactionRepository.findAllByMerchant(merchant);
    }

}
