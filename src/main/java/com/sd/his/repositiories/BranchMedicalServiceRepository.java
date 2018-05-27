package com.sd.his.repositiories;

import com.sd.his.model.BranchMedicalService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 15-May-18
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
 * @Package   : com.sd.his.repositiories
 * @FileName  : BranchMedicalServiceRepository
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
public interface BranchMedicalServiceRepository extends JpaRepository<BranchMedicalService, Long> {

    List<BranchMedicalService> findAllByBranch_IdAndMedicalService_Id(long branchId, long msId);


    BranchMedicalService findByMedicalService_Id(long aLong);
}
