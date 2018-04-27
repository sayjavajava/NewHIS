package com.sd.his.controller.setting;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.request.ICDCreateRequest;
import com.sd.his.response.GenericAPIResponse;
import com.sd.his.service.ICDService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.ICDVersionWrapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/*
 * @author    : Irfan Nasim
 * @Date      : 26-Apr-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.controller.setting
 * @FileName  : ICDController
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RequestMapping("/setting/icd")
@RestController
public class ICDAPI {

    @Autowired
    private ICDService iCDService;
    private final Logger logger = LoggerFactory.getLogger(ICDAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "ICD Code Versions ",
            notes = "This method will return ICD Code Versions ",
            produces = "application/json", nickname = "ICD Code Versions ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ICD Code Versions fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/iCDCodeVersions", method = RequestMethod.GET)
    public ResponseEntity<?> getICDCodeVersions(HttpServletRequest request) {

        logger.error("getICDCodeVersionList API initiated.");
        logger.info("getICDCodeVersionList API initiated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.code.versions.not.found"));
        response.setResponseCode(ResponseEnum.ICD_CODE_VERSION_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (!HISCoreUtil.isListEmpty(iCDService.getICDCodeVersionList())) {
                logger.info("ICD Versions Found Successfully");
                response.setResponseData(iCDService.getICDCodeVersionList());
                response.setResponseMessage(messageBundle.getString("icd.code.versions.found.success"));
                response.setResponseCode(ResponseEnum.ICD_CODE_VERSION_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getICDCodeVersionList failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ApiOperation(httpMethod = "POST", value = "Save ICD Version ",
            notes = "This method will return Status while saving ICD Version",
            produces = "application/json", nickname = "Save ICD Version",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save ICD Version successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveICD(@RequestBody ICDCreateRequest createRequest) {
        logger.info("saveICDVersion API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        ICDVersionWrapper icd = new ICDVersionWrapper();

        try {
            if (HISCoreUtil.isNull(createRequest.getCode()) ||
                    createRequest.getSelectedICDVersion() <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveICDVersion API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseMessage(messageBundle.getString("icd.save.success"));
            response.setResponseCode(ResponseEnum.ICD_SUCCESS.getValue());//ICD_SAVE_SUC_01
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);

        } catch (Exception e) {
            logger.error("saveICD failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
