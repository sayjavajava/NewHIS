package com.sd.his.repository;

import com.sd.his.model.*;
import com.sd.his.wrapper.InsuranceProfileWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface InsuranceProfileRepository extends JpaRepository<InsuranceProfile, Long> {


    @Query(" SELECT profile " +
            "FROM  com.sd.his.model.InsuranceProfile profile " +
            "WHERE profile.status=1")
    List<InsuranceProfile> findAllByCreatedOnNotNull();

    InsuranceProfile findByName(String name);


    InsuranceProfile  findById(long id);
}

