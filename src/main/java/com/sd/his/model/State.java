package com.sd.his.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jamal on 11/7/2018.
 */
@Entity
@Table
public class State extends BaseEntity {

    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state")
    private List<City> cities;

    private boolean status;

    public State() {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
