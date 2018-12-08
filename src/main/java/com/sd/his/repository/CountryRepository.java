package com.sd.his.repository;

import com.sd.his.model.Country;
import com.sd.his.model.State;
import com.sd.his.wrapper.CountryWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("SELECT new com.sd.his.wrapper.CountryWrapper(c) FROM com.sd.his.model.Country c ")
    List<CountryWrapper> getAllCountries();


    @Query("SELECT c FROM Country c")
    List<Country> getAllBy();


    @Query("SELECT t FROM Country t where t.name = :name")
    Country findTitleById(@Param("name") String id);

    @Query("SELECT t FROM Country t where UPPER(t.name) like UPPER(:name) ")
    Country findByName(@Param("name") String name);
}
