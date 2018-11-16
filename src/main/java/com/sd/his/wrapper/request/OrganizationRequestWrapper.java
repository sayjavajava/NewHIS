package com.sd.his.wrapper.request;/*
 * @author    : waqas kamran
 * @Date      : 29-May-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.*
 * @FileName  : OrganizationRequestWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */

import com.sd.his.model.Zone;

import java.util.List;

public class OrganizationRequestWrapper {

    String CompanyEmail;    //profile form
    String companyName;
    String officePhone;
    String specialty;
    String fax;
    String formName;
    String address;
    String website;

    Long defaultBranch;     //geenral form
    Long durationOfExam;
    Long durationFollowUp;
    String prefixSerialPatient;
    String prefixSerialUser;
    String prefixSerialDepartment;
    String prefixSerialAppointment;
    String prefixSerialInvoices;

    String firstName;
    String lastName;
    String userName;
    String userEmail;
    String cellPhone;
    String userAddress;
    String homePhone;
    Long userId;
    String dateFormat;
    String timeFormat;

    public String getZoneFormat() {
        return zoneFormat;
    }

    public void setZoneFormat(String zoneFormat) {
        this.zoneFormat = zoneFormat;
    }

    String zoneFormat;

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    String  selectedCity;
    String selectedCountry;

    public String getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    public String getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public String getSelectedState() {
        return selectedState;
    }

    public void setSelectedState(String selectedState) {
        this.selectedState = selectedState;
    }

    String selectedState;
    String zoneId;






    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    String state;
    String city;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyEmail() {
        return CompanyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        CompanyEmail = companyEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Long getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(Long defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public Long getDurationOfExam() {
        return durationOfExam;
    }

    public void setDurationOfExam(Long durationOfExam) {
        this.durationOfExam = durationOfExam;
    }

    public Long getDurationFollowUp() {
        return durationFollowUp;
    }

    public void setDurationFollowUp(Long durationFollowUp) {
        this.durationFollowUp = durationFollowUp;
    }

    public String getPrefixSerialPatient() {
        return prefixSerialPatient;
    }

    public void setPrefixSerialPatient(String prefixSerialPatient) {
        this.prefixSerialPatient = prefixSerialPatient;
    }

    public String getPrefixSerialUser() {
        return prefixSerialUser;
    }

    public void setPrefixSerialUser(String prefixSerialUser) {
        this.prefixSerialUser = prefixSerialUser;
    }

    public String getPrefixSerialDepartment() {
        return prefixSerialDepartment;
    }

    public void setPrefixSerialDepartment(String prefixSerialDepartment) {
        this.prefixSerialDepartment = prefixSerialDepartment;
    }

    public String getPrefixSerialAppointment() {
        return prefixSerialAppointment;
    }

    public void setPrefixSerialAppointment(String prefixSerialAppointment) {
        this.prefixSerialAppointment = prefixSerialAppointment;
    }

    public String getPrefixSerialInvoices() {
        return prefixSerialInvoices;
    }

    public void setPrefixSerialInvoices(String prefixSerialInvoices) {
        this.prefixSerialInvoices = prefixSerialInvoices;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }
}