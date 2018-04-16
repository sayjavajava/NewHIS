package com.sd.his.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    @NotNull
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    @Column(name = "IS_DELETED")
    private boolean isDeleted;
    @Column(name = "UPDATED_ON")
    private long updated_On;
    @Column(name = "CREATED_ON")
    private long created_On;

    @ManyToMany
    @JoinTable(
            name="ROLE_PERMISSION",
            joinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="PERMISSION_ID", referencedColumnName="ID")})
    private List<Permission> permissions;

    @ManyToMany(mappedBy = "Role")
    @JsonBackReference
    private List<User> users;

    public Role(){}

    public Role(String name, String description, boolean isActive, boolean isDeleted, long updated_On, long created_On, List<Permission> permissions, List<User> users) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.updated_On = updated_On;
        this.created_On = created_On;
        this.permissions = permissions;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    public long getUpdated_On() {
        return updated_On;
    }

    public void setUpdated_On(long updated_On) {
        this.updated_On = updated_On;
    }

    public long getCreated_On() {
        return created_On;
    }

    public void setCreated_On(long created_On) {
        this.created_On = created_On;
    }
}
