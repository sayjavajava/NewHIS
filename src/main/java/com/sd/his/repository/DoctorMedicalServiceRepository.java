package com.sd.his.repository;

import com.sd.his.model.DoctorMedicalService;
import com.sd.his.model.MedicalService;
import com.sd.his.wrapper.response.MedicalServicesDoctorWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorMedicalServiceRepository extends JpaRepository<DoctorMedicalService, Long> {
    @Query("SELECT dms.medicalService FROM DoctorMedicalService dms inner join dms.doctor d where dms.doctor.id=:id")
    List<MedicalService> getDoctorMedicalServices(@Param("id") Long id);

   // (Long dmsId, Long drId, String docFirstName, String docLastName, Long msId, String msName, String msDescription )
    @Query("SELECT NEW  com.sd.his.wrapper.response.MedicalServicesDoctorWrapper(dms.id ,dr.id ,dr.firstName,dr.lastName,ms.id,ms.name,ms.description,ms.duration)FROM DoctorMedicalService dms inner JOIN dms.doctor dr inner join  dms.medicalService ms where ms.status=TRUE ")
    List<MedicalServicesDoctorWrapper> findAllByDoctorAndServices();

    void deleteDoctorMedicalServiceByDoctor_Id(Long doctorId);
}
