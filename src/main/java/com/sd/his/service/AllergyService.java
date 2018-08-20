package com.sd.his.service;

import com.sd.his.model.Allergy;
import com.sd.his.model.Appointment;
import com.sd.his.model.Patient;
import com.sd.his.repository.AllergyRepository;
import com.sd.his.repository.AppointmentRepository;
import com.sd.his.repository.PatientRepository;
import com.sd.his.wrapper.AllergyWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 8/20/2018.
 */
@Service
@Transactional
public class AllergyService {

    @Autowired
    AllergyRepository allergyRepository;
    @Autowired
    PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;


    public void saveAllergy(AllergyWrapper allergyWrapper) {
        Allergy allergy = new Allergy(allergyWrapper);
        Patient patient = this.patientRepository.findOne(allergyWrapper.getPatientId());
        Appointment appointment = this.appointmentRepository.findOne(allergyWrapper.getAppointmentId());

        allergy.setPatient(patient);
        allergy.setAppointment(appointment);

        this.allergyRepository.save(allergy);
    }

    public void updateAllergy(AllergyWrapper allergyWrapper) {
        Allergy allergy = this.allergyRepository.findOne(allergyWrapper.getId());
        new Allergy(allergy, allergyWrapper);
        Patient patient = this.patientRepository.findOne(allergyWrapper.getPatientId());
        Appointment appointment = this.appointmentRepository.findOne(allergyWrapper.getAppointmentId());

        allergy.setPatient(patient);
        allergy.setAppointment(appointment);

        this.allergyRepository.save(allergy);
    }

    public void deleteAllergy(long allergyId) {
        Allergy allergy = this.allergyRepository.findOne(allergyId);
        if (allergy != null) {
            this.allergyRepository.delete(allergy);
        }
    }

    public AllergyWrapper getAllergyById(long allergyId) {
        return this.allergyRepository.getAllergyById(allergyId);
    }

    public List<AllergyWrapper> getPaginatedAllergies(Pageable pageable) {
        return this.allergyRepository.getPaginatedAllergies(pageable);
    }

    public int countPaginatedAllergies() {
        return this.allergyRepository.findAll().size();
    }

    public List<AllergyWrapper> getAllAllergies(){
        return this.allergyRepository.getAllAllergies();
    }


}
