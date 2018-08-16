package com.sd.his.repository;

import com.sd.his.model.Doctor;
import com.sd.his.model.DoctorMedicalService;
import com.sd.his.model.MedicalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorMedicalServiceRepository extends JpaRepository<DoctorMedicalService, Long> {
    @Query("SELECT dms.medicalService FROM DoctorMedicalService dms inner join dms.doctor d where dms.doctor.id=:id")
    List<MedicalService> getDoctorMedicalServices(@Param("id") Long id);

    void deleteDoctorMedicalServiceByDoctor_Id(Long doctorId);
}
