package com.sd.his.service;

import com.sd.his.enums.InvoiceStatusEnum;
import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.wrapper.request.PatientInvoiceRequestWrapper;
import com.sd.his.wrapper.request.PaymentRequestWrapper;
import com.sd.his.wrapper.response.InvoiceItemsResponseWrapper;
import com.sd.his.wrapper.response.InvoiceResponseWrapper;
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


    public Invoice getInvoiceById(Long id){
       return patientInvoiceRepository.findOne(id);
    }

    @Transactional
    public void saveInvoice(ArrayList<PatientInvoiceRequestWrapper> createInvoiceRequest)
    {
        Appointment appointment =appointmentRepository.findOne(Long.parseLong(createInvoiceRequest.get(0).getAppointmentId()));

        Invoice invoice = patientInvoiceRepository.findByAppointmentId(appointment.getId());

        if(invoice == null)
        {
            invoice = new Invoice();
            invoice.setAppointment(appointment);
            invoice.setPatient(patientRepository.findOne(Long.parseLong(createInvoiceRequest.get(0).getPatientId())));
            invoice.setInvoiceId(hisUtilService.getPrefixId(ModuleEnum.INVOICE));
            invoice.setCreatedOn(new Date());
            invoice.setUpdatedOn(new Date());
            invoice.setPaidAmount(0.0);
            invoice.setStatus(InvoiceStatusEnum.PENDING.toString());

            patientInvoiceRepository.save(invoice);
        }

        List<Long> ids = createInvoiceRequest.stream().filter(x->x.getId()!=null).map(PatientInvoiceRequestWrapper::getId).collect(Collectors.toList());
    //    invoiceItemsRepository.deleteInvoiceItem(ids);

        if(ids.size()>0){
            deleteRemoveInviceItems(ids,invoice.getId());
        }


        double amount =0.00;
        double taxAmount =0.00;
        double discountAmount = 0.00;
        double ivoiceTotal =0.00;
        for(PatientInvoiceRequestWrapper pInvc : createInvoiceRequest)
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
            ivoiceTotal += amount + taxAmount - discountAmount;
        }

        invoice.setInvoiceAmount(ivoiceTotal);
        patientInvoiceRepository.save(invoice);

    }


// Save Payment
    @Transactional
    public void savePayment(PaymentRequestWrapper paymentRequest)
    {

        Invoice invoice = patientInvoiceRepository.findOne(paymentRequest.getId());

        if(invoice != null)
        {
            double advanceCredit= 0.00;
            if(paymentRequest.getUseAdvancedBal()){
                advanceCredit = paymentRequest.getPatientAdvanceDeposit();
            }
        //  double receivedAmount = (invoice.getPaidAmount()== null? 0.00 : invoice.getPaidAmount()) + paymentRequest.getPaidAmount();
            double receivedAmount = (invoice.getPaidAmount()== null? 0.00 : invoice.getPaidAmount()) + paymentRequest.getPaidAmount() + advanceCredit;

            if(receivedAmount >= invoice.getInvoiceAmount())
            {
                invoice.setStatus(InvoiceStatusEnum.CLOSE.toString());
                double advanceDeposit = receivedAmount - invoice.getInvoiceAmount();

                Patient patient = patientRepository.findOne(invoice.getPatient().getId());
        //        patient.setAdvanceBalance(patient.getAdvanceBalance()== null? 0.00 : (patient.getAdvanceBalance() + advanceDeposit));
                patient.setAdvanceBalance(advanceDeposit);
                patientRepository.save(patient);
                receivedAmount = invoice.getInvoiceAmount();
            }else if(paymentRequest.getUseAdvancedBal() && paymentRequest.getPatientAdvanceDeposit() > 0){
                Patient patient = patientRepository.findOne(invoice.getPatient().getId());
                patient.setAdvanceBalance(0.0);
                patientRepository.save(patient);
            }

            invoice.setPaidAmount(receivedAmount);
            invoice.setUpdatedOn(new Date());
            patientInvoiceRepository.save(invoice);

            Payment payment = new Payment();
            payment.setCreatedOn(new Date());
            payment.setUpdatedOn(new Date());
            payment.setPaymentId(hisUtilService.getPrefixId(ModuleEnum.PAYMENT));

    //      payment.setPaymentAmount(paymentRequest.getPaidAmount());
            payment.setPaymentAmount((paymentRequest.getPaidAmount()+ advanceCredit));
            paymentRepository.save(payment);

            PatientInvoicePayment patientInvoicePayment = new PatientInvoicePayment();
            patientInvoicePayment.setCreatedOn(new Date());
            patientInvoicePayment.setUpdatedOn(new Date());
    //      patientInvoicePayment.setPaymentAmount(paymentRequest.getPaidAmount()>=invoice.getInvoiceAmount()? invoice.getInvoiceAmount() : paymentRequest.getPaidAmount());
            patientInvoicePayment.setPaymentAmount((paymentRequest.getPaidAmount()+ advanceCredit) >= invoice.getInvoiceAmount()? invoice.getInvoiceAmount() : paymentRequest.getPaidAmount()+advanceCredit);
            patientInvoicePayment.setInvoice(invoice);
            patientInvoicePayment.setPayment(payment);
            patientInvoicePayment.setPatient(invoice.getPatient());
            patientInvoicePaymentRepository.save(patientInvoicePayment);
        }
    }



    @Transactional
    public void deleteRemoveInviceItems(List<Long> ids,Long invID) {
        invoiceItemsRepository.deleteRemoveInviceItems(ids,invID);
    }

    public List<InvoiceItemsResponseWrapper> getInvoiceItemsById(long id){
        return invoiceItemsRepository.findAllByInvoiceId(patientInvoiceRepository.findByAppointmentId(id)!=null ? patientInvoiceRepository.findByAppointmentId(id).getId() : null);
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
            invoice.setStatus(InvoiceStatusEnum.PENDING.toString());

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

                invoice.setInvoiceAmount(ivoiceTotal);
                patientInvoiceRepository.save(invoice);
            }
        }
    }

    public List<InvoiceResponseWrapper> getAllInvoice(){
        return patientInvoiceRepository.findAllInvoices();
    }
}
