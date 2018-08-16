package com.sd.his.controller;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.service.PatientService;
import com.sd.his.service.ProblemService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.GenericAPIResponse;
import com.sd.his.wrapper.ProblemWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/*
 * @author    : Muhammad Jamal
 * @Date      : 13-August-18
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
 * @Package   : com.sd.his.controller.PatientHistoryAPI
 * @FileName  : PatientHistoryAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RestController
@RequestMapping("/patient/history")
public class PatientHistoryAPI {

    private final Logger logger = LoggerFactory.getLogger(PatientHistoryAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");
    @Autowired
    private PatientService patientService;
    @Autowired
    private ProblemService problemService;


    @ApiOperation(httpMethod = "POST", value = "Save Patient Problem",
            notes = "This method will save the patient Problem.",
            produces = "application/json", nickname = "Save Patient Problem",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save Patient Problem successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/problem/save", method = RequestMethod.POST)//, consumes = "multipart/form-data"
    public ResponseEntity<?> savePatientProblem(HttpServletRequest request,
                                                @RequestBody ProblemWrapper problemWrapper) {
        logger.info("savePatientProblem API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (problemWrapper.getSelectedICDVersionId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.save.version.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_VERSION_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("savePatientProblem API - Required version ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (problemWrapper.getSelectedCodeId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.save.code.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_CODE_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("savePatientProblem API - Required code ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            problemService.savePatientProblem(problemWrapper);

            response.setResponseMessage(messageBundle.getString("patient.problem.save.success"));
            response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("savePatientProblem API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("savePatientProblem exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "GET Paginated Problems",
            notes = "This method will return Paginated  Problems",
            produces = "application/json", nickname = "GET Paginated  Problems",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  Problems fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/problem/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedProblem(HttpServletRequest request,
                                                         @PathVariable("page") int page,
                                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        logger.error("getPaginatedProblem API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("getPaginatedProblem -  fetching from DB");
            List<ProblemWrapper> patientProblems = this.problemService.findPaginatedProblem(page, pageSize);
            int patientProblemCount = problemService.countPaginatedProblem();

            logger.error("getPaginatedProblem - fetched successfully");

            if (!HISCoreUtil.isListEmpty(patientProblems)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (patientProblemCount > pageSize) {
                    int remainder = patientProblemCount % pageSize;
                    int totalPages = patientProblemCount / pageSize;
                    if (remainder > 0) {
                        totalPages = totalPages + 1;
                    }
                    pages = new int[totalPages];
                    pages = IntStream.range(0, totalPages).toArray();
                    currPage = page;
                    nextPage = (currPage + 1) != totalPages ? currPage + 1 : null;
                    prePage = currPage > 0 ? currPage : null;
                } else {
                    pages = new int[1];
                    pages[0] = 0;
                    currPage = 0;
                    nextPage = null;
                    prePage = null;
                }

                Map<String, Object> returnValues = new LinkedHashMap<>();
                returnValues.put("nextPage", nextPage);
                returnValues.put("prePage", prePage);
                returnValues.put("currPage", currPage);
                returnValues.put("pages", pages);
                returnValues.put("data", patientProblems);

                response.setResponseMessage(messageBundle.getString("patient.problem.fetch.success"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedProblem API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getPaginatedProblem exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
