package com.sd.his.model;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name = "PROFILE")
public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "HOME_PHONE")
    private String homePhone;

    @Column(name = "CELL_PHONE")
    private String cellPhone;

    @Column(name = "ACCOUNT_EXPIRY")
    private String accountExpiry;



    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default false", nullable = false)
    private Boolean active;

    @Column(name = "IS_DELETED", columnDefinition = "boolean default false", nullable = false)
    private Boolean deleted;

    @Column(name = "IS_SEND_BILLING_REPORT", columnDefinition = "boolean default false")
    private Boolean sendBillingReport;

    @Column(name = "IS_USE_RECEPT_DASHBOARD", columnDefinition = "boolean default false")
    private Boolean useReceptDashBoard;

    @Column(name = "IS_OTHER_DOCTOR_DASHBOARD", columnDefinition = "boolean default false")
    private Boolean otherDoctorDashBoard;

    @Column(name = "IS_MANAGE_PATIENT_RECORDS", columnDefinition = "boolean default false")
    private Boolean managePatientRecords;

    @Column(name = "IS_MANAGE_PATIENT_INVOICES", columnDefinition = "boolean default false")
    private Boolean managePatientInvoices;

    @Column(name = "CHECK_UP_INTERVAL")
    private Long checkUpInterval;

/*   @ElementCollection
    @Column(name = "WORKING_DAYS")
    private Set<String> workingDays ;*/

    @ElementCollection
    @Column(name = "WORKING_DAYS")
    private List<String> workingDays =new ArrayList<>();

    @Column(name = "ALLOW_DISCOUNT")
    private Boolean allowDiscount;


    @Column(name = "GENDER")
    private String gender;

    @Column(name = "PROFILE_IMG")
    private String profileImg;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DOB")
    private Date dob;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "OTHER_DOCTOR_DASHBOARD")
    private String otherDashboard;

    @Column(name = "CREATED_ON")
    private Long createdOn;

    @Column(name = "UPDATED_ON")
    private Long updatedOn;

    @Column(name = "ABOUT_ME")
    private String aboutMe;

    public Profile() {
    }

    public Profile(String firstName, String lastName, String homePhone, String cellPhone, String accountExpiry, Boolean active, Boolean deleted, Boolean sendBillingReport, Boolean useReceptDashBoard, Boolean otherDoctorDashBoard, Boolean managePatientRecords, Boolean managePatientInvoices, Long checkUpInterval, List<String> workingDays, Boolean allowDiscount, String gender, String profileImg, String address, String city, String state, String country, String status, Date dob, String type, String otherDashboard, Long createdOn, Long updatedOn, String aboutMe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.accountExpiry = accountExpiry;
        this.active = active;
        this.deleted = deleted;
        this.sendBillingReport = sendBillingReport;
        this.useReceptDashBoard = useReceptDashBoard;
        this.otherDoctorDashBoard = otherDoctorDashBoard;
        this.managePatientRecords = managePatientRecords;
        this.managePatientInvoices = managePatientInvoices;
        this.checkUpInterval = checkUpInterval;
        this.workingDays = workingDays;
        this.allowDiscount = allowDiscount;
        this.gender = gender;
        this.profileImg = profileImg;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.status = status;
        this.dob = dob;
        this.type = type;
        this.otherDashboard = otherDashboard;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.aboutMe = aboutMe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAccountExpiry() {
        return accountExpiry;
    }

    public void setAccountExpiry(String accountExpiry) {
        this.accountExpiry = accountExpiry;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getSendBillingReport() {
        return sendBillingReport;
    }

    public void setSendBillingReport(Boolean sendBillingReport) {
        this.sendBillingReport = sendBillingReport;
    }

    public Boolean getUseReceptDashBoard() {
        return useReceptDashBoard;
    }

    public void setUseReceptDashBoard(Boolean useReceptDashBoard) {
        this.useReceptDashBoard = useReceptDashBoard;
    }

    public Boolean getOtherDoctorDashBoard() {
        return otherDoctorDashBoard;
    }

    public void setOtherDoctorDashBoard(Boolean otherDoctorDashBoard) {
        this.otherDoctorDashBoard = otherDoctorDashBoard;
    }

    public Boolean getManagePatientRecords() {
        return managePatientRecords;
    }

    public void setManagePatientRecords(Boolean managePatientRecords) {
        this.managePatientRecords = managePatientRecords;
    }

    public Boolean getManagePatientInvoices() {
        return managePatientInvoices;
    }

    public void setManagePatientInvoices(Boolean managePatientInvoices) {
        this.managePatientInvoices = managePatientInvoices;
    }

    public Long getCheckUpInterval() {
        return checkUpInterval;
    }

    public void setCheckUpInterval(Long checkUpInterval) {
        this.checkUpInterval = checkUpInterval;
    }

    public List<String> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<String> workingDays) {
        this.workingDays = workingDays;
    }

    public Boolean getAllowDiscount() {
        return allowDiscount;
    }

    public void setAllowDiscount(Boolean allowDiscount) {
        this.allowDiscount = allowDiscount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOtherDashboard() {
        return otherDashboard;
    }

    public void setOtherDashboard(String otherDashboard) {
        this.otherDashboard = otherDashboard;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}