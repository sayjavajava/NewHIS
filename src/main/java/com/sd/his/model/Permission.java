/**
 *
 */
package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Siva
 */
@Entity
@Table(name = "PERMISSION")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "DESCRIPTION",length = 1024)
    private String description;
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    @Column(name = "IS_DELETED")
    private boolean isDeleted;
    @Column(name = "CREATED_ON")
    private Date created_On;
    @Column(name = "UPDATED_ON")
    private boolean updated_On;


    public Permission() {
    }

    public Permission(String name, String description, boolean isActive, boolean isDeleted, Date created_On, boolean updated_On, List<Role> roles) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.created_On = created_On;
        this.updated_On = updated_On;
        this.roles = roles;
    }

    @ManyToMany(mappedBy = "permissions")
    @JsonBackReference
    private List<Role> roles;


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreated_On() {
        return created_On;
    }

    public void setCreated_On(Date created_On) {
        this.created_On = created_On;
    }

    public boolean isUpdated_On() {
        return updated_On;
    }

    public void setUpdated_On(boolean updated_On) {
        this.updated_On = updated_On;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
