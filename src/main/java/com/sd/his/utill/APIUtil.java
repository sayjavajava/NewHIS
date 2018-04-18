package com.sd.his.utill;

import com.sd.his.model.Permission;
import com.sd.his.model.Role;
import com.sd.his.model.User;
import com.sd.his.model.wrapper.AdminWrapper;
import com.sd.his.model.wrapper.PermissionWrapper;

import java.util.ArrayList;
import java.util.List;

/*
 * @author    : irfan
 * @Date      : 16-Apr-18
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
 * @Package   : com.sd.his.utill
 * @FileName  : APIUtil
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
public class APIUtil {
    public static AdminWrapper buildAdminWrapper(User dbAdmin) {
        AdminWrapper admin = new AdminWrapper();
        List<PermissionWrapper> permissions = new ArrayList<>();

        admin.setId(dbAdmin.getId());
        admin.setEmail(dbAdmin.getEmail());
        admin.setUserName(dbAdmin.getUsername());
        admin.setActive(dbAdmin.isActive());
        //#TODO need to change
        admin.setRole(dbAdmin.getRole().get(0).getName());
        admin.setFirstName(dbAdmin.getContact().getFirstName());
        admin.setLastName(dbAdmin.getContact().getLastName());
        admin.setPhoneNumber(dbAdmin.getContact().getPhoneNumber());
        admin.setDeleted(dbAdmin.getContact().getDeleted());
        admin.setCreatedByFullName("");
        admin.setCreatedOn(dbAdmin.getContact().getCreatedOn());
        admin.setGender(dbAdmin.getContact().getGender());
        admin.setProfileImg(dbAdmin.getContact().getProfileImg());
        admin.setAddress(dbAdmin.getContact().getAddress());
        admin.setState(dbAdmin.getContact().getState());
        admin.setCity(dbAdmin.getContact().getCity());
        admin.setCountry(dbAdmin.getContact().getCountry());
        admin.setStatus(dbAdmin.getContact().getStatus());
        for (Role role : dbAdmin.getRole()) {
            for (Permission per : role.getPermissions()) {
                PermissionWrapper permissionWrapper = new PermissionWrapper(per.getName(), per.getDescription());
                permissions.add(permissionWrapper);
            }
            admin.setPermission(permissions);
        }
        return admin;
    }
}
