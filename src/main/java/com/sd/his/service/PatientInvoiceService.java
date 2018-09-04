package com.sd.his.service;

import com.sd.his.enums.InvoiceStatusEnum;
import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.Appointment;
import com.sd.his.model.Invoice;
import com.sd.his.model.InvoiceItems;
import com.sd.his.repository.AppointmentRepository;
import com.sd.his.repository.InvoiceItemsRepository;
import com.sd.his.repository.PatientInvoiceRepository;
import com.sd.his.repository.PatientRepository;
import com.sd.his.wrapper.request.PatientInvoiceRequestWrapper;
import com.sd.his.wrapper.response.InvoiceItemsResponseWrapper;
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
    InvoiceItemsRepository invoiceItemsRepository;

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
            invoice.setStatus(InvoiceStatusEnum.PENDING.toString());

            patientInvoiceRepository.save(invoice);
        }

        List<Long> ids = createInvoiceRequest.stream().filter(x->x.getId()!=null).map(PatientInvoiceRequestWrapper::getId).collect(Collectors.toList());
    //    invoiceItemsRepository.deleteInvoiceItem(ids);

        if(ids.size()>0){
            deleteRemoveInviceItems(ids,invoice.getId());
        }

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
        }

    }


    @Transactional
    public void deleteRemoveInviceItems(List<Long> ids,Long invID) {
        invoiceItemsRepository.deleteRemoveInviceItems(ids,invID);
    }

    public List<InvoiceItemsResponseWrapper> getInvoiceItemsById(long id){
        return invoiceItemsRepository.findAllByInvoiceId(patientInvoiceRepository.findByAppointmentId(id).getId());
    }
}
