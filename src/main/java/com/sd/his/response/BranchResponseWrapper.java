package com.sd.his.response;

import com.sd.his.model.Branch;
import com.sd.his.model.Room;
import com.sd.his.model.User;

import java.util.List;

/*
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
public class BranchResponseWrapper {

    long id;
    String branchName;
    String name;
    String officeHoursStart;
    String officeHoursEnd;
    int noOfExamRooms;
    String state;
    String city;
    String primaryDoctor;
    int zipCode;
    String country;
    String officePhone;
    String fax;
    String formattedAddress;
    String billingName;
    String billingBranch;
    String billingTaxID;
    boolean showBranchOnline;
    boolean allowOnlineSchedulingInBranch;
    Integer rooms;
    String username;
    List<Room> examRooms;
    String address;
    User user;

    public BranchResponseWrapper(Branch branch) {
        this.branchName = branch.getName();
        this.country = branch.getCountry();
        this.officeHoursEnd = branch.getOfficeEndTime();
        this.officeHoursStart = branch.getOfficeStartTime();
        this.billingBranch = branch.getBillingBranchName();
        this.billingTaxID = branch.getBillingTaxId();
        this.billingName = branch.getBillingName();
        this.city = branch.getCity();
        this.rooms = branch.getNoOfRooms();
        this.fax = branch.getFax();
        this.formattedAddress = branch.getFormattedAddress();
        this.zipCode = branch.getZipCode();
        this.officePhone = branch.getOfficePhone();

        this.state = branch.getState();
        this.showBranchOnline = branch.isShowBranchInfoOnline();
        this.allowOnlineSchedulingInBranch = branch.isAllowOnlineSchedule();
        this.name = branch.getName();
        this.id = branch.getId();
        this.address = branch.getAddress();


    }

    public BranchResponseWrapper(long id, String name, String country, String city, Integer rooms) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.country = country;
        this.rooms = rooms;

    }

    public BranchResponseWrapper(long id, String name, String country, String city, Integer rooms,String username) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.country = country;
        this.rooms = rooms;
        this.primaryDoctor=username;

    }
    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Room> getExamRooms() {
        return examRooms;
    }

    public void setExamRooms(List<Room> examRooms) {
        this.examRooms = examRooms;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficeHoursStart() {
        return officeHoursStart;
    }

    public void setOfficeHoursStart(String officeHoursStart) {
        this.officeHoursStart = officeHoursStart;
    }

    public String getOfficeHoursEnd() {
        return officeHoursEnd;
    }

    public void setOfficeHoursEnd(String officeHoursEnd) {
        this.officeHoursEnd = officeHoursEnd;
    }

    public int getNoOfExamRooms() {
        return noOfExamRooms;
    }

    public void setNoOfExamRooms(int noOfExamRooms) {
        this.noOfExamRooms = noOfExamRooms;
    }

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

    public String getPrimaryDoctor() {
        return primaryDoctor;
    }

    public void setPrimaryDoctor(String primaryDoctor) {
        this.primaryDoctor = primaryDoctor;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }


    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingBranch() {
        return billingBranch;
    }

    public void setBillingBranch(String billingBranch) {
        this.billingBranch = billingBranch;
    }

    public String getBillingTaxID() {
        return billingTaxID;
    }

    public void setBillingTaxID(String billingTaxID) {
        this.billingTaxID = billingTaxID;
    }

    public boolean isShowBranchOnline() {
        return showBranchOnline;
    }

    public void setShowBranchOnline(boolean showBranchOnline) {
        this.showBranchOnline = showBranchOnline;
    }

    public boolean isAllowOnlineSchedulingInBranch() {
        return allowOnlineSchedulingInBranch;
    }

    public void setAllowOnlineSchedulingInBranch(boolean allowOnlineSchedulingInBranch) {
        this.allowOnlineSchedulingInBranch = allowOnlineSchedulingInBranch;
    }
}