package com.sd.his.controller.setting;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.State;
import com.sd.his.service.StateService;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.GenericAPIResponse;
import com.sd.his.wrapper.StateWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.*;
import com.sd.his.service.*;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.Country;
import com.sd.his.service.CountryService;
import com.sd.his.utill.HISCoreUtil;
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
@RequestMapping(value = "/CityAPI")
public class CityAPI {

    @Autowired
    private CityService cityService;
    private final Logger logger = LoggerFactory.getLogger(CityAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "City",
            notes = "This method will return Cities ",
            produces = "application/json", nickname = "City",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getCityAPI(HttpServletRequest request) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("city.not.found"));
        response.setResponseCode(ResponseEnum.CITY_NOT_FOUND_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            List<City> cityLst = this.cityService.getCities();
            if (HISCoreUtil.isValidObject(cityLst)) {
                response.setResponseData(cityLst);
                response.setResponseMessage(messageBundle.getString("city.found"));
                response.setResponseCode(ResponseEnum.CITY_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("City Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("city.search.not.found"));
                response.setResponseCode(ResponseEnum.CITY_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("City Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("City Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "City",
            notes = "This method will return Cities",
            produces = "application/json", nickname = "City",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cities List", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get/{stateId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCityByStateIdAPI(HttpServletRequest request, @PathVariable("stateId") long stateId) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("city.not.found"));
        response.setResponseCode(ResponseEnum.CITY_NOT_FOUND_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<CityWrapper> cityList = this.cityService.getCitiesByStateId(stateId);
            if (HISCoreUtil.isValidObject(cityList)) {
                StateWrapper state = cityList.get(0).getStateWrapper();

                Map<String, Object> returnValues = new LinkedHashMap<>();
                returnValues.put("cityList", cityList);
                returnValues.put("state", state);

                response.setResponseData(returnValues);
                response.setResponseMessage(messageBundle.getString("city.found"));
                response.setResponseCode(ResponseEnum.CITY_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("City Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("city.search.not.found"));
                response.setResponseCode(ResponseEnum.CITY_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("City Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("City Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "City",
            notes = "This method will return One City",
            produces = "application/json", nickname = "City",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City Data", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCityByIdAPI(HttpServletRequest request, @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("city.not.found"));
        response.setResponseCode(ResponseEnum.CITY_NOT_FOUND_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            City city = this.cityService.getCityById(id);
            if (HISCoreUtil.isValidObject(city)) {
                response.setResponseData(city);
                response.setResponseMessage(messageBundle.getString("city.found"));
                response.setResponseCode(ResponseEnum.CITY_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("City Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("city.search.not.found"));
                response.setResponseCode(ResponseEnum.CITY_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("City Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("City Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}

