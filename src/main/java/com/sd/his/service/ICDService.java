package com.sd.his.service;

import com.sd.his.repositiories.ICDRepository;
import com.sd.his.repositiories.ICDVersionRepository;
import com.sd.his.utill.APIUtil;
import com.sd.his.wrapper.ICDVersionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ICDService {

    @Autowired
    private ICDRepository iCDRepository;
    @Autowired
    private ICDVersionRepository iCDVersionRepository;


    public List<ICDVersionWrapper> getICDCodeVersionList(){
        return APIUtil.buildICDVersionWrapper(this.iCDVersionRepository.findAll());
    }
}
