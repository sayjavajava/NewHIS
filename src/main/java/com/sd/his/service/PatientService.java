package com.sd.his.service;

import com.sd.his.model.User;
import com.sd.his.repositiories.PatientRepository;
import com.sd.his.utill.DateUtil;
import com.sd.his.wrapper.PatientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.util.List;

/*
 * @author    : Muhammad Jamal
 * @Date      : 5-Jun-18
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
 * @FileName  : ManagePatientService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public List<PatientWrapper> findAllPaginatedPatients(int offset, int limit,String userType) {
        Pageable pageable = new PageRequest(offset, limit);
        return patientRepository.findAllByDeletedFalseAndActiveTrue(pageable,userType);
    }

    public int countAllPaginatedPatients(String userType) {
        return patientRepository.findAllByDeletedFalseAndActiveTrue(userType).size();
    }

    public void deletePatientById(long patientId) {
        User user = this.patientRepository.findOne(patientId);
        if (user != null) {
            user.setDeleted(true);
            user.getProfile().setDeleted(true);
            user.getProfile().setUpdatedOn(System.currentTimeMillis());

            user.getInsurance().setUpdated(System.currentTimeMillis());
            user.getInsurance().setDeleted(true);
            this.patientRepository.save(user);
        }
    }

   /* @Transactional(rollbackOn = Throwable.class)
    public void deleteTax(Tax tax) {
        tax.setDeleted(true);
        patientRepository.save(tax);
    }*/

//    public Tax findTaxById(long taxId) {
//        return patientRepository.findOne(taxId);
//    }

    /*@Transactional(rollbackOn = Throwable.class)
    public Tax saveTax(SaveTaxRequest saveTaxRequest) {
        Tax tax = new Tax(saveTaxRequest);
        return patientRepository.save(tax);
    }*/

    /*public boolean isAlreadyExist(SaveTaxRequest taxWrapper) {
        Boolean isAlreadyExist = false;

        if (taxWrapper.getId() > 0) {
            List<Tax> taxes = this.patientRepository.findAllByNameAndIdNotAndDeletedFalse(taxWrapper.getName(), taxWrapper.getId());
            if (!HISCoreUtil.isListEmpty(taxes)) {
                isAlreadyExist = true;
            }
        } else {
            Tax tax = this.patientRepository.findByNameAndDeletedFalse(taxWrapper.getName());
            if (HISCoreUtil.isValidObject(tax)) {
                isAlreadyExist = true;
            }
        }
        return isAlreadyExist;
    }

    public Tax updateTaxService(SaveTaxRequest updateRequest) {
        Tax dbTax = this.patientRepository.findById(updateRequest.getId());
        APIUtil.buildTax(dbTax, updateRequest);
        return this.patientRepository.save(dbTax);
    }

    public List<SaveTaxRequest> searchByTaxByName(String searchTaxName, int pageNo, int pageSize) {
        List<SaveTaxRequest> taxesResponse = new ArrayList<>();
        Pageable pageable = new PageRequest(pageNo, pageSize);
        List<Tax> dbTaxes = patientRepository.findAllByNameContainingAndDeletedFalse(searchTaxName, pageable);
        APIUtil.buildTaxWrapper(taxesResponse, dbTaxes);
        return taxesResponse;
    }

    public int countSearchByTaxByName(String searchTaxName) {
        return patientRepository.findAllByNameContainingAndDeletedFalse(searchTaxName).size();
    }*/
}
