package com.sd.his.service;

import com.sd.his.repository.CityRepository;
import com.sd.his.wrapper.CityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Service
@Transactional
public class CityService  {

    @Autowired
    private CityRepository cityRepository;


    public List<CityWrapper> getCitiesByStateId(long stateId){
        return this.cityRepository.getAllCitiesByStateId(stateId);
    }
}
