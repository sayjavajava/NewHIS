package com.sd.his.service;

import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.Prefix;
import com.sd.his.repository.PrefixRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
