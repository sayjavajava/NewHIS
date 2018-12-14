package com.sd.his.service;

import com.sd.his.enums.InvoiceStatusEnum;
import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.wrapper.request.*;
import com.sd.his.wrapper.response.InvoiceItemsResponseWrapper;
import com.sd.his.wrapper.response.InvoiceResponseWrapper;
import com.sd.his.wrapper.response.ReceiptListResponseWrapper;
import com.sd.his.wrapper.response.RefundListResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientInvoiceService {

    @Autowired
    private PatientInvoiceRepository patientInvoiceRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    HISUtilService hisUtilService;
    @Autowired
    private InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PatientInvoicePaymentRepository patientInvoicePaymentRepository;

    @Autowired
    private MedicalServiceRepository medicalServiceRepository;

    @Autowired
    private PatientRefundRepository patientRefundRepository;

    @Autowired
    PaymentTypeRepository  paymentTypeRepository;


    @Autowired
    ReceiptPaymentTypeRepository receiptPaymentTypeRepository;



    public Invoice getInvoiceById(Long id){
       return patientInvoiceRepository.findOne(id);
    }

    @Transactional
    public void saveInvoice(GenerateInvoiceRequestWrapper createInvoiceRequest)
    {
        Appointment appointment =appointmentRepository.findOne(Long.parseLong(createInvoiceRequest.getInvoiceRequestWrapper().get(0).getAppointmentId()));

        Invoice invoice = patientInvoiceRepository.findByAppointmentId(appointment.getId());
//        invoice.setCompleted(createInvoiceRequest.getCompleted());

        if(invoice == null)
        {
            invoice = new Invoice();
            invoice.setAppointment(appointment);
            invoice.setPatient(patientRepository.findOne(Long.parseLong(createInvoiceRequest.getInvoiceRequestWrapper().get(0).getPatientId())));
            invoice.setInvoiceId(hisUtilService.getPrefixId(ModuleEnum.INVOICE));
            invoice.setCreatedOn(new Date());
            invoice.setUpdatedOn(new Date());
            invoice.setPaidAmount(0.0);
            invoice.setStatus(InvoiceStatusEnum.PENDING.toString());

            patientInvoiceRepository.save(invoice);
        }

        List<Long> ids = createInvoiceRequest.getInvoiceRequestWrapper().stream().filter(x->x.getId()!=null).map(PatientInvoiceRequestWrapper::getId).collect(Collectors.toList());
    //    invoiceItemsRepository.deleteInvoiceItem(ids);

        if(ids.size()>0){
            deleteRemoveInviceItems(ids,invoice.getId());
        }


        double amount =0.00;
        double totalAmount =0.00;
        double taxAmount =0.00;
        double discountAmount = 0.00;
        double ivoiceTotal =0.00;
        double discountTotal =0.00;
        double taxTotal =0.00;
        for(PatientInvoiceRequestWrapper pInvc : createInvoiceRequest.getInvoiceRequestWrapper())
        {
            InvoiceItems invItems ;
            if(pInvc.getId()==null){
                invItems = new InvoiceItems();
            }else{
                invItems =invoiceItemsRepository.findOne(pInvc.getId());
            }
            invItems.setCode(pInvc.getCode());
            invItems.setDescription(pInvc.getDescription());
            invItems.setDiscountRate(pInvc.getDiscountRate());
            invItems.setTaxRate(pInvc.getTaxRate());
            invItems.setQuantity(pInvc.getQuantity());
            invItems.setServiceName(pInvc.getServiceName());
            invItems.setUnitFee(pInvc.getUnitFee());
            invItems.setCreatedOn(new Date());
            invItems.setUpdatedOn(new Date());
            invItems.setInvoice(invoice);
            invoiceItemsRepository.save(invItems);

            amount = invItems.getQuantity() * invItems.getUnitFee();
            taxAmount = (invItems.getQuantity() * invItems.getUnitFee()) * invItems.getTaxRate()/100;
            discountAmount =(invItems.getQuantity() * invItems.getUnitFee()) * invItems.getDiscountRate()/100;
            discountTotal += discountAmount;
            taxTotal += taxAmount;
            ivoiceTotal += amount;
            totalAmount += amount + taxAmount - discountAmount;
        }

        invoice.setDiscountAmount(discountTotal);
        invoice.setTaxAmount(taxTotal);
        invoice.setInvoiceAmount(ivoiceTotal);
        invoice.setTotalInvoiceAmount(totalAmount);
        patientInvoiceRepository.save(invoice);
    }


// Save Payment
    @Transactional
    public void savePayment(PaymentRequestWrapper paymentRequest)
    {
        Invoice invoice = patientInvoiceRepository.findOne(paymentRequest.getId());

        if(invoice != null)
        {
            double advanceCredit = 0.00;
            if(paymentRequest.getUseAdvancedBal()){
                advanceCredit = paymentRequest.getUsedAdvanceDeposit() == null ? 0D : paymentRequest.getUsedAdvanceDeposit();
            }
            double receivedAmount = (invoice.getPaidAmount()== null? 0.00 : invoice.getPaidAmount()) + paymentRequest.getPaidAmount() + advanceCredit + paymentRequest.getDiscountAmount();

            Patient patient = patientRepository.findOne(invoice.getPatient().getId());
            double advanceConsumed = (patient.getAdvanceBalance() == null ? 0D : patient.getAdvanceBalance()) - advanceCredit;
            if(receivedAmount >= invoice.getInvoiceAmount())
            {
                invoice.setStatus(InvoiceStatusEnum.CLOSE.toString());
            //    patient.setAdvanceBalance(advanceDeposit);
                patient.setAdvanceBalance(advanceConsumed);
                patientRepository.save(patient);
                receivedAmount = invoice.getInvoiceAmount();
            }else if(paymentRequest.getUseAdvancedBal() && paymentRequest.getUsedAdvanceDeposit() > 0){
                patient.setAdvanceBalance(advanceConsumed);
                patientRepository.save(patient);
            }

            invoice.setPaidAmount(receivedAmount);
            invoice.setUpdatedOn(new Date());
            patientInvoiceRepository.save(invoice);

            Payment payment = new Payment();
            payment.setCreatedOn(new Date());
            payment.setUpdatedOn(new Date());
            payment.setPaymentId(hisUtilService.getPrefixId(ModuleEnum.PAYMENT));
            payment.setPaymentAmount(paymentRequest.getPaidAmount());
    //        payment.setPaymentAmount((paymentRequest.getPaidAmount()+ advanceCredit));
            paymentRepository.save(payment);

            PatientInvoicePayment patientInvoicePayment = new PatientInvoicePayment();
            patientInvoicePayment.setCreatedOn(new Date());
            patientInvoicePayment.setUpdatedOn(new Date());
            patientInvoicePayment.setPaymentAmount(paymentRequest.getPaidAmount());
    //        patientInvoicePayment.setPaymentAmount((paymentRequest.getPaidAmount()+ advanceCredit) >= invoice.getInvoiceAmount()? invoice.getInvoiceAmount() : paymentRequest.getPaidAmount()+advanceCredit);
            patientInvoicePayment.setInvoice(invoice);
            patientInvoicePayment.setPayment(payment);
            patientInvoicePayment.setPatient(invoice.getPatient());

            patientInvoicePayment.setDiscountAmount(paymentRequest.getDiscountAmount());
            patientInvoicePayment.setAdvanceAmount(paymentRequest.getUsedAdvanceDeposit());
            patientInvoicePaymentRepository.save(patientInvoicePayment);
        }
    }



    // Save Advance Payment
    // Added By : Naeem Saeed
    @Transactional
    public void saveAdvancePayment(AdvancePaymentRequestWrapper advancePaymentRequestWrapper)
    {
        double advanceDeposit = 0.00;

        Patient patient = patientRepository.findOne(advancePaymentRequestWrapper.getPatientId());
        advanceDeposit = patient.getAdvanceBalance() + advancePaymentRequestWrapper.getAmount();
        patient.setAdvanceBalance(advanceDeposit);
        patientRepository.save(patient);

        Payment payment = new Payment();
        payment.setCreatedOn(new Date());
        payment.setUpdatedOn(new Date());
        payment.setPaymentId(advancePaymentRequestWrapper.getPaymentId());
    //    payment.setPaymentId(hisUtilService.getPrefixId(ModuleEnum.PAYMENT));  // To Do  (handle this from front end)
        payment.setPaymentAmount(advancePaymentRequestWrapper.getAmount());
        payment.setTransactionType("Advance");
        paymentRepository.save(payment);


        ReceiptPaymentType receiptPaymentType = new ReceiptPaymentType();
        receiptPaymentType.setPayment(payment);
        receiptPaymentType.setPaymentType(paymentTypeRepository.findOne(advancePaymentRequestWrapper.getPaymentTypeId()));
        receiptPaymentType.setPaymentAmount(advancePaymentRequestWrapper.getAmount());
        receiptPaymentTypeRepository.save(receiptPaymentType);

        PatientInvoicePayment patientInvoicePayment = new PatientInvoicePayment();
        patientInvoicePayment.setCreatedOn(new Date());
        patientInvoicePayment.setUpdatedOn(new Date());
        patientInvoicePayment.setPaymentAmount(advancePaymentRequestWrapper.getAmount()) ;
        patientInvoicePayment.setPayment(payment);
        patientInvoicePayment.setPatient(patient);
        patientInvoicePayment.setAdvanceAmount(0.00);
        patientInvoicePayment.setDiscountAmount(0.00);
        patientInvoicePaymentRepository.save(patientInvoicePayment);

    }

    public String getPaymentPrefixId(){
        return hisUtilService.getPrefixId(ModuleEnum.PAYMENT);
    }


    public String getRefundPrefixId(){
        return hisUtilService.getPrefixId(ModuleEnum.REFUND);
    }

    public void refundPayment(RefundPaymentRequestWrapper refundPaymentRequestWrapper)
    {
        double advanceDeposit = 0.00;

        PatientRefund patientRefund = new PatientRefund();
        Patient patient = patientRepository.findOne(refundPaymentRequestWrapper.getPatientId());
        if(refundPaymentRequestWrapper.getRefundType().equalsIgnoreCase("Advance")){
            advanceDeposit = patient.getAdvanceBalance() - refundPaymentRequestWrapper.getRefundAmount();
            patient.setAdvanceBalance(advanceDeposit);
            patientRepository.save(patient);
        }else{
            Invoice invoice = patientInvoiceRepository.findOne(Long.parseLong(refundPaymentRequestWrapper.getInvoiceId()));
            invoice.setPaidAmount(invoice.getPaidAmount() - refundPaymentRequestWrapper.getRefundAmount());
            if(invoice.getPaidAmount()<1){
                invoice.setStatus(InvoiceStatusEnum.REFUND.toString());
            }
            patientInvoiceRepository.save(invoice);
            patientRefund.setInvoice(invoice);
        }


        patientRefund.setRefundId(refundPaymentRequestWrapper.getRefundId());
        patientRefund.setCreatedOn(new Date());
        patientRefund.setUpdatedOn(new Date());
        patientRefund.setPatient(patient);
        patientRefund.setRefundType(refundPaymentRequestWrapper.getRefundType());
        patientRefund.setPaymentType(paymentTypeRepository.findOne(Long.parseLong(refundPaymentRequestWrapper.getPaymentTypeId())));

        patientRefund.setDescription(refundPaymentRequestWrapper.getDescription());
        patientRefund.setRefundAmount(refundPaymentRequestWrapper.getRefundAmount());
        patientRefundRepository.save(patientRefund);

    }

    // Save Bulk Payment
    @Transactional
    public void saveBulkPayment(BulkReceitRequestWrapper bulkReceitRequestWrapper)
    {
        double appliedAmount = bulkReceitRequestWrapper.getPaymentAmount();
        double advanceCredit = bulkReceitRequestWrapper.getUseAdvanceTotal();

        if(appliedAmount > 0 || advanceCredit > 0)
        {
            if(bulkReceitRequestWrapper.isUseAdvance() && advanceCredit > 0)
            {
                Patient patient = patientRepository.findOne(bulkReceitRequestWrapper.getPatientId());
                double advanceDeposit = patient.getAdvanceBalance() - advanceCredit;
                patient.setAdvanceBalance(advanceDeposit);
                patientRepository.save(patient);
            }

            Payment payment = new Payment();
            payment.setCreatedOn(new Date());
            payment.setUpdatedOn(new Date());
            payment.setPaymentId(hisUtilService.getPrefixId(ModuleEnum.PAYMENT));
            payment.setPaymentAmount(bulkReceitRequestWrapper.getPaymentAmount());
            payment.setTransactionType("Invoice");
            paymentRepository.save(payment);


            ReceiptPaymentType receiptPaymentType = new ReceiptPaymentType();
            receiptPaymentType.setPayment(payment);
            receiptPaymentType.setPaymentType(paymentTypeRepository.findOne(bulkReceitRequestWrapper.getPaymentTypeId()));
            receiptPaymentType.setPaymentAmount(bulkReceitRequestWrapper.getPaymentAmount());
            receiptPaymentTypeRepository.save(receiptPaymentType);

            for(InvoiceResponseWrapper iRW:bulkReceitRequestWrapper.getInvoiceListPaymentRequest())
            {
                Invoice invoice = patientInvoiceRepository.findOne(iRW.getId());
                if(invoice != null && iRW.isSelected())
                {
                    invoice.setPaidAmount(invoice.getPaidAmount() + iRW.getAdvanceBalance() + iRW.getAppliedAmount());

                    if(invoice.getPaidAmount()>= invoice.getTotalInvoiceAmount())
                    {
                        invoice.setStatus(InvoiceStatusEnum.CLOSE.toString());
                    }
                    invoice.setUpdatedOn(new Date());
                    patientInvoiceRepository.save(invoice);

                    PatientInvoicePayment patientInvoicePayment = new PatientInvoicePayment();
                    patientInvoicePayment.setCreatedOn(new Date());
                    patientInvoicePayment.setUpdatedOn(new Date());
            //        patientInvoicePayment.setPaymentAmount(iRW.getAdvanceBalance() + iRW.getAppliedAmount());
                    patientInvoicePayment.setPaymentAmount(iRW.getAppliedAmount());
                    patientInvoicePayment.setInvoice(invoice);
                    patientInvoicePayment.setPayment(payment);
                    patientInvoicePayment.setPatient(invoice.getPatient());

                    patientInvoicePayment.setDiscountAmount(iRW.getDiscountOnPayment());
                    patientInvoicePayment.setAdvanceAmount(iRW.getAdvanceBalance());
                    patientInvoicePaymentRepository.save(patientInvoicePayment);
                }
            }
        }
    }


    @Transactional
    public void deleteRemoveInviceItems(List<Long> ids,Long invID) {
        invoiceItemsRepository.deleteRemoveInviceItems(ids,invID);
    }

    public List<InvoiceItemsResponseWrapper> getInvoiceItemsById(long id){
        return invoiceItemsRepository.findAllByInvoiceId(patientInvoiceRepository.findByAppointmentId(id)!=null ? patientInvoiceRepository.findByAppointmentId(id).getId() : null);
    }




    // Get Patient ALl Invoice List
    // Added By : Naeem Saeed
    public List<InvoiceResponseWrapper> getInvoiceListByPatientId(long id){
        return patientInvoiceRepository.findAllByPatientId(id);
    }


    // Get Patient ALl Invoice List
    // Added By : Naeem Saeed
    public List<ReceiptListResponseWrapper> getReceiptList(){
        return paymentRepository.findAllReceipt();
    }


    public List<RefundListResponseWrapper> getRefundList(){
        return patientRefundRepository.findAllRefund();
    }


    public void generateInvoiceOnCheckIn(long id)
    {
        Appointment appointment = appointmentRepository.findOne(id);
        Invoice invoice = patientInvoiceRepository.findByAppointmentId(appointment.getId());
        if(invoice == null)
        {
            invoice = new Invoice();
            invoice.setAppointment(appointment);
            invoice.setPatient(appointment.getPatient());
            invoice.setInvoiceId(hisUtilService.getPrefixId(ModuleEnum.INVOICE));
            invoice.setCreatedOn(new Date());
            invoice.setUpdatedOn(new Date());
            invoice.setPaidAmount(0.0);
            invoice.setDiscountAmount(0.0);
            invoice.setTaxAmount(0.00);
            invoice.setStatus(InvoiceStatusEnum.PENDING.toString());
            invoice.setCompleted(false);

            patientInvoiceRepository.save(invoice);

            double amount =0.00;
            double taxAmount =0.00;
            double discountAmount = 0.00;
            double ivoiceTotal =0.00;

            MedicalService medicalService =medicalServiceRepository.findOne(appointment.getMedicalService().getId()); // TO DO  --- take id from appointment
            if(medicalService!=null){
                InvoiceItems invItems = new InvoiceItems();
                invItems.setCode(medicalService.getCode());
                invItems.setDescription(medicalService.getDescription());
                invItems.setDiscountRate(0.0);
                invItems.setTaxRate(medicalService.getTax().getRate());
                invItems.setQuantity(1);  // TO DO
                invItems.setServiceName(medicalService.getName());
                invItems.setUnitFee(medicalService.getFee());
                invItems.setCreatedOn(new Date());
                invItems.setUpdatedOn(new Date());
                invItems.setInvoice(invoice);
                invoiceItemsRepository.save(invItems);

                amount = invItems.getQuantity() * invItems.getUnitFee();
                taxAmount = (invItems.getQuantity() * invItems.getUnitFee()) * invItems.getTaxRate()/100;
                discountAmount =(invItems.getQuantity() * invItems.getUnitFee()) * invItems.getDiscountRate()/100;
                ivoiceTotal += amount + taxAmount - discountAmount;

                invoice.setTotalInvoiceAmount(ivoiceTotal);
                invoice.setInvoiceAmount(ivoiceTotal);
                patientInvoiceRepository.save(invoice);
            }
        }
    }

    public List<InvoiceResponseWrapper> getAllInvoice(){
        return patientInvoiceRepository.findAllInvoices();
    }

    public InvoiceResponseWrapper getPatientInvoicesBalance(Long patientId){
        return patientInvoiceRepository.getPatientInvoicesBalance(patientId);
    }
}
