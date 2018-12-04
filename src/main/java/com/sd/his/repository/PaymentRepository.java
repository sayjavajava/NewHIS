package com.sd.his.repository;

import com.sd.his.model.Payment;
import com.sd.his.wrapper.response.ReceiptListResponseWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository  extends JpaRepository<Payment, Long> {


    @Query(" SELECT NEW com.sd.his.wrapper.response.ReceiptListResponseWrapper(pmt) FROM Payment pmt ")
    List<ReceiptListResponseWrapper> findAllReceipt();
}
