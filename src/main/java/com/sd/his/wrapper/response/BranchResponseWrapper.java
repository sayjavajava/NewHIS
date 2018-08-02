package com.sd.his.wrapper.response;

import com.sd.his.model.Branch;
import com.sd.his.model.Doctor;
import com.sd.his.model.Room;
import com.sd.his.model.User;
import com.sd.his.utill.HISCoreUtil;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    Integer noOfExamRooms;
    String state;
    String city;
    String primaryDoctor;
    Integer zipCode;
    String country;
    String officePhone;
    String fax;
    String formattedAddress;
    String billingName;
    String billingBranch;
    String billingTaxID;
    Boolean showBranchOnline;
    Boolean allowOnlineSchedulingInBranch;
    Long rooms;
    String username;
    List<Room> examRooms;
    String address;
    User user;
    Room roomList;
    Doctor doctor;
    String firstName;
    String lastName;

    public BranchResponseWrapper(Branch branch) {
        this.branchName = branch.getName();
        this.country = branch.getCountry();
        this.officeHoursEnd = HISCoreUtil.convertTimeToString(branch.getOfficeEndTime());
        this.officeHoursStart = HISCoreUtil.convertTimeToString(branch.getOfficeStartTime());
        this.billingBranch = branch.getBillingBranchName();
        this.billingTaxID = branch.getBillingTaxId();
        this.billingName = branch.getBillingName();
        this.city = branch.getCity();
        this.rooms = branch.getNoOfRooms();
        this.fax = branch.getFax();
        this.zipCode = branch.getZipCode();
        this.officePhone = branch.getOfficePhone();
        this.state = branch.getState();
        this.name = branch.getName();
        this.id = branch.getId();
        this.address = branch.getAddress();
        this.examRooms = branch.getRooms().stream().filter(x->x.getRoomName() !=null)
                .map(x-> new Room(x.getId(),x.getRoomName(),x.getAllowOnlineScheduling()))
                .collect(Collectors.toList());
        this.user = user;

    }


  /*  public BranchResponseWrapper(Long id, String name, String country, String city, Long rooms, String username) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.country = country;
        this.rooms = rooms;
        this.primaryDoctor = username;

    }*/

    public BranchResponseWrapper(long id, String name, String country, String city, Long rooms, String firstName) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.country = country;
        this.rooms = rooms;
        this.firstName=firstName;
        //this.doctor =doctor;
        System.out.println(firstName);

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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Integer getNoOfExamRooms() {
        return noOfExamRooms;
    }

    public void setNoOfExamRooms(Integer noOfExamRooms) {
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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
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

    public Boolean getShowBranchOnline() {
        return showBranchOnline;
    }

    public void setShowBranchOnline(Boolean showBranchOnline) {
        this.showBranchOnline = showBranchOnline;
    }

    public Boolean getAllowOnlineSchedulingInBranch() {
        return allowOnlineSchedulingInBranch;
    }

    public void setAllowOnlineSchedulingInBranch(Boolean allowOnlineSchedulingInBranch) {
        this.allowOnlineSchedulingInBranch = allowOnlineSchedulingInBranch;
    }

    public Long getRooms() {
        return rooms;
    }

    public void setRooms(Long rooms) {
        this.rooms = rooms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Room> getExamRooms() {
        return examRooms;
    }

    public void setExamRooms(List<Room> examRooms) {
        this.examRooms = examRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoomList() {
        return roomList;
    }

    public void setRoomList(Room roomList) {
        this.roomList = roomList;
    }
}