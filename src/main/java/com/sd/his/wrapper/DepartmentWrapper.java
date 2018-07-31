package com.sd.his.wrapper;

import com.sd.his.model.Department;

/*
 * @author    : Arif Heer
 * @Date      : 4/10/2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer    Date       Version  Operation  Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.wrapper
 * @FileName  : DepartmentWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class DepartmentWrapper {

    long id;
    String name;
    String description;
    boolean active;
    long branchId;

    public DepartmentWrapper() {
    }

    public DepartmentWrapper(Department dpt) {
        this.id = dpt.getId();
        this.name = dpt.getName();
        this.description = dpt.getDescription();
        this.active = dpt.getActive() == null ? false : dpt.getActive();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}