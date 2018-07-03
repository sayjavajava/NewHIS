package com.sd.his.enums;

/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
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
 * @FileName  : UserTypeEnum
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public enum EmailServerTypeEnum {
    SMTP("SMTP"),
    AWS_SES("SES");

    private String value;

    EmailServerTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
