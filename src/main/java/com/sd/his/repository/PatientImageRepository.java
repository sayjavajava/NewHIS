package com.sd.his.repository;

import com.sd.his.model.PatientImageSetup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.sd.his.model.VitalSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientImageRepository  extends JpaRepository<PatientImageSetup, Long> {

  //  PatientImageSetup findByName(String name);



     //List<PatientImageSetup> getAll();
}
