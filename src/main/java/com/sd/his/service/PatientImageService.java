package com.sd.his.service;

import com.sd.his.model.PatientImageSetup;
import com.sd.his.repository.PatientImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientImageService {

    @Autowired
    PatientImageRepository patientImageRepository;

    public List<PatientImageSetup> getAll(){
        return patientImageRepository.findAll();
    }

    public void save(PatientImageSetup Setup){
        patientImageRepository.save(Setup);
    }

    public void deleteImage(long id){
        patientImageRepository.delete(id);
    }
    public void updateImage(PatientImageSetup Setup){
        patientImageRepository.save(Setup);
    }

}
