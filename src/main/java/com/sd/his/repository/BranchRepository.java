package com.sd.his.repository;

import com.sd.his.model.Branch;

import com.sd.his.wrapper.response.BranchResponseWrapper;
import org.springframework.data.domain.Pageable;
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
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Branch findByName(String name);

    Branch findByNameAndIdNot(String name,Long id);

    @Query("SELECT new com.sd.his.wrapper.response.BranchResponseWrapper(b.id,b.name, b.country,b.city,b.noOfRooms,bb.firstName) FROM Branch b LEFT OUTER JOIN b.branchDoctors bu LEFT OUTER JOIN bu.doctor bb WHERE bu.primaryBranch = TRUE ")
    List<BranchResponseWrapper> findAllByActive(Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.response.BranchResponseWrapper(b) FROM Branch b WHERE b.id =:id ")
    BranchResponseWrapper findAllById(@Param("id") Long id);

    List<Branch> findAllByIdIn(List<Long> ids);

    @Query("SELECT new com.sd.his.wrapper.response.BranchResponseWrapper(b) FROM Branch b WHERE b.active = TRUE")
    List<BranchResponseWrapper> findAllByActiveTrue();
    Branch findBySystemBranchTrue();
    @Query("SELECT new com.sd.his.wrapper.response.BranchResponseWrapper(b.id,b.name, b.country,b.city,b.noOfRooms,bb.firstName) FROM Branch b LEFT OUTER JOIN b.branchDoctors bu LEFT OUTER JOIN bu.doctor bb WHERE b.active = TRUE and b.name LIKE CONCAT('%',:name,'%')")
    List<BranchResponseWrapper> findByNameAndBranchDepartments(@Param("name")String name ,Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.response.BranchResponseWrapper(br,bdd.id,bdd.firstName,bdd.lastName) from Branch br INNER JOIN br.branchDoctors bd inner join bd.doctor bdd")
    List<BranchResponseWrapper> findByBranchAndBranchDoctors();

    //  List<Branch> findByNameIgnoreCaseContainingAndActiveTrueOrBranchDepartments_department_nameIgnoreCaseContaining(String name, String department, Pageable pageable);



 /*   Branch findByIdAndDeletedFalse(long id);

    @Query("SELECT new com.sd.his.response.BranchResponseWrapper(b.id,b.name, b.country,b.city,b.noOfRooms,pu.username) FROM Branch b INNER JOIN b.users bu JOIN bu.user pu WHERE b.active = TRUE AND b.deleted = FALSE")
    List<BranchResponseWrapper> findAllByName(Pageable pageable);

    List<Branch> findByNameIgnoreCaseContainingAndActiveTrueAndDeletedFalseOrClinicalDepartments_clinicalDpt_nameIgnoreCaseContaining(String name, String department, Pageable pageable);

    List<Branch> findAllByActiveTrueAndDeletedFalseOrderByNameAsc(Pageable pageable);

    List<Branch> findAllByActiveTrueAndDeletedFalse();

    @Query("SELECT new com.sd.his.response.BranchResponseWrapper(b.id,b.name, b.country,b.city,b.noOfRooms) FROM Branch b  WHERE b.active = TRUE AND b.deleted = FALSE")
    List<BranchResponseWrapper> findBranchWrapperAllByActiveTrueAndDeletedFalse();

  ;
*/
}

