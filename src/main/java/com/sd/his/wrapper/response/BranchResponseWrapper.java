package com.sd.his.wrapper.response;

import com.sd.his.model.*;
import com.sd.his.utill.HISCoreUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    State state;
//    City city;
    String primaryDoctor;
    Integer zipCode;
    Country country;
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
    Long doctorId;
    boolean checkedBranch;
    long value;
    String label;
    String companyName;
    String flow;
    Map<String, List<BranchResponseWrapper>> doctorsInBranch;
    Map<String, Object> city;

    public BranchResponseWrapper(Branch branch) {
        this.branchName = branch.getName();
        this.officeHoursEnd = HISCoreUtil.convertTimeToString(branch.getOfficeEndTime());
        this.officeHoursStart = HISCoreUtil.convertTimeToString(branch.getOfficeStartTime());
      //  this.rooms = branch.getNoOfRooms();
        this.fax = branch.getFax();
        this.officePhone = branch.getOfficePhone();
        this.name = branch.getName();
        this.id = branch.getId();
        this.address = branch.getAddress();
        this.examRooms = branch.getRooms().stream().filter(x->x.getRoomName() !=null)
                .map(x-> new Room(x.getId(),x.getRoomName(),x.getAllowOnlineScheduling()))
                .collect(Collectors.toList());
        this.user = user;
        this.city = new HashMap<>();
        this.city.put("cityId", branch.getCity().getId());
        this.city.put("city", branch.getCity().getName());
        this.city.put("stateId", branch.getCity().getState().getId());
        this.city.put("state", branch.getCity().getState().getName());
        this.city.put("countryId", branch.getCity().getState().getCountry().getId());
        this.city.put("country", branch.getCity().getState().getCountry().getName());
//        branch.getCity();
        this.value=branch.getId() ;
        this.label =branch.getName();
        this.flow =branch.getFlow();


      //  this.state = branch.getCity().getState();
      //  this.country = branch.getCity().getState().getCountry();

    }
    public BranchResponseWrapper(){}
//b.id,b.name, b.country,b.city,b.noOfRooms,bb.firstName
    public BranchResponseWrapper(long id, String name){
        this.name =name;
        this.id = id;
        this.label = name;
        this.value = id;
    }

    public BranchResponseWrapper(Branch branch, Long drId, String drFirstName,String drLastName) {
       this.branchName=branch.getName();
       this.id=branch.getId();
       this.firstName = drFirstName;
       this.lastName =drLastName;
       this.doctorId =drId;
        this.examRooms = branch.getRooms().stream().filter(x->x.getRoomName() !=null)
                .map(x-> new Room(x.getId(),x.getRoomName(),x.getAllowOnlineScheduling()))
                .collect(Collectors.toList());

    }
    public BranchResponseWrapper(long id, String name, String firstName) {
        this.id = id;
        this.name = name;
        this.firstName=firstName;
        //this.doctor =doctor;


    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public Map<String, List<BranchResponseWrapper>> getDoctorsInBranch() {
        return doctorsInBranch;
    }

    public void setDoctorsInBranch(Map<String, List<BranchResponseWrapper>> doctorsInBranch) {
        this.doctorsInBranch = doctorsInBranch;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public boolean isCheckedBranch() {
        return checkedBranch;
    }

    public void setCheckedBranch(boolean checkedBranch) {
        this.checkedBranch = checkedBranch;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Map<String, Object> getCity() {
        return city;
    }

    public void setCity(Map<String, Object> city) {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
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