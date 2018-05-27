package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 24-Apr-18
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
 * @FileName  : Organization
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Entity
@Table(name = "ORGANIZATION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "OFFICE_PHONE")
    private String officePhone;

    @Column(name = "LOGO_URL")
    private String logUrl;

    @Column(name = "APT_DEFAULT_CLR")
    private String aptDefaultClr;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default false", nullable = false)
    private boolean active;

    @Column(name = "IS_DELETED", columnDefinition = "boolean default false", nullable = false)
    private boolean deleted;

    @Column(name = "UPDATED_ON")
    private long updatedOn;

    @Column(name = "CREATED_ON")
    private long createdOn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "S3_BUCKET_ID")
    private S3Bucket s3Bucket;

    @JsonIgnore
    @OneToMany(targetEntity = Prefix.class, mappedBy = "prefix", fetch = FetchType.LAZY)
    private List<Prefix> prefixes;

    @ManyToMany(targetEntity = Speciality.class, cascade = {CascadeType.ALL})
    @JoinTable(name = "ORG_SPECIALITY", joinColumns = {@JoinColumn(name = "ORG_ID")},
            inverseJoinColumns = {@JoinColumn(name = "SPECIALITY_ID")})
    private List<Speciality> specialities;

    public Organization() {
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", deleted=" + deleted +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public String getAptDefaultClr() {
        return aptDefaultClr;
    }

    public void setAptDefaultClr(String aptDefaultClr) {
        this.aptDefaultClr = aptDefaultClr;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public S3Bucket getS3Bucket() {
        return s3Bucket;
    }

    public void setS3Bucket(S3Bucket s3Bucket) {
        this.s3Bucket = s3Bucket;
    }

    public List<Prefix> getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(List<Prefix> prefixes) {
        this.prefixes = prefixes;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }
}
