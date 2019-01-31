package com.sd.his.service;



import com.sd.his.model.*;

import com.sd.his.repository.PatientRepository;
import com.sd.his.repository.PatientVitalRepository;

import com.sd.his.utill.DateTimeUtil;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.PatientVitalWrapper;
import com.sd.his.wrapper.Patient_OrderWrapper;
import com.sd.his.wrapper.VitaPatientWrapper;
import com.sd.his.wrapper.VitalWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class PatientVitalService{

    @Autowired
    PatientVitalRepository patientVitalRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    private OrganizationService organizationService;

    public List<PatientVitalWrapper> getAll(){
        return patientVitalRepository.getAll();
    }

    public void save(PatientVitalWrapper vital){
        PatientVital patientVital=new PatientVital();
        //  patientVital.setId(vital.);
        patientVital.setName(vital.getName());
        patientVital.setUnit(vital.getUnit());
        patientVital.setStandardValue(vital.getStandardValue());
        patientVital.setStatus(vital.getStatus());
        patientVital.setCurrentValue(vital.getCurrentValue());
        patientVital.setUpdatedOn(HISCoreUtil.convertStringDateObject(vital.getUpdatedOn()) );
        Optional<Patient> patient=patientRepository.findById(Long.valueOf(vital.getPatientId()));
        patientVital.setPatient(patient.get());
        patientVitalRepository.save(patientVital);
    }


    @Transactional(rollbackOn = Throwable.class)
    public PatientVital update(VitaPatientWrapper wrapper) {
        PatientVital wrapperObj = patientVitalRepository.findOne(Long.valueOf(wrapper.getId()));
        wrapperObj.setName(wrapperObj.getName());
        wrapperObj.setUnit(wrapperObj.getUnit());
        wrapperObj.setStandardValue(wrapperObj.getStandardValue());
        wrapperObj.setStatus(wrapperObj.getStatus());
        wrapperObj.setCurrentValue(wrapper.getCurrentValue());
        Optional<Patient> patient=patientRepository.findById(Long.valueOf(wrapperObj.getPatient().getId()));
        wrapperObj.setPatient(patient.get());
        wrapperObj.setUpdatedOn(new Date());

        return patientVitalRepository.save(wrapperObj);
    }


    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteVital(long id) {

        patientVitalRepository.delete(id);
        return true;
    }

    public PatientVital getById(long Id) {
        return patientVitalRepository.findOne(Id);

    }


    public List<PatientVitalWrapper> getPaginatedOrder(Pageable pageable, Long patientId) {
        List<PatientVitalWrapper> patientVitals=this.patientVitalRepository.getPaginatedOrder(pageable,patientId);
        return patientVitals;

    }


    public int countPaginatedDocuments() {
        return this.patientVitalRepository.findAll().size();
    }


    public void saveListVital(List<VitalWrapper>  vital,String  patientId){
        for(int i=0;i<vital.size();i++){
            PatientVital patientVital=new PatientVital();

            patientVital.setName(vital.get(i).getName());
            patientVital.setUnit(vital.get(i).getUnit());
            patientVital.setStandardValue(vital.get(i).getStandardValue());
            Appointment app=appointmentService.findById(vital.get(i).getAppointmentId());
            patientVital.setAppointment(app);
            patientVital.setChiefComplaint(vital.get(i).getChiefComplaint());
           // patientVital.setStatus(vital.get(i).get);
            patientVital.setCurrentValue(vital.get(i).getCurrentValue());
            Optional<Patient> patient=patientRepository.findById(Long.valueOf(patientId));
            patientVital.setPatient(patient.get());
            Organization dbOrganization = organizationService.getAllOrgizationData();
            String systemDateFormat = dbOrganization.getDateFormat();
            String Zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
            String readDate = HISCoreUtil.convertDateToTimeZone(new Date(), "YYYY-MM-dd hh:mm:ss", Zone);
            //  System.out.println("Read Date"+readDate);
            Date scheduledDate = HISCoreUtil.convertStringDateObject(readDate);
            patientVital.setDateVital(scheduledDate);
            patientVitalRepository.save(patientVital);
        }

    }
}
