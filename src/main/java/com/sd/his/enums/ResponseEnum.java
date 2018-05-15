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
    ICD_CODE_SAVE_SUCCESS("ICD_SAVE_SUC_01"),
    ICD_CODE_SAVE_ERROR("ICD_ERR_02"),
    ICD_CODE_NOT_FOUND("ICD_ERR_03"),
    ICD_CODE_FOUND("ICD_SUC_02"),
    ICD_CODE_DELETE_SUCCESS("ICD_SUC_03"),
    ICD_CODE_DELETE_ERROR("ICD_ERR_04"),
    ICD_CODE_ALREADY_EXIST_ERROR("ICD_ERR_05"),
    ICD_CODE_UPDATE_ERROR("ICD_CODE_UPDATE_ERR_06"),
    ICD_CODE_UPDATE_SUCC("ICD_CODE_UPDATE_SUC_07"),
    ICD_VERSION_ERROR("ICD_VERSION_ERROR_01"),
    ICD_VERSION_SAVE_SUCCESS("ICD_VERSION_SUC_08"),
    ICD_VERSION_ALREADY_EXIST_ERROR("ICD_VERSION_ERR_02"),
    ICD_VERSION_SAVE_ERROR("ICD_VERSION_ERR_09"),
    ICD_VERSIONS_FETCH_SUCCESS("ICD_VERSIONS_FOUND_03"),
    ICD_VERSIONS_FOUND_SUCCESS("ICD_VERSIONS_FOUND_SUC_13"),
    ICD_VERSIONS_NOT_FOUND("ICD_VERSIONS_ERR_04"),
    ICD_VERSION_FETCH_FAILED("ICD_ERR_01"),
    ICD_VERSION_UPDATE_ERROR("ICD_VERSION_UPDATE_ERR_06"),
    ICD_VERSION_UPDATE_SUCCESS("ICD_VERSION_UPDATE_SUC_07"),
    ICD_VERSION_DELETE_SUCCESS("ICD_VERSION_DEL_SUC_11"),
    ICD_VERSION_DELETE_ERROR("ICD_VERSION_DEL_SUC_12"),
    ICD_ASSOCIATED_FOUND_SUCCESS("ICD_ASSOCIATED_FOUND_SUC_02"),
    ICD_CODE_VERSION_DELETE_ERROR("ICD_CODE_VERSION_DEL_ERR_18"),
    ICD_CODE_VERSION_SAVE_SUCCESS("ICD_ASSOCIATE_SUC_18"),
    ICD_CODE_VERSION_SAVE_ERROR("ICD_CODE_VERSION_ERR_19"),
    ICD_CODE_VERSION_DELETE_SUCCESS("ICD_CODE_VERSION_DEL_SUC_17"),
    ICD_CODE_VERSION_FETCH_SUCCESS("ICD_SUC_16"),
    ICD_CODE_VERSION_NOT_FOUND("ICD_CODE_VERSION_ERR_15"),
    CLI_DPT_FETCH_SUCCESS("CLI_DPT_SUC_01"),
    CLI_DPT_FETCH_ERROR("CLI_DPT_ERR_01"),
    CLI_DPT_DELETE_SUCCESS("CLI_DPT_SUC_02"),
    CLI_DPT_DELETE_ERROR("CLI_DPT_ERR_02"),
    CLI_DPT_SAVE_ERROR("CLI_DPT_ERR_02"),
    CLI_DPT_SAVE_SUCCESS("CLI_DPT_SUC_02"),
    CLI_DPT_ALREADY_EXIST("CLI_DPT_ERR_04"),
    CLI_DPT_UPDATE_SUCCESS("CLI_DPT_SUC_02"),
    CLI_DPT_UPDATE_ERROR("CLI_DPT_ERR_04"),
    CLI_DPT_NOT_FOUND("CLI_DPT_ERR_03"),
    BRANCH_FETCH_FAILED("BR_ERR_01"),
    BRANCH_FETCH_SUCCESS("BR_SUC_01"),
    BRANCH_NOT_FOUND("BR_ERR_02"),
    ROLE_PERMISSION_ASSIGN_SUCCESS("ROL_PER_SUC_03"),
    ROLE_PERMISSION_ASSIGN_ERROR("ROL_PER_ERR_02"),
    ROLE_ADD_SUCCESS("ROL_SUC_01"),
    ROLE_ADD_ERROR("ROL_ERR_01"),
    ROLE_ALREADY_EXIST_ERROR("ROL_ERR_02"),
    PERMISSION_ADD_SUCCESS("PER_SUC_01"),
    PERMISSION_ADD_ERROR("PER_ERR_01"),
    PERMISSION_ALREADY_EXIST_ERROR("PER_ERR_02"),
    SERVICE_TAX_FETCH_ERROR("SER_TAX_ERR_01"),
    SERVICE_TAX_FETCH_SUCCESS("SER_TAX_SUC_01"),
    SERVICE_TAX_DELETE_SUCCESS("SER_TAX_SUC_02"),
    SERVICE_TAX_DELETE_ERROR("SER_TAX_ERR_02"),
    SERVICE_TAX_NOT_FOUND_ERROR("SER_TAX_ERR_03"),
    MED_SERVICE_FETCH_ERROR("MED_SER_ERR_01"),
    MED_SERVICE_FETCH_SUCCESS("MED_SER_SUC_01"),
    MED_SERVICE_DELETE_SUCCESS("MED_SER_SUC_02"),
    MED_SERVICE_DELETE_ERROR("MED_SER_ERR_02"),
    MED_SERVICE_SAVE_ERROR("MED_SER_ERR_02"),
    MED_SERVICE_SAVE_SUCCESS("MED_SER_SUC_02"),
    MED_SERVICE_ALREADY_EXIST("MED_SER_ERR_04"),
    MED_SERVICE_UPDATE_SUCCESS("MED_SER_SUC_02"),
    MED_SERVICE_UPDATE_ERROR("MED_SER_ERR_04"),
    MED_SERVICE_NOT_FOUND("MED_SER_ERR_03");

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
