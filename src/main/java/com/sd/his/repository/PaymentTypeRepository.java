package com.sd.his.repository;

import com.sd.his.model.PaymentType;
import com.sd.his.wrapper.response.PaymentTypeWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PaymentTypeRepository  extends JpaRepository<PaymentType,Long> {

    @Query("SELECT pt FROM PaymentType pt")
    List<PaymentType> getAllBy();

    List<PaymentType> findAll();


    @Query("SELECT new com.sd.his.wrapper.response.PaymentTypeWrapper(pt) FROM PaymentType pt")
    List<PaymentTypeWrapper> getAllWithWrapper();
}
