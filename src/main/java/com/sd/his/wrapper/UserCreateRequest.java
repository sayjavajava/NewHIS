package com.sd.his.wrapper;/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
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
 * @FileName  : UserAuthAPI
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class UserCreateRequest {
    String userType;
    String userName;
    String firstName;
    String lastName;
    String email;
    String password;
    int phoneNumber;
    boolean active;
    boolean sendBillingReport;
    boolean useReceptDashboard;
    boolean otherDoctorDashBoard;
    String accountExpiry;
    boolean managePatientInvoices;
    boolean managePatientRecords;
    boolean shift2;
    boolean shift1;
    boolean vacation;
    String secondShiftFromTime;
    String secondShiftToTime;
    String firstShiftFromTime;
    String firstShiftToTime;
    String[] selectedDepartment;
    String[] selectedServices;
    String[] selectedWorkingDays;
    String[] selectedDoctors;
    String allowDiscount;
    long interval;
    String homePhone;
    String cellPhone;
    String primaryBranch;
    String[] selectedRestrictedBranches;
    String dateFrom;
    String dateTo;
    long createdOn;
    long updatedOn;


    public String[] getSelectedWorkingDays() {
        return selectedWorkingDays;
    }

    public void setSelectedWorkingDays(String[] selectedWorkingDays) {
        this.selectedWorkingDays = selectedWorkingDays;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSendBillingReport() {
        return sendBillingReport;
    }

    public void setSendBillingReport(boolean sendBillingReport) {
        this.sendBillingReport = sendBillingReport;
    }

    public boolean isUseReceptDashboard() {
        return useReceptDashboard;
    }

    public void setUseReceptDashboard(boolean useReceptDashboard) {
        this.useReceptDashboard = useReceptDashboard;
    }

    public boolean isOtherDoctorDashBoard() {
        return otherDoctorDashBoard;
    }

    public void setOtherDoctorDashBoard(boolean otherDoctorDashBoard) {
        this.otherDoctorDashBoard = otherDoctorDashBoard;
    }

    public String getAccountExpiry() {
        return accountExpiry;
    }

    public void setAccountExpiry(String accountExpiry) {
        this.accountExpiry = accountExpiry;
    }

    public boolean isManagePatientInvoices() {
        return managePatientInvoices;
    }

    public void setManagePatientInvoices(boolean managePatientInvoices) {
        this.managePatientInvoices = managePatientInvoices;
    }

    public boolean isManagePatientRecords() {
        return managePatientRecords;
    }

    public void setManagePatientRecords(boolean managePatientRecords) {
        this.managePatientRecords = managePatientRecords;
    }

    public boolean isShift2() {
        return shift2;
    }

    public void setShift2(boolean shift2) {
        this.shift2 = shift2;
    }

    public boolean isShift1() {
        return shift1;
    }

    public void setShift1(boolean shift1) {
        this.shift1 = shift1;
    }

    public boolean isVacation() {
        return vacation;
    }

    public void setVacation(boolean vacation) {
        this.vacation = vacation;
    }

    public String getSecondShiftFromTime() {
        return secondShiftFromTime;
    }

    public void setSecondShiftFromTime(String secondShiftFromTime) {
        this.secondShiftFromTime = secondShiftFromTime;
    }

    public String getSecondShiftToTime() {
        return secondShiftToTime;
    }

    public void setSecondShiftToTime(String secondShiftToTime) {
        this.secondShiftToTime = secondShiftToTime;
    }

    public String getFirstShiftFromTime() {
        return firstShiftFromTime;
    }

    public void setFirstShiftFromTime(String firstShiftFromTime) {
        this.firstShiftFromTime = firstShiftFromTime;
    }

    public String getFirstShiftToTime() {
        return firstShiftToTime;
    }

    public void setFirstShiftToTime(String firstShiftToTime) {
        this.firstShiftToTime = firstShiftToTime;
    }

    public String[] getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(String[] selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public String[] getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(String[] selectedServices) {
        this.selectedServices = selectedServices;
    }


    public String[] getSelectedDoctors() {
        return selectedDoctors;
    }

    public void setSelectedDoctors(String[] selectedDoctors) {
        this.selectedDoctors = selectedDoctors;
    }

    public String getAllowDiscount() {
        return allowDiscount;
    }

    public void setAllowDiscount(String allowDiscount) {
        this.allowDiscount = allowDiscount;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
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

    public String getPrimaryBranch() {
        return primaryBranch;
    }

    public void setPrimaryBranch(String primaryBranch) {
        this.primaryBranch = primaryBranch;
    }

    public String[] getSelectedRestrictedBranches() {
        return selectedRestrictedBranches;
    }

    public void setSelectedRestrictedBranches(String[] selectedRestrictedBranches) {
        this.selectedRestrictedBranches = selectedRestrictedBranches;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }
}