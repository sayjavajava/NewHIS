package com.sd.his.service;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.*;
import com.sd.his.repositiories.*;
import com.sd.his.request.MedicalServiceRequest;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.MedicalServiceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 15-May-2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer    Date       Version  Operation  Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.service
 * @FileName  : MedicalServicesService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class MedicalServicesService {

    @Autowired
    MedicalServicesRepository medicalServicesRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    ClinicalDepartmentRepository clinicalDepartmentRepository;
    @Autowired
    TaxRepository taxRepository;
    @Autowired
    BranchMedicalServiceRepository branchMedicalServiceRepository;
    @Autowired
    ClinicalDepartmentMedicalServiceRepository clinicalDepartmentMedicalServiceRepository;


    public List<MedicalServiceWrapper> findAllPaginatedMedicalServices(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return medicalServicesRepository.findAllPaginated(pageable);
    }

    public int countAllMedicalServices() {
        return medicalServicesRepository.findAllMedicalServiceWrappers().size();
    }

    public MedicalService findByTitleAndStatusTrueAndDeletedFalse(String title) {
        return medicalServicesRepository.findByTitleAndStatusTrueAndDeletedFalse(title);
    }

    public MedicalServiceWrapper findByTitleAndBranchAndDptDeletedFalse(String title, long branchId, long dptId) {
        return medicalServicesRepository.findOneByTitleAndDptAndBranch(title, branchId, dptId);
    }

    @Transactional(rollbackOn = Throwable.class)
    public MedicalService saveMedicalService(MedicalServiceRequest createRequest) {
        Branch branch = branchRepository.findOne(createRequest.getBranchId());
        ClinicalDepartment dpt = clinicalDepartmentRepository.findOne(createRequest.getDptId());
        Tax tax = taxRepository.findOne(createRequest.getTaxId());

        if (HISCoreUtil.isValidObject(branch) && HISCoreUtil.isValidObject(dpt) && HISCoreUtil.isValidObject(tax)) {
            MedicalService medicalService = new MedicalService(createRequest, tax);
            medicalService = medicalServicesRepository.save(medicalService);

            BranchMedicalService branchMedicalService = new BranchMedicalService(branch, medicalService);
            branchMedicalServiceRepository.save(branchMedicalService);

            ClinicalDepartmentMedicalService clinicalDepartmentMedicalService = new ClinicalDepartmentMedicalService(dpt, medicalService);
            clinicalDepartmentMedicalServiceRepository.save(clinicalDepartmentMedicalService);

            return medicalService;
        } else {
            return null;
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    public String deleteMedicalService(long msId, long dptId, long branchId) {
        MedicalService medicalService = medicalServicesRepository.findOne(msId);
        if (HISCoreUtil.isValidObject(medicalService)) {
            medicalService.setDeleted(true);
            medicalServicesRepository.save(medicalService);

            List<BranchMedicalService> branchMedicalServices = branchMedicalServiceRepository.findAllByBranch_IdAndMedicalService_Id(branchId, msId);
            branchMedicalServiceRepository.delete(branchMedicalServices);

            List<ClinicalDepartmentMedicalService> clinicalDepartmentMedicalServices =
                    clinicalDepartmentMedicalServiceRepository.findAllByClinicalDpt_IdAndMedicalService_Id(dptId, msId);
            clinicalDepartmentMedicalServiceRepository.delete(clinicalDepartmentMedicalServices);
            return ResponseEnum.SUCCESS.getValue();
        } else {
            return ResponseEnum.NOT_FOUND.getValue();
        }
    }
}