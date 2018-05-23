package com.sd.his.repositiories;/*
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

import com.sd.his.model.Branch;
import com.sd.his.model.BranchUser;
import com.sd.his.response.BranchResponseWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Long>{

    @Query("SELECT b FROM Branch b INNER JOIN b.users up WHERE up.user.id = :userId")
    List<BranchUser> findByName(@Param("userId") long userId);

    Branch findByName(String name);

    Branch findById(long id);


    List<Branch> findAllByActiveTrueAndDeletedFalseOrderByNameAsc(Pageable pageable);

    @Query("SELECT new com.sd.his.response.BranchResponseWrapper(b.id,b.name, b.country,b.city,b.noOfRooms) FROM Branch b  WHERE b.active = TRUE AND b.deleted = FALSE")
    List<BranchResponseWrapper> findAllByNameAndActiveTrueAndDeletedFalse(Pageable pageable);

    List<BranchResponseWrapper> findByNameIgnoreCaseContainingAndActiveTrueAndDeletedFalse(String name, Pageable pageable);


}

