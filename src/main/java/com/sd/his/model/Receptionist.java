package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "RECEPTIONIST")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Receptionist extends StaffProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="USER_ID", unique= true)
    private User user;

    @JsonIgnore
    @OneToMany(targetEntity = BranchReceptionist.class, mappedBy = "receptionist")
    private List<BranchReceptionist> branchReceptionists;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "ALLOW_DISCOUNT")
    private Double allowDiscount;

    @Column(name = "CAN_RECEIVE_PAYMENT")
    private Boolean canReceivePayment;

    @ElementCollection
    @JoinTable(name="RECEPTIONIST_DOCTOR_DASHBOARD", joinColumns=@JoinColumn(name="ID"))
    @MapKeyColumn (name="DOC_ID")
    @Column(name="NAME")
    private Map<Long, String> selectedDoctorDashboard = new HashMap<Long, String>();

    public User getUser() {
        return user;
    }


    public List<BranchReceptionist> getBranchReceptionists() {
        return branchReceptionists;
    }

    public void setBranchReceptionists(List<BranchReceptionist> branchReceptionists) {
        this.branchReceptionists = branchReceptionists;
    }

    public Boolean getCanReceivePayment() {
        return canReceivePayment;
    }

    public void setCanReceivePayment(Boolean canReceivePayment) {
        this.canReceivePayment = canReceivePayment;
    }

    public Double getAllowDiscount() {
        return allowDiscount;
    }

    public void setAllowDiscount(Double allowDiscount) {
        this.allowDiscount = allowDiscount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Map<Long, String> getSelectedDoctorDashboard() {
        return selectedDoctorDashboard;
    }

    public void setSelectedDoctorDashboard(Map<Long, String> selectedDoctorDashboard) {
        this.selectedDoctorDashboard = selectedDoctorDashboard;
    }

    public void setUser(User user) {
        this.user = user;
    }
}