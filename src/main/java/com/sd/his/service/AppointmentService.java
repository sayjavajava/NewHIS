package com.sd.his.service;

import com.google.gson.Gson;
import com.sd.his.enums.*;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.AppointmentWrapper;
import com.sd.his.wrapper.response.MedicalServicesDoctorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * @author    : Irfan Nasim
 * @Date      : 08-Jun-18
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
 * @Package   : com.sd.his.service
 * @FileName  : AppointmentService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    HISUtilService hisUtilService;
    @Autowired
    private MedicalServiceRepository medicalServiceRepository;
    @Autowired
    private DoctorMedicalServiceRepository doctorMedicalServiceRepository;


    public List<AppointmentWrapper> findAllPaginatedAppointments(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        // List<AppointmentWrapper> list= appointmentRepository.findAllPaginatedAppointments(pageable);
        //   return appointmentRepository.findAllPaginatedAppointments(pageable);
        return null;
    }

    public List<AppointmentWrapper> findAllAppointments() {
        return appointmentRepository.findAllAppointments();
    }

    public AppointmentWrapper getSingleAppointment(long id) {
        return appointmentRepository.findAllAppointmentById(id);
    }

    public Appointment findById(long id) {
        return appointmentRepository.findOne(id);
    }

    public Appointment findByNaturalId(String id) {
        return appointmentRepository.findByAppointmentId(id);
    }


    public AppointmentWrapper findAppointmentById(long id) {
        return appointmentRepository.findAppointmentById(id);
    }

    public int countAllAppointments() {
        return appointmentRepository.findAll().size();
    }

    @Transactional
    public String saveAppointment(AppointmentWrapper appointmentWrapper) {
        Appointment appointment = new Appointment();
        String result = "success";
        Patient patient = null;
        Optional<String> patientType = appointmentWrapper.getAppointmentType().stream()
                .filter(x -> x.equalsIgnoreCase("NewPatient")).findFirst();
        if (patientType.isPresent()) {
            patient = new Patient();
            // patient.setEmail(appointmentWrapper.getEmail());
            patient.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
            patient.setFirstName(appointmentWrapper.getNewPatient());
            patient.setGender(GenderTypeEnum.MALE);
            patient.setMaritalStatus(MaritalStatusTypeEnum.MARRIED);
            patient.setStatus(PatientStatusTypeEnum.ACTIVE);
            if (appointmentWrapper.getDoctorId() != null) {
                Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
                appointment.setDoctor(doctor);
            } else
                patient.setPrimaryDoctor(doctorRepository.findOne(1L));
            patient.setLastName(appointmentWrapper.getNewPatient());
            patient.setMiddleName(appointmentWrapper.getNewPatient());
            // patient.setLastName(appointmentWrapper.getNewPatient());
            patient.setCellPhone(appointmentWrapper.getCellPhone());
            //  patient.setDob(appointmentWrapper.getDateOfBirth());
            patientRepository.save(patient);
            appointment.setPatient(patient);
        }
        Branch branch = branchRepository.findOne(appointmentWrapper.getBranchId());
        // appointment.setRecurringDays(new Gson().toJson(appointmentWrapper.getSelectedRecurringDays()));
        Date scheduleDate = HISCoreUtil.convertToDate(appointmentWrapper.getScheduleDate());
        appointment.setSchdeulledDate(scheduleDate);
        Date date2 = Date.from(Instant.parse(appointmentWrapper.getScheduleDate()));
        appointment.setStartedOn(date2);
        //  appointment.setStartedOn(HISCoreUtil.convertToTime(scheduleDate));
        Date ended = HISCoreUtil.addTimetoDate(scheduleDate, appointmentWrapper.getDuration());
        appointment.setEndedOn(ended);
        appointment.setReason(appointmentWrapper.getReason());
        appointment.setNotes(appointmentWrapper.getNotes());
        appointment.setColor(appointmentWrapper.getColor());
        appointment.setType(new Gson().toJson(appointmentWrapper.getAppointmentType()));
        appointment.setDuration(appointmentWrapper.getDuration());
        appointment.setStatus(AppointmentStatusTypeEnum.valueOf(appointmentWrapper.getStatus()));
        if (appointmentWrapper.getFollowUpReminder() == true) {
            appointment.setFollowUpDate(HISCoreUtil.convertToDate(appointmentWrapper.getFollowUpDate()));
            appointment.setFollowUpReasonReminder(appointmentWrapper.getFollowUpReason());
            appointment.setFollowUpReminder(appointmentWrapper.getFollowUpReminder());
        }

        appointment.setAppointmentId(hisUtilService.getPrefixId(ModuleEnum.APPOINTMENT));
      /*  appointment.setRecurring(appointmentWrapper.isRecurringAppointment());
        appointment.setFirstAppointmentOn(HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getFirstAppointment()));
        appointment.setLastAppointmentOn(HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getLastAppointment()));
        appointment.setType(new Gson().toJson(appointmentWrapper.getAppointmentType()));
        appointment.setStatus(appointmentWrapper.getStatus());
        appointment.setFollowUpReasonReminder(appointmentWrapper.getFollowUpReason());
        appointment.setFollowUpReminder(appointmentWrapper.getFollowUpReminder());
        appointment.setFollowUpDate(HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getFollowUpDate()));*/
        appointment.setName(appointmentWrapper.getTitle());
        appointment.setBranch(branch);
        Room room = findExamRoomById(appointmentWrapper.getRoomId());
        if (HISCoreUtil.isValidObject(room)) {
            appointment.setRoom(room);
        }
        Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
        appointment.setDoctor(doctor);
        MedicalService medicalService = medicalServiceRepository.findOne(appointmentWrapper.getServiceId());
        appointment.setMedicalService(medicalService);
        /*if(appointmentWrapper.getAppointmentType().contains(AppointmentTypeEnum.NEW_PATIENT.getValue())) {
            User user = new User();
            Profile profile = new Profile();
            user.setPassword(bCryptPasswordEncoder.encode(appointmentWrapper.getTitle()));
            user.setEmail(appointmentWrapper.getEmail());
            user.setUsername(appointmentWrapper.getTitle());
            user.setUserType(String.valueOf(UserTypeEnum.PATIENT));
            user.setDeleted(false);
            user.setActive(true);

            profile.setType(String.valueOf(UserTypeEnum.PATIENT));
            profile.setFirstName(appointmentWrapper.getTitle());
            profile.setLastName(appointmentWrapper.getTitle());
            profile.setDeleted(false);
            profile.setActive(true);
            profile.setHomePhone(appointmentWrapper.getCellPhone());
            profile.setCellPhone(appointmentWrapper.getCellPhone());
            profile.setUpdatedOn(System.currentTimeMillis());
            profile.setCreatedOn(System.currentTimeMillis());
            profile.setGender(appointmentWrapper.getGender());
          //  appointment.setAge(Long.valueOf(appointmentWrapper.getAge()).longValue());
            user.setProfile(profile);
            userRepository.save(user);
            appointment.setPatient(user);
            appointmentRepository.save(appointment);
        }else {
            User user = userRepository.findByUsername(appointmentWrapper.getPatient());
            appointment.setPatient(user);
            appointmentRepository.save(appointment);
        }*/
        if (appointmentWrapper.getPatientId() != null) {
            patient = patientRepository.findOne(appointmentWrapper.getPatientId());
            appointment.setPatient(patient);
        }

        //check date time exist already
        if (!checkTimeAndDateAlreadyExist(scheduleDate,date2 ,ended,appointmentWrapper.getDoctorId())) {
            appointmentRepository.save(appointment);
        } else {
            result = "already";
        }

        return result;
    }

    @Transactional
    public String updateAppointment(AppointmentWrapper appointmentWrapper, Appointment alreadyExistAppointment) {
        String result = "success";
        Branch branch = branchRepository.findOne(appointmentWrapper.getBranchId());

        Date scheduleDate = HISCoreUtil.convertToDate(appointmentWrapper.getScheduleDate());
        alreadyExistAppointment.setSchdeulledDate(scheduleDate);
        Date date2 = Date.from(Instant.parse(appointmentWrapper.getScheduleDate()));
        alreadyExistAppointment.setStartedOn(date2);
        Date ended =HISCoreUtil.addTimetoDate(scheduleDate, appointmentWrapper.getDuration());
        alreadyExistAppointment.setEndedOn(ended);
      /*  Date scheduleDate = HISCoreUtil.convertToDate(appointmentWrapper.getScheduleDate());
        alreadyExistAppointment.setSchdeulledDate(scheduleDate);
        alreadyExistAppointment.setEndedOn(HISCoreUtil.addTimetoDate(scheduleDate,appointmentWrapper.getDuration()));*/
        alreadyExistAppointment.setReason(appointmentWrapper.getReason());
        alreadyExistAppointment.setNotes(appointmentWrapper.getNotes());
        alreadyExistAppointment.setColor(appointmentWrapper.getColor());
        alreadyExistAppointment.setType(new Gson().toJson(appointmentWrapper.getAppointmentType()));
        alreadyExistAppointment.setDuration(appointmentWrapper.getDuration());
        alreadyExistAppointment.setStatus(AppointmentStatusTypeEnum.valueOf(appointmentWrapper.getStatus()));
        //  alreadyExistAppointment.setName(appointmentWrapper.getTitle());
        alreadyExistAppointment.setBranch(branch);
        Room room = findExamRoomById(appointmentWrapper.getRoomId());
        if (HISCoreUtil.isValidObject(room)) {
            alreadyExistAppointment.setRoom(room);
        }
        Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
        alreadyExistAppointment.setDoctor(doctor);
        MedicalService medicalService = medicalServiceRepository.findOne(appointmentWrapper.getServiceId());
        alreadyExistAppointment.setMedicalService(medicalService);

        Patient patient = null;
        if (appointmentWrapper.getPatientId() != null) {
            patient = patientRepository.findOne(appointmentWrapper.getPatientId());
            alreadyExistAppointment.setPatient(patient);
        }
        if (!checkTimeAndDateAlreadyExist(scheduleDate,  date2,ended,appointmentWrapper.getDoctorId())) {
             appointmentRepository.save(alreadyExistAppointment);

        } else {
            result = "already";
        }

        return result;
    }

    public List<AppointmentWrapper> getPageableSearchedAppointments(Long doctorId, Long branchId) {
        //Pageable pageable = new PageRequest(offset, limit);
        List<AppointmentWrapper> test = appointmentRepository.findAllAppointmentsByDoctor(doctorId, branchId);
        return appointmentRepository.findAllAppointmentsByDoctor(doctorId, branchId);
    }

    public int countSearchedAppointments(Long doctorId, Long branchId) {
        Doctor doctor = doctorRepository.findOne(doctorId);
        Branch branch = branchRepository.findOne(branchId);
        return appointmentRepository.findByDoctorAndBranch(doctor, branch).size();
    }

    /*
        public Appointment updateAppointment(AppointmentWrapper appointmentWrapper,Appointment appointment) {
            Branch branch = branchRepository.getOne(appointmentWrapper.getBranchId());

            appointment.setRecurringDays(new Gson().toJson(appointmentWrapper.getSelectedRecurringDays()));
            long startTime= HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getStart());
            appointment.setStartedOn(startTime);
            appointment.setEndedOn(startTime + appointmentWrapper.getDuration()*60*1000);
            appointment.setUpdatedOn(System.currentTimeMillis());
            appointment.setDeleted(false);
            appointment.setActive(true);
            appointment.setRecurring(appointmentWrapper.isRecurringAppointment());
            appointment.setDuration(appointmentWrapper.getDuration());
    *//*
       if(HISCoreUtil.isValidObject((HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getFirstAppointment())))){
           appointment.setFirstAppointmentOn(HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getFirstAppointment()));
        }
        if(HISCoreUtil.isValidObject((HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getLastAppointment())))){
            appointment.setFirstAppointmentOn(HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getLastAppointment()));
        }
*//*
        appointment.setNotes(appointmentWrapper.getNotes());
        appointment.setColor(appointmentWrapper.getColor());
        appointment.setReason(appointmentWrapper.getReason());
        appointment.setType(new Gson().toJson(appointmentWrapper.getAppointmentType()));
        appointment.setStatus(appointmentWrapper.getStatus());
        appointment.setFollowUpReasonReminder(appointmentWrapper.getFollowUpReason());
        appointment.setFollowUpReminder(appointmentWrapper.getFollowUpReminder());
     // appointment.setFollowUpDate(HISCoreUtil.convertDateToMilliSeconds(appointmentWrapper.getFollowUpDate()));
        appointment.setFollowUpReasonReminder(appointmentWrapper.getReason());
        appointment.setName(appointmentWrapper.getTitle());
        appointment.setBranch(branch);
        roomRepository.getOne(appointmentWrapper.getRoomId());
        if(appointmentWrapper.getAppointmentType().contains(AppointmentTypeEnum.NEW_PATIENT.getValue())) {
            User user = userRepository.findByUsernameOrEmail(appointmentWrapper.getPatient(), appointmentWrapper.getEmail());
            Profile profile = user.getProfile();
            user.setUsername(appointmentWrapper.getPatient());
            user.setUserType(String.valueOf(UserTypeEnum.PATIENT));
            user.setDeleted(false);
            user.setActive(true);
            appointment.setAge(Long.valueOf(appointmentWrapper.getAge()).longValue());

            profile.setType(String.valueOf(UserTypeEnum.PATIENT));
            profile.setFirstName(appointmentWrapper.getTitle());
            profile.setLastName(appointmentWrapper.getTitle());
            profile.setDeleted(false);
            profile.setActive(true);
            profile.setHomePhone(appointmentWrapper.getCellPhone());
            profile.setCellPhone(appointmentWrapper.getCellPhone());
            profile.setUpdatedOn(System.currentTimeMillis());
            profile.setGender(appointmentWrapper.getGender());
            user.setProfile(profile);
            userRepository.save(user);
            appointment.setPatient(user);
            appointmentRepository.save(appointment);
        }else {
            appointmentRepository.save(appointment);}
            return appointment;
    }
*/
    //getAllLabOrdersById
    public Room findExamRoomById(Long id) {
        if (id != null) {
            Room room = roomRepository.findOne(id);
            return room;
        }
        return null;
    }

    public boolean changeStatus(String currentStatus, Appointment alreadyExistAppointment) {
        boolean statusChanged = false;
        alreadyExistAppointment.setStatus(AppointmentStatusTypeEnum.valueOf(currentStatus));
        Appointment appt = appointmentRepository.save(alreadyExistAppointment);
        if (HISCoreUtil.isValidObject(appt)) {
            statusChanged = true;
        }
        return statusChanged;
    }

    private boolean checkTimeAndDateAlreadyExist(Date date1,Date startedOn, Date endedOn,Long drId) {
        boolean isExist = false;
        int appointments = appointmentRepository.findAppointmentClash(date1,startedOn ,endedOn,drId);
      /*  if (appointments.size() > 0 ) {
            boolean drExist =  this.checkDoctor(appointments,drId);
            if(drExist)
            isExist = true;
        }*/

        if(appointments > 0)
            isExist =true;
         return isExist;
    }

    private boolean checkDoctor(List<Appointment> appointments, Long id) {
        boolean alreadyExist = false;
        long count = appointments.stream().filter(x -> x.getDoctor().getId() == id).count();
        if (count > 0) {
            alreadyExist = true;
        }
        return alreadyExist;
    }


    public List<MedicalServicesDoctorWrapper> getMedicalServicesAgainstDoctors() {
        return doctorMedicalServiceRepository.findAllByDoctorAndDoctor();
    }

  /*  public void deleteAppointment(Appointment appointment) {
        appointment.setDeleted(true);
        appointmentRepository.save(appointment);
    }
*/
}
