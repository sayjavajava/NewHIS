package com.sd.his.repository;


import com.sd.his.model.TimeZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
 * @Package   : com.sd.his.repositories
 * @FileName  : TaxRepository
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Repository
public interface TimezoneRepository extends JpaRepository<TimeZone, Long> {

//    @Query("SELECT new com.sd.his.wrapper.TimezoneWrapper(t.id,t.countryCode,t.name) FROM TimeZone t")
//     List<TimezoneWrapper> findAllByCountryCode();
}
