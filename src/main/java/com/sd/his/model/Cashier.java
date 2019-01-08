package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "CASHIER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cashier extends StaffProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    private User user;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "ALLOW_DISCOUNT")
    private Double allowDiscount;

    @Column(name = "CAN_RECEIVE_PAYMENT")
    private Boolean canReceivePayment;

    @ElementCollection
    @JoinTable(name="CASHIER_DOCTOR_DASHBOARD", joinColumns=@JoinColumn(name="ID"))
    @MapKeyColumn (name="DOC_ID")
    @Column(name="NAME")
    private Map<Long, String> selectedDoctorDashboard = new HashMap<Long, String>();

    @JsonIgnore
    @OneToMany(targetEntity = BranchCashier.class, mappedBy = "cashier")
    private List<BranchCashier> branchCashiers;

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

    public Boolean getCanReceivePayment() {
        return canReceivePayment;
    }

    public void setCanReceivePayment(Boolean canReceivePayment) {
        this.canReceivePayment = canReceivePayment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BranchCashier> getBranchCashiers() {
        return branchCashiers;
    }

    public void setBranchCashiers(List<BranchCashier> branchCashiers) {
        this.branchCashiers = branchCashiers;
    }

    public Double getAllowDiscount() {
        return allowDiscount;
    }

    public void setAllowDiscount(Double allowDiscount) {
        this.allowDiscount = allowDiscount;
    }
}
