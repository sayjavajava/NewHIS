package com.sd.his.service;

import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.GeneralLedgerTransaction;
import com.sd.his.repository.GeneralLedgerTransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralLedgerTransactionsService {

    @Autowired
    GeneralLedgerTransactionsRepository generalLedgerTransactionsRepository;
    @Autowired
    private HISUtilService hisUtilService;

    public GeneralLedgerTransaction getById(long id) {
        return generalLedgerTransactionsRepository.findOne(id);
    }

    public List<GeneralLedgerTransaction> getAll() {
        return generalLedgerTransactionsRepository.findAll();
    }

    public void saveConfiguration(GeneralLedgerTransaction generalLedgerTransaction) {
        generalLedgerTransactionsRepository.save(generalLedgerTransaction);
    }

    public String getGLTransactionCode(){
        return this.hisUtilService.generatePrefix(ModuleEnum.GENERAL_LEDGER_TRANSACTIONS);
    }

    public void updateGLTransactionCode(){
        this.hisUtilService.updatePrefix(ModuleEnum.GENERAL_LEDGER_TRANSACTIONS);
    }
}
