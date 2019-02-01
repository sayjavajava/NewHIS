package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sd.his.wrapper.ICDVersionWrapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sd.his.wrapper.ICDVersionWrapper;
import com.sd.his.wrapper.InsuranceProfileWrapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "INSURANCE_PROFILE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InsuranceProfile extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS", columnDefinition = "boolean default true", nullable = false)
    private boolean status;



    public InsuranceProfile() {
    }

    public InsuranceProfile(InsuranceProfileWrapper createRequest) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.status = createRequest.isStatus();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

