package com.sd.his.repository;

import com.sd.his.model.TimeZone;
import com.sd.his.model.Zone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {



    //List<Zone> findAllBy();

}