package com.sd.his.repository;

import com.sd.his.model.BranchMedicalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jamal on 7/31/2018.
 */
@Repository
public interface BranchMedicalServiceRepository extends JpaRepository<BranchMedicalService,Long> {

    void deleteByMedicalService_id(long medicalServiceId);
    BranchMedicalService findAllByBranch_IdAndMedicalService_Id(long branchId, long msId);

    BranchMedicalService findByMedicalService_Id(long aLong);
}
