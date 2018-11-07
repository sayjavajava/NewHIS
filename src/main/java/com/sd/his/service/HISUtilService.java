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
/*
* this method used where you need to get current prefix and update sequence of that prefix
* used where you need both operation
* */
    public String getPrefixId(ModuleEnum moduleName) {
       String currentPrefix =  generatePrefix(moduleName);
        updatePrefix(moduleName);
        return currentPrefix;
    }

/*
this method use for getting prefix on popup and will not update current sequence in database
* */
    public String generatePrefix(ModuleEnum moduleName) {
        Prefix prefix= prefixRepository.findByModule(moduleName);
        String currentPrefix = prefix.getName()+ prefix.getCurrentValue();
        return currentPrefix;
    }

    /*
this method use for update current sequence in database after saving new entity in database
* */
    public void updatePrefix(ModuleEnum moduleName) {
        Prefix prefix= prefixRepository.findByModule(moduleName);
        prefix.setCurrentValue(prefix.getCurrentValue()+1L);
        prefixRepository.save(prefix);
    }

}
