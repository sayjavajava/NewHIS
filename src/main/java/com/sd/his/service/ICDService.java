package com.sd.his.service;

import com.sd.his.model.ICDCode;
import com.sd.his.model.ICDCodeVersion;
import com.sd.his.model.ICDVersion;
import com.sd.his.repository.ICDCodeRepository;
import com.sd.his.repository.ICDCodeVersionRepository;
import com.sd.his.repository.ICDVersionRepository;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.ICDCodeVersionWrapper;
import com.sd.his.wrapper.ICDCodeWrapper;
import com.sd.his.wrapper.ICDVersionWrapper;
import com.sd.his.wrapper.ICDCodeCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ICDService {

    @Autowired
    private ICDCodeRepository codeRepository;
    @Autowired
    private ICDVersionRepository versionRepository;
    @Autowired
    private ICDCodeVersionRepository codeVersionRepository;


    public List<ICDVersionWrapper> versios() {
        return this.versionRepository.findAllByCreatedOnNotNull();
    }

    public List<ICDCodeWrapper> codes() {
        return this.codeRepository.findAllByCreatedOnNotNull();
    }


    public ICDCode saveICD(ICDCodeCreateRequest createRequest) {
        ICDCode icd = new ICDCode(createRequest);
        return codeRepository.save(icd);
    }

    public boolean isICDCodeAlreadyExist(String iCDCode) {
        ICDCode icd = codeRepository.findByCode(iCDCode);
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }

    public boolean isICDVersionNameAlreadyExist(String iCDVersionName) {
        ICDVersion icd = versionRepository.findByName(iCDVersionName);
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }

    public boolean isICDCodeAlreadyExistAgainstICDCodeId(String iCDCode, long iCDCodeId) {
        ICDCode icd = codeRepository.findByCodeAndIdNot(iCDCode, iCDCodeId);
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }

    public List<ICDCodeWrapper> findCodes(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<ICDCodeWrapper> list = codeRepository.findAllByCreatedOnNotNull(pageable);

        if (list != null) {
            for (ICDCodeWrapper codeWrapper : list) {
                if (this.codeVersionRepository.isCodeAssociated(codeWrapper.getId())) {
                    codeWrapper.setHasChild(true);
                }
            }
        }
        return list;
    }

    public int countCodes() {
        return codeRepository.findAllByCreatedOnNotNull().size();
    }

    public List<ICDVersionWrapper> findVersions(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<ICDVersionWrapper> list = versionRepository.findAllByCreatedOnNotNull(pageable);
        if (list != null) {
            for (ICDVersionWrapper versionWrapper : list) {
                if (this.codeVersionRepository.isVersionAssociated(versionWrapper.getId())) {
                    versionWrapper.setHasChild(true);
                }
            }
        }
        return list;
    }

    public int countVersion() {
        return versionRepository.findAllByCreatedOnNotNull().size();
    }

    public List<ICDCodeWrapper> searchCodes(String code, int offset, int limit) {
        Pageable page = new PageRequest(offset, limit);
        List<ICDCodeWrapper> list = codeRepository.findAllByCodeContaining(code, page);
        if (list != null) {
            for (ICDCodeWrapper codeWrapper : list) {
                if (this.codeVersionRepository.isCodeAssociated(codeWrapper.getId())) {
                    codeWrapper.setHasChild(true);
                }
            }
        }
        return list;
    }

    public int countSearchCodes(String code) {
        return codeRepository.findAllByCodeContaining(code).size();
    }

    public List<ICDCodeVersionWrapper> searchCodeVersionByCodeAndVersionName(Pageable pageable, String versionName, String code) {
        return this.codeVersionRepository.findAllCodeVersionByVersion_NameAndIcd_Code(versionName, code, pageable);
    }

    public int countSearchCodeVersionByCodeAndVersionName(String versionName, String code) {
        return this.codeVersionRepository.countFindAllCodeVersionByVersion_NameAndIcd_Code(versionName, code).size();
    }

    public List<ICDCodeVersionWrapper> searchCodeVersionByVersionName(Pageable pageable, String versionName) {
        return this.codeVersionRepository.findAllCodeVersionByVersion_Name(versionName, pageable);
    }

    public int countSearchCodeVersionByVersionName(String versionName) {
        return codeVersionRepository.countFindAllCodeVersionByVersion_Name(versionName).size();
    }

    public List<ICDCodeVersionWrapper> searchCodeVersionByCode(Pageable pageable, String code) {
        return this.codeVersionRepository.findAllCodeVersionByIcd_Code(code, pageable);
    }

    public int countSearchCodeVersionByCode(String code) {
        return codeVersionRepository.countFindAllCodeVersionByIcd_Code(code).size();
    }

    public List<ICDVersionWrapper> searchByVersion(String name, int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);

        List<ICDVersionWrapper> list = versionRepository.findAllByNameContaining(name, pageable);
        if (list != null) {
            for (ICDVersionWrapper versionWrapper : list) {
                if (this.codeVersionRepository.isCodeAssociated(versionWrapper.getId())) {
                    versionWrapper.setHasChild(true);
                }
            }
        }
        return list;
    }

    public int countSearchByVersion(String name) {
        return versionRepository.findAllByNameContaining(name).size();
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean deletedICD(Long icdId) {
        ICDCode icd = codeRepository.findOne(icdId);
        if (HISCoreUtil.isValidObject(icd)) {
            this.codeRepository.delete(icd);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean deletedICDVersion(long icdId) {
        ICDVersion icdVersion = versionRepository.findOne(icdId);
        if (HISCoreUtil.isValidObject(icdVersion)) {
            versionRepository.delete(icdVersion);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean deletedAssociateICDCV(long icdId) {
        if (icdId > 0) {
            ICDCodeVersion codeVersion = this.codeVersionRepository.findOne(icdId);
            if (codeVersion == null) {
                return false;
            }
            codeVersionRepository.delete(codeVersion);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public void updateICD(ICDCodeCreateRequest createRequest) {
        ICDCode icdCode = this.codeRepository.findOne(createRequest.getId());
        if (HISCoreUtil.isValidObject(icdCode)) {
            icdCode.setCode(createRequest.getCode());
            icdCode.setTitle(createRequest.getTitle());
            icdCode.setDescription(createRequest.getDescription());
            icdCode.setStatus(createRequest.isStatus());
        }
        this.codeRepository.save(icdCode);
    }

    @Transactional(rollbackOn = Throwable.class)
    public ICDVersion saveICDVersion(ICDVersionWrapper createRequest) {
        ICDVersion icdVersion = new ICDVersion(createRequest);
        return versionRepository.save(icdVersion);
    }

    public boolean isICDVersionNameAlreadyExistAgainstICDVersionNameId(String name, long id) {
        ICDVersion icdVersion = versionRepository.findByNameAndIdNot(name, id);
        if (HISCoreUtil.isValidObject(icdVersion)) {
            return true;
        }
        return false;
    }

    @Transactional(rollbackOn = Throwable.class)
    public ICDVersion updateICDVersion(ICDVersionWrapper request) {
        ICDVersion icdVersion = versionRepository.findOne(request.getId());
        if (HISCoreUtil.isValidObject(icdVersion)) {
            icdVersion.setName(request.getName());
            icdVersion.setTitle(request.getTitle());
            icdVersion.setStatus(request.isStatus());
        }
        return icdVersion;
    }

    public List<ICDCodeVersionWrapper> codeVersions(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
//        List<ICDCodeVersionWrapper> cvs = new ArrayList<>();
    /*    List<ICDCodeVersion> codeVersions = this.codeVersionRepository.findAllByOrderByVersion_name(pageable);
        if (codeVersions != null) {
            cvs = APIUtil.buildICDCodeVersionWrapper(codeVersions);
        }*/
        return this.codeVersionRepository.findAllByOrderByVersion_name(pageable);
    }

    public int countCodeVersions() {
        return codeVersionRepository.findAllByOrderByVersion_name().size();
    }

    @Transactional(rollbackOn = Throwable.class)
    public List<ICDCodeVersion> saveAssociateICDCVs(ICDCodeVersionWrapper createRequest) {
        ICDCodeVersion associateICDCVs = null;
        List<ICDCodeVersion> codeVersions = new ArrayList<>();

        ICDVersion icdVersion = this.versionRepository.findOne(Long.parseLong(createRequest.getSelectedICDVersionId()));

        this.codeVersionRepository.deleteAllByVersion_id(icdVersion.getId());
        this.codeVersionRepository.flush();

        for (ICDCodeWrapper codeWrapper : createRequest.getiCDCodes()) {
            if (codeWrapper.isCheckedCode()) {
                associateICDCVs = new ICDCodeVersion();
                associateICDCVs.setVersion(icdVersion);
                associateICDCVs.setDescription(createRequest.getDescription());
                associateICDCVs.setIcd(this.codeRepository.findOne(codeWrapper.getId()));
                codeVersions.add(associateICDCVs);
            }
        }

        return codeVersionRepository.save(codeVersions);
    }

    @Transactional(rollbackOn = Throwable.class)
    public List<ICDCodeVersion> updateCVs(ICDCodeVersionWrapper createRequest) {
        ICDCodeVersion associateICDCVs = null;
        List<ICDCodeVersion> icdCodeVersions = new ArrayList<>();

        ICDVersion icdVersion = this.versionRepository.findOne(Long.parseLong(createRequest.getSelectedICDVersionId()));

        this.codeVersionRepository.deleteAllByVersion_id(icdVersion.getId());
        this.codeVersionRepository.flush();

        for (ICDCodeWrapper codeWrapper : createRequest.getSelectedICDCodes()) {
            associateICDCVs = new ICDCodeVersion();
            associateICDCVs.setVersion(icdVersion);
            associateICDCVs.setIcd(this.codeRepository.findOne(codeWrapper.getId()));

            icdCodeVersions.add(associateICDCVs);
        }

        return codeVersionRepository.save(icdCodeVersions);
    }

    public List<ICDCodeWrapper> getAssociatedICDCVByVId(long versionId) {
        if (versionId > 0) {
            return codeVersionRepository.findAllByVersion_id(versionId);
        }
        return null;
    }

    public boolean isCodeAssociated(long codeId) {
        return this.codeVersionRepository.isCodeAssociated(codeId);
    }

    public boolean isVersionAssociated(long versionId) {
        return this.codeVersionRepository.isVersionAssociated(versionId);
    }
}
