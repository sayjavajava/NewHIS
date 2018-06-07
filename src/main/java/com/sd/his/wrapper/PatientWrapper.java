package com.sd.his.wrapper;

import com.sd.his.model.Insurance;
import com.sd.his.model.Profile;
import com.sd.his.model.User;

import java.util.List;

public class PatientWrapper {

    long id;
    String userType;
    String email;
    String userName;
    private boolean active;
    private boolean deleted;
    private ProfileWrapper profileWrapper;
    private InsuranceWrapper insuranceWrapper;

    public PatientWrapper() {
    }

    public PatientWrapper(User user, Profile profile, Insurance insurance) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.active = user.isActive();
        this.deleted = user.isDeleted();
        this.email = user.getEmail();
        this.userType = user.getUserType();

        this.setProfileWrapper(new ProfileWrapper(profile));
        this.setInsuranceWrapper(new InsuranceWrapper(insurance));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ProfileWrapper getProfileWrapper() {
        return profileWrapper;
    }

    public void setProfileWrapper(ProfileWrapper profileWrapper) {
        this.profileWrapper = profileWrapper;
    }

    public InsuranceWrapper getInsuranceWrapper() {
        return insuranceWrapper;
    }

    public void setInsuranceWrapper(InsuranceWrapper insuranceWrapper) {
        this.insuranceWrapper = insuranceWrapper;
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
}
