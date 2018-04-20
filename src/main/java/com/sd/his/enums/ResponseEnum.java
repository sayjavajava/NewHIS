package com.sd.his.enums;

/*
 * @author    : Irfan Nasim
 * @Date      : 18-Apr-17
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
 * @Package   : com.sd.his.enums
 * @FileName  : ResponseEnum
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */

public enum ResponseEnum {

    DATA("DATA"),
    STATUS("STATUS"),
    REASON("REASON"),
    ERROR("ERROR"),
    SUCCESS("SUCCESS"),
    INFO("INFO"),
    NOT_FOUND("NOT_FOUND"),
    ADMIN_LOGGEDIN_FAILED("ADM_ERR_01"),
    ADMIN_LOGGEDIN_SUCCESS("ADM_SUC_01"),
    ADMIN_NOT_FOUND("ADM_ERR_02"),
    ADMIN_ACCESS_GRANTED("ADM_AUTH_SUC_01"),
    EXCEPTION("SYS_ERR_01"),
    INSUFFICIENT_PARAMETERS("SYS_ERR_02"),
    USER_LOGGED_OUT_FAILED("USR_ERR_01"),
    USER_LOGGED_OUT_SUCCESS("USR_AUTH_SUC_02"),
    ROLE_PERMISSION_FETCH_FAILED("ROL_PER_ERR_01"),
    ROLE_PERMISSION_FETCH_SUCCESS("ROL_PER_SUC_02"),
    ROLE_PERMISSION_ASSIGN_SUCCESS("ROL_PER_SUC_02"),
    ROLE_PERMISSION_ASSIGN_ERROR("ROL_PER_ERR_02");

    private String value;

    ResponseEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
