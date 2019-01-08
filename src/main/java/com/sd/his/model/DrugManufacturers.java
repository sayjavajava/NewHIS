package com.sd.his.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DRUG_MANUFACTURERS")
public class DrugManufacturers  extends BaseEntity {

    @NaturalId
    @Column(name = "drug_manufacturer_natural")
    private String drugManufacturerNaturalId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ACTIVE")
    private Boolean status;

    public DrugManufacturers() {
    }

    public DrugManufacturers(String drugManufacturerNaturalId, String name, Boolean status) {
        this.drugManufacturerNaturalId = drugManufacturerNaturalId;
        this.name = name;
        this.status = status;
    }

    public String getDrugManufacturerNaturalId() {
        return drugManufacturerNaturalId;
    }

    public void setDrugManufacturerNaturalId(String drugManufacturerNaturalId) {
        this.drugManufacturerNaturalId = drugManufacturerNaturalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

