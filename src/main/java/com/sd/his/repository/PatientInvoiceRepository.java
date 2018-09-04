package com.sd.his.repository;

import com.sd.his.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientInvoiceRepository  extends JpaRepository<Invoice, Long> {

    Invoice findByAppointmentId(Long id);

}
