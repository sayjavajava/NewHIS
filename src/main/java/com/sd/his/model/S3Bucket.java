package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
 * @FileName  : S3Bucket
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "S3_BUCKET")
@JsonIgnoreProperties(ignoreUnknown = true)
public class S3Bucket implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACCESS_KEY", unique = true)
    private String accessKey;

    @Column(name = "SECRET_KEY", unique = true)
    private String secretKey;

    @Column(name = "ACCESS_PROTOCOL")
    private String accessProtocol;

    @Column(name = "PUBLIC_BASE_URL")
    private String publicBaseURL;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_ON", nullable = false)
    private Date updatedOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_ON", nullable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUCKET_ID")
    private S3Bucket bucket;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default true", nullable = false)
    private boolean active;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAccessProtocol() {
        return accessProtocol;
    }

    public void setAccessProtocol(String accessProtocol) {
        this.accessProtocol = accessProtocol;
    }

    public String getPublicBaseURL() {
        return publicBaseURL;
    }

    public void setPublicBaseURL(String publicBaseURL) {
        this.publicBaseURL = publicBaseURL;
    }

    public S3Bucket getBucket() {
        return bucket;
    }

    public void setBucket(S3Bucket bucket) {
        this.bucket = bucket;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "S3Bucket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", accessProtocol='" + accessProtocol + '\'' +
                ", publicBaseURL='" + publicBaseURL + '\'' +
                ", active=" + active +
                '}';
    }


}
