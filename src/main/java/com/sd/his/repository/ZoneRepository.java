package com.sd.his.repository;

import com.sd.his.model.State;
import com.sd.his.model.TimeZone;
import com.sd.his.model.Zone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {




    @Query("SELECT t FROM Zone t where t.name = :name")
    Zone findZoneByName(@Param("name") String paraName);

}