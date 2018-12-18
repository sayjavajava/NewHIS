package com.sd.his.controller.patient;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.Appointment;
import com.sd.his.model.LabOrder;
import com.sd.his.model.Organization;
import com.sd.his.repository.LabOrderProjection;
import com.sd.his.service.OrganizationService;
import com.sd.his.service.PatientService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.GenericAPIResponse;
import com.sd.his.wrapper.LabOrderWrapper;
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
import java.util.*;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/patient/laborder")
public class LabOrderAPI {

    @Autowired
    private PatientService patientService;
    @Autowired
    private OrganizationService organizationService;
    private final Logger logger = LoggerFactory.getLogger(LabOrderAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "POST", value = "Create LabOrder",
            notes = "This method will Create Lab Order",
            produces = "application/json", nickname = "Create LabOrder",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "LabOrder successfully created", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createLabOrder(HttpServletRequest request,
                                            @RequestBody LabOrderWrapper labOrderWrapper) {
        logger.info("Create LabOrder API called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.add.error"));
        response.setResponseCode(ResponseEnum.LABORDER_ADD_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            /*Branch alreadyExist = branchService.findByBranchName(branchRequestWrapper.getBranchName());
            if (HISCoreUtil.isValidObject(alreadyExist)) {
                response.setResponseMessage(messageBundle.getString("branch.add.already-found.error"));
                response.setResponseCode(ResponseEnum.BRANCH_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Branch already exist with the same name...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }*/
            LabOrderWrapper labOrder = patientService.saveLabOrder(labOrderWrapper);
            if (HISCoreUtil.isValidObject(labOrder)) {
                response.setResponseData(labOrder);
                response.setResponseMessage(messageBundle.getString("laborder.add.success"));
                response.setResponseCode(ResponseEnum.LABORDER_ADD_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("LabOrder created successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


        } catch (Exception ex) {
            logger.error("LabOrder Creation Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


   @ApiOperation(httpMethod = "GET", value = "Paginated LabOrders",
            notes = "This method will return Paginated LabOrders",
            produces = "application/json", nickname = "Get Paginated LabOrders ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated LabOrders fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedLabOrder(HttpServletRequest request,
                                                     @PathVariable("page") int page,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllLabOrders paginated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.not.found"));
        response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<LabOrderProjection> labordersdata = patientService.getAllLabOrders(page,pageSize);
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String stdDateTime=dbOrganization.getDateFormat()+" "+dbOrganization.getTimeFormat();
            for(int i=0;i<labordersdata.size();i++){
             String readDate=HISCoreUtil.convertDateToString(labordersdata.get(i).getDateTest(),stdDateTime);

                labordersdata.get(i).setDateTest(HISCoreUtil.convertToDate(readDate));

            }
            int countOrders = patientService.totaLabOrders();

            if (!HISCoreUtil.isListEmpty(labordersdata)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countOrders > pageSize) {
                    int remainder = countOrders % pageSize;
                    int totalPages = countOrders / pageSize;
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
                returnValues.put("data", labordersdata);

                response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
                response.setResponseCode(ResponseEnum.LABORDER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedLabOrders Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

           return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated countOrders failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //get order

    @ApiOperation(httpMethod = "PUT", value = "Update LaBOrder ",
            notes = "This method will Update LabOrder",
            produces = "application/json", nickname = "Update LabOrder",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "LabOrder successfully updated", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLabOrder(HttpServletRequest request,
                                                @PathVariable("id") long id,
                                            @RequestBody LabOrderWrapper labOrderWrapper) {

        logger.info("update LabOrder API called...");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.update.error"));
        response.setResponseCode(ResponseEnum.LABORDER_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            LabOrder alreadyExistLabOrder = patientService.findById(id);
            if (HISCoreUtil.isValidObject(alreadyExistLabOrder)) {
                logger.info("LabOrder founded...");
                LabOrderWrapper labOrderUpdated = patientService.updateLabOrder(labOrderWrapper, alreadyExistLabOrder);
                if (HISCoreUtil.isValidObject(labOrderUpdated)) {
                    logger.info("LabOrder Updated...");
                    response.setResponseData(labOrderUpdated);
                    response.setResponseMessage(messageBundle.getString("laborder.update.success"));
                    response.setResponseCode(ResponseEnum.LABORDER_UPDATE_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("LabOrder  updated successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                logger.info("LabOrder not found...");
                response.setResponseMessage(messageBundle.getString("laborder.not.found"));
                response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("laborder not updated...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception ex) {
            logger.error("Update LabOrder Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Fetch LabOrder",
            notes = "This method will return LabOrder on base of id",
            produces = "application/json", nickname = "Get Single Order",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "LabOrder found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLabOrderById(HttpServletRequest request,
                                           @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.not.found"));
        response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            LabOrderProjection dbLabOrder = this.patientService.getLabOrderById(id);

            if (HISCoreUtil.isValidObject(dbLabOrder)) {
                response.setResponseData(dbLabOrder);
                response.setResponseCode(ResponseEnum.LABORDER_FOUND.getValue());
                response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("LabOrder Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("LabOrder Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(httpMethod = "DELETE", value = "Delete LasbOrder",
            notes = "This method will Delete LabOrder on base of id",
            produces = "application/json", nickname = "Delete LabOrder ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "LabOrder Deleted successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLabOrder(HttpServletRequest request,
                                          @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.delete.error"));
        response.setResponseCode(ResponseEnum.LABORDER_DELETED_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            Boolean LabOrder = patientService.deleteByLabOrder(id);
                if(HISCoreUtil.isValidObject(LabOrder)){
                    response.setResponseData(null);
                    response.setResponseMessage(messageBundle.getString("laborder.delete.success"));
                    response.setResponseCode(ResponseEnum.LABORDER_DELETED_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("LabOrder Deleted successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);}

            }

            catch (Exception ex) {
            logger.error("Unable to delete LabOrder.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //get by id
    @ApiOperation(httpMethod = "GET", value = "Paginated LabOrders",
            notes = "This method will return Paginated LabOrders",
            produces = "application/json", nickname = "Get Paginated LabOrders ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated LabOrders fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/order/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedOrdersByPatient(HttpServletRequest request,
                                                     @PathVariable("page") int page,
                                                     @RequestParam (value = "name", required = false) String patientId,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllLabOrders paginated.." + patientId);

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.not.found"));
        response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<LabOrderProjection> labordersdata = patientService.getAllLabOrdersByPatient(page,pageSize, Long.valueOf(patientId));
            List<String>  objappoiment = new ArrayList<>();
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String stdDateTime=dbOrganization.getDateFormat()+" "+dbOrganization.getTimeFormat();
            for(int i=0;i<labordersdata.size();i++){
               String readDate=HISCoreUtil.convertDateToString(labordersdata.get(i).getDateTest(),stdDateTime);
            //    labordersdata.get(i).setDateTestString(readDate);
                labordersdata.get(i).setDateTest(HISCoreUtil.convertToDateString(readDate,stdDateTime));
           //     String doctorFirstName=labordersdata.get(i).getAppointment().get(i).getDoctor().getFirstName();
            //    String doctorLastName=labordersdata.get(i).getAppointment().get(i).getDoctor().getLastName();
           //     objappoiment.add(doctorFirstName+""+doctorLastName);
            }
            int countOrders = patientService.totaLabOrders();

            if (!HISCoreUtil.isListEmpty(labordersdata)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countOrders > pageSize) {
                    int remainder = countOrders % pageSize;
                    int totalPages = countOrders / pageSize;
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
                returnValues.put("data", labordersdata);
             //   returnValues.put("doctors",objappoiment);
                response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
                response.setResponseCode(ResponseEnum.LABORDER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedLabOrders Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated countOrders failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
