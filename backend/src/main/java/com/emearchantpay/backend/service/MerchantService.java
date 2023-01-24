package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Merchant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MerchantService {

    void create(Merchant merchant) throws Exception;

     boolean delete(Long id);

    Merchant getById(Long id);


    List<Merchant> getAll();

}
