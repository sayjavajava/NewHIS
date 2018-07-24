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
}