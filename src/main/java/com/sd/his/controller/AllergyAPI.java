package com.sd.his.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ResourceBundle;

/**
 * Created by jamal on 8/20/2018.
 */
@RestController
@RequestMapping("/allergy")
public class AllergyAPI {

    Logger logger = LoggerFactory.getLogger(AppointmentAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");





}
