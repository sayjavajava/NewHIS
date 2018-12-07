package com.sd.his.repository;

import com.sd.his.model.LabTest;
import com.sd.his.model.LabTestSpeciman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestSpecimanRepository extends JpaRepository<LabTestSpeciman, Long> {

    @Query("SELECT new com.sd.his.model.LabTestSpeciman(labTestSpeciman) FROM LabTestSpeciman labTestSpeciman")
    List<LabTestSpeciman> getAll();

    @Query("SELECT new com.sd.his.model.LabTestSpeciman(lts) FROM LabTestSpeciman lts " +
            " WHERE lts.testCode = :testCode AND lts.testName = :testName AND lts.minNormalRange = :minNormalRange " +
            " AND lts.maxNormalRange = :maxNormalRange ")
    LabTestSpeciman findDuplicateTestEntry(@Param("testCode") String testCode, @Param("testName") String testName,
                                           @Param("minNormalRange") String minNormalRange, @Param("maxNormalRange") String maxNormalRange);
}
