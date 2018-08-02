package com.sd.his.service;

import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.PermissionWrapper;
import com.sd.his.wrapper.RoleWrapper;
import com.sd.his.wrapper.UserWrapper;
import com.sd.his.wrapper.request.AssignAuthoritiesRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HISUtilService {


    @Autowired
    private PrefixRepository prefixRepository;



    private final Logger logger = LoggerFactory.getLogger(HISUtilService.class);



    public String getPrefixId(ModuleEnum moduleName) {
        Prefix prefix= prefixRepository.findByModule(moduleName);
        String currentPrefix = prefix.getName()+ prefix.getCurrentValue();
        prefix.setCurrentValue(prefix.getCurrentValue()+1L);
        prefixRepository.save(prefix);
        return currentPrefix;
    }



}
