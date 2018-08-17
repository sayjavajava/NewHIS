package com.sd.his.service;

import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.utill.DateTimeUtil;
import com.sd.his.utill.HISConstants;
import com.sd.his.wrapper.ProblemWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamal on 8/15/2018.
 */
@Service
@Transactional
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ICDCodeRepository icdCodeRepository;
    @Autowired
    private ICDVersionRepository icdVersionRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;

    public void savePatientProblem(ProblemWrapper problemWrapper) throws Exception {
        Problem problem = new Problem();
        this.populateProblem(problemWrapper, problem);
        this.problemRepository.save(problem);
    }

    public void deleteProblemById(long problemId) {
        Problem problem = this.problemRepository.findOne(problemId);
        if (problem != null) {
            this.problemRepository.delete(problem);
        }
    }

    public void updatePatientProblem(ProblemWrapper problemWrapper) throws Exception {
        Problem problem = this.problemRepository.findOne(problemWrapper.getId());
        this.populateProblem(problemWrapper, problem);
        this.problemRepository.save(problem);
    }

    private void populateProblem(ProblemWrapper problemWrapper, Problem problem) throws Exception {
        Appointment appointment = null;
        if (problemWrapper.getAppointmentId()  > 0) {
            appointment = this.appointmentRepository.findOne(problemWrapper.getAppointmentId());
            if (appointment != null) {
                problem.setAppointment(appointment);
            }
        }
        if (problemWrapper.getPatientId() > 0) {
            Patient patient = this.patientRepository.findOne(problemWrapper.getPatientId());
            if (patient != null) {
                problem.setPatient(patient);
            }
        }
        if (problemWrapper.getSelectedICDVersionId() > 0) {
            ICDVersion icdVersion = this.icdVersionRepository.findOne(problemWrapper.getSelectedICDVersionId());
            if (icdVersion != null) {
                problem.setIcdVersion(icdVersion);
            }
        }
        if (problemWrapper.getSelectedCodeId() > 0) {
            ICDCode icdCode = this.icdCodeRepository.findOne(problemWrapper.getSelectedCodeId());
            if (icdCode != null) {
                problem.setIcdCode(icdCode);
            }
        }

        if (problemWrapper.getDateDiagnosis() != null) {
            problem.setDateDiagnosis(DateTimeUtil.getDateFromString(problemWrapper.getDateDiagnosis(), HISConstants.DATE_FORMATE_THREE));
        }
        if (problemWrapper.getStatus() != null) {
            problem.setStatus(problemWrapper.getStatus());
        }
        if (problemWrapper.getNote() != null) {
            problem.setNote(problemWrapper.getNote());
        }
    }

    private void populateProblemWrapper(ProblemWrapper problemWrapper, Problem problem) throws ParseException {
        if (problem.getId() != null && problem.getId() > 0) {
            problemWrapper.setId(problem.getId());
        }
        if (problem.getAppointment() != null) {
            problemWrapper.setAppointmentId(problem.getAppointment().getId());
        }
        if (problem.getPatient() != null && problem.getPatient().getId() != null && problem.getPatient().getId() > 0) {
            Patient patient = this.patientRepository.findOne(problem.getPatient().getId());
            if (patient != null) {
                problemWrapper.setPatientId(patient.getId());
            }
        }
        if (problem.getIcdVersion() != null) {
            ICDVersion icdVersion = this.icdVersionRepository.findOne(problem.getIcdVersion().getId());
            if (icdVersion != null) {
                problemWrapper.setSelectedICDVersionId(icdVersion.getId());
                problemWrapper.setVersionName(icdVersion.getName());
            }
        }
        if (problem.getIcdCode() != null) {
            ICDCode icdCode = this.icdCodeRepository.findOne(problem.getIcdCode().getId());
            if (icdCode != null) {
                problemWrapper.setSelectedCodeId(icdCode.getId());
                problemWrapper.setCodeName(icdCode.getCode());
            }
        }

        if (problem.getDateDiagnosis() != null) {
            problemWrapper.setDateDiagnosis(DateTimeUtil.getFormattedDateFromDate(problem.getDateDiagnosis(), HISConstants.DATE_FORMATE_THREE));
        }
        if (problem.getStatus() != null) {
            problemWrapper.setStatus(problem.getStatus());
        }
        if (problem.getNote() != null) {
            problemWrapper.setNote(problem.getNote());
        }
    }

    public List<ProblemWrapper> findPaginatedProblem(int pageNo, int pageSize) throws ParseException {
        List<ProblemWrapper> problemWrappers = new ArrayList<>();
        Page<Problem> problems = this.problemRepository.findAllByCreatedOnNotNull(new PageRequest(pageNo, pageSize));

        if (problems != null) {
            for (Problem problem : problems) {
                ProblemWrapper problemWrapper = new ProblemWrapper();
                this.populateProblemWrapper(problemWrapper, problem);
                problemWrappers.add(problemWrapper);
            }
        }

        return problemWrappers;
    }

    public int countPaginatedProblem() {
        return this.problemRepository.findAll().size();
    }

    public ProblemWrapper getProblemById(long id) throws ParseException {
        Problem problem = this.problemRepository.findOne(id);
        if (problem != null) {
            ProblemWrapper problemWrapper = new ProblemWrapper();
            this.populateProblemWrapper(problemWrapper, problem);
            return problemWrapper;
        }
        return null;
    }
}
