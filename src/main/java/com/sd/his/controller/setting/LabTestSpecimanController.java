package com.sd.his.controller.setting;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.LabTestSpeciman;
import com.sd.his.service.LabTestSpecimanService;
import com.sd.his.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ResourceBundle;

@RestController
@RequestMapping("/labTest")
public class LabTestSpecimanController {

    @Autowired
    LabTestSpecimanService labTestSpecimanService;

    private final Logger logger = LoggerFactory.getLogger(SmsTemplateController.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "get lab test speciman Configurations",
            notes = "This method will get lab test speciman Configurations",
            produces = "application/json", nickname = "Get All lab test speciman Configurations",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTestSpeciman(){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(labTestSpecimanService.getAll());

            response.setResponseMessage(messageBundle.getString("lab.test.speciman.configuration.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Lab test speciman List data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Lab test speciman List data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            System.out.println("Exception : " + ex.getStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("lab.test.speciman.configuration.fetch.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Save lab test speciman Configuration",
            notes = "This method will save lab test speciman Configuration",
            produces = "application/json", nickname = "Save lab test speciman Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/saveLabTestSpeciman", method = RequestMethod.POST)
    public ResponseEntity<?> saveLabTestSpeciman(@RequestBody LabTestSpeciman labTestSpecimanRequestWrapper) {

        logger.error("Save lab test speciman Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            labTestSpecimanService.saveConfiguration(labTestSpecimanRequestWrapper);
            response.setResponseData(labTestSpecimanService.getAll());
            response.setResponseMessage(messageBundle.getString("lab.test.speciman.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Lab test speciman Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Lab test speciman Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("lab.test.speciman.configuration.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
