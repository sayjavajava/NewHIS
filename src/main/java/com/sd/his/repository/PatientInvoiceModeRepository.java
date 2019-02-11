package com.sd.his.repository;

import com.sd.his.model.Invoice;
import com.sd.his.model.PatientInvoiceModePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientInvoiceModeRepository extends JpaRepository<PatientInvoiceModePayment, Long> {


}
