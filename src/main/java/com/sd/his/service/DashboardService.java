package com.sd.his.service;


import com.sd.his.repository.AppointmentRepository;
import com.sd.his.repository.PatientRepository;
import com.sd.his.wrapper.response.DashboardResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

@Autowired
private PatientRepository patientRepository;
@Autowired
private AppointmentRepository appointmentRepository;

public List<DashboardResponseWrapper> getDoctorDashboard(){
   // appointmentRepository.findAllAppointments();
    return  null;
}
}
