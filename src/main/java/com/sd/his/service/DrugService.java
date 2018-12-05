package com.sd.his.service;

import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.Country;
import com.sd.his.model.Drug;
import com.sd.his.model.Prefix;
import com.sd.his.repository.CountryRepository;
import com.sd.his.repository.DrugRepository;
import com.sd.his.repository.PrefixRepository;
import com.sd.his.wrapper.DrugWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 10/22/2018.
 */
@Service
public class DrugService {

    @Autowired
    DrugRepository drugRepository;
    @Autowired
    private PrefixRepository prefixRepository;

    @Autowired
    private CountryRepository countryRepository;

    public boolean isNameDrugDuplicateByName(String name) {
        return this.drugRepository.getDrugByDrugName(name);
    }

    public boolean isNameDrugDuplicateByNameAndNotEqualId(long id, String name) {
        return this.drugRepository.getDrugByDrugNameAndNotEqualId(id, name);
    }

    @Transactional
    public String saveDrug(DrugWrapper drugWrapper) {
        Drug drug = new Drug(drugWrapper);
        drug.setStrengths(drugWrapper.getStrengths());
        boolean chkStatusCountry=containsDigit(drugWrapper.getSelectedCountry());
        long numCountry;
        if(chkStatusCountry==true){
            numCountry = Long.parseLong(drugWrapper.getSelectedCountry());
        }else{
            Country countryObj=countryRepository.findTitleById(drugWrapper.getSelectedCountry());
            numCountry=countryObj.getId();

        }
        Country countryObj=countryRepository.findOne(Long.valueOf(numCountry));
        drug.setCountry(countryObj);
        Prefix pr = this.prefixRepository.findByModule(ModuleEnum.DRUG.name());
        pr.setCurrentValue(pr.getCurrentValue() + 1L);
        this.prefixRepository.save(pr);
        this.drugRepository.save(drug);
        return "";
    }

    public List<DrugWrapper> getPaginatedAllDrugs(Pageable pageable) {
        return this.drugRepository.findAllByCreatedOn(pageable);
    }

    public int countPaginatedAllDrugs() {
        return this.drugRepository.findAll().size();
    }

    @Transactional
    public boolean deleteDrug(long id) {
        Drug drug = this.drugRepository.findOne(id);
        if (drug != null) {
            this.drugRepository.delete(drug);
            return true;
        }
        return false;
    }

    public DrugWrapper getDrugWrapper(long id) {
        return this.drugRepository.getDrugById(id);
    }

    @Transactional
    public String updateDrug(DrugWrapper drugWrapper) {
        Drug drug = this.drugRepository.findOne(drugWrapper.getId());
        boolean chkStatusCountry=containsDigit(drugWrapper.getSelectedCountry());
        long numCountry;
        if(chkStatusCountry==true){
            numCountry = Long.parseLong(drugWrapper.getSelectedCountry());
        }else{
            Country countryObj=countryRepository.findTitleById(drugWrapper.getSelectedCountry());
            numCountry=countryObj.getId();

        }
        drugWrapper.setSelectedCountry(String.valueOf(numCountry));
        new Drug(drug, drugWrapper);
        this.drugRepository.save(drug);
        return "";
    }

    public List<DrugWrapper> searchDrugByParams(Pageable pageable, DrugWrapper drugWrapper) {
        return /*this.drugRepository.searchDrugByParams(pageable, drugWrapper.getDrugName())*/ null;
    }

    public String getDrugNaturalId() {
        Prefix prefix = prefixRepository.findByModule(ModuleEnum.DRUG.name());
        String currentPrefix = prefix.getName() + "-" + prefix.getCurrentValue();
        return currentPrefix;
    }

    public List<String> searchByDrugNameAutoComplete(String text) {
        return this.drugRepository.searchDrugByParams(text);
    }

    public List<DrugWrapper> getAllDrugWrappers() {
        return this.drugRepository.getAllDrugWrappers();
    }

    public Drug getDrugById(long id) {
        return this.drugRepository.findOne(id);
    }


    public final boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }

    public String searchByDrugNameAutoCompleteDetail(String text) {
        return  this.drugRepository.searchDrugByDifferentParams(text);
    }


    /*public List<String> searchByDrugNameAuto(String text) {
        return this.drugRepository.searchDrugByParamsNames(text);
    }*/

    public Drug searchByDrugNameAutoCompleteStrengths(String text) {
        return this.drugRepository.searchDrugStrengthsByDifferentParams(text);
    }
}
