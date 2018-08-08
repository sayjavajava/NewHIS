package com.sd.his.repository;

import com.sd.his.model.Appointment;
import com.sd.his.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
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
 * @Package   : com.sd.his.*
 * @FileName  : UserAuthAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


}

