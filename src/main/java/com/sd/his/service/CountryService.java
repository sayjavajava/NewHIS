package com.sd.his.service;

import com.sd.his.model.Country;
import com.sd.his.model.Permission;
import com.sd.his.repository.CountryRepository;
import com.sd.his.wrapper.CountryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Service
@Transactional
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<CountryWrapper> getAllCountries() {
        return this.countryRepository.getAllCountries();
    }
    public Country getCountryById(Long id) {
        return this.countryRepository.findOne(id);
    }


    public List<Country> getAllCountriesBy() {
        return this.countryRepository.getAllBy();
    }
}
