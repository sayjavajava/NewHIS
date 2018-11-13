package com.sd.his.wrapper;

import com.sd.his.model.State;
import com.sd.his.utill.HISConstants;
import com.sd.his.utill.HISCoreUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
public class StateWrapper extends BaseWrapper {

    private String name;
    private CountryWrapper countryWrapper;
    private List<CityWrapper> cityWrappers;
    private boolean status;


    public StateWrapper() {
    }

    public StateWrapper(Long id, Date createdOn, Date updatedOn,
                        String name, CountryWrapper countryWrapper, List<CityWrapper> cityWrappers, boolean status) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn, HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(updatedOn, HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.name = name;
        this.countryWrapper = countryWrapper;
        this.cityWrappers = cityWrappers;
        this.status = status;
    }

    public StateWrapper(State state) {

        super(state.getId(),
                HISCoreUtil.convertDateToString(state.getCreatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(state.getUpdatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.name = state.getName();
        /*this.countryWrapper = state.getcountryWrapper;
        this.cityWrappers = cityWrappers;*/
        this.status = state.isStatus();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryWrapper getCountryWrapper() {
        return countryWrapper;
    }

    public void setCountryWrapper(CountryWrapper countryWrapper) {
        this.countryWrapper = countryWrapper;
    }

    public List<CityWrapper> getCityWrappers() {
        return cityWrappers;
    }

    public void setCityWrappers(List<CityWrapper> cityWrappers) {
        this.cityWrappers = cityWrappers;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
