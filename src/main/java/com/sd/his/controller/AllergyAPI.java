package com.sd.his.controller;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.service.AllergyService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.AllergyWrapper;
import com.sd.his.wrapper.GenericAPIResponse;
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

/**
 * Created by jamal on 8/20/2018.
 */
@RestController
@RequestMapping("/patient/allergy")
public class AllergyAPI {

    Logger logger = LoggerFactory.getLogger(AppointmentAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private AllergyService allergyService;


    @ApiOperation(httpMethod = "POST", value = "Save Allergy ",
            notes = "This method will save the allergy .",
            produces = "application/json", nickname = "Save Allergy ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save Allergy  successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveAllergy(HttpServletRequest request,
                                         @RequestBody AllergyWrapper allergyWrapper) {
        logger.info("saveAllergy API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (allergyWrapper.getAppointmentId() <= 0) {
                response.setResponseMessage(messageBundle.getString("allergy.save.appointment.required"));
                response.setResponseCode(ResponseEnum.ALLERGY_SAVE_APPOINTMENT_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveAllergy API - Required appointment id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (allergyWrapper.getPatientId() <= 0) {
                response.setResponseMessage(messageBundle.getString("allergy.save.patient.required"));
                response.setResponseCode(ResponseEnum.ALLERGY_SAVE_PATIENT_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveAllergy API - Required patient id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (HISCoreUtil.isNull(allergyWrapper.getName())) {
                response.setResponseMessage(messageBundle.getString("allergy.save.name.required"));
                response.setResponseCode(ResponseEnum.ALLERGY_SAVE_NAME_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveAllergy API - Required Name of allergy ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            this.allergyService.saveAllergy(allergyWrapper);

            response.setResponseMessage(messageBundle.getString("allergy.save.success"));
            response.setResponseCode(ResponseEnum.ALLERGY_SAVE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("saveAllergy API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveAllergy exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "GET Paginated Allergies",
            notes = "This method will return Paginated  Allergies",
            produces = "application/json", nickname = "GET Paginated  Allergies",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  Allergies fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedAllergy(HttpServletRequest request,
                                                 @PathVariable("page") int page,
                                                 @RequestParam(value = "pageSize",
                                                         required = false, defaultValue = "10") int pageSize) {

        logger.error("getPaginatedAllergy API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("getPaginatedAllergy -  fetching from DB");
            List<AllergyWrapper> allergyWrappers = this.allergyService.getPaginatedAllergies(page, pageSize);
            int allergyWrappersCount = allergyService.countPaginatedAllergies();

            logger.error("getPaginatedAllergy - fetched successfully");

            if (!HISCoreUtil.isListEmpty(allergyWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (allergyWrappersCount > pageSize) {
                    int remainder = allergyWrappersCount % pageSize;
                    int totalPages = allergyWrappersCount / pageSize;
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
                returnValues.put("data", allergyWrappers);

                response.setResponseMessage(messageBundle.getString("allergy.paginated.success"));
                response.setResponseCode(ResponseEnum.ALLERGY_PAGINATED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedAllergy API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getPaginatedAllergy exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "GET", value = "Patient",
            notes = "This method will return User on base of id",
            produces = "application/json", nickname = "Get Single User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Patient found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<?> getAllergyById(HttpServletRequest request, @RequestParam("allergyId") int id) {

        GenericAPIResponse response = new GenericAPIResponse();
        try {
            AllergyWrapper allergyWrapper = this.allergyService.getAllergyById(id);
            if (HISCoreUtil.isValidObject(allergyWrapper)) {
                response.setResponseData(allergyWrapper);
                response.setResponseMessage(messageBundle.getString("allergy.get.success"));
                response.setResponseCode(ResponseEnum.ALLERGY_GET_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("getAllergyById User Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("already.deleted"));
                response.setResponseCode(ResponseEnum.ALLERGY_GET_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("getAllergyById Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllergyById Exception", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Update Patient Allergy",
            notes = "This method will Update the patient Allergy.",
            produces = "application/json", nickname = "Update Patient Allergy",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update Patient Allergy successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAllergy(HttpServletRequest request,
                                           @RequestBody AllergyWrapper allergyWrapper) {
        logger.info("updatePatientProblem API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (allergyWrapper.getAppointmentId() <= 0) {
                response.setResponseMessage(messageBundle.getString("allergy.save.appointment.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_VERSION_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateAllergy API - Required version ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (allergyWrapper.getPatientId() <= 0) {
                response.setResponseMessage(messageBundle.getString("allergy.save.patient.required"));
                response.setResponseCode(ResponseEnum.ALLERGY_SAVE_PATIENT_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updatePatientProblem API - Required patient id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            if (HISCoreUtil.isNull(allergyWrapper.getName())) {
                response.setResponseMessage(messageBundle.getString("allergy.save.name.required"));
                response.setResponseCode(ResponseEnum.ALLERGY_SAVE_NAME_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updatePatientProblem API - Required Name of allergy ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.allergyService.updateAllergy(allergyWrapper)) {
                response.setResponseMessage(messageBundle.getString("allergy.update.success"));
                response.setResponseCode(ResponseEnum.ALLERGY_UPDATE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateAllergy API - successfully saved.");
            }else {
                response.setResponseMessage(messageBundle.getString("already.deleted"));
                response.setResponseCode(ResponseEnum.ALLERGY_UPDATE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateAllergy API - successfully saved.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateAllergy exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "DELETE", value = "Delete Patient Problem",
            notes = "This method will Delete the Patient Problem",
            produces = "application/json", nickname = "Delete Patient Problem",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted Patient Problem successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{allergyId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllergy(HttpServletRequest request,
                                           @PathVariable("allergyId") long allergyId) {
        logger.info("deleteAllergy API - Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (allergyId <= 0) {
                response.setResponseMessage(messageBundle.getString("allergy.delete.id.required"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteAllergy API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (this.allergyService.deleteAllergyById(allergyId)) {
                response.setResponseMessage(messageBundle.getString("allergy.delete.success"));
                response.setResponseCode(ResponseEnum.ALLERGY_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("deleteAllergy API - Deleted Successfully...");
            } else {
                response.setResponseMessage(messageBundle.getString("already.deleted"));
                response.setResponseCode(ResponseEnum.ALLERGY_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("deleteAllergy API - Deleted Successfully...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("deleteAllergy API - deleted failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
