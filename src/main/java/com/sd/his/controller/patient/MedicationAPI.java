package com.sd.his.controller.patient;

import com.sd.his.controller.AppointmentAPI;
import com.sd.his.enums.ResponseEnum;
import com.sd.his.service.MedicationService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.DocumentWrapper;
import com.sd.his.wrapper.GenericAPIResponse;
import com.sd.his.wrapper.MedicationWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
 * Created by jamal on 8/28/2018.
 */
@RestController
@RequestMapping("/patient/medication")
public class MedicationAPI {


    Logger logger = LoggerFactory.getLogger(AppointmentAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private MedicationService medicationService;

    @ApiOperation(httpMethod = "POST", value = "Save Medication ",
            notes = "This method will save the Medication .",
            produces = "application/json", nickname = "Save Medication ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save Medication  successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveMedication(HttpServletRequest request,
                                            @RequestBody MedicationWrapper medicationWrapper) {
        logger.info("saveMedication API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (medicationWrapper.getAppointmentId() <= 0) {
                response.setResponseMessage(messageBundle.getString("medication.save.appointment.required"));
                response.setResponseCode(ResponseEnum.MEDICATION_SAVE_APPOINTMENT_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveMedication API - Required appointment id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (medicationWrapper.getPatientId() <= 0) {
                response.setResponseMessage(messageBundle.getString("medication.save.patient.required"));
                response.setResponseCode(ResponseEnum.MEDICATION_SAVE_PATIENT_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveMedication API - Required patient id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (HISCoreUtil.isNull(medicationWrapper.getDrugName())) {
                response.setResponseMessage(messageBundle.getString("medication.save.name.required"));
                response.setResponseCode(ResponseEnum.MEDICATION_SAVE_NAME_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveMedication API - Required Name of allergy ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            this.medicationService.saveMedication(medicationWrapper);
            response.setResponseMessage(messageBundle.getString("medication.save.success"));
            response.setResponseCode(ResponseEnum.MEDICATION_SAVE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("saveMedication API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveMedication exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "GET Paginated Medications",
            notes = "This method will return Paginated  Medications",
            produces = "application/json", nickname = "GET Paginated Medications",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  Medications fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedMedication(HttpServletRequest request,
                                                    @PathVariable("page") int page,
                                                    @RequestParam("selectedPatientId") String selectedPatientId,
                                                    @RequestParam(value = "pageSize",
                                                            required = false, defaultValue = "10") int pageSize) {

        logger.error("getPaginatedMedication API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("getPaginatedMedication -  fetching from DB");
            Pageable pageable = new PageRequest(page, pageSize);
            List<MedicationWrapper> medicationWrappers = this.medicationService.getPaginatedMedications(pageable,Long.valueOf(selectedPatientId));
            int medicationWrappersCount = this.medicationService.countPaginatedMedications(Long.valueOf(selectedPatientId));

            logger.error("getPaginatedMedication - fetched successfully");

            if (!HISCoreUtil.isListEmpty(medicationWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (medicationWrappersCount > pageSize) {
                    int remainder = medicationWrappersCount % pageSize;
                    int totalPages = medicationWrappersCount / pageSize;
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
                returnValues.put("data", medicationWrappers);

                response.setResponseMessage(messageBundle.getString("medication.paginated.success"));
                response.setResponseCode(ResponseEnum.MEDICATION_PAGINATED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedMedication API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getPaginatedMedication exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Medication",
            notes = "This method will return Medication on base of id",
            produces = "application/json", nickname = "Get Medication",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Medication found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<?> getMedicationById(HttpServletRequest request, @RequestParam("medicationId") int id) {

        GenericAPIResponse response = new GenericAPIResponse();
        try {
            MedicationWrapper medicationWrapper = this.medicationService.getMedication(id);
            if (HISCoreUtil.isValidObject(medicationWrapper)) {
                response.setResponseData(medicationWrapper);
                response.setResponseMessage(messageBundle.getString("medication.get.success"));
                response.setResponseCode(ResponseEnum.MEDICATION_GET_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("getMedicationById User Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("already.deleted"));
                response.setResponseCode(ResponseEnum.MEDICATION_GET_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("getMedicationById Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getMedicationById Exception", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Update patient Mediation",
            notes = "This method will Update the patient Mediation.",
            produces = "application/json", nickname = "Update patient Mediation",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update patient Mediation successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMediation(HttpServletRequest request,
                                             @RequestBody MedicationWrapper medicationWrapper) {
        logger.info("updateMediation API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (medicationWrapper.getAppointmentId() <= 0) {
                response.setResponseMessage(messageBundle.getString("medication.save.appointment.required"));
                response.setResponseCode(ResponseEnum.MEDICATION_SAVE_APPOINTMENT_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateMediation API - Required version ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (medicationWrapper.getPatientId() <= 0) {
                response.setResponseMessage(messageBundle.getString("medication.save.patient.required"));
                response.setResponseCode(ResponseEnum.MEDICATION_SAVE_PATIENT_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateMediation API - Required patient id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            if (HISCoreUtil.isNull(medicationWrapper.getDrugName())) {
                response.setResponseMessage(messageBundle.getString("medication.save.name.required"));
                response.setResponseCode(ResponseEnum.MEDICATION_SAVE_NAME_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateMediation API - Required Name of allergy ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.medicationService.updateMedication(medicationWrapper)) {
                response.setResponseMessage(messageBundle.getString("medication.update.success"));
                response.setResponseCode(ResponseEnum.MEDICATION_UPDATE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateMediation API - successfully saved.");
            } else {
                response.setResponseMessage(messageBundle.getString("already.deleted"));
                response.setResponseCode(ResponseEnum.MEDICATION_UPDATE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateMediation API - successfully saved.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateMediation exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete patient Medication",
            notes = "This method will Delete the patient Medication",
            produces = "application/json", nickname = "Delete patient Medication",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted patient Medication successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{medicationId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMedication(HttpServletRequest request,
                                              @PathVariable("medicationId") long medicationId) {
        logger.info("deleteMedication API - Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (medicationId <= 0) {
                response.setResponseMessage(messageBundle.getString("medication.delete.id.required"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteMedication API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (this.medicationService.deleteMedicationById(medicationId)) {
                response.setResponseMessage(messageBundle.getString("medication.delete.success"));
                response.setResponseCode(ResponseEnum.MEDICATION_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("deleteMedication API - Deleted Successfully...");
            } else {
                response.setResponseMessage(messageBundle.getString("already.deleted"));
                response.setResponseCode(ResponseEnum.MEDICATION_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("deleteMedication API - Deleted Successfully...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("deleteMedication API - deleted failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    @RequestMapping(value = "/status/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedMedicationsByStatusAndPatientId(HttpServletRequest request,
                                                                     @PathVariable("page") int page,
                                                                     @RequestParam("status") String status,
                                                                     @RequestParam("selectedPatientId") String selectedPatientId,
                                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        logger.error("getPaginatedMedicationsByStatusAndPatientId API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("getPaginatedMedicationsByStatusAndPatientId -  fetching from DB");
            Pageable pageable = new PageRequest(page,pageSize);
            List<MedicationWrapper> m = this.medicationService.getPaginatedMedicationsByStatusAndPatientId(pageable,status,Long.valueOf(selectedPatientId));
            int count = medicationService.countPaginatedMedicationsByStatusAndPatientId(status,Long.valueOf(selectedPatientId));

            logger.error("getPaginatedMedicationsByStatusAndPatientId - fetched successfully");

            if (!HISCoreUtil.isListEmpty(m)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (count > pageSize) {
                    int remainder = count % pageSize;
                    int totalPages = count / pageSize;
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
                returnValues.put("data", m);

                response.setResponseMessage(messageBundle.getString("medication.paginated.success"));
                response.setResponseCode(ResponseEnum.MEDICATION_PAGINATED_STATUS_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedMedicationsByStatusAndPatientId API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getPaginatedMedicationsByStatusAndPatientId exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
