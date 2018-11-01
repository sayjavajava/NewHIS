package com.sd.his.service;

import com.sd.his.model.Prefix;
import com.sd.his.model.SmsTemplate;
import com.sd.his.repository.PrefixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrefixServices {

    @Autowired
    PrefixRepository prefixRepository;

    public List<Prefix> getAll(){
        return prefixRepository.getAll();
    }

    public void saveConfiguration(Prefix prefix){
        prefixRepository.save(prefix);
    }
}
