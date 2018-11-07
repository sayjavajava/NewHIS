package com.sd.his.controller.setting;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.GeneralLedger;
import com.sd.his.service.GeneralLedgerService;
import com.sd.his.wrapper.GenericAPIResponse;
import com.sd.his.wrapper.request.AccountConfigRequestWrapper;
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
import java.util.HashMap;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/chartOfAccountConfigurations")
public class ChartOfAccountController {

    @Autowired
    GeneralLedgerService generalLedgerService;

    private final Logger logger = LoggerFactory.getLogger(SmsTemplateController.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "get chart of accounts Configurations",
            notes = "This method will get chart of accounts Configurations",
            produces = "application/json", nickname = "Get All chart of accounts Configurations",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAccountsConfigurations(){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
//            response.setResponseData(generalLedgerService.getAll());

            HashMap<String,Object> hashMap=new HashMap<String,Object>();
            hashMap.put("accountList",generalLedgerService.getAll());
            hashMap.put("accountConfig",generalLedgerService.getAccountConfig());

            response.setResponseData(hashMap);

            response.setResponseMessage(messageBundle.getString("chart.of.account.configuration.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Chart of accounts List data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Chart of accounts List data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            System.out.println("Exception : " + ex.getStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("chart.of.account.configuration.fetch.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Save chart of account Configuration",
            notes = "This method will save chart of account Configuration",
            produces = "application/json", nickname = "Save chart of account Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/saveChartOfAccount", method = RequestMethod.POST)
    public ResponseEntity<?> saveChartOfAccount(HttpServletRequest request, @RequestBody GeneralLedger generalLedgerRequestWrapper) {

        logger.error("Save Chart Of Account Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            generalLedgerService.saveConfiguration(generalLedgerRequestWrapper);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("chart.of.account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Chart Of Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Chart Of Account Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("chart.of.account.configuration.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @ApiOperation(httpMethod = "POST", value = "Save account Configuration",
            notes = "This method will save account Configuration",
            produces = "application/json", nickname = "Save account Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/updateAssetsConfig", method = RequestMethod.POST)
    public ResponseEntity<?> updateAssetsConfig(@RequestBody AccountConfigRequestWrapper accountConfigRequest) {

        logger.error("Save Account Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            generalLedgerService.saveAssetsConfiguration(accountConfigRequest);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Account Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Save account Configuration",
            notes = "This method will save account Configuration",
            produces = "application/json", nickname = "Save account Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/updateLiabilityConfig", method = RequestMethod.POST)
    public ResponseEntity<?> updateLiabilityConfig(@RequestBody AccountConfigRequestWrapper accountConfigRequest) {

        logger.error("Save Account Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            generalLedgerService.saveLiabilityConfiguration(accountConfigRequest);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Account Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Save account Configuration",
            notes = "This method will save account Configuration",
            produces = "application/json", nickname = "Save account Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/updateRevenueConfig", method = RequestMethod.POST)
    public ResponseEntity<?> updateRevenueConfig(@RequestBody AccountConfigRequestWrapper accountConfigRequest) {

        logger.error("Save Account Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            generalLedgerService.saveRevenueConfiguration(accountConfigRequest);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Account Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Save account Configuration",
            notes = "This method will save account Configuration",
            produces = "application/json", nickname = "Save account Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/updateCOSConfig", method = RequestMethod.POST)
    public ResponseEntity<?> updateCOSConfig(@RequestBody AccountConfigRequestWrapper accountConfigRequest) {

        logger.error("Save Account Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            generalLedgerService.saveCOGSConfiguration(accountConfigRequest);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Account Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Save account Configuration",
            notes = "This method will save account Configuration",
            produces = "application/json", nickname = "Save account Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/updateExpenseConfig", method = RequestMethod.POST)
    public ResponseEntity<?> updateExpenseConfig(@RequestBody AccountConfigRequestWrapper accountConfigRequest) {

        logger.error("Save Account Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            generalLedgerService.saveExpenseConfiguration(accountConfigRequest);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Account Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
