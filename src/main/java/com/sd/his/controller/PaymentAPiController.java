package com.sd.his.controller;

import com.sd.his.enums.InvoiceMessageEnum;
import com.sd.his.enums.ResponseEnum;
import com.sd.his.service.PatientInvoiceService;
import com.sd.his.service.PatientService;
import com.sd.his.wrapper.GenericAPIResponse;
import com.sd.his.wrapper.request.AdvancePaymentRequestWrapper;
import com.sd.his.wrapper.request.GenerateInvoiceRequestWrapper;
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
import java.util.ResourceBundle;

@RestController
@RequestMapping("/payment")
public class PaymentAPiController {

    @Autowired
    private PatientInvoiceService patientInvoiceService;

    @Autowired
    private PatientService patientService;

    private final Logger logger = LoggerFactory.getLogger(StaffAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");


    @ApiOperation(httpMethod = "POST", value = "Create patient Advance Payment",
            notes = "This method will Create  patient Advance Payment",
            produces = "application/json", nickname = " patient Advance Payment",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/saveAdvancePayment", method = RequestMethod.POST)
    public ResponseEntity<?> saveAdvancePayment(@RequestBody AdvancePaymentRequestWrapper createAdvancePaymentRequest) {
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            if (createAdvancePaymentRequest.getDate().trim().length() == 10) {
                createAdvancePaymentRequest.setDate( patientService.convertDateToGMT( createAdvancePaymentRequest.getDate().trim(), "yyyy-MM-dd" ) );
            } else {
                createAdvancePaymentRequest.setDate( patientService.convertDateToGMT( createAdvancePaymentRequest.getDate().trim(), "E MMM dd yyyy HH:mm:ss" ) );
            }
            patientInvoiceService.saveAdvancePayment(createAdvancePaymentRequest);

            response.setResponseMessage(messageBundle.getString("paymentApi.save.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("Advance Payment created successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Create User Failed.", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
   //     return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @ApiOperation(httpMethod = "GET", value = "Get Invoice List By patient Id",
            notes = "This method will Get Invoice List By patient Id",
            produces = "application/json", nickname = " patient Invoice List",
            response = GenericAPIResponse.class, protocols = "https")

    @ApiResponses({
            @ApiResponse(code = 200, message = "Invoice List data found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getPatientInvoiceListById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientInvoiceListById(HttpServletRequest request, @PathVariable("id") long  patientID) {

        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(patientInvoiceService.getInvoiceListByPatientId(patientID));

            response.setResponseMessage(messageBundle.getString("paymentApi.invoice.list.fetched.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("patient Invoioce List data fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Patient Invoice List Fetched Failed.", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
