package com.sd.his.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.lang.annotation.Target;
import java.util.List;

/**
 * Created by jamal on 11/7/2018.
 */
@Entity
@Table(name = "COUNTRY")
public class Country extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "ISO3")
    private String iso3;

    @Column(name = "ISO2")
    private String iso2;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "PHONE_CODE")
    private String phoneCode;

    @Column(name = "CAPITAL")
    private String capital;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "STATUS")
    private boolean status;

    @OneToMany(targetEntity = State.class, mappedBy = "country")
    private List<State> states;

    @OneToMany(targetEntity = City.class, mappedBy = "country")
    private List<City> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}
