package com.sd.his.controller;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.*;
import com.sd.his.service.CustomerService;
import com.sd.his.service.PaymentTypeService;
import com.sd.his.utill.HISCoreUtil;
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
import java.util.List;

import com.sd.his.controller.patient.PatientAPI;
import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.CustomerEntity;
import com.sd.his.service.CustomerService;
import com.sd.his.service.PatientService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.DepartmentWrapper;
import com.sd.his.wrapper.GenericAPIResponse;
import com.sd.his.wrapper.PatientWrapper;
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
import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequestMapping(value = "/PaymentType")
public class PaymentTypeAPI {

    private final Logger logger = LoggerFactory.getLogger(com.sd.his.controller.customer.CustomerAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private PaymentTypeService paymentServiceType;


    @ApiOperation(httpMethod = "GET", value = "All Payment Type",
            notes = "This method will returns all Payment Type",
            produces = "application/json", nickname = "Payment Type",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Payment Type fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaymentType(HttpServletRequest request) {

        logger.error("getAll Payment Type API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("paymentType.fetch.error"));
        response.setResponseCode(ResponseEnum.PAYMENTTYPE_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("ALL Payment  API - Payment Type fetching from DB");
            List<PaymentType> paymentType = paymentServiceType.getAllPaymentType();

            if (HISCoreUtil.isListEmpty(paymentType)) {
                response.setResponseMessage(messageBundle.getString("paymentType.not.found"));
                response.setResponseCode(ResponseEnum.PAYMENTTYPE_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Payment Type API  - Payment  not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("paymentType.fetched.success"));
            response.setResponseCode(ResponseEnum.PAYMENTTYPE_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(paymentType);

            logger.error("Payment Type  API - Payment Type successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Payment Type   API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////// Update Customer////////////////////////////////////////
    @ApiOperation(httpMethod = "PUT", value = "Update Payment Type",
            notes = "This method will Update Payment Type",
            produces = "application/json", nickname = "Update Payment Type",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Payment Type  Updated successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePaymentType(HttpServletRequest request,
                                            @RequestBody PaymentType paymentType) {


        logger.info("Update Payment API - Update Payment Type By id:" + paymentType.getId());
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("paymentType.update.error"));
        response.setResponseCode(ResponseEnum.PAYMENTTYPE_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (HISCoreUtil.isNull(paymentType.getPaymentTitle()) || paymentType.getId()<= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("update Payment Type API - parameter is insufficient...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            paymentServiceType.updatePaymentType(paymentType);
            response.setResponseMessage(messageBundle.getString("paymentType.update.success"));
            response.setResponseCode(ResponseEnum.PAYMENTTYPE_UPDATE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("update Customer API - Customer updated successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("update Payment Type  API - Exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "POST", value = "Save Payment Type ",
            notes = "This method will Save Payment Type",
            produces = "application/json", nickname = "Save Payment Type ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save Payment Type  successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> savePaymentType(HttpServletRequest request,
                                          @RequestBody PaymentType paymentType) {

        logger.info("Customer API Called...!!");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("paymentType.save.error"));
        response.setResponseCode(ResponseEnum.PAYMENTTYPE_SAVE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            if (HISCoreUtil.isNull(paymentType.getPaymentTitle())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("The requested parameter is insufficient...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            PaymentType paymentTypeSave = paymentServiceType.savePaymentAPI(paymentType);
            if (HISCoreUtil.isValidObject(paymentTypeSave)) {
                response.setResponseMessage(messageBundle.getString("paymentType.save.success"));
                response.setResponseCode(ResponseEnum.PAYMENTTYPE_SAVE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("The Payment Type saved successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("save Payment Type exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @ApiOperation(httpMethod = "DELETE", value = "DELETE Payment Type",
            notes = "This method will Delete Payment Type",
            produces = "application/json", nickname = "Delete Payment Type",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Delete Payment Type successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{custId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePaymentType(HttpServletRequest request,
                                            @PathVariable("custId")  long  cusId) {

        logger.error("Delete Payment Type API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("paymentType.delete.error"));
        response.setResponseCode(ResponseEnum.PAYMENTTYPE_DELETE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("Delete Customer  -  fetching from DB for existence");

            if (cusId <= 0) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.delete.id"));
                response.setResponseCode(ResponseEnum.PAYMENTTYPE_DELETE_ID.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("delete - Please provide proper Id");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            paymentServiceType.deletePaymentType(cusId);
            response.setResponseMessage(messageBundle.getString("paymentType.delete.success"));
            response.setResponseCode(ResponseEnum.PAYMENTTYPE_DELETE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);

            logger.error("Delete Payment Api - deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Delete Payment Api exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
