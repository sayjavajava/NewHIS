package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    public User getUser() {
        return user;
    }

    public List<BranchReceptionist> getBranchReceptionists() {
        return branchReceptionists;
    }

    public void setBranchReceptionists(List<BranchReceptionist> branchReceptionists) {
        this.branchReceptionists = branchReceptionists;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }
}