package com.sd.his.repository;

import com.sd.his.model.Country;
import com.sd.his.wrapper.CountryWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("SELECT new com.sd.his.wrapper.CountryWrapper(c) FROM com.sd.his.model.Country c ")
    List<CountryWrapper> getAllCountries();
}
