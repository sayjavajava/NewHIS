package com.sd.his.wrapper;

import com.sd.his.model.Organization;
import com.sd.his.model.Patient;
import com.sd.his.model.PatientVital;
import com.sd.his.repository.PatientRepository;
import com.sd.his.service.OrganizationService;
import com.sd.his.utill.DateTimeUtil;
import com.sd.his.utill.HISConstants;
import com.sd.his.utill.HISCoreUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class PatientVitalWrapper extends BaseWrapper {


    @Autowired
    PatientRepository patientRepository;
    @Autowired
    private OrganizationService organizationService;
    private Long id;
    private String name;
    private String unit;
    private String standardValue;
    private String currentValue;
    private boolean status;
    private long appointmentId = -1;



    // private Date vitalDate;
    private String vitalStrDate;



    private String chiefComplaint;

    List<PatientVital> listOfVital;

    public PatientVitalWrapper(long id, String name, String unit, String standardValue, String currentValue, boolean status, String patient) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.standardValue = standardValue;
        this.currentValue = currentValue;
        this.status = status;
        this.patientId = patient;
    }

    public PatientVitalWrapper(long id, String name, String unit, String standardValue, String currentValue, boolean status, Patient patient) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.standardValue = standardValue;
        this.currentValue = currentValue;
        this.status = status;
        this.patientId = String.valueOf(patient.getId());
    }

    public PatientVitalWrapper(long id, String name, String unit, String standardValue, String currentValue, boolean status, Patient patient, Date updatedOn,String chief,Date dte) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.standardValue = standardValue;
        this.currentValue = currentValue;
        this.status = status;
        this.patientId = String.valueOf(patient.getId());
        super.setUpdatedOn(HISCoreUtil.convertDateToString(updatedOn, "dd MMM yyyy hh:mm:ss"));
        this.chiefComplaint=chief;
        if(dte!=null){
        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
        String systemDateFormat=dbOrganization.getDateFormat();
        String systemTimeFormat=dbOrganization.getTimeFormat();
        String hoursFormat = dbOrganization.getHoursFormat();
        if(hoursFormat.equals("12")){
            if(systemTimeFormat.equals("hh:mm")){
                systemTimeFormat="hh:mm";
            }else{
                systemTimeFormat="hh:mm:ss";
            }
        }else{
            if(systemTimeFormat.equals("hh:mm")){
                systemTimeFormat="HH:mm";
            }else{
                systemTimeFormat="HH:mm:ss";
            }
        }
        String standardFormatDateTime=systemDateFormat+" "+systemTimeFormat;
        String dteStrTest = DateTimeUtil.getFormattedDateFromDate(dte, HISConstants.DATE_FORMAT_APP);
        Date dteFrom=HISCoreUtil.convertStringDateObjectOrder(dteStrTest);
        String readDateFrom=HISCoreUtil.convertDateToTimeZone(dteFrom,standardFormatDateTime,Zone);
        Date fromDte= null;
        try {
            fromDte = DateTimeUtil.getDateFromString(readDateFrom,standardFormatDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(systemDateFormat!=null || !systemDateFormat.equals("")){
            String  dtelFrom=HISCoreUtil.convertDateToStringWithDateDisplay(fromDte,standardFormatDateTime);

            this.vitalStrDate=dtelFrom;

        }
        }else{
            this.vitalStrDate="";
        }

    }

    public PatientVitalWrapper() {
    }

    public PatientVitalWrapper(List<PatientVital> list, String name, Patient patient, boolean status, String unit) {
        this.listOfVital = list;
        this.name = name;
        this.patient = patient;
        this.status = status;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    private Patient patient;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    private String patientId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }


    public List<PatientVital> getListOfVital() {
        return listOfVital;
    }

    public void setListOfVital(List<PatientVital> listOfVital) {
        this.listOfVital = listOfVital;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public PatientVitalWrapper(String createdDate, String currentValue, long id, String name, long patientId,
                               String Standard, boolean status, String unit, String updated) {
        super.setCreatedOn(createdDate);
        this.currentValue = currentValue;
        this.id = id;
        this.name = name;
        this.patientId=String.valueOf(patientId);
        this.standardValue = Standard;
        this.status = status;
        this.unit = unit;
        super.setUpdatedOn(updated);
    }

    public PatientVitalWrapper(String currentValue,long id,String name,boolean status,String unit){
        this.currentValue=currentValue;
        this.id=id;
        this.name=name;
        this.status=status;
        this.unit=unit;
    }

    public boolean isStatus() {
        return status;
    }



    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }


    public String getVitalStrDate() {
        return vitalStrDate;
    }

    public void setVitalStrDate(String vitalStrDate) {
        this.vitalStrDate = vitalStrDate;
    }
}