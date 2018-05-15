package com.sd.his.repositiories;

import com.sd.his.model.MedicalService;
import com.sd.his.wrapper.MedicalServiceWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 15-May-2018
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
 * @FileName  : MedicalServicesRepository
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public interface MedicalServicesRepository extends JpaRepository<MedicalService, Long> {

    @Query("SELECT new com.sd.his.wrapper.MedicalServiceWrapper(ms.id, ms.title, ms.fee, ms.cost, ms.status, b.id, b.name, cd.id, cd.name, t.id, t.rate) " +
            "FROM MedicalService ms JOIN ms.departments cdms JOIN cdms.clinicalDpt cd JOIN ms.branches bms JOIN bms.branch b JOIN ms.tax t ")
    List<MedicalServiceWrapper> findAllPaginated(Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.MedicalServiceWrapper(ms.id, ms.title, ms.fee, ms.cost, ms.status, b.id, b.name, cd.id, cd.name, t.id, t.rate) " +
            "FROM MedicalService ms JOIN ms.departments cdms JOIN cdms.clinicalDpt cd JOIN ms.branches bms JOIN bms.branch b JOIN ms.tax t " +
            "WHERE ms.title = :title AND b.id = :branchId AND cd.id = :dptId AND ms.status = TRUE")
    MedicalServiceWrapper findOneByTitleAndDptAndBranch(@Param("title") String title, @Param("branchId") long branchId, @Param("dptId") long dptId);

    @Query("SELECT new com.sd.his.wrapper.MedicalServiceWrapper(ms.id, ms.title, ms.fee, ms.cost, ms.status, b.id, b.name, cd.id, cd.name, t.id, t.rate) " +
            "FROM MedicalService ms JOIN ms.departments cdms JOIN cdms.clinicalDpt cd JOIN ms.branches bms JOIN bms.branch b JOIN ms.tax t ")
    List<MedicalServiceWrapper> findAllMedicalServiceWrappers();

    MedicalService findByTitleAndStatusTrueAndDeletedFalse(String title);
}
