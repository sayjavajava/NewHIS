package com.sd.his.service;

import com.sd.his.model.Tax;
import com.sd.his.repositiories.AppointmentRepository;
import com.sd.his.repositiories.TaxRepository;
import com.sd.his.request.SaveTaxRequest;
import com.sd.his.utill.APIUtil;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.AppointmentWrapper;
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
 * @Date      : 08-Jun-18
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
 * @FileName  : AppointmentService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    public List<AppointmentWrapper> findAllPaginatedAppointments(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return appointmentRepository.findAllPaginatedAppointments(pageable);
    }

    public int countAllAppointments() {
        return appointmentRepository.findAll().size();
    }

}
