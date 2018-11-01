package com.sd.his.repository;

import com.sd.his.model.LabTestSpeciman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestSpecimanRepository extends JpaRepository<LabTestSpeciman, Long> {

    @Query("SELECT new com.sd.his.model.LabTestSpeciman(labTestSpeciman) FROM LabTestSpeciman labTestSpeciman")
    List<LabTestSpeciman> getAll();
}
