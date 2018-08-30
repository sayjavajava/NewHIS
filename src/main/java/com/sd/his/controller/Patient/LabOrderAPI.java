package com.sd.his.controller.Patient;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.LabOrder;
import com.sd.his.repository.LabOrderProjection;
import com.sd.his.service.PatientService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.GenericAPIResponse;
import com.sd.his.wrapper.LabOrderWrapper;
import com.sd.his.wrapper.response.BranchResponseWrapper;
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

@RestController
@RequestMapping("/patient/laborder")
public class LabOrderAPI {

    @Autowired
    private PatientService patientService;

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
    public ResponseEntity<?> getAllPaginatedBranches(HttpServletRequest request,
                                                     @PathVariable("page") int page,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllLabOrders paginated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.not.found"));
        response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<LabOrderProjection> branchWrappers = patientService.getAllLabOrders(page,pageSize);
           /* int countOrders = patientService.totaLabOrders();

            if (!HISCoreUtil.isListEmpty(branchWrappers)) {
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
                returnValues.put("data", branchWrappers);

                response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
                response.setResponseCode(ResponseEnum.LABORDER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedBranch Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }*/
            response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
            response.setResponseCode(ResponseEnum.LABORDER_FOUND.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(branchWrappers);
            logger.info("getAllPaginatedBranch Fetched successfully...");
            return new ResponseEntity<>(response, HttpStatus.OK);
        //    return new ResponseEntity<>(response, HttpStatus.OK);
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
