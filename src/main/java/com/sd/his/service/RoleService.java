package com.sd.his.service;

import com.sd.his.model.Role;
import com.sd.his.repositiories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @Package   : com.sd.his.service
 * @FileName  : RoleService
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    private final Logger logger = LoggerFactory.getLogger(RoleService.class);

    public List<Role> getAllActiveRoles() {
        return roleRepository.findAllByActiveTrueAndDeletedFalse();
    }

}
