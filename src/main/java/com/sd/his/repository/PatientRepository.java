package com.sd.his.repository;

import com.sd.his.model.Patient;
import com.sd.his.wrapper.PatientWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findAllByEmail(String email);

    @Query("SELECT new com.sd.his.wrapper.PatientWrapper(p.id, p.patientId, p.patientSSN, p.firstName,p.lastName,p.email,p.city,p.formattedAddress,p.cellPhone) FROM Patient p ")
    List<PatientWrapper> getAllByStatusTrue();

    @Query("SELECT p FROM com.sd.his.model.Patient p")
    List<Patient> getAllPaginatedPatients(Pageable pageable);

    @Query("SELECT p FROM Patient p WHERE ( lower( p.firstName ) LIKE concat('%',:searchString,'%') or lower( p.middleName ) LIKE concat('%',:searchString,'%') or lower( p.lastName ) LIKE concat('%',:searchString,'%') or p.cellPhone LIKE concat('%',:searchString,'%') ) order by p.firstName asc")
    List<Patient> searchPatientByNameOrCellNbr(Pageable pageable, @Param("searchString") String searchString);

    Optional<Patient> findById(Long id);
}
