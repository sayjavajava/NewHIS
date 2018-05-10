package com.sd.his.repositiories;

import com.sd.his.model.ClinicalDepartment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author    : Arif Heer
 * @Date      : 4/10/2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer    Date       Version  Operation  Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.repositiories
 * @FileName  : ClinicalDepartmentRepository
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public interface ClinicalDepartmentRepository extends JpaRepository<ClinicalDepartment, Long> {

    List<ClinicalDepartment> findAllByActiveTrueAndDeletedFalseOrderByNameAsc(Pageable pageable);

    List<ClinicalDepartment> findAllByActiveTrueAndDeletedFalse();

    ClinicalDepartment findByIdAndActiveTrueAndDeletedFalse(long id);

    List<ClinicalDepartment> findByNameContainingAndActiveTrueAndDeletedFalse(Pageable pageable, String name);

    List<ClinicalDepartment> findByNameContainingAndActiveTrueAndDeletedFalse(String name);

    ClinicalDepartment findByName(String name);

    ClinicalDepartment findById(long id);

}
