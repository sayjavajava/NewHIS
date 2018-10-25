package com.sd.his.service;

import com.sd.his.model.GeneralLedger;
import com.sd.his.repository.GeneralLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralLedgerService {


    @Autowired
    GeneralLedgerRepository generalLedgerRepository;

    public List<GeneralLedger> getAll(){
        return generalLedgerRepository.getAll();
    }

    public void saveConfiguration(GeneralLedger generalLedger){
        generalLedgerRepository.save(generalLedger);
    }
}
