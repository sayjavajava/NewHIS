package com.sd.his.repositiories;

import com.sd.his.model.Tax;
import com.sd.his.model.User;
import com.sd.his.wrapper.PatientWrapper;
import com.sd.his.wrapper.UserWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

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
 * @Package   : com.sd.his.repositiories
 * @FileName  : PatientRepository
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Repository
public interface PatientRepository extends JpaRepository<User, Long> {

    /*@Query("SELECT NEW  com.sd.his.wrapper.PatientWrapper(u,u.profile,u.insurance) from User u where u.userType=:uType AND u.deleted=FALSE")
    List<PatientWrapper> findAllByDeletedFalse(Pageable pageable, @Param("uType") String uType);
    @Query("SELECT NEW  com.sd.his.wrapper.PatientWrapper(u,u.profile,u.insurance) from User u where u.userType=:uType AND u.deleted=FALSE")
    List<PatientWrapper> findAllByDeletedFalse(@Param("uType") String uType);
*/

    //    List<Tax> findAllByNameAndIdNotAndDeletedFalse(String name, long id);
//    Tax findByNameAndDeletedFalse(String name);
//    Tax findById(long id);
//
//    List<Tax> findAllByNameContainingAndDeletedFalse(String name, Pageable pageable);
//    List<Tax> findAllByNameContainingAndDeletedFalse(String name);

}
