package com.sd.his.repositiories;

import com.sd.his.model.Branch;
import com.sd.his.response.BranchResponseWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    Branch findByIdAndDeletedFalse(long id);

    @Query("SELECT new com.sd.his.response.BranchResponseWrapper(b.id,b.name, b.country,b.city,b.noOfRooms) FROM Branch b  WHERE b.active = TRUE AND b.deleted = FALSE")
    List<BranchResponseWrapper> findAllByNameAndActiveTrueAndDeletedFalse(Pageable pageable);

    List<Branch> findByNameIgnoreCaseContainingAndActiveTrueAndDeletedFalseOrClinicalDepartments_clinicalDpt_nameIgnoreCaseContaining(String name,String department, Pageable pageable);

    List<Branch> findAllByActiveTrueAndDeletedFalseOrderByNameAsc(Pageable pageable);

    @Query("SELECT new com.sd.his.response.BranchResponseWrapper(b.id,b.name, b.country,b.city,b.noOfRooms) FROM Branch b  WHERE b.active = TRUE AND b.deleted = FALSE")
    List<BranchResponseWrapper> findAllByActiveTrueAndDeletedFalse();
// findAllByUsernameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrRoles_role_nameIgnoreCaseContaining

    List<Branch> findAllByIdIn(List<Long> branches);
}

