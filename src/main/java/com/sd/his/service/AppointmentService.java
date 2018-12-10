package com.sd.his.service;

import com.google.gson.Gson;
import com.sd.his.enums.*;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.AppointmentWrapper;
import com.sd.his.wrapper.response.MedicalServicesDoctorWrapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * @author    : waqas kamran
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
    @Autowired
    private StatusRepository statusRepository;


    Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    public List<AppointmentWrapper> findAllPaginatedAppointments(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<AppointmentWrapper> list = appointmentRepository.findAllPaginatedAppointments(pageable);
        return appointmentRepository.findAllPaginatedAppointments(pageable);
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
        Appointment appointmentObj;
        Appointment appointmentObj2;
        String result = "success";
        Patient patient = null;
        /*Optional<String> patientType = appointmentWrapper.getAppointmentType().stream()
                .filter(x -> x.equalsIgnoreCase("NewPatient")).findFirst();*/
        if (appointmentWrapper.getStateOfPatientBox()) {
            patient = this.buildPatient(appointmentWrapper);
            appointment.setPatient(patient);
        }
        appointment = this.buildAppointment(appointmentWrapper, appointment);

        if (appointmentWrapper.isRecurringAppointment()) {
            //  LocalDate start = LocalDate.parse( new SimpleDateFormat("dd-MM-yyyy").format(appointmentWrapper.getFirstAppointment()));
            LocalDate start = HISCoreUtil.convertDateToLocalDate(appointmentWrapper.getFirstAppointment());
            LocalDate end = HISCoreUtil.convertDateToLocalDate(appointmentWrapper.getLastAppointment());
            List<LocalDate> listOfDates = new ArrayList<>();
            //  LocalDate testEnd = LocalDate.of(2018,12,07);
            List<LocalDate> dates = Stream.iterate(start, date -> date.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(start, end))
                    .collect(Collectors.toList());
            for (LocalDate locald : dates) {
                for (String s : appointmentWrapper.getSelectedRecurringDays()) {
                    if (locald.getDayOfWeek().toString().equalsIgnoreCase(s)) {
                        listOfDates.add(locald);
                    }
                }
            }

            if (!HISCoreUtil.isListEmpty(listOfDates)) {
                for (LocalDate localDate1 : listOfDates) {
                    boolean isAlreadyExist = this.checkTimeAndDateAlreadyExist(HISCoreUtil.convertLocalDateToDate(localDate1), appointment.getStartedOn(), appointment.getEndedOn(), appointmentWrapper.getDoctorId());
                    if (!isAlreadyExist) {
                        appointmentObj = new Appointment();
                        appointmentObj2 = this.buildAppointment(appointmentWrapper, appointmentObj);
                        appointmentObj2.setSchdeulledDate(HISCoreUtil.convertLocalDateToDate(localDate1));
                        appointmentRepository.save(appointmentObj2);
                    }
                }
            }
        }
        //check date time exist already
        if (!checkTimeAndDateAlreadyExist(appointment.getSchdeulledDate(), appointment.getStartedOn(), appointment.getEndedOn(), appointmentWrapper.getDoctorId())) {
            appointmentRepository.save(appointment);
        } else {
            result = "already";
        }

        return result;
    }

    private Patient buildPatient(AppointmentWrapper appointmentWrapper) {
        Patient patient = new Patient();
        patient.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
        patient.setFirstName(appointmentWrapper.getNewPatient());
        patient.setGender(GenderTypeEnum.MALE);
        patient.setMaritalStatus(MaritalStatusTypeEnum.MARRIED);
        patient.setStatus(PatientStatusTypeEnum.ACTIVE);
        /*if (appointmentWrapper.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
            appointment.setDoctor(doctor);
        } else
            patient.setPrimaryDoctor(doctorRepository.findOne(1L));*/
        patient.setLastName(appointmentWrapper.getNewPatient());
        patient.setMiddleName(appointmentWrapper.getNewPatient());
        // patient.setLastName(appointmentWrapper.getNewPatient());
        patient.setCellPhone(appointmentWrapper.getCellPhone());
        //  patient.setDob(appointmentWrapper.getDateOfBirth());
        patientRepository.save(patient);
        return patient;
    }

    private Appointment buildAppointment(AppointmentWrapper appointmentWrapper, Appointment appointment) {
        Patient patient = new Patient();
        Branch branch = branchRepository.findOne(appointmentWrapper.getBranchId());
        // appointment.setRecurringDays(new Gson().toJson(appointmentWrapper.getSelectedRecurringDays()));
        Date scheduleDate = appointmentWrapper.getDateSchedule();
        appointment.setSchdeulledDate(scheduleDate);

        //Date date2 = Date.from(Instant.parse(appointmentWrapper.getScheduleDateAndTime()));
        LocalDateTime date2 = HISCoreUtil.convertToLocalDateTimeViaSqlTimestamp(appointment.getSchdeulledDate());

        appointment.setStartedOn(HISCoreUtil.convertToDateViaLocalDateTime(date2));
        //  appointment.setStartedOn(HISCoreUtil.convertToTime(scheduleDate));
        Date ended = HISCoreUtil.addTimetoDate(scheduleDate, appointmentWrapper.getDuration());
        appointment.setEndedOn(ended);
        appointment.setReason(appointmentWrapper.getReason());
        appointment.setNotes(appointmentWrapper.getNotes());
        appointment.setColor(appointmentWrapper.getColor());
        appointment.setType(new Gson().toJson(appointmentWrapper.getAppointmentType()));
        appointment.setDuration(appointmentWrapper.getDuration());
        // appointment.setStatus(AppointmentStatusTypeEnum.valueOf(appointmentWrapper.getStatus()));
        if (appointmentWrapper.getFollowUpReminder() == true) {
            // appointment.setFollowUpDate(HISCoreUtil.convertToDate(appointmentWrapper.getFollowUpDate()));
            appointment.setFollowUpDate(appointmentWrapper.getFollowUpDate());
            appointment.setFollowUpReasonReminder(appointmentWrapper.getFollowUpReason());
            appointment.setFollowUpReminder(appointmentWrapper.getFollowUpReminder());
        }
        appointment.setAppointmentId(hisUtilService.getPrefixId(ModuleEnum.APPOINTMENT));
        appointment.setBranch(branch);
        Room room = findExamRoomById(appointmentWrapper.getRoomId());
        if (HISCoreUtil.isValidObject(room)) {
            appointment.setRoom(room);
        }
        Doctor doctor = doctorRepository.findOne(appointmentWrapper.getDoctorId());
        appointment.setDoctor(doctor);
        MedicalService medicalService = medicalServiceRepository.findOne(appointmentWrapper.getServiceId());
        appointment.setMedicalService(medicalService);
        if (appointmentWrapper.getPatientId() != null) {
            patient = patientRepository.findOne(appointmentWrapper.getPatientId());
            appointment.setPatient(patient);
        }
        if (appointmentWrapper.getStatusId() != null) {
            Status apptStatus = statusRepository.findOne(appointmentWrapper.getStatusId());
            appointment.setStatus(apptStatus);
        }
        return appointment;
    }

    @Transactional
    public String updateAppointment(AppointmentWrapper appointmentWrapper, Appointment alreadyExistAppointment) {
        String result = "success";
        Branch branch = branchRepository.findOne(appointmentWrapper.getBranchId());

        Date scheduleDate = HISCoreUtil.convertToDate(appointmentWrapper.getScheduleDate());
        alreadyExistAppointment.setSchdeulledDate(scheduleDate);
        Date date2 = Date.from(Instant.parse(appointmentWrapper.getScheduleDate()));
        alreadyExistAppointment.setStartedOn(date2);
        Date ended = HISCoreUtil.addTimetoDate(scheduleDate, appointmentWrapper.getDuration());
        alreadyExistAppointment.setEndedOn(ended);
      /*  Date scheduleDate = HISCoreUtil.convertToDate(appointmentWrapper.getScheduleDate());
        alreadyExistAppointment.setSchdeulledDate(scheduleDate);
        alreadyExistAppointment.setEndedOn(HISCoreUtil.addTimetoDate(scheduleDate,appointmentWrapper.getDuration()));*/
        alreadyExistAppointment.setReason(appointmentWrapper.getReason());
        alreadyExistAppointment.setNotes(appointmentWrapper.getNotes());
        alreadyExistAppointment.setColor(appointmentWrapper.getColor());
        alreadyExistAppointment.setType(new Gson().toJson(appointmentWrapper.getAppointmentType()));
        alreadyExistAppointment.setDuration(appointmentWrapper.getDuration());
        //   alreadyExistAppointment.setStatus(AppointmentStatusTypeEnum.valueOf(appointmentWrapper.getStatus()));
        //  alreadyExistAppointment.setName(appointmentWrapper.getProblem());
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
        if (appointmentWrapper.getStatusId() != null) {
            Status apptStatus = statusRepository.findOne(appointmentWrapper.getStatusId());
            alreadyExistAppointment.setStatus(apptStatus);
        }
        if (!checkTimeAndDateAlreadyExistForUpdate(scheduleDate, date2, ended, appointmentWrapper.getDoctorId(), appointmentWrapper.getAppointmentId())) {
            appointmentRepository.save(alreadyExistAppointment);

        } else {
            result = "already";
        }

        return result;
    }

    public List<AppointmentWrapper> getPageableSearchedAppointments(Long doctorId, Long branchId) {
        return appointmentRepository.findAllAppointmentsByDoctor(doctorId, branchId);
    }

    public List<AppointmentWrapper> searchAppointmentByPatients(String patientName, int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return appointmentRepository.searchAllAppointmentsByPatients(patientName, pageable);
    }

    public int countSearchedAppointments(Long doctorId, Long branchId) {
        Doctor doctor = doctorRepository.findOne(doctorId);
        Branch branch = branchRepository.findOne(branchId);
        return appointmentRepository.findByDoctorAndBranch(doctor, branch).size();
    }

    public int countSearchedAppointmentsByPatient(String patientName) {
        return appointmentRepository.countAllByPatientFirstName(patientName);
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
        appointment.setName(appointmentWrapper.getProblem());
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
            profile.setFirstName(appointmentWrapper.getProblem());
            profile.setLastName(appointmentWrapper.getProblem());
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
        //  alreadyExistAppointment.setStatus(AppointmentStatusTypeEnum.valueOf(currentStatus));
        Appointment appt = appointmentRepository.save(alreadyExistAppointment);
        if (HISCoreUtil.isValidObject(appt)) {
            statusChanged = true;
        }
        return statusChanged;
    }

    private boolean checkTimeAndDateAlreadyExist(Date date1, Date startedOn, Date endedOn, Long drId) {
        boolean isExist = false;
        int appointments = appointmentRepository.findAppointmentClash(date1, startedOn, endedOn, drId);
        if (appointments > 1)
            isExist = true;
        return isExist;
    }

    private boolean checkTimeAndDateAlreadyExistForUpdate(Date date1, Date startedOn, Date endedOn, Long drId, String aptId) {
        boolean isExist = false;
        List<Appointment> appointments = appointmentRepository.findAppointmentClashForUpdate(date1, startedOn, endedOn, drId);
        for (Appointment apt : appointments) {
            if (!(apt.getAppointmentId().equalsIgnoreCase(aptId)))
                isExist = true;
        }
        return isExist;
    }

    public String updateAppointmentRoom(String  roomId, Appointment alreadyAppointment) {
        String apt = "";
        long rId = Long.valueOf(roomId);
        if (rId !=0) {
            Room roomObj = roomRepository.findOne(rId);
            alreadyAppointment.setRoom(roomObj);
            appointmentRepository.save(alreadyAppointment);
            return "success";
        }
        return apt;
    }


    public List<MedicalServicesDoctorWrapper> getMedicalServicesAgainstDoctors() {
        return doctorMedicalServiceRepository.findAllByDoctorAndServices();
    }

    /*
    public void deleteAppointment(Appointment appointment) {
        appointment.setDeleted(true);
        appointmentRepository.save(appointment);
    }
    */
}
