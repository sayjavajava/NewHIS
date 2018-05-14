package com.sd.his.controller.setting;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.response.GenericAPIResponse;
import com.sd.his.service.BranchService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.BranchWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

/*
 * @author    : irfan
 * @Date      : 14-May-18
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
 * @FileName  : ${FILE_NAME}
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@RestController
@RequestMapping("/setting/branch")
public class BranchController {

    Logger logger = LoggerFactory.getLogger(BranchController.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    BranchService branchService;

    @ApiOperation(httpMethod = "GET", value = "All Branches",
            notes = "This method will return all Branches",
            produces = "application/json", nickname = "All Branches",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Branches fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBranches(HttpServletRequest request) {

        logger.error("getAllBranches API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllBranches API - branches fetching from DB");
            List<BranchWrapper> branches = branchService.getAllActiveBranches();
            if (HISCoreUtil.isListEmpty(branches)) {
                response.setResponseMessage(messageBundle.getString("branch.not-found"));
                response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAllBranches API - Branches not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("branch.fetch.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(branches);

            logger.error("getAllBranches API - Branches successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllBranches API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
