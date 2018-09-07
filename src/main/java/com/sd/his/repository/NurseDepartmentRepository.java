package com.sd.his.repository;

import com.sd.his.model.Department;
import com.sd.his.model.Nurse;
import com.sd.his.model.NurseDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseDepartmentRepository extends JpaRepository<NurseDepartment, Long> {
    void deleteAllByNurse(Nurse nurse);
    void deleteAllByNurse_Id(Long nurseId);
    @Query("SELECT nd.department FROM NurseDepartment nd inner join nd.nurse n where n.id=:nurseId")
    List<Department> getNurseDepartments(@Param("nurseId") Long nurseId);
}
