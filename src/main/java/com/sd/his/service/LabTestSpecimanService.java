package com.sd.his.service;

import com.sd.his.model.LabTestSpeciman;
import com.sd.his.repository.LabTestSpecimanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabTestSpecimanService {

    @Autowired
    LabTestSpecimanRepository labTestSpecimanRepository;

    public List<LabTestSpeciman> getAll(){
        return labTestSpecimanRepository.getAll();
    }

    public void saveConfiguration(LabTestSpeciman labTestSpeciman){
        labTestSpecimanRepository.save(labTestSpeciman);
    }
}
