package com.sd.his.repository;

import com.sd.his.model.Patient;
import com.sd.his.wrapper.reports.AdvancePaymentReportWrapper;
import com.sd.his.wrapper.PatientWrapper;
import com.sd.his.wrapper.reports.PatientPaymentReportWrapper;
import com.sd.his.wrapper.reports.RefundReceiptReportWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findAllByEmail(String email);

    @Query("SELECT new com.sd.his.wrapper.PatientWrapper(p.id, p.patientId, p.patientSSN, p.firstName,p.lastName,p.email,p.city,p.formattedAddress,p.cellPhone, p.patientGroup) FROM Patient p ")
    List<PatientWrapper> getAllByStatusTrue();

    @Query("SELECT new com.sd.his.wrapper.PatientWrapper(p) FROM Patient p ")
    List<PatientWrapper> getAll();

    @Query("SELECT p FROM com.sd.his.model.Patient p")
    List<Patient> getAllPaginatedPatients(Pageable pageable);

    @Query("SELECT p FROM Patient p WHERE ( lower( p.firstName ) LIKE concat('%',:searchString,'%') or lower( p.middleName ) LIKE concat('%',:searchString,'%') or lower( p.lastName ) LIKE concat('%',:searchString,'%') or p.cellPhone LIKE concat('%',:searchString,'%') ) order by p.firstName asc")
    List<Patient> searchPatientByNameOrCellNbr(Pageable pageable, @Param("searchString") String searchString);

    Optional<Patient> findById(Long id);

    @Query("SELECT p FROM Patient p WHERE p.patientId = :EMR ")
    Patient getByEMR(@Param("EMR") String patientEMR);

    @Query("SELECT p FROM Patient p WHERE ( lower( p.firstName ) LIKE lower(:firstName) and lower( p.lastName ) LIKE lower(:lastName) " +
            " and p.cellPhone LIKE :cellPhone and  DATE(p.dob) = DATE_FORMAT(:dob, '%Y-%m-%d') ) ")
    Patient findDuplicatePatientForBulkImport(@Param("firstName") String firstName, @Param("lastName") String lastName,
                                              @Param("cellPhone") String cellPhone, @Param("dob") Date dob);

    @Query("SELECT new com.sd.his.wrapper.reports.RefundReceiptReportWrapper(pr.refundId, p.firstName, p.middleName, p.lastName, pt.paymentMode, pr.updatedOn, " +
            " p.patientId, pr.refundType, COALESCE(inv.totalInvoiceAmount, p.advanceBalance), pr.refundAmount ) " +
            " FROM com.sd.his.model.PatientRefund pr LEFT JOIN pr.patient p LEFT JOIN pr.invoice inv  JOIN pr.paymentType pt " +
            " WHERE pr.refundId = :id ")
    RefundReceiptReportWrapper getOneRefundData(@Param("id") String refundId);

    @Query("SELECT new com.sd.his.wrapper.reports.AdvancePaymentReportWrapper(pt.paymentId, p.firstName, p.middleName, p.lastName, pip.updatedOn, " +
            " p.patientId, pt.transactionType, pt.paymentAmount) " +
            " FROM com.sd.his.model.PatientInvoicePayment pip JOIN pip.payment pt JOIN pip.patient p " +
            " WHERE pt.paymentId = :paymentId ")
    AdvancePaymentReportWrapper getOneAdvancePaymentData(@Param("paymentId") String paymentId);

    /*
    PatientPaymentReportWrapper(String paymentId, String firstName, String middleName, String lastName, String patientEMR,
                                       Date paymentDate, String invoiceId, Double invoiceAmount,
                                       Double paidAmount, Double discountAmount, Double refund, Double advance)
     */
    @Query("SELECT new com.sd.his.wrapper.reports.PatientPaymentReportWrapper(pip.payment.paymentId, pip.patient.firstName," +
            " pip.patient.middleName, pip.patient.lastName, pip.patient.patientId, pip.updatedOn, pip.invoice.invoiceId, pip.invoice.invoiceAmount," +
            " pip.invoice.paidAmount, pip.invoice.discountAmount, pip.advanceAmount) " +
            " FROM com.sd.his.model.PatientInvoicePayment pip WHERE pip.invoice.invoiceId = :id ")
    PatientPaymentReportWrapper getOneInvoicePaymentData(@Param("id") String invoiceId);
}
