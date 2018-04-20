package com.sd.his.request;

import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 20-Apr-18
 * @version   : ver. 1.0.0
 * 
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________ 
 *	
 * 
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.request
 * @FileName  : AssignAuthoritiesRequestWrapper
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
public class AssignAuthoritiesRequestWrapper {

    String role;
    List<Integer> permissionsIds;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Integer> getPermissionsIds() {
        return permissionsIds;
    }

    public void setPermissionsIds(List<Integer> permissionsIds) {
        this.permissionsIds = permissionsIds;
    }
}
