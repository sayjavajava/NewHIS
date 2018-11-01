package com.sd.his.controller.setting;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.VitalSetup;
import com.sd.his.service.VitalSetupServices;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/vitalSetup")
public class VitalSetupController {

    @Autowired
    VitalSetupServices vitalSetupServices;

    private final Logger logger = LoggerFactory.getLogger(EmailConfigurationController.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "POST", value = "Save Vital Setup Configuration",
            notes = "This method will save Vital Setup Configuration",
            produces = "application/json", nickname = "Save Vital Setup Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/saveVitalSetup", method = RequestMethod.POST)
    public ResponseEntity<?> saveVitalSetup(HttpServletRequest request, @RequestBody VitalSetup vitalSetupRequestWrapper) {

        logger.error("Save Vital Setup Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            vitalSetupServices.saveConfiguration(vitalSetupRequestWrapper);
            response.setResponseData(vitalSetupServices.getAll());
            response.setResponseMessage(messageBundle.getString("vital.setup.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Vital Setup Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Vital Setup Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("vital.setup.configuration.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "GET", value = "get Vital Setup Configurations",
            notes = "This method will get Vital Setup Configurations",
            produces = "application/json", nickname = "Get Vital Setup Configurations",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getSetup", method = RequestMethod.GET)
    public ResponseEntity<?> getSetup(){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(vitalSetupServices.getAll());


            response.setResponseMessage(messageBundle.getString("vital.setup.configuration.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Vital Setup data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Vital Setup data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("vital.setup.configuration.fetch.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
