package com.sd.his.repository;

import com.sd.his.model.Invoice;
import com.sd.his.wrapper.response.InvoiceResponseWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientInvoiceRepository  extends JpaRepository<Invoice, Long> {

    Invoice findByAppointmentId(Long id);

    @Query(" SELECT NEW com.sd.his.wrapper.response.InvoiceResponseWrapper(invc) " +
           " FROM Invoice invc ")
    List<InvoiceResponseWrapper> findAllInvoices();

    @Query("SELECT NEW com.sd.his.wrapper.response.InvoiceResponseWrapper( ( SUM(invc.invoiceAmount)-SUM(invc.paidAmount) ), SUM(invc.invoiceAmount), SUM(invc.paidAmount), SUM(p.advanceBalance) ) "+
            "FROM Invoice invc inner join invc.patient p WHERE p.id=:patientId ")
    InvoiceResponseWrapper getPatientInvoicesBalance(@Param("patientId") Long patientId);
}
