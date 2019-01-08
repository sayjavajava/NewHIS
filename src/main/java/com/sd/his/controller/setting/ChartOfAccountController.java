package com.sd.his.controller.setting;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.GeneralLedger;
import com.sd.his.service.GeneralLedgerService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.GenericAPIResponse;
import com.sd.his.wrapper.request.AccountConfigRequestWrapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    public ResponseEntity<?> getAllAccountsConfigurations() {
        GenericAPIResponse response = new GenericAPIResponse();
        try {
       //     response.setResponseData(generalLedgerService.getAll());

            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            List<GeneralLedger> lstofGeneralLedger=generalLedgerService.getAll();
            for(int i=0;i<lstofGeneralLedger.size();i++){
                //lstofGeneralLedger.get(i).;

            }
            hashMap.put("accountList", generalLedgerService.getAll());
            hashMap.put("accountConfig", generalLedgerService.getAccountConfig());

            response.setResponseData(hashMap);

            response.setResponseMessage(messageBundle.getString("chart.of.account.configuration.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Chart of accounts List data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
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
        try {
            generalLedgerService.saveConfiguration(generalLedgerRequestWrapper);
            generalLedgerService.updateAccountCode();
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("chart.of.account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Chart Of Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
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
        try {
            generalLedgerService.saveAssetsConfiguration(accountConfigRequest);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
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
        try {
            generalLedgerService.saveLiabilityConfiguration(accountConfigRequest);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
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
        try {
            generalLedgerService.saveRevenueConfiguration(accountConfigRequest);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
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
        try {
            generalLedgerService.saveCOGSConfiguration(accountConfigRequest);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
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
        try {
            generalLedgerService.saveExpenseConfiguration(accountConfigRequest);
            response.setResponseData(generalLedgerService.getAll());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Account Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("account.configuration.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Get latest account code",
            notes = "This method will get account code",
            produces = "application/json", nickname = "Get latest account code",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/accountCode", method = RequestMethod.GET)
    public ResponseEntity<?> getAccountCode() {

        logger.info("getAccountCode API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            String accountCode = generalLedgerService.getAccountCode();

            Map<String, Object> returnValue = new LinkedHashMap<>();
            returnValue.put("data", accountCode);

            response.setResponseData(returnValue);
            response.setResponseMessage(messageBundle.getString("account.code.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Account Code Fetched Successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Account Code Fetch Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("account.code.fetch.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "get chart of accounts Configurations",
            notes = "This method will get chart of accounts Configurations",
            produces = "application/json", nickname = "Get All chart of accounts Configurations",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getAllForPaymentType", method = RequestMethod.GET)
    public ResponseEntity<?> getAllForPaymentType(){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(generalLedgerService.getAll());

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




    @ApiOperation(httpMethod = "POST", value = "Get latest account code",
            notes = "This method will get account code",
            produces = "application/json", nickname = "Get latest account code",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAccount(HttpServletRequest request, @PathVariable("id") long accountId) {

        logger.info("deleteAccount API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            GeneralLedger generalLedger = generalLedgerService.getById(accountId);
//            response.setResponseData(null);
//            response.setResponseMessage(messageBundle.getString("account.not.found.error"));
//            response.setResponseCode(ResponseEnum.GENERAL_LEDGER_NOT_FOUND_ERROR.getValue());
//            response.setResponseStatus(ResponseEnum.ERROR.getValue());
//            logger.info("General LEDGER not Found ...");

            if (HISCoreUtil.isValidObject(generalLedger)) {
                generalLedgerService.deleteLedger(accountId);
                if (HISCoreUtil.isValidObject(generalLedger)) {
                    response.setResponseData(generalLedger);
                    response.setResponseMessage(messageBundle.getString("account.delete.success"));
                    response.setResponseCode(ResponseEnum.GENERAL_LEDGER_DELETE_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.GENERAL_LEDGER_DELETE_SUCCESS.getValue());
                    logger.info("General Ledger Deleted Successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (DataIntegrityViolationException ex) {
            logger.error("Account Delete Process Failed due to foreign key constraint.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("account.delete.foreign.key.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            logger.error("Account Delete Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("account.delete.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
