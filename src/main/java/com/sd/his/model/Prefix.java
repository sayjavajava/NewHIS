package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sd.his.enums.ModuleEnum;
import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
 * @author    : Tahir Mehmood
 * @Date      : 16-Jul-18
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
 * @Package   : com.sd.his.model
 * @FileName  : Prefix
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Entity
@Table(name = "PREFIX")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prefix extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "PREFIX")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "MODULE")
    private ModuleEnum module;

    @Column(name = "START_VALUE")
    private Long startValue;

    @Column(name = "CURRENT_VALUE")
    private Long currentValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organization organization;

    public Prefix() {}

    public Prefix(String name, ModuleEnum module, Long startValue, Long currentValue, Organization organization) {
        this.name = name;
        this.module = module;
        this.startValue = startValue;
        this.currentValue = currentValue;
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartValue() {
        return startValue;
    }

    public void setStartValue(Long startValue) {
        this.startValue = startValue;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
