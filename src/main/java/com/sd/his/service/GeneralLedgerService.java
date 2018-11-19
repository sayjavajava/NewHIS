package com.sd.his.service;

import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.AccountConfig;
import com.sd.his.model.GeneralLedger;
import com.sd.his.repository.AccountConfigRepository;
import com.sd.his.repository.GeneralLedgerRepository;
import com.sd.his.wrapper.request.AccountConfigRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralLedgerService {


    @Autowired
    GeneralLedgerRepository generalLedgerRepository;

    @Autowired
    AccountConfigRepository accountConfigRepository;

    @Autowired
    private HISUtilService hisUtilService;

    public GeneralLedger getById(long id){
        return generalLedgerRepository.findOne(id);
    }

    public List<GeneralLedger> getAll(){
        return generalLedgerRepository.findAll();
    }

    public void saveConfiguration(GeneralLedger generalLedger){
        generalLedgerRepository.save(generalLedger);
    }


    public AccountConfig getAccountConfig(){
//        List<AccountConfig> result = accountConfigRepository.findAll();
//        return result.size() > 0 ? result.get(0) : null;
        return accountConfigRepository.findOne(1L);
    }

    public void saveAssetsConfiguration(AccountConfigRequestWrapper accountConfigRequest){
        AccountConfig accountConfig=accountConfigRepository.findAll().size()>0 ? accountConfigRepository.findAll().get(0) : null;

        if(accountConfig ==null){
            accountConfig = new AccountConfig();
        }
        accountConfig.setCash(generalLedgerRepository.findOne(accountConfigRequest.getCash()));
        accountConfig.setBank(generalLedgerRepository.findOne(accountConfigRequest.getBank()));
        accountConfig.setInventory(generalLedgerRepository.findOne(accountConfigRequest.getInventory()));
        accountConfig.setAccountReceivable(generalLedgerRepository.findOne(accountConfigRequest.getAccountReceivable()));
        accountConfig.setPalntEquipment(generalLedgerRepository.findOne(accountConfigRequest.getPalntEquipment()));
        accountConfig.setFurnitureFixture(generalLedgerRepository.findOne(accountConfigRequest.getFurnitureFixture()));
        accountConfigRepository.save(accountConfig);
    }

    public void saveLiabilityConfiguration(AccountConfigRequestWrapper accountConfigRequest){
        AccountConfig accountConfig=accountConfigRepository.findAll().size()>0 ? accountConfigRepository.findAll().get(0) : null;

        if(accountConfig ==null){
            accountConfig = new AccountConfig();
        }
        accountConfig.setTaxPayable(generalLedgerRepository.findOne(accountConfigRequest.getTaxPayable()));
        accountConfig.setAccountPayable(generalLedgerRepository.findOne(accountConfigRequest.getAccountPayable()));

        accountConfig.setAccuredSalary(generalLedgerRepository.findOne(accountConfigRequest.getAccuredSalary()));
        accountConfig.setLoan(generalLedgerRepository.findOne(accountConfigRequest.getLoan()));
        accountConfig.setOtherPayable(generalLedgerRepository.findOne(accountConfigRequest.getOtherPayable()));
        accountConfigRepository.save(accountConfig);
    }

    public void saveRevenueConfiguration(AccountConfigRequestWrapper accountConfigRequest){
        AccountConfig accountConfig=accountConfigRepository.findAll().size()>0 ? accountConfigRepository.findAll().get(0) : null;

        if(accountConfig ==null){
            accountConfig = new AccountConfig();
        }
        accountConfig.setIncome(generalLedgerRepository.findOne(accountConfigRequest.getIncome()));
        accountConfig.setOtherIncome(generalLedgerRepository.findOne(accountConfigRequest.getOtherIncome()));
        accountConfigRepository.save(accountConfig);
    }

    public void saveCOGSConfiguration(AccountConfigRequestWrapper accountConfigRequest){
        AccountConfig accountConfig=accountConfigRepository.findAll().size()>0 ? accountConfigRepository.findAll().get(0) : null;

        if(accountConfig ==null){
            accountConfig = new AccountConfig();
        }
        accountConfig.setCostOfSale(generalLedgerRepository.findOne(accountConfigRequest.getCostOfSale()));
        accountConfigRepository.save(accountConfig);
    }

    public void saveExpenseConfiguration(AccountConfigRequestWrapper accountConfigRequest){
        AccountConfig accountConfig=accountConfigRepository.findAll().size()>0 ? accountConfigRepository.findAll().get(0) : null;

        if(accountConfig ==null){
            accountConfig = new AccountConfig();
        }
        accountConfig.setGeneralExpense(generalLedgerRepository.findOne(accountConfigRequest.getGeneralExpense()));
        accountConfigRepository.save(accountConfig);
    }

    public String getAccountCode(){
        return this.hisUtilService.generatePrefix(ModuleEnum.GENERAL_LEDGER);
    }

    public void updateAccountCode(){
        this.hisUtilService.updatePrefix(ModuleEnum.GENERAL_LEDGER);
    }

    public void deleteLedger(Long generalLedgerId) {
        this.generalLedgerRepository.delete(generalLedgerId);
    }
}
