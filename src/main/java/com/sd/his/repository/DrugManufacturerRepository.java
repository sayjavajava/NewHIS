package com.sd.his.repository;

import com.sd.his.model.DrugManufacturer;
import com.sd.his.wrapper.DrugManufacturerWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DrugManufacturerRepository extends JpaRepository<DrugManufacturer, Long> {

    @Query("SELECT new com.sd.his.wrapper.DrugManufacturerWrapper(dm) FROM DrugManufacturer dm ")
    List<DrugManufacturerWrapper> getAllDrugManufacturers();

    @Query("SELECT new com.sd.his.wrapper.DrugManufacturerWrapper(dm) FROM DrugManufacturer dm WHERE dm.id = :id ")
    DrugManufacturerWrapper getDrugManufacturerById(@Param("id") Long id);
}
