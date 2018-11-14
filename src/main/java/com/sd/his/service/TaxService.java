package com.sd.his.service;

import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.Tax;
import com.sd.his.repository.TaxRepository;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.TaxWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 14-May-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.service
 * @FileName  : TaxService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class TaxService {

    @Autowired
    TaxRepository taxRepository;
    @Autowired
    HISUtilService hisUtilService;

    public List<TaxWrapper> findAllActiveTax() {
        return taxRepository.findAllByActiveTrue(true);
    }

    public List<TaxWrapper> getAllTaxesForDataTable() {
        return taxRepository.findAllTaxesForDataTable();
    }
    public List<TaxWrapper> findAllPaginatedTax(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return taxRepository.findAllByCreatedOnNotNull(pageable);
    }

    public int countAllTax() {
        return taxRepository.findAll().size();
    }

    @Transactional(rollbackOn = Throwable.class)
    public void deleteTax(long taxId) {
        taxRepository.delete(taxId);
    }

    public Tax findTaxById(long taxId) {
        return taxRepository.findOne(taxId);
    }

    @Transactional(rollbackOn = Throwable.class)
    public void saveTax(TaxWrapper taxWrapper) throws ParseException {
        Tax tax = new Tax(taxWrapper);
        tax.setTaxId(this.hisUtilService.generateAndUpdatePrefix(ModuleEnum.TAX));
        taxRepository.save(tax);
    }

    public boolean isAlreadyExist(TaxWrapper taxWrapper) {
        Boolean isAlreadyExist = false;

        if (taxWrapper.getId() > 0) {
            List<Tax> taxes = this.taxRepository.findAllByNameAndIdNot(taxWrapper.getName(), taxWrapper.getId());
            if (!HISCoreUtil.isListEmpty(taxes)) {
                isAlreadyExist = true;
            }
        } else {
            Tax tax = this.taxRepository.findByName(taxWrapper.getName());
            if (HISCoreUtil.isValidObject(tax)) {
                isAlreadyExist = true;
            }
        }
        return isAlreadyExist;
    }

    public void updateTaxService(TaxWrapper updateRequest) throws ParseException {
        Tax dbTax = this.taxRepository.findOne(updateRequest.getId());
        new Tax(dbTax, updateRequest);
        this.taxRepository.save(dbTax);
    }

    public List<TaxWrapper> searchByTaxByName(String searchTaxName, int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize);
        return taxRepository.findAllByNameContaining(searchTaxName, pageable);
    }

    public int countSearchByTaxByName(String searchTaxName) {
        return taxRepository.findAllByNameContaining(searchTaxName).size();
    }

    public boolean hasChild(long taxId) {
        Tax tax = this.taxRepository.findOne(taxId);
        if (tax != null && tax.getMedicalServices() != null && tax.getMedicalServices().size() > 0) {
            return true;
        }
        return false;
    }
}
