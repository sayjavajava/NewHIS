package com.sd.his.repository;

import com.sd.his.model.City;
import com.sd.his.wrapper.CityWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT new com.sd.his.wrapper.CityWrapper(c) " +
            "FROM City c " +
            "WHERE c.state.id=:id")
    List<CityWrapper> getAllCitiesByStateId(@Param("id") Long stateId);



    @Query("SELECT c FROM City c WHERE c.state.id=:id")
    List<City> getAllCitiesById(@Param("id") Long stateId);

    @Query("SELECT c FROM City c")
    List<City> findAllBy();


    @Query("SELECT t FROM City t where t.name = :name")
    City findTitleById(@Param("name") String id);


}
