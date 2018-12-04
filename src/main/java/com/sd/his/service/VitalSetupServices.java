package com.sd.his.service;

import com.sd.his.model.VitalSetup;
import com.sd.his.repository.VitalSetupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VitalSetupServices {

    @Autowired
    VitalSetupRepository vitalSetupRepository;

    public List<VitalSetup> getAll(){
        return vitalSetupRepository.getAll();
    }

    public void saveConfiguration(VitalSetup vitalSetup){
        vitalSetupRepository.save(vitalSetup);
    }

    public void deleteConfiguration(Long id){
        vitalSetupRepository.delete(id);
    }
}
