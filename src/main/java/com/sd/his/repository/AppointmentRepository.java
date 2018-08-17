package com.sd.his.repository;

import com.sd.his.model.Appointment;
import com.sd.his.model.Branch;
import com.sd.his.model.Doctor;
import com.sd.his.wrapper.AppointmentWrapper;
import com.sd.his.wrapper.response.BranchResponseWrapper;
import com.sd.his.wrapper.response.DashboardResponseWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
/*   @Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id, a.name, a.notes, a.reason, a.color, a.status, a.type," +
            " a.duration, a.followUpReminder, a.followUpReasonReminder, a.startedOn, a.endedOn, a.createdOn, a.updatedOn, " +
            "a.recurring, a.recurringDays, a.firstAppointmentOn, a.lastAppointmentOn, a.patient.id,a.patient.username, " +
            "a.patient.profile.firstName, a.patient.profile.lastName, a.branch.id, a.branch.name, a.room.id, a.room.examName) " +
            "FROM Appointment a WHERE a.deleted =FALSE ")
    List<AppointmentWrapper> findAllPaginatedAppointments(Pageable pageable);*/
    @Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id,a.appointmentId, a.name, a.notes, a.reason, a.color, a.status, a.type," +
            " a.duration, a.followUpReminder, a.followUpReasonReminder,a.schdeulledDate, a.startedOn, a.endedOn, a.createdOn, a.updatedOn, " +
            "a.recurring, a.firstAppointmentOn, a.lastAppointmentOn, a.patient.firstName,a.patient.lastName,a.patient.id,a.branch.id, a.branch.name,a.room.id,a.room.roomName,a.doctor.firstName,a.doctor.lastName,a.doctor.id) " +
            "FROM Appointment a")
    List<AppointmentWrapper> findAllAppointments();
    //a.patient.id, a.branch.id, a.branch.name, a.room.id, a.room.roomName

    @Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id, a.name, a.notes) "+
            "FROM Appointment a")
    List<AppointmentWrapper> findAllByAppointmentId();

    @Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id,a.appointmentId ,a.name, a.notes, a.reason, a.color, a.status, a.type," +
            " a.duration, a.followUpReminder, a.followUpReasonReminder,a.schdeulledDate, a.startedOn, a.endedOn, a.createdOn, a.updatedOn, " +
            "a.recurring, a.firstAppointmentOn, a.lastAppointmentOn, a.patient.firstName,a.patient.lastName,a.patient.id,a.branch.id, a.branch.name,a.room.id,a.room.roomName,a.doctor.firstName,a.doctor.lastName,a.doctor.id) " +
            "FROM Appointment a WHERE a.doctor.id =?1 or a.branch.id =?2")
    List<AppointmentWrapper> findAllAppointmentsByDoctor(Long doctorId,Long branchId);

    @Query("SELECT NEW  com.sd.his.wrapper.AppointmentWrapper(a.id,a.appointmentId ,a.name, a.notes, a.reason, a.color, a.status, a.type," +
            " a.duration, a.followUpReminder, a.followUpReasonReminder,a.schdeulledDate, a.startedOn, a.endedOn, a.createdOn, a.updatedOn, " +
            "a.recurring, a.firstAppointmentOn, a.lastAppointmentOn, a.patient.firstName,a.patient.lastName,a.patient.id,a.branch.id, a.branch.name,a.room.id,a.room.roomName,a.doctor.firstName,a.doctor.lastName,a.doctor.id) " +
            "FROM Appointment a WHERE a.patient.id =?1 ")
    List<AppointmentWrapper> findAllAppointmentsByPatient(Long patientId);

    List<Appointment> findByDoctorAndBranch(Doctor doctor,Branch branch);

    @Query("SELECT NEW  com.sd.his.wrapper.response.DashboardResponseWrapper(a.id,a.appointmentId,a.patient.firstName,a.patient.lastName,a.schdeulledDate ,a.doctor.firstName,a.doctor.lastName,a.branch.name,a.reason,a.schdeulledDate,a.room.roomName, a.status, a.branch.id,a.doctor.id,a.room.id )" +
            "FROM Appointment a")
    List<DashboardResponseWrapper> findAllAppointmentsByPatientAndDoctor();

  //  DashboardResponseWrapper(Long appointmentId,String patientFirstName,String patientLastName,String inTime, String doctorFirstName,String doctorLastName, String branch, String group, String scheduleAt, String examRoom, String status, Long branchId, Long doctorId, Long roomId)

    }

