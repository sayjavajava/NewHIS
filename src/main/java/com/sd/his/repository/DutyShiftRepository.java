package com.sd.his.repository;

import com.sd.his.enums.DutyShiftEnum;
import com.sd.his.model.Branch;
import com.sd.his.model.BranchCashier;
import com.sd.his.model.Doctor;
import com.sd.his.model.DutyShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface DutyShiftRepository extends JpaRepository<DutyShift, Long> {

    void deleteAllByDoctor(Doctor doctor);

    DutyShift findByDoctorAndShiftName(Doctor doctor ,DutyShiftEnum name);
    DutyShift findByDoctor(Doctor doctor);

}

