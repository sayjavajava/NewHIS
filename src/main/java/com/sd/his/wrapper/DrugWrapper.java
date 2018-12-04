package com.sd.his.wrapper;

import com.sd.his.model.Country;
import com.sd.his.model.Drug;
import com.sd.his.utill.HISConstants;
import com.sd.his.utill.HISCoreUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jamal on 10/22/2018.
 */
public class DrugWrapper extends BaseWrapper {

    private String drugNaturalId;
    private String drugName;
    private String genericName;
    private String companyName;
    private String route;
    private List<String> strengths;
    private String strength = "";
    private String uOM;

    public String getCountryView() {
        return countryView;
    }

    public void setCountryView(String country) {
        this.countryView = country;
    }

    private String countryView;
    public String getDrugInfo() {
        return drugInfo;
    }

    public void setDrugInfo(String drugInfo) {
        this.drugInfo = drugInfo;
    }

    private String drugInfo;


   public String getSelectedCountry() {
        return selectedCountry;
    }


    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    private String selectedCountry;

   /* public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    // private String origin;
    private Country country;*/
    private boolean active = true;
    private boolean hasChild;

    public Map<String, Object> getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(Map<String, Object> addInfo) {
        this.addInfo = addInfo;
    }

    Map<String, Object> addInfo;

    public DrugWrapper() {
    }

    public DrugWrapper(String drugName,String route) {
        this.drugName = drugName;
        this.route=route;
    }

    public DrugWrapper(Long id, Date createdOn, Date updatedOn,
                       String drugNaturalId, String drugName, String genericName, String companyName, String route,
                       List<String> strengths, String uOM, Country origin, boolean active, boolean hasChild) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn, HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(updatedOn, HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.drugNaturalId = drugNaturalId;
        this.drugName = drugName;
        this.genericName = genericName;
        this.companyName = companyName;
        this.route = route;
       this.strengths = strengths;

        if (strengths != null && strengths.size() > 0) {
            for (String s : strengths) {
                this.strength.concat(s + ",");
            }
        }
        this.uOM = uOM;
    //    this.selectedCountry = origin;
        this.active = active;
        this.hasChild = hasChild;
    }

    public DrugWrapper(Drug drug) {
        super(drug.getId(),
                HISCoreUtil.convertDateToString(drug.getCreatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(drug.getUpdatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.drugNaturalId = drug.getDrugNaturalId();
        this.drugName = drug.getDrugName();
        this.genericName = drug.getGenericName();
        this.companyName = drug.getCompanyName();
        this.countryView=drug.getCountry().getName();
        this.route = drug.getRoute();
        this.strengths = drug.getStrengths();

        if (drug.getStrengths() != null && drug.getStrengths().size() > 0) {
            for (String s : drug.getStrengths()) {
                this.strength += (s + ",");
            }
            this.strength = this.strength.substring(0, this.getStrength().length() - 1);
        }
        this.uOM = drug.getuOM();
       // this.origin = drug.getOrigin();
   //     this.selectedCountry=drug.getCountry();
        this.active = drug.isActive();
        this.hasChild = false;
    }

    public String getDrugNaturalId() {
        return drugNaturalId;
    }

    public void setDrugNaturalId(String drugNaturalId) {
        this.drugNaturalId = drugNaturalId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

   public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public String getuOM() {
        return uOM;
    }

    public void setuOM(String uOM) {
        this.uOM = uOM;
    }

   /* public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }*/

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }
}
