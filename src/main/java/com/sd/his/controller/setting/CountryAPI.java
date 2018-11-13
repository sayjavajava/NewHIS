package com.sd.his.controller.setting;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.Country;
import com.sd.his.model.PaymentType;
import com.sd.his.service.CountryService;
import com.sd.his.service.PaymentTypeService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.CountryWrapper;
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

import com.sd.his.enums.ResponseEnum;

import com.sd.his.model.PaymentType;
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
import java.util.ResourceBundle;

@RestController
@RequestMapping(value = "/CountryAPI")
public class CountryAPI {

    private final Logger logger = LoggerFactory.getLogger(CountryAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");



    @Autowired
    private CountryService countryService;


    @ApiOperation(httpMethod = "GET", value = "All Countries",
            notes = "This method will returns all Countries ",
            produces = "application/json", nickname = "Countries",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Countries  fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCountries(HttpServletRequest request) {

        logger.error("get All Countries  API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("country.fetch.error"));
        response.setResponseCode(ResponseEnum.COUNTRY_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("ALL Country  API - Country  fetching from DB");
            List<CountryWrapper> countriesLst = countryService.getAllCountries();

            if (HISCoreUtil.isListEmpty(countriesLst)) {
                response.setResponseMessage(messageBundle.getString("country.not.found"));
                response.setResponseCode(ResponseEnum.COUNTRY_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Country   - Country  not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("country.fetched.success"));
            response.setResponseCode(ResponseEnum.COUNTRY_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(countriesLst);

            logger.error("Country API - Country successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Country   API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }








}
