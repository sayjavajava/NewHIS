package com.sd.his.service;

import com.sd.his.model.Appointment;
import com.sd.his.model.Medication;
import com.sd.his.model.Patient;
import com.sd.his.repository.AppointmentRepository;
import com.sd.his.repository.MedicationRepository;
import com.sd.his.repository.PatientRepository;
import com.sd.his.wrapper.MedicationWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 8/28/2018.
 */
@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public void saveMedication(MedicationWrapper medicationWrapper) throws Exception {
        Medication medication = new Medication(medicationWrapper);

        Patient patient = this.patientRepository.findOne(medicationWrapper.getPatientId());
        medication.setPatient(patient);

        Appointment appointment = this.appointmentRepository.findOne(medicationWrapper.getAppointmentId());
        medication.setAppointment(appointment);

        this.medicationRepository.save(medication);
    }

    @Transactional
    public boolean updateMedication(MedicationWrapper medicationWrapper) throws Exception {
        Medication medication = this.medicationRepository.findOne(medicationWrapper.getId());
        if (medication != null){
            Patient patient = this.patientRepository.findOne(medicationWrapper.getPatientId());
            new Medication(medication, medicationWrapper);
            medication.setPatient(patient);

            Appointment appointment = this.appointmentRepository.findOne(medicationWrapper.getAppointmentId());
            medication.setAppointment(appointment);

            this.medicationRepository.save(medication);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteMedicationById(long medicationId) {
        Medication medication = this.medicationRepository.findOne(medicationId);
        if (medication != null) {
            this.medicationRepository.delete(medication);
            return true;
        }
        return false;
    }

    public MedicationWrapper getMedication(long medicationId) {
        return this.medicationRepository.getMedicationById(medicationId);
    }

    public List<MedicationWrapper> getPaginatedMedications(Pageable pageable) {
        return this.medicationRepository.getPaginatedMedications(pageable);
    }
    public int countPaginatedMedications() {
        return this.medicationRepository.findAll().size();
    }


}
