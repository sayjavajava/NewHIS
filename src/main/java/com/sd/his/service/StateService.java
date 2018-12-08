package com.sd.his.service;

import com.sd.his.model.State;
import com.sd.his.repository.StateRepository;
import com.sd.his.wrapper.StateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Service
@Transactional
public class StateService {
    @Autowired
    private StateRepository stateRepository;


    public List<StateWrapper> getAllStates(){
        return this.stateRepository.getAllStates();
    }

    public List<StateWrapper> getAllStatesByCountryId(long countryId){
        return this.stateRepository.getAllStatesByCountry(countryId);
    }


    public State getStateById(long stateId){
        return this.stateRepository.getOne(stateId);
    }

}
