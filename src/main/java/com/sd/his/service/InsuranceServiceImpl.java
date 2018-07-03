package com.sd.his.service;

import com.sd.his.repositories.InsuranceRepository;
import com.sd.his.wrapper.InsuranceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by jamal on 7/2/2018.
 */
@Service
@Transactional
public class InsuranceServiceImpl implements InsuranceManager {

    @Autowired
    InsuranceRepository insuranceRepository;

    public InsuranceWrapper getInsuranceWrapper(long id){
        return this.insuranceRepository.getInsuranceWrapperById(id);
    }

}
