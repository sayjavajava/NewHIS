package com.sd.his.service;

import com.sd.his.model.ICD;
import com.sd.his.model.ICDVersion;
import com.sd.his.repositiories.ICDRepository;
import com.sd.his.repositiories.ICDVersionRepository;
import com.sd.his.request.ICDCreateRequest;
import com.sd.his.utill.APIUtil;
import com.sd.his.utill.HISCoreUtil;
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


    public List<ICDVersionWrapper> getICDCodeVersionList() {
        return APIUtil.buildICDVersionWrapper(this.iCDVersionRepository.findAll());
    }

    public ICD saveICD(ICDCreateRequest createRequest) {
        ICD icd = new ICD(createRequest);
        ICDVersion icdVersion = iCDVersionRepository.findOne(createRequest.getSelectedICDVersion());

        if (HISCoreUtil.isValidObject(icdVersion)) {
            icd.setCodeVersion(icdVersion.getName());
            return iCDRepository.save(icd);
        }
        return null;
    }

    /**
     * code version from ICD VERSION it means PK value
     */
    public boolean isCodeVersionAvailableAgainstICD(long codeVersion) {
        ICD icd = iCDRepository.findByCodeVersion(iCDVersionRepository.findOne(codeVersion).getName());
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }
}
