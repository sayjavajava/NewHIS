package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "CASHIER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cashier extends StaffProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    private User user;

    @JsonIgnore
    @OneToMany(targetEntity = BranchCashier.class, mappedBy = "cashier")
    private List<BranchCashier> branchCashiers;

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
}
