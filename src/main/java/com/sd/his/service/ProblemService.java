package com.sd.his.service;

import com.sd.his.model.ICDCode;
import com.sd.his.model.ICDVersion;
import com.sd.his.model.Problem;
import com.sd.his.repository.ICDCodeRepository;
import com.sd.his.repository.ICDVersionRepository;
import com.sd.his.repository.ProblemRepository;
import com.sd.his.utill.DateTimeUtil;
import com.sd.his.utill.HISConstants;
import com.sd.his.wrapper.ProblemWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void savePatientProblem(ProblemWrapper problemWrapper) throws ParseException {

        Problem problem = new Problem();
        this.populateProblem(problemWrapper, problem);


        this.problemRepository.save(problem);

    }

    private void populateProblem(ProblemWrapper problemWrapper, Problem problem) throws ParseException {
        if (problemWrapper.getId() != null && problemWrapper.getId() > 0) {
            problem.setId(problemWrapper.getId());
        }
        if (problemWrapper.getSelectedICDVersionId() > 0) {
            ICDVersion icdVersion = this.icdVersionRepository.findOne(problemWrapper.getSelectedICDVersionId());
            if (icdVersion != null) {
                problem.setIcdVersion(icdVersion);
            } else {
                new Exception("Version not found");
            }
        } else {
            new Exception("Please provide Version ");
        }
        if (problemWrapper.getSelectedCodeId() > 0) {
            ICDCode icdCode = this.icdCodeRepository.findOne(problemWrapper.getSelectedCodeId());
            if (icdCode != null) {
                problem.setIcdCode(icdCode);
            } else {
                new Exception("Code not found");
            }
        } else {
            new Exception("Please provide code");
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
            problemWrapper.setDateDiagnosis(problem.getDateDiagnosis() + "");
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

}
