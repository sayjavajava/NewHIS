package com.sd.his.service;

import com.sd.his.model.Tax;
import com.sd.his.repositiories.TaxRepository;
import com.sd.his.wrapper.TaxWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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


    public List<TaxWrapper> findAllTax(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<Tax> dbTax = taxRepository.findAllByDeletedFalse(pageable);
        List<TaxWrapper> taxWrappers = new ArrayList<>();

        for (Tax tax : dbTax) {
            TaxWrapper taxWrapper = new TaxWrapper(tax);
            taxWrappers.add(taxWrapper);
        }
        return taxWrappers;
    }

    public int countAllTax() {
        return taxRepository.findAllByDeletedFalse().size();
    }

    @Transactional(rollbackOn = Throwable.class)
    public void deleteTax(Tax tax) {
        tax.setDeleted(true);
        taxRepository.save(tax);
    }

    public Tax findTaxById(long taxId) {
        return taxRepository.findOne(taxId);
    }

}
