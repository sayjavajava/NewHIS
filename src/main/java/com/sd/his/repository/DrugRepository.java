package com.sd.his.repository;

import com.sd.his.model.Drug;
import com.sd.his.wrapper.DrugWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 10/22/2018.
 */
@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {

    @Query("SELECT CASE WHEN COUNT (drug) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.Drug drug " +
            "WHERE drug.name=:name")
    boolean getDrugByName(@Param("name") String name);

    @Query("SELECT CASE WHEN COUNT (drug) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.Drug drug " +
            "WHERE drug.name=:name AND drug.id<>:id")
    boolean getDrugByNameAndNotEqualId(@Param("id") Long id, @Param("name") String name);

    @Query("SELECT new com.sd.his.wrapper.DrugWrapper(drug.id,drug.createdOn,drug.updatedOn,drug.name,drug.url,drug.strengthMin,drug.strengthMax," +
            "drug.oral,drug.frequency,drug.duration,drug.refill,drug.days,drug.sig,drug.notes,drug.active,false ) " +
            "FROM Drug drug")
    List<DrugWrapper> findAllByCreatedOn(Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.DrugWrapper(drug.id,drug.createdOn,drug.updatedOn,drug.name,drug.url,drug.strengthMin,drug.strengthMax," +
            "drug.oral,drug.frequency,drug.duration,drug.refill,drug.days,drug.sig,drug.notes,drug.active,false )" +
            "FROM com.sd.his.model.Drug drug " +
            "WHERE drug.id=:id")
    DrugWrapper getDrugById(@Param("id") Long drugId);

    @Query("SELECT new com.sd.his.wrapper.DrugWrapper(drug.id,drug.createdOn,drug.updatedOn,drug.name,drug.url,drug.strengthMin,drug.strengthMax," +
            "drug.oral,drug.frequency,drug.duration,drug.refill,drug.days,drug.sig,drug.notes,drug.active,false )" +
            "FROM com.sd.his.model.Drug drug " +
            "WHERE drug.name LIKE CONCAT('%',:name,'%') ")
    List<DrugWrapper> searchDrugByParams(Pageable pageable, @Param("name") String drugName);
}
