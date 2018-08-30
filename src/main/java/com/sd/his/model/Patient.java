package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sd.his.enums.GenderTypeEnum;
import com.sd.his.enums.MaritalStatusTypeEnum;
import com.sd.his.enums.PatientStatusTypeEnum;
import org.apache.http.util.NetUtils;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PATIENT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @NaturalId
    @Column(name = "PATIENT_ID", unique = true, nullable = false, updatable = false)
    private String patientId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PATIENT_SSN")
    private String patientSSN;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "FOREIGN_NAME")
    private String foreignName;

    @Temporal(TemporalType.DATE)
    @Column(name = "DOB")
    private Date dob;

    @Column(name = "HOME_PHONE")
    private String homePhone;

    @Column(name = "CELL_PHONE")
    private String cellPhone;

    @Column(name = "OFFICE_PHONE")
    private String officePhone;

    @Column(name = "PHONE_EXTENSION")
    private String officeExtension;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private GenderTypeEnum gender;

    @Column(name = "PROFILE_IMG_URL")
    private String profileImgURL;


    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "EMAIL")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "MARITAL_STATUS")
    private MaritalStatusTypeEnum maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private PatientStatusTypeEnum status;


    @Column(name = "DISABLE_SMS_TEXT", columnDefinition = "boolean default true")
    private Boolean disableSMSText;


    @Column(name = "PREFERRED_COMMUNICATION")
    private String preferredCommunication;

    @Column(name = "REMINDER_LANGUAGE")
    private String reminderLanguage;

    @Column(name = "STREET_ADDRESS")
    private String streetAddress;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "FORMATTED_ADDRESS")
    private String formattedAddress;

    @Column(name = "EMERGENCY_CONTACT_NAME")
    private String emergencyContactName;

    @Column(name = "EMERGENCY_CONTACT_PHONE")
    private String emergencyContactPhone;

    @Column(name = "EMERGENCY_CONTACT_RELATION")
    private String emergencyContactRelation;

    @Column(name = "SIGNATURE_ON_FILE",  columnDefinition = "boolean default true")
    private Boolean signatureOnFile;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="INSURANCE_ID")
    private Insurance insurance;

    @ElementCollection
    @Column(name = "RACES")
    private List<String> races;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRIMARY_DOCTOR_ID")
    private Doctor primaryDoctor;

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    @JsonIgnore
    @OneToMany(targetEntity = Problem.class, mappedBy = "patient")
    private List<Problem> problems;

    @JsonIgnore
    @OneToMany(targetEntity = Allergy.class, mappedBy = "patient")
    private List<Allergy> allergies;

    @JsonIgnore
    @OneToMany(targetEntity = Medication.class, mappedBy = "patient")
    private List<Medication> medications;

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPatientSSN() {
        return patientSSN;
    }

    public void setPatientSSN(String patientSSN) {
        this.patientSSN = patientSSN;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getOfficeExtension() {
        return officeExtension;
    }

    public void setOfficeExtension(String officeExtension) {
        this.officeExtension = officeExtension;
    }

    public GenderTypeEnum getGender() {
        return gender;
    }

    public void setGender(GenderTypeEnum gender) {
        this.gender = gender;
    }

    public String getProfileImgURL() {
        return profileImgURL;
    }

    public void setProfileImgURL(String profileImgURL) {
        this.profileImgURL = profileImgURL;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MaritalStatusTypeEnum getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusTypeEnum maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public PatientStatusTypeEnum getStatus() {
        return status;
    }

    public void setStatus(PatientStatusTypeEnum status) {
        this.status = status;
    }

    public Boolean getDisableSMSText() {
        return disableSMSText;
    }

    public void setDisableSMSText(Boolean disableSMSText) {
        this.disableSMSText = disableSMSText;
    }

    public String getPreferredCommunication() {
        return preferredCommunication;
    }

    public void setPreferredCommunication(String preferredCommunication) {
        this.preferredCommunication = preferredCommunication;
    }

    public String getReminderLanguage() {
        return reminderLanguage;
    }

    public void setReminderLanguage(String reminderLanguage) {
        this.reminderLanguage = reminderLanguage;
    }

    /*public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }*/

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getEmergencyContactRelation() {
        return emergencyContactRelation;
    }

    public void setEmergencyContactRelation(String emergencyContactRelation) {
        this.emergencyContactRelation = emergencyContactRelation;
    }

    public Boolean getSignatureOnFile() {
        return signatureOnFile;
    }

    public void setSignatureOnFile(Boolean signatureOnFile) {
        this.signatureOnFile = signatureOnFile;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public List<String> getRaces() {
        return races;
    }

    public void setRaces(List<String> races) {
        this.races = races;
    }

    public Doctor getPrimaryDoctor() {
        return primaryDoctor;
    }

    public void setPrimaryDoctor(Doctor primaryDoctor) {
        this.primaryDoctor = primaryDoctor;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergy> allergies) {
        this.allergies = allergies;
    }
}
