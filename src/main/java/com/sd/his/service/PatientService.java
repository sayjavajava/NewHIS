package com.sd.his.service;

import com.sd.his.enums.GenderTypeEnum;
import com.sd.his.enums.MaritalStatusTypeEnum;
import com.sd.his.enums.ModuleEnum;
import com.sd.his.enums.PatientStatusTypeEnum;
import com.sd.his.model.*;
import com.sd.his.model.LabTest;
import com.sd.his.repository.*;
import com.sd.his.utill.DateTimeUtil;
import com.sd.his.utill.HISConstants;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class  PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private HISUtilService hisUtilService;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private LabOrderRepository labOrderRepository;
    @Autowired
    private LabTestRepository labTestRepository;
    @Autowired
    private FamilyHistoryRepository familyHistoryRepository;

    @Autowired
    private SmokingStatusRepository smokingStatusRepository;

    //response populate
    private void populatePatientWrapper(PatientWrapper patientWrapper, Patient patient) {
        patientWrapper.setId(patient.getId());//patient pk
        patientWrapper.setPatientId(patient.getPatientId());//patient natural id
        patientWrapper.setTitlePrefix(patient.getTitle());
        patientWrapper.setPatientSSN(patient.getPatientSSN());
        patientWrapper.setFirstName(patient.getFirstName());
        patientWrapper.setMiddleName(patient.getMiddleName());
        patientWrapper.setLastName(patient.getLastName());
        patientWrapper.setForeignName(patient.getForeignName());
        if (patient.getDob() != null)
            patientWrapper.setDob(patient.getDob() + "");

        patientWrapper.setHomePhone(patient.getHomePhone());
        patientWrapper.setCellPhone(patient.getCellPhone());
        patientWrapper.setOfficePhone(patient.getOfficePhone());
        patientWrapper.setOfficeExtension(patient.getOfficeExtension());
        patientWrapper.setGender(patient.getGender().name());
        //image profile
        patientWrapper.setCountry(patient.getCountry());
        patientWrapper.setEmail(patient.getEmail());
        patientWrapper.setMarital(patient.getMaritalStatus().name());

        patientWrapper.setStatusUser(patient.getStatus().name().equalsIgnoreCase("ACTIVE"));

        //patientWrapper.setProfileStatus( patient.getStatus().name().equalsIgnoreCase("ACTIVE") );
        patientWrapper.setDisableSMSTxt(patient.getDisableSMSText()==null?false:patient.getDisableSMSText());
        patientWrapper.setPreferredCommunication(patient.getPreferredCommunication());
        patientWrapper.setReminderLanguage(patient.getReminderLanguage());
        patientWrapper.setStreetAddress(patient.getStreetAddress());
        patientWrapper.setZipCode(patient.getZipCode());
        patientWrapper.setCity(patient.getCity());
        patientWrapper.setState(patient.getState());
        patientWrapper.setFormattedAddress(patient.getFormattedAddress());
        patientWrapper.setEmergencyContactName(patient.getEmergencyContactName());
        patientWrapper.setProfileImgURL(patient.getProfileImgURL());
        patientWrapper.setPhotoFrontURL(patient.getInsurance() !=null ? patient.getInsurance().getPhotoFrontURL():null);
        patientWrapper.setPhotoBackURL(patient.getInsurance() !=null ? patient.getInsurance().getPhotoBackURL():null);
        if (patient.getEmergencyContactPhone() != null)
            patientWrapper.setEmergencyContactPhone(patient.getEmergencyContactPhone());
        if (patient.getEmergencyContactRelation() != null)
            patientWrapper.setEmergencyContactRelation(patient.getEmergencyContactRelation());
        if (patient.getSignatureOnFile() != null)
            patientWrapper.setSignatureOnFile(patient.getSignatureOnFile());
        patientWrapper.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
    }

    //Request Populate
    private void populatePatient(Patient patient, PatientWrapper patientWrapper) throws ParseException {
        patient.setTitle(patientWrapper.getTitlePrefix());
        patient.setPatientSSN(patientWrapper.getPatientSSN());
        patient.setFirstName(patientWrapper.getFirstName());
        patient.setMiddleName(patientWrapper.getMiddleName());
        patient.setLastName(patientWrapper.getLastName());
        patient.setForeignName(patientWrapper.getForeignName());
        if(!patientWrapper.getDob().isEmpty())
            patient.setDob(DateTimeUtil.getDateFromString(patientWrapper.getDob(), HISConstants.DATE_FORMATE_ONE));
        patient.setHomePhone(patientWrapper.getHomePhone());
        patient.setCellPhone(patientWrapper.getCellPhone());
        patient.setOfficePhone(patientWrapper.getOfficePhone());
        patient.setOfficeExtension(patientWrapper.getOfficeExtension());
        patient.setGender(GenderTypeEnum.valueOf(patientWrapper.getGender().toUpperCase()));
        //image profile
        patient.setCountry(patientWrapper.getCountry());
        patient.setEmail(patientWrapper.getEmail());
        patient.setMaritalStatus(MaritalStatusTypeEnum.valueOf(patientWrapper.getMarital().toUpperCase()));
        patient.setStatus(patientWrapper.isStatusUser() ? PatientStatusTypeEnum.ACTIVE : PatientStatusTypeEnum.INACTIVE);
        patient.setDisableSMSText(patientWrapper.isDisableSMSTxt());
        patient.setPreferredCommunication(patientWrapper.getPreferredCommunication());
        patient.setReminderLanguage(patientWrapper.getReminderLanguage());
        patient.setStreetAddress(patientWrapper.getStreetAddress());
        patient.setZipCode(patientWrapper.getZipCode());
        patient.setCity(patientWrapper.getCity());
        patient.setState(patientWrapper.getState());
        patient.setFormattedAddress(patientWrapper.getFormattedAddress());
        patient.setEmergencyContactName(patientWrapper.getEmergencyContactName());
        patient.setEmergencyContactPhone(patientWrapper.getEmergencyContactPhone());
        patient.setEmergencyContactRelation(patientWrapper.getEmergencyContactRelation());
        patient.setSignatureOnFile(patientWrapper.isSignatureOnFile());
        if(patient.getId() == null)
            patient.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
    }

    private void populateInsurance(Insurance insurance, PatientWrapper patientWrapper) throws ParseException {
        insurance.setCompany(patientWrapper.getCompany());
        insurance.setInsuranceID(patientWrapper.getInsuranceId());
        insurance.setGroupNumber(patientWrapper.getGroupNumber());
        insurance.setPlanName(patientWrapper.getPlanName());
        insurance.setPlanType(patientWrapper.getPlanType());

        if (!patientWrapper.getCardIssuedDate().isEmpty())
            insurance.setCardIssuedDate(DateTimeUtil.getDateFromString(patientWrapper.getCardIssuedDate(), HISConstants.DATE_FORMATE_ONE));
        if (!patientWrapper.getCardExpiryDate().isEmpty())
            insurance.setCardExpiryDate(DateTimeUtil.getDateFromString(patientWrapper.getCardExpiryDate(), HISConstants.DATE_FORMATE_ONE));
        insurance.setPrimaryInsuranceNotes(patientWrapper.getPrimaryInsuranceNotes());
    }

    private void populateInsurance(PatientWrapper patientWrapper, Patient patient) {
        if(patient.getInsurance()!=null) {
            patientWrapper.setCompany(patient.getInsurance().getCompany());
            patientWrapper.setInsuranceId(patient.getInsurance().getInsuranceID());
            patientWrapper.setGroupNumber(patient.getInsurance().getGroupNumber());
            patientWrapper.setPlanName(patient.getInsurance().getPlanName());
            patientWrapper.setPlanType(patient.getInsurance().getPlanType());
            if (patient.getInsurance().getCardIssuedDate() != null)
                patientWrapper.setCardIssuedDate(patient.getInsurance().getCardIssuedDate().toString());
            if (patient.getInsurance().getCardExpiryDate() != null)
                patientWrapper.setCardExpiryDate(patient.getInsurance().getCardExpiryDate().toString());
            patientWrapper.setPrimaryInsuranceNotes(patientWrapper.getPrimaryInsuranceNotes());
        }
    }

    public String savePatient(PatientWrapper patientWrapper) throws Exception {
        Patient patient = null;
        if(patientWrapper.getId()>0){
            patient = patientRepository.findOne(patientWrapper.getId());
            if(patient==null)
                patient = new Patient();
        }else{
            patient = new Patient();
            patient.setAdvanceBalance(0.0);
        }


        this.populatePatient(patient, patientWrapper);
        Insurance insurance = new Insurance();
        this.populateInsurance(insurance, patientWrapper);
        if(!HISCoreUtil.isNull(insurance.getCompany()))
        patient.setInsurance(insurance);
        Doctor doctor = doctorRepository.findOne(patientWrapper.getSelectedDoctor());
        patient.setPrimaryDoctor(doctor);
        List<String> racesList = patientWrapper.getRaces().stream().filter(ps -> ps.isSelected())
                .map(x -> x.getNameRace()).collect(Collectors.toList());
        patient.setRaces(racesList);

        patient = patientRepository.save(patient);
        //patientWrapper.

        /*
        Profile profile = new Profile(patientWrapper);
        UserRole userRole;
        User selectedDoctor = this.userRepository.findOne(patientWrapper.getSelectedDoctor());
        Insurance insurance = new Insurance(patientWrapper);
        User patient = new User(patientWrapper, UserTypeEnum.PATIENT.toString());
        patient.setPrimaryDoctor(selectedDoctor);


        this.profileRepository.save(profile);
        this.insuranceRepository.save(insurance);
        patient.setProfile(profile);
        patient.setInsurance(insurance);
        this.userRepository.save(patient);
        userRole = new UserRole(patient, roleRepo.findByName(UserTypeEnum.PATIENT.getValue()));
        userRoleRepository.save(userRole);

*/
        /// now saving images against user id

        ///profile photo save

        String url = null;
        if (patientWrapper.getProfileImg() != null) {
            url = userService.saveImage(patientWrapper.getProfileImg(),
                    HISConstants.S3_USER_PATIENT_PROFILE_DIRECTORY_PATH,
                    patient.getId()
                            + "_"
                            + patient.getId()
                            + "_"
                            + HISConstants.S3_USER_PROFILE_GRAPHIC_NAME,
                    patient.getId()
                            + "_"
                            + patient.getId()
                            + "_"
                            + HISConstants.S3_USER_PROFILE_GRAPHIC_NAME,
                    "/"
                            + HISConstants.S3_USER_PATIENT_PROFILE_DIRECTORY_PATH
                            + patient.getId()
                            + "_"
                            + patient.getId()
                            + "_"
                            + HISConstants.S3_USER_PROFILE_GRAPHIC_NAME);
        }


        if (HISCoreUtil.isValidObject(url)) {
            patient.setProfileImgURL(url);
            this.patientRepository.save(patient);
            url = null;
        }
        ///profile photo save

        ///front photo save

        if (patientWrapper.getPhotoFront() != null) {
            url = userService.saveImage(patientWrapper.getPhotoFront(),
                    HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH,
                    patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_FRONT_PHOTO_THUMBNAIL_GRAPHIC_NAME,
                    patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_FRONT_PHOTO_GRAPHIC_NAME,
                    "/"
                            + HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH
                            + patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_FRONT_PHOTO_THUMBNAIL_GRAPHIC_NAME);

        }
        if (HISCoreUtil.isValidObject(url)) {
            patient.getInsurance().setPhotoFrontURL(url);
            this.patientRepository.save(patient);
            url = null;
        }
        ///back photo save
        if (patientWrapper.getPhotoBack() != null) {
            url = userService.saveImage(patientWrapper.getPhotoBack(),
                    HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH,
                    patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_BACK_PHOTO_THUMBNAIL_GRAPHIC_NAME,
                    patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_BACK_PHOTO_GRAPHIC_NAME,
                    "/"
                            + HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH
                            + patient.getId()
                            + "_"
                            + patient.getInsurance().getId()
                            + "_"
                            + HISConstants.S3_USER_INSURANCE_BACK_PHOTO_THUMBNAIL_GRAPHIC_NAME);
        }

        if (HISCoreUtil.isValidObject(url)) {
            patient.getInsurance().setPhotoBackURL(url);
            this.patientRepository.save(patient);
            url = null;
        }

        return patient.getId() + "";

    }

    public boolean isEmailAlreadyExists(String email) {
        Patient patient = this.patientRepository.findAllByEmail(email);
        return patient!=null;
    }

    public int countAllPaginatedPatients() {
        return this.patientRepository.findAll().size();
    }

    public List<PatientWrapper> findAllPaginatedPatients(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<Patient> patientList = patientRepository.getAllPaginatedPatients(pageable);
        return this.getPatientWrapperList(patientList);
        /*List<PatientWrapper> patientWrapperList = new ArrayList<>();
        for (patient p: patientList) {
            patientWrapper = new PatientWrapper();
            this.populatePatientWrapper(patientWrapper, p );
            patientWrapperList.add(patientWrapper);
        }
        return patientWrapperList;*/
    }
    public List<PatientWrapper> getAllPatientList(){
        List<Patient> patient =  patientRepository.findAll();
        return this.getPatientWrapperList(patient);

    }

    public PatientWrapper getPatientById(long id) {
        Patient patient = patientRepository.findOne(id);
        PatientWrapper patientWrapper = new PatientWrapper();
        this.populatePatientWrapper(patientWrapper, patient);
        patientWrapper.setSelectedDoctor(patient.getPrimaryDoctor().getId());
        patientWrapper.setPrimaryDoctorFirstName(patient.getPrimaryDoctor().getFirstName());
        patientWrapper.setPrimaryDoctorLastName(patient.getPrimaryDoctor().getLastName());
        patientWrapper.setSmokingStatuses( patient.getSmokingStatusList()!=null ? patient.getSmokingStatusList() : null);
        this.populateRaces(patientWrapper, patient);
        this.populateAppointments(patientWrapper,patient);
        this.populateInsurance(patientWrapper, patient);
        return patientWrapper;
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findOne(id);
    }

    private void populateRaces(PatientWrapper patientWrapper, Patient patient){
        List<RaceWrapper> raceWrapperList = new ArrayList<>();
        RaceWrapper raceWrapper = null;
        for (String raceName : patient.getRaces()) {
            raceWrapper = new RaceWrapper();
            raceWrapper.setNameRace(raceName);
            raceWrapper.setSelected(true);
            raceWrapperList.add(raceWrapper);
        }
        patientWrapper.setRaces(raceWrapperList);
    }

    private void populateAppointments(PatientWrapper patientWrapper, Patient patient){
        List<AppointmentWrapper> apptFutureWrapperList = new ArrayList<>();
        List<AppointmentWrapper> apptPastWrapperList = new ArrayList<>();
        List<AppointmentWrapper>  listOfAppointments = appointmentRepository.findAllAppointmentsByPatient(patient.getId());
        Map<Boolean,List<AppointmentWrapper>> listOfApp = listOfAppointments.stream().collect(Collectors.partitioningBy(x->x.getCompareDate().toInstant().isAfter(Instant.now())));
        patientWrapper.setFutureAppointments(listOfApp.get(true));
        patientWrapper.setPastAppointments(listOfApp.get(false));
    }

    public void deletePatientById(long patientId) {
        patientRepository.delete(patientId);
    }

    public List<PatientWrapper> searchAllPaginatedPatient(int offset, int limit, String searchString) {//searchString may contain patient name or cell number
        Pageable pageable = new PageRequest(offset, limit);
        List<Patient> patientList = patientRepository.searchPatientByNameOrCellNbr(pageable, searchString.toLowerCase());
        return this.getPatientWrapperList(patientList);
    }

    private List<PatientWrapper> getPatientWrapperList(List<Patient> patientList){
        PatientWrapper patientWrapper = new PatientWrapper();
        List<PatientWrapper> patientWrapperList = new ArrayList<>();
        for (Patient p: patientList) {
            patientWrapper = new PatientWrapper();
            this.populatePatientWrapper(patientWrapper, p );
            patientWrapperList.add(patientWrapper);
        }
        return patientWrapperList;
    }

    public List<PatientWrapper> getAllPatient(){
        return   patientRepository.getAllByStatusTrue();
    }

    //Lab Order work
    public LabOrderWrapper saveLabOrder(LabOrderWrapper labOrderWrapper) {
        LabOrder labOrder = new LabOrder();

        labOrder.setComments(labOrderWrapper.getComments());
        labOrder.setStatus(labOrderWrapper.getOrderStatus());
        labOrder.setDateTest(HISCoreUtil.convertToDate(labOrderWrapper.getOrderTestDate()));
        Optional<Patient> patient = patientRepository.findById(labOrderWrapper.getPatientId());
        patient.ifPresent(labOrder::setPatient);
        Appointment appointment = appointmentRepository.findOne(labOrderWrapper.getAppointmentId());
        labOrder.setAppointment(appointment);
        List<com.sd.his.wrapper.LabTest> list = Arrays.stream(labOrderWrapper.getLabTest()).collect(Collectors.toList());
        labOrderRepository.save(labOrder);
        for (com.sd.his.wrapper.LabTest labOrder1 : list) {
            LabTest labTest = new LabTest();
            labTest.setDescription(labOrder1.getDescription());
            labTest.setNormalRange(labOrder1.getResultValue());
            labTest.setUnits(labOrder1.getUnits());
            labTest.setResultValue(labOrder1.getResultValue());
            labTest.setLabOrder(labOrder);
            labTest.setLoincCode(labOrder1.getLoincCode());
            labTestRepository.save(labTest);
        }
        return labOrderWrapper;
    }

    public List<LabOrderProjection> getAllLabOrders(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return labOrderRepository.findAllProjectedBy(pageable);
    }

    public List<LabOrderProjection> getAllLabOrdersByPatient(int offset, int limit,Long patientId) {
        Patient patient= patientRepository.findOne(patientId);
        Pageable pageable = new PageRequest(offset, limit);
        return labOrderRepository.findAllByPatient(pageable,patient);
    }


    public int totaLabOrders() {
        return (int) labOrderRepository.count();
    }

    public LabOrderProjection getLabOrderById(long id) {
        return labOrderRepository.findById(id);
    }

    public LabOrder findById(long id) {
        return labOrderRepository.findOne(id);
    }

    public LabOrderWrapper updateLabOrder(LabOrderWrapper labOrderWrapper, LabOrder labOrder) {
        labOrder.setComments(labOrderWrapper.getComments());
        labOrder.setStatus(labOrderWrapper.getOrderStatus());
        labOrder.setDateTest(HISCoreUtil.convertToDate(labOrderWrapper.getOrderTestDate()));
        Optional<Patient> patient = patientRepository.findById(labOrderWrapper.getPatientId());
        patient.ifPresent(labOrder::setPatient);
        Appointment appointment = appointmentRepository.findOne(labOrderWrapper.getAppointmentId());
        labOrder.setAppointment(appointment);
        List<com.sd.his.wrapper.LabTest> list = Arrays.stream(labOrderWrapper.getLabTest()).collect(Collectors.toList());
        List<String> lionicCodeList = list.stream().map(x -> x.getLoincCode()).collect(Collectors.toList());
        labOrderRepository.save(labOrder);
        List<LabTest> labTests = labTestRepository.findAllByLoincCodeIn(lionicCodeList);
        if (!HISCoreUtil.isListEmpty(labTests))
            labTestRepository.delete(labTests);
        for (com.sd.his.wrapper.LabTest labOrder1 : list) {
            LabTest labTest = new LabTest();
            labTest.setDescription(labOrder1.getDescription());
            labTest.setNormalRange(labOrder1.getResultValue());
            labTest.setUnits(labOrder1.getUnits());
            labTest.setResultValue(labOrder1.getResultValue());
            labTest.setLabOrder(labOrder);
            labTest.setLoincCode(labOrder1.getLoincCode());
            labTestRepository.save(labTest);
        }
        return labOrderWrapper;
    }

    @Transactional
    public boolean deleteByLabOrder(long id) {
        List<LabOrder> labOrder = labOrderRepository.findAllById(id);
        if (!HISCoreUtil.isValidObject(labOrder)) return false;
        List<LabTest> labTests = labTestRepository.findAllByLabOrderIn(labOrder);
        labTestRepository.deleteInBatch(labTests);
        int del = labOrderRepository.deleteById(id);
        if (del > 0) return true;
        return false;

    }

    //Family History

    public FamilyHistoryWrapper saveFamilyHistory(FamilyHistoryWrapper familyHistoryWrapper) {

        Patient patient = patientRepository.findOne(familyHistoryWrapper.getPatientId());
        FamilyHistory familyHistory = new FamilyHistory();
        familyHistory.setName(familyHistoryWrapper.getName());
        familyHistory.setEthnicGroup(familyHistoryWrapper.getEthnicGroup());
        familyHistory.setRelation(familyHistoryWrapper.getRelation());
        familyHistory.setStatus(familyHistoryWrapper.getStatus());
        familyHistory.setPatient(patient);
        familyHistoryRepository.save(familyHistory);

        return familyHistoryWrapper;
    }

    public List<FamilyHistoryWrapper> getAllFamilyHistoryByPatient(int offset, int limit,Long id) {
        Pageable pageable = new PageRequest(offset, limit);
        return familyHistoryRepository.findAllByPatient(id,pageable);
    }
    public List<FamilyHistoryWrapper> getAllFamilyHistory(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return familyHistoryRepository.findAllByActive(pageable);
    }
    public int familyHistoryCount() {
        return (int) familyHistoryRepository.count();
    }
    public FamilyHistory findFamilyHistoryById(Long id){
        return familyHistoryRepository.findOne(id);
    }
    public FamilyHistoryWrapper  updateFamilyHistory(FamilyHistoryWrapper familyHistoryWrapper,FamilyHistory familyHistory){
        familyHistory.setName(familyHistoryWrapper.getName());
        familyHistory.setEthnicGroup(familyHistoryWrapper.getEthnicGroup());
        familyHistory.setRelation(familyHistoryWrapper.getRelation());
        familyHistory.setStatus(familyHistoryWrapper.getStatus());
        familyHistoryRepository.save(familyHistory);
       return familyHistoryWrapper;

    }


    public boolean deleteFamilyHistory(long id) {
        if(id != 0 || id >0){
          FamilyHistory familyHistory  = familyHistoryRepository.findOne(id);
            if(HISCoreUtil.isValidObject(familyHistory)){
            familyHistoryRepository.delete(familyHistory);
            return true;
        }
        }
        return false;

    }

     public void populateSmokeStatus(SmokingStatusWrapper smokeStatusWrapper, SmokingStatus smokeStatus) throws ParseException {
        if(!smokeStatusWrapper.getEndDate().isEmpty()){
            smokeStatus.setEndDate(DateTimeUtil.getDateFromString(smokeStatusWrapper.getEndDate(), HISConstants.DATE_FORMATE_ONE));
        }
        if(!smokeStatusWrapper.getStartDate().isEmpty()){
            smokeStatus.setStartDate(DateTimeUtil.getDateFromString(smokeStatusWrapper.getStartDate(), HISConstants.DATE_FORMATE_ONE));
        }
        if(!smokeStatusWrapper.getRecordedDate().isEmpty()){
            smokeStatus.setRecordedDate(DateTimeUtil.getDateFromString(smokeStatusWrapper.getRecordedDate(), HISConstants.DATE_FORMATE_ONE));
        }
        smokeStatus.setSmokingStatus(smokeStatusWrapper.getSmokingStatus());
    }

    //smoke status
    public void savePatientSmokeStatus(SmokingStatus smokingStatus){
        smokingStatusRepository.save(smokingStatus);
    }

    public void deleteSmokeStatusById(Long smokingId){
        smokingStatusRepository.delete(smokingId);
    }
}
