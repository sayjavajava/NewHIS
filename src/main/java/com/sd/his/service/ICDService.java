package com.sd.his.service;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.ICDCode;
import com.sd.his.model.ICDCodeVersion;
import com.sd.his.model.ICDVersion;
import com.sd.his.repositiories.ICDCodeRepository;
import com.sd.his.repositiories.ICDCodeVersionRepository;
import com.sd.his.repositiories.ICDVersionRepository;
import com.sd.his.request.ICDCodeCreateRequest;
import com.sd.his.utill.APIUtil;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.ICDCodeVersionWrapper;
import com.sd.his.wrapper.ICDCodeWrapper;
import com.sd.his.wrapper.ICDVersionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ICDService {

    @Autowired
    private ICDCodeRepository iCDCodeRepository;
    @Autowired
    private ICDCodeVersionRepository icdCodeVersionRepository;
    @Autowired
    private ICDVersionRepository iCDVersionRepository;


    public List<ICDVersionWrapper> versiosNotDeleted() {
        return APIUtil.buildICDVersionWrapper(this.iCDVersionRepository.findAllByDeletedFalse());
    }

    public List<ICDCodeWrapper> codesNotDeleted() {
        return APIUtil.buildICDCodesWrapper(this.iCDCodeRepository.findAllByDeletedFalse());
    }

    @Transactional
    public ICDCode saveICD(ICDCodeCreateRequest createRequest) {
        ICDCode icd = new ICDCode(createRequest);
        return iCDCodeRepository.save(icd);
    }

    public boolean isICDCodeAlreadyExist(String iCDCode) {
        ICDCode icd = iCDCodeRepository.findByCode(iCDCode);
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }

    public boolean isICDVersionNameAlreadyExist(String iCDVersionName) {
        ICDVersion icd = iCDVersionRepository.findByNameAndDeletedFalse(iCDVersionName);
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }

    public boolean isICDCodeAlreadyExistAgainstICDCodeId(String iCDCode, long iCDCodeId) {
        ICDCode icd = iCDCodeRepository.findByCodeAndIdNot(iCDCode, iCDCodeId);
        if (HISCoreUtil.isValidObject(icd)) {
            return true;
        }
        return false;
    }

    public List<ICDCodeWrapper> findCodes(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<ICDCodeWrapper> icds = new ArrayList<>();
        List<ICDCode> paginatedICDs = iCDCodeRepository.findAllByDeletedFalse(pageable);
        if (!HISCoreUtil.isListEmpty(paginatedICDs)) {
            icds = APIUtil.buildICDCodeWrapper(paginatedICDs);
        }
        return icds;
    }

    public int countCodes() {
        return iCDCodeRepository.findAllByDeletedFalse().size();
    }

    public List<ICDVersionWrapper> findVersions(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<ICDVersionWrapper> icdsVersion = new ArrayList<>();
        List<ICDVersion> paginatedICDsVersion = iCDVersionRepository.findAllByDeletedFalse(pageable);
        if (!HISCoreUtil.isListEmpty(paginatedICDsVersion)) {
            icdsVersion = APIUtil.buildICDVersionWrapper(paginatedICDsVersion);
        }
        return icdsVersion;
    }

    public int countVersion() {
        return iCDVersionRepository.findAllByDeletedFalse().size();
    }

    public List<ICDCodeWrapper> searchCodes(String code, int offset, int limit) {
        List<ICDCodeWrapper> icds = new ArrayList<>();
        Pageable page = new PageRequest(offset, limit);
        List<ICDCode> searchedICDs = iCDCodeRepository.findAllByCodeContainingAndDeletedFalse(code, page);
        if (!HISCoreUtil.isListEmpty(searchedICDs)) {
            icds = APIUtil.buildICDCodeWrapper(searchedICDs);
        }
        return icds;
    }

    public int countSearchCodes(String code) {
        return iCDCodeRepository.findAllByCodeContainingAndDeletedFalse(code).size();
    }

    public List<ICDCodeVersionWrapper> searchCodeVersionByVersionName(int offset, int limit, String versionName) {
        List<ICDCodeVersionWrapper> codeVersionsWrapper = new ArrayList<>();
        Pageable pageable = new PageRequest(offset, limit);

        List<ICDCodeVersion> searchedCVsByName = this.icdCodeVersionRepository.findAllByVersion_NameContainingAndVersion_DeletedFalse(versionName, pageable);
        if (!HISCoreUtil.isListEmpty(searchedCVsByName)) {
            codeVersionsWrapper = APIUtil.buildICDCodeVersionWrapper(searchedCVsByName);
        }
        return codeVersionsWrapper;
    }

    public int countSearchCodeVersionByVersionName(String versionName) {
        return icdCodeVersionRepository.findAllByVersion_NameContainingAndVersion_DeletedFalse(versionName).size();
    }

    public List<ICDVersionWrapper> searchByVersion(String name, int offset, int limit) {
        List<ICDVersionWrapper> icdsVersion = new ArrayList<>();
        Pageable pageable = new PageRequest(offset, limit);
        List<ICDVersion> searchedICDsVersion = iCDVersionRepository.findAllByNameContainingAndDeletedFalse(name, pageable);
        if (!HISCoreUtil.isListEmpty(searchedICDsVersion)) {
            icdsVersion = APIUtil.buildICDVersionWrapper(searchedICDsVersion);
        }
        return icdsVersion;
    }

    public int countSearchByVersion(String name) {
        return iCDVersionRepository.findAllByNameContainingAndDeletedFalse(name).size();
    }

    @Transactional(rollbackOn = Throwable.class)
    public String deletedICD(Long icdId) {
        ICDCode icd = iCDCodeRepository.findOne(icdId);
        if (HISCoreUtil.isValidObject(icd)) {
            icd.setDeleted(true);
            iCDCodeRepository.save(icd);
            return ResponseEnum.SUCCESS.getValue();
        } else {
            return ResponseEnum.NOT_FOUND.getValue();
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public String deletedICDVersion(long icdId) {
        ICDVersion icdVersion = iCDVersionRepository.findOne(icdId);
        if (HISCoreUtil.isValidObject(icdVersion)) {
            icdVersion.setDeleted(true);
            iCDVersionRepository.save(icdVersion);
            return ResponseEnum.SUCCESS.getValue();
        } else {
            return ResponseEnum.NOT_FOUND.getValue();
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public String deletedAssociateICDCV(long icdId) {
        if (icdId > 0) {
            icdCodeVersionRepository.delete(icdId);
            return ResponseEnum.SUCCESS.getValue();
        } else {
            return ResponseEnum.NOT_FOUND.getValue();
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public void updateICD(ICDCodeCreateRequest createRequest) {
        ICDCode icdCode = this.iCDCodeRepository.findOne(createRequest.getId());
        if (HISCoreUtil.isValidObject(icdCode)) {
            icdCode.setCode(createRequest.getCode());
            icdCode.setTitle(createRequest.getTitle());
            icdCode.setDescription(createRequest.getDescription());
            icdCode.setStatus(createRequest.isStatus());
            icdCode.setDeleted(false);
            icdCode.setUpdatedOn(System.currentTimeMillis());
        }
        this.iCDCodeRepository.save(icdCode);
    }

    @Transactional(rollbackOn = Throwable.class)
    public ICDVersion saveICDVersion(ICDVersionWrapper createRequest) {
        ICDVersion icdVersion = new ICDVersion(createRequest);
        return iCDVersionRepository.save(icdVersion);
    }

    public boolean isICDVersionNameAlreadyExistAgainstICDVersionNameId(String name, long id) {
        ICDVersion icdVersion = iCDVersionRepository.findByNameAndDeletedFalseAndIdNot(name, id);
        if (HISCoreUtil.isValidObject(icdVersion)) {
            return true;
        }
        return false;
    }

    @Transactional(rollbackOn = Throwable.class)
    public ICDVersion updateICDVersion(ICDVersionWrapper request) {
        ICDVersion icdVersion = iCDVersionRepository.findOne(request.getId());
        if (HISCoreUtil.isValidObject(icdVersion)) {
            icdVersion.setUpdatedOn(System.currentTimeMillis());
            icdVersion.setName(request.getName());
            icdVersion.setTitle(request.getTitle());
            icdVersion.setStatus(request.isStatus());
        }
        return icdVersion;
    }

    public List<ICDCodeVersionWrapper> codeVersions(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<ICDCodeVersionWrapper> icdcvs = new ArrayList<>();
        List<ICDCodeVersion> paginatedICDs = this.icdCodeVersionRepository.findAllByOrderByVersion_name(pageable);
        if (paginatedICDs != null) {
            icdcvs = APIUtil.buildICDCodeVersionWrapper(paginatedICDs);
        }
        return icdcvs;
    }

    public int countCodeVersions() {
        return icdCodeVersionRepository.findAllByOrderByVersion_name().size();
    }

    @Transactional(rollbackOn = Throwable.class)
    public List<ICDCodeVersion> saveAssociateICDCVs(ICDCodeVersionWrapper createRequest) {
        ICDCodeVersion associateICDCVs = null;
        List<ICDCodeVersion> icdCodeVersions = new ArrayList<>();

        ICDVersion icdVersion = this.iCDVersionRepository.findOne(Long.parseLong(createRequest.getSelectedICDVersionId()));

        this.icdCodeVersionRepository.deleteAllByVersion_id(icdVersion.getId());
        this.icdCodeVersionRepository.flush();

        for (ICDCodeWrapper codeWrapper : createRequest.getiCDCodes()) {
            if (codeWrapper.isCheckedCode()) {
                associateICDCVs = new ICDCodeVersion();
                System.out.println(codeWrapper.isCheckedCode());
                associateICDCVs.setVersion(icdVersion);
                associateICDCVs.setIcd(this.iCDCodeRepository.findOne(codeWrapper.getId()));

                icdCodeVersions.add(associateICDCVs);
            }
        }

        return icdCodeVersionRepository.save(icdCodeVersions);
    }

    @Transactional(rollbackOn = Throwable.class)
    public List<ICDCodeVersion> updateCVs(ICDCodeVersionWrapper createRequest) {
        ICDCodeVersion associateICDCVs = null;
        List<ICDCodeVersion> icdCodeVersions = new ArrayList<>();

        ICDVersion icdVersion = this.iCDVersionRepository.findOne(Long.parseLong(createRequest.getSelectedICDVersionId()));

        this.icdCodeVersionRepository.deleteAllByVersion_id(icdVersion.getId());
        this.icdCodeVersionRepository.flush();

        for (ICDCodeWrapper codeWrapper : createRequest.getSelectedICDCodes()) {
            associateICDCVs = new ICDCodeVersion();
            associateICDCVs.setVersion(icdVersion);
            associateICDCVs.setIcd(this.iCDCodeRepository.findOne(codeWrapper.getId()));

            icdCodeVersions.add(associateICDCVs);
        }

        return icdCodeVersionRepository.save(icdCodeVersions);
    }

    public List<ICDCodeWrapper> getAssociatedICDCVByVId(long iCDCVsId) {
        if (iCDCVsId > 0) {
            List<ICDCodeVersion> dbList = icdCodeVersionRepository.findAllByVersion_idAndVersion_deletedFalseAndIcd_DeletedFalse(iCDCVsId);
            if (dbList != null && dbList.size() > 0) {
                ///here we only want associated by version id
                return APIUtil.buildAssociatedICDCodesWrapper(dbList);
            }
        }
        return null;
    }
}
