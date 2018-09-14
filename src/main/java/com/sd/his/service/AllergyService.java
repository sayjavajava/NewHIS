package com.sd.his.service;

import com.sd.his.model.Allergy;
import com.sd.his.model.Appointment;
import com.sd.his.model.Patient;
import com.sd.his.repository.AllergyRepository;
import com.sd.his.repository.AppointmentRepository;
import com.sd.his.repository.PatientRepository;
import com.sd.his.wrapper.AllergyWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamal on 8/20/2018.
 */
@Service
@Transactional
public class AllergyService {

    @Autowired
    private AllergyRepository allergyRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;


    public void saveAllergy(AllergyWrapper allergyWrapper) throws Exception {
        Allergy allergy = new Allergy(allergyWrapper);
        Patient patient = this.patientRepository.findOne(allergyWrapper.getPatientId());
        Appointment appointment = this.appointmentRepository.findOne(allergyWrapper.getAppointmentId());

        if (patient != null) {
            allergy.setPatient(patient);
        } else {
            throw new Exception("Patient not found");
        }
        if (appointment != null) {
            allergy.setAppointment(appointment);
        } else {
            throw new Exception("Appoint not found");
        }
        this.allergyRepository.save(allergy);
    }

    public boolean updateAllergy(AllergyWrapper allergyWrapper) {
        Allergy allergy = this.allergyRepository.findOne(allergyWrapper.getId());
        if (allergy != null){
            new Allergy(allergy, allergyWrapper);
            Patient patient = this.patientRepository.findOne(allergyWrapper.getPatientId());
            Appointment appointment = this.appointmentRepository.findOne(allergyWrapper.getAppointmentId());

            allergy.setPatient(patient);
            allergy.setAppointment(appointment);

            this.allergyRepository.save(allergy);
            return true;
        }
        return false;
    }

    public boolean deleteAllergyById(long allergyId) {
        Allergy allergy = this.allergyRepository.findOne(allergyId);
        if (allergy != null) {
            this.allergyRepository.delete(allergy);
            return true;
        }
        return false;
    }

    public AllergyWrapper getAllergyById(long allergyId) {
        return this.allergyRepository.getAllergyById(allergyId);
    }

    public List<AllergyWrapper> getPaginatedAllergies(Pageable pageable,Long patientId) {
        return this.allergyRepository.getPaginatedAllergies(pageable,patientId);
    }

    public int countPaginatedAllergies(Long patientId) {
        return this.allergyRepository.countAllByPatient_Id(patientId);
    }

    public List<AllergyWrapper> getAllAllergies() {
        return this.allergyRepository.getAllAllergies();
    }


    public List<AllergyWrapper> getPaginatedAllergiesByStatusAndPatientId(Pageable pageable, String status, Long aLong) {
        return this.allergyRepository.getAllAllergiesByStatusAndPatientId(pageable,status,aLong);
    }

    public int countPaginatedAllergiesByStatusAndPatientId(String status, Long aLong) {
        return this.allergyRepository.countAllAllergiesByStatusAndPatientId(status,aLong).size();
    }
}
