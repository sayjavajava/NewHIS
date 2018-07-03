package com.sd.his.repositories;

import com.sd.his.model.Insurance;
import com.sd.his.wrapper.InsuranceWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
 * @Package   : com.sd.his.repositories
 * @FileName  : InsuranceRepository
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    @Query("SELECT NEW com.sd.his.wrapper.InsuranceWrapper(ins) FROM Insurance ins where ins.id = :id")
    InsuranceWrapper getInsuranceWrapperById(@Param("id") long id);
}
