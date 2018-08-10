package com.sd.his.wrapper.response;

import com.sd.his.enums.UserTypeEnum;
import com.sd.his.model.*;
import com.sd.his.utill.HISCoreUtil;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class StaffResponseWrapper{

    Long uid;
    Long pId;
    Long primaryBranchId;
    String userType;
    String email;
    String userName;
    String firstName;
    String lastName;
    String homePhone;
    String cellPhone;
    String expiryDate;
    String primaryBranch;
    DutyShift dutyShift;
    String workingDaysOfDoctor;
    List<Branch> visitBranches;
    Boolean active;
    Long checkUpInterval;
    Boolean vacation;
    String vacationFrom;
    String vacationTo;
    List<String> workingDays;
    List<DutyShift> dutyShifts;
    Boolean managePatientRecords;
    Boolean managePatientInvoices;
    String phoneNumber;
    String address;
    String city;
    String country;
    String status;
    String profileImg;
    Long id;

     // List<ClinicalDepartment> clinicalDepartments;
    // List<DutyWithDoctor> dutyWithDoctors;

    List<Branch> staffBranches = new ArrayList();
    List<Doctor> dutyWithDoctors;
    boolean checkedDoc;
    public StaffResponseWrapper() {
    }

    //constructor for cashier and receptionist
    public StaffResponseWrapper(Long uId,Long pId,UserTypeEnum userType,String firstName,String lastName,String userName,
                                String email,String primaryBranch,String homePhone,String cellPhone,Boolean active,Long primaryId,
                                Date expiryDate

                                ){
        this.uid = uId;
        this.pId=pId;
        this.userType = userType.name();
        this.userName = userName;
        this.firstName=firstName;
        this.lastName =lastName;
        this.email=email;
        this.primaryBranchId=primaryId;
     //   this.visitBranches=branchList;
        this.active=active;
        this.expiryDate= HISCoreUtil.convertDateToString(expiryDate);
        this.homePhone=homePhone;
        this.cellPhone=cellPhone;
        this.primaryBranch=primaryBranch;

       // this.visitBranches=branchCashiers;

    }


    //constructor for doctor
    public StaffResponseWrapper(Long uId,Long pId,UserTypeEnum userType,String firstName,String lastName,String userName,
                                String email,String primaryBranch,String homePhone,String cellPhone,Boolean active,Long primaryId,
                                Date expiryDate,Long checkUpInterval, Boolean vacation,Date vacationFrom,Date vacationTo,
                                Doctor doctor

    ){
        this.uid = uId;
        this.pId=pId;
        this.userType = userType.name();
        this.userName = userName;
        this.firstName=firstName;
        this.lastName =lastName;
        this.email=email;
        this.primaryBranchId=primaryId;
        //this.visitBranches=branchList;
        this.active=active;
        this.expiryDate= HISCoreUtil.convertDateToString(expiryDate);
        this.homePhone=homePhone;
        this.cellPhone=cellPhone;
        this.primaryBranch=primaryBranch;
        this.checkUpInterval=checkUpInterval;
        this.vacation = vacation;
        this.vacationFrom = HISCoreUtil.convertDateToString(vacationFrom);
//        this.vacationTo  = HISCoreUtil.convertDateToString(vacationTo);
        this.vacationTo=null;
        this.workingDays = doctor.getWorkingDays();
        this.dutyShifts = doctor.getDutyShifts();
        // this.visitBranches=branchCashiers;

    }

    //constructor for nurse
    public StaffResponseWrapper(Long uId,Long pId,UserTypeEnum userType,String firstName,String lastName,String userName,
                                String email,String primaryBranch,String homePhone,String cellPhone,Boolean active,Long primaryId,
                                Date expiryDate,Boolean managePatientRecords,Boolean managePatientInvoices

    ){
        this.uid = uId;
        this.pId=pId;
        this.userType = userType.name();
        this.userName = userName;
        this.firstName=firstName;
        this.lastName =lastName;
        this.email=email;
        this.primaryBranchId=primaryId;
        //   this.visitBranches=branchList;
        this.active=active;
        this.expiryDate= HISCoreUtil.convertDateToString(expiryDate);
        this.homePhone=homePhone;
        this.cellPhone=cellPhone;
        this.primaryBranch=primaryBranch;
        this.managePatientInvoices=managePatientInvoices;
        this.managePatientRecords=managePatientRecords;

        // this.visitBranches=branchCashiers;

    }

    //constructor for nurse
    public StaffResponseWrapper(Nurse nurse){
        this.uid = nurse.getUser().getId();
        this.pId= nurse.getId();
        this.userType = nurse.getUser().getUserType().name();
        this.userName = nurse.getUser().getUsername();
        this.firstName=nurse.getFirstName();
        this.lastName =nurse.getLastName();
        this.email=nurse.getEmail();
        OptionalLong pbId =nurse.getBranchNurses().stream().filter(b->b.getPrimaryBranch()==true).mapToLong(pb->pb.getId()).findFirst();
        this.primaryBranchId = pbId.getAsLong();
        //   this.visitBranches=branchList;
        this.active=nurse.getUser().getActive();
        this.expiryDate= HISCoreUtil.convertDateToString(nurse.getAccountExpiry());
        this.homePhone=nurse.getHomePhone();
        this.cellPhone=nurse.getCellPhone();
        this.primaryBranch = nurse.getBranchNurses().stream().filter(p->p.getPrimaryBranch()==true).map(pb->pb.getBranch().getName()).findAny().get();
        this.managePatientInvoices=nurse.getManagePatientInvoices();
        this.managePatientRecords=nurse.getManagePatientRecords();
        //List<Branch> branches = nurse.getBranchNurses().stream().map(b->b.getBranch()).collect(Collectors.toList());
        //this.staffBranches = branches;
        //this.dutyWithDoctors = nurse.getNurseWithDoctorList().stream().map(d->d.getDoctor()).collect(Collectors.toList());
    }

    public StaffResponseWrapper(Object object){
        Object obj = new Object();
        if(object instanceof Doctor){
            Doctor doctor = (Doctor)object;
            this.id = doctor.getId();
            this.userType = doctor.getUser().getUserType().name();
            this.email = doctor.getEmail();
            this.userName = doctor.getUser().getUsername();
            this.firstName = doctor.getFirstName();
            this.lastName = doctor.getLastName();
            this.cellPhone = doctor.getCellPhone();
            this.address = doctor.getAddress();
            this.city = doctor.getCity();
            this.country = doctor.getCountry();
            //this.gender = doctor.getGender().name();
            this.profileImg = doctor.getProfileImgURL();
            this.status = doctor.getStatus().name();
        }else
        if(object instanceof Nurse){
            Nurse nurse = (Nurse)object;
            this.email = nurse.getEmail();
            this.userName = nurse.getUser().getUsername();
            this.firstName = nurse.getFirstName();
            this.lastName = nurse.getLastName();
            this.cellPhone = nurse.getCellPhone();
            this.address = nurse.getAddress();
            this.city = nurse.getCity();
            this.country = nurse.getCountry();
            //this.gender = nurse.getGender().name();
            this.profileImg = nurse.getProfileImgURL();
            this.status = nurse.getStatus().name();
        }else
        if(object instanceof Cashier){
            Cashier cashier = (Cashier)object;
            this.email = cashier.getEmail();
            this.userName = cashier.getUser().getUsername();
            this.firstName = cashier.getFirstName();
            this.lastName = cashier.getLastName();
            this.cellPhone = cashier.getCellPhone();
            this.address = cashier.getAddress();
            this.city = cashier.getCity();
            this.country = cashier.getCountry();
            //this.gender = cashier.getGender().name();
            this.profileImg = cashier.getProfileImgURL();
            this.status = cashier.getStatus().name();
        }else
        if(object instanceof Receptionist) {
            Receptionist receptionist = (Receptionist) object;
            this.email = receptionist.getEmail();
            this.userName = receptionist.getUser().getUsername();
            this.firstName = receptionist.getFirstName();
            this.lastName = receptionist.getLastName();
            this.cellPhone = receptionist.getCellPhone();
            this.address = receptionist.getAddress();
            this.city = receptionist.getCity();
            this.country = receptionist.getCountry();
            //this.gender = receptionist.getGender().name();
            this.profileImg = receptionist.getProfileImgURL();
            this.status = receptionist.getStatus().name();
        }
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

    public List<DutyShift> getDutyShifts() {
        return dutyShifts;
    }

    public void setDutyShifts(List<DutyShift> dutyShifts) {
        this.dutyShifts = dutyShifts;
    }

    public List<String> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<String> workingDays) {
        this.workingDays = workingDays;
    }

    public Long getCheckUpInterval() {
        return checkUpInterval;
    }

    public void setCheckUpInterval(Long checkUpInterval) {
        this.checkUpInterval = checkUpInterval;
    }

    public Boolean getVacation() {
        return vacation;
    }

    public void setVacation(Boolean vacation) {
        this.vacation = vacation;
    }

    public String getVacationFrom() {
        return vacationFrom;
    }

    public void setVacationFrom(String vacationFrom) {
        this.vacationFrom = vacationFrom;
    }

    public String getVacationTo() {
        return vacationTo;
    }

    public void setVacationTo(String vacationTo) {
        this.vacationTo = vacationTo;
    }

    public Long getPrimaryBranchId() {
        return primaryBranchId;
    }

    public void setPrimaryBranchId(Long primaryBranchId) {
        this.primaryBranchId = primaryBranchId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPrimaryBranch() {
        return primaryBranch;
    }

    public void setPrimaryBranch(String primaryBranch) {
        this.primaryBranch = primaryBranch;
    }

    public DutyShift getDutyShift() {
        return dutyShift;
    }

    public void setDutyShift(DutyShift dutyShift) {
        this.dutyShift = dutyShift;
    }

    public String getWorkingDaysOfDoctor() {
        return workingDaysOfDoctor;
    }

    public void setWorkingDaysOfDoctor(String workingDaysOfDoctor) {
        this.workingDaysOfDoctor = workingDaysOfDoctor;
    }

    public List<Branch> getVisitBranches() {
        return visitBranches;
    }

    public void setVisitBranches(List<Branch> visitBranches) {
        this.visitBranches = visitBranches;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Branch> getStaffBranches() {
        return staffBranches;
    }

    public void setStaffBranches(List<Branch> staffBranches) {
        this.staffBranches = staffBranches;
    }

    public List<Doctor> getDutyWithDoctors() {
        return dutyWithDoctors;
    }

    public void setDutyWithDoctors(List<Doctor> dutyWithDoctors) {
        this.dutyWithDoctors = dutyWithDoctors;
    }

    public boolean isCheckedDoc() {
        return checkedDoc;
    }

    public void setCheckedDoc(boolean checkedDoc) {
        this.checkedDoc = checkedDoc;
    }
}