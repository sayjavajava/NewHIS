package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Table(name = "LAB_TEST_SPECIMAN")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LabTestSpeciman extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "TEST_CODE")
    private String testCode;

    @Column(name = "TEST_NAME")
    private String testName;

    @Column(name = "MIN_NORMAL_RANGE")
    private String minNormalRange;

    @Column(name = "MAX_NORMAL_RANGE")
    private String maxNormalRange;

    @Column(name = "DESCRIPTION")
    private String description;


    public LabTestSpeciman() {
    }

    public LabTestSpeciman(LabTestSpeciman labTestSpeciman) {
        this.setId(labTestSpeciman.getId());
        this.testCode = labTestSpeciman.getTestCode();
        this.testName = labTestSpeciman.getTestName();
        this.maxNormalRange = labTestSpeciman.getMaxNormalRange();
        this.minNormalRange = labTestSpeciman.getMinNormalRange();
        this.description = labTestSpeciman.getDescription();
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getMinNormalRange() {
        return minNormalRange;
    }

    public void setMinNormalRange(String minNormalRange) {
        this.minNormalRange = minNormalRange;
    }

    public String getMaxNormalRange() {
        return maxNormalRange;
    }

    public void setMaxNormalRange(String maxNormalRange) {
        this.maxNormalRange = maxNormalRange;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
