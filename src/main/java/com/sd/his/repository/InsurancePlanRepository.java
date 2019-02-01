package com.sd.his.repository;

import com.sd.his.model.Insurance;
import com.sd.his.model.InsurancePlan;
import com.sd.his.model.InsuranceProfile;
import com.sd.his.wrapper.ICDVersionWrapper;
import com.sd.his.wrapper.InsuranceProfileWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Long> {


    @Query("SELECT new com.sd.his.wrapper.InsuranceProfileWrapper(plan.id,plan.name,plan.status,plan.description) " +
            "FROM com.sd.his.model.InsurancePlan plan " +
            "WHERE plan.status=1")
    List<InsuranceProfileWrapper> findAllByCreatedOnNotNull();


    InsurancePlan findByName(String name);

    InsurancePlan  findById(long id);


}