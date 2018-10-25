package com.sd.his.repository;

import com.sd.his.model.GeneralLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralLedgerRepository  extends JpaRepository<GeneralLedger, Long> {

    @Query("SELECT new com.sd.his.model.GeneralLedger(generalLedger) FROM GeneralLedger generalLedger")
    List<GeneralLedger> getAll();
}
