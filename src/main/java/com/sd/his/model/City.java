package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jamal on 11/7/2018.
 */
@Entity
@Table(name = "CITY")
public class City extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    @JsonBackReference
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_ID")
    @JsonBackReference
    private State state;

    @Column(name = "STATUS")
    private boolean status;

    @OneToMany(targetEntity = Branch.class, mappedBy = "city")
    @JsonBackReference
    private List<Branch> branches;

    public City() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
