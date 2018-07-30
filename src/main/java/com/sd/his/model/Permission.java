package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PERMISSION")
public class Permission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME",nullable = false, unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "URL")
    private String url;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default true", nullable = false)
    private Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "permission")
    private List<RolePermission> rolePermissions;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}
