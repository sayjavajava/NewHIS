package com.sd.his.controller.setting;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.ICD;
import com.sd.his.request.ICDCreateRequest;
import com.sd.his.response.GenericAPIResponse;
import com.sd.his.service.ICDService;
import com.sd.his.utill.HISCoreUtil;
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
    public ResponseEntity<?> saveICD(HttpServletRequest request,
                                     @RequestBody ICDCreateRequest createRequest) {
        logger.info("saveICDVersion API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();

        try {

            if (createRequest.getSelectedICDVersion() <= 0) {
                response.setResponseMessage(messageBundle.getString("icd.save.error.code.version.empty"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveICDVersion API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            if (HISCoreUtil.isNull(createRequest.getCode()) || createRequest.getCode().isEmpty()) {
                response.setResponseMessage(messageBundle.getString("icd.save.error.code.empty"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveICDVersion API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            /*
             * code version must be unique in icd table
             * it mean still now one to one relation we implemented
             * **/
            if (iCDService.isCodeVersionAvailableAgainstICD(createRequest.getSelectedICDVersion())) {
                response.setResponseMessage(messageBundle.getString("icd.save.error.code.version.dup"));
                response.setResponseCode(ResponseEnum.ICD_CODE_SAVE_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            ICD icd = iCDService.saveICD(createRequest);
            if (HISCoreUtil.isValidObject(icd)) {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("icd.save.success"));
                response.setResponseCode(ResponseEnum.ICD_CODE_SAVE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseData(null);
            response.setResponseMessage(messageBundle.getString("icd.save.error"));
            response.setResponseCode(ResponseEnum.ICD_CODE_SAVE_ERROR.getValue());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("saveICD failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("icd.save.exception.occurred"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return Response with all paginated ICD Versions.
     * @author Irfan Nasim
     * @description API will return detail of all ICD Versions paginated.
     * @since 30-04-2017
     */
   /* @ApiOperation(httpMethod = "GET", value = "Paginated ICD Versions ",
            notes = "This method will return Paginated ICD Versions",
            produces = "application/json", nickname = "Get Paginated ICD Versions ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated ICD Versions fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedICDVersions(HttpServletRequest request,
                                                        @PathVariable("page") int page,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("get all Admins paginated requested by User Name: " + request.getRemoteUser().toString());

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("admins.not.found"));
        response.setResponseCode(ResponseEnum.NO_ADMINS_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<ICDVersion> dbICDVersions = icdService.findAllActiveAdmins(page, pageSize);
            List<ICDVersionWrapper> icdVersions = APIUtil.buildICDVersions(dbICDVersions);
            int icdVersionsCount = icdService.countAllICDVersions();

            if (!HISCoreUtil.isListEmpty(icdVersions)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (icdVersionsCount > pageSize) {
                    int remainder = icdVersionsCount % pageSize;
                    int totalPages = icdVersionsCount / pageSize;
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
                returnValues.put("data", icdVersions);

                response.setResponseMessage(messages.get("admins.fetched.success"));
                response.setResponseCode(ResponseEnum.ADMINS_FETCHED_SUCCESSFULLY.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("ICD Versions Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated ICD Versions failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messages.get("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }*/

}
