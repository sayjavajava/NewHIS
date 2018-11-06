package com.sd.his.repository;

import com.sd.his.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTypeRepository  extends JpaRepository<PaymentType,Long> {

    @Query("SELECT pt FROM PaymentType pt")
    List<PaymentTypeRepository> getAllBy();

    List<PaymentType> findAll();

}
