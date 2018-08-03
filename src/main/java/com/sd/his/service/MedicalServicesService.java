package com.sd.his.service;

import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.DepartmentWrapper;
import com.sd.his.wrapper.MedicalServiceWrapper;
import com.sd.his.wrapper.response.BranchResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/*
 * @author    : Qari Muhammad Jamal
 * @Date      : 30-july-2018
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
    private  MedicalServiceRepository medicalServiceRepository;
    @Autowired
    private  BranchRepository branchRepository;
    @Autowired
    private  DepartmentRepository departmentRepository;
    @Autowired
    private  TaxRepository taxRepository;
    @Autowired
    private  BranchMedicalServiceRepository branchMedicalServiceRepository;
    @Autowired
    private DepartmentMedicalServiceRepository departmentMedicalServiceRepository;
    @Autowired
    private DepartmentService departmentService;

    public List<MedicalServiceWrapper> findAllPaginatedMedicalServices(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<MedicalService> mSList = medicalServiceRepository.findAllByCreatedOnNotNull(pageable);
        List<MedicalServiceWrapper> mSWList = null;
        if (mSList != null) {
            mSWList = new ArrayList<>();
            for (MedicalService ms : mSList) {
                MedicalServiceWrapper mSW = new MedicalServiceWrapper(ms);

                if (ms.getDepartmentMedicalServices() != null) {
                    mSW.setCheckedDepartments(new ArrayList<>());
                    for (DepartmentMedicalService dMS : ms.getDepartmentMedicalServices()) {
                        if (dMS.getDepartment() != null) {
                            mSW.getCheckedDepartments().add(new DepartmentWrapper(dMS.getDepartment()));
                        }
                    }
                }
                if (ms.getBranchMedicalServices() != null) {
                    mSW.setCheckedBranches(new ArrayList<>());
                    for (BranchMedicalService branchMedicalService : ms.getBranchMedicalServices()) {
                        if (branchMedicalService.getBranch() != null) {
                            mSW.getCheckedBranches().add(new BranchResponseWrapper(branchMedicalService.getBranch()));
                        }
                    }
                }
                mSWList.add(mSW);
            }
        }
        return mSWList;
    }

    public List<MedicalServiceWrapper> findAllMedicalServices() {

        return null/*medicalServiceRepository.findAllMedicalServiceWrappers()*/;
    }

    public int countAllMedicalServices() {
        return medicalServiceRepository.findAll().size();
    }

    public MedicalService findByTitleAndDeletedFalse(String name) {
        return medicalServiceRepository.findByName(name);
    }

    public boolean findByNameAgainstId(long id, String name) {
        return medicalServiceRepository.findByIdNotAndName(id, name) == null ? false : true;
    }

    public MedicalServiceWrapper findByTitleAndBranchAndDptDeletedFalse(String title, long branchId, long dptId) {
        return null; //medicalServiceRepository.findOneByNameAndDptAndBranch(title, branchId, dptId);
    }

    @Transactional(rollbackOn = Throwable.class)
    public String saveMedicalService(MedicalServiceWrapper createRequest) {
        Tax tax = taxRepository.findOne(createRequest.getTax().getId());
        MedicalService medicalService = new MedicalService(createRequest);
        if (tax != null) {
            medicalService.setTax(tax);
        }
        medicalServiceRepository.save(medicalService);
        if (HISCoreUtil.isListValid(createRequest.getBranches())) {
            List<BranchMedicalService> list = new ArrayList<>();
            for (BranchResponseWrapper branchWrapper : createRequest.getBranches()) {
                if (branchWrapper.isCheckedBranch()) {
                    Branch branch = this.branchRepository.findOne(branchWrapper.getId());
                    BranchMedicalService branchMedicalService = new BranchMedicalService(branch, medicalService);
                    list.add(branchMedicalService);
                }
            }
            if (HISCoreUtil.isListValid(list)) {
                branchMedicalServiceRepository.save(list);
            }
        }
        if (HISCoreUtil.isListValid(createRequest.getDepartments())) {
            List<DepartmentMedicalService> list = new ArrayList<>();
            for (DepartmentWrapper departmentWrapper : createRequest.getDepartments()) {
                if (departmentWrapper.isCheckedDepartment()) {
                    Department department = this.departmentRepository.findOne(departmentWrapper.getId());
                    DepartmentMedicalService departmentMedicalService = new DepartmentMedicalService(department, medicalService);
                    list.add(departmentMedicalService);
                }
            }
            if (HISCoreUtil.isListValid(list)) {
                departmentMedicalServiceRepository.save(list);
            }
        }
        return "";
    }

    @Transactional(rollbackOn = Throwable.class)
    public String updateMedicalService(MedicalServiceWrapper createRequest) {
        Tax tax = taxRepository.findOne(createRequest.getTax().getId());
        MedicalService medicalService = this.medicalServiceRepository.findOne(createRequest.getId());
        new MedicalService(medicalService, createRequest);
        if (tax != null) {
            medicalService.setTax(tax);
        }else {
            medicalService.setTax(null);
        }
        medicalServiceRepository.save(medicalService);
        if (HISCoreUtil.isListValid(createRequest.getBranches())) {
            List<BranchMedicalService> list = new ArrayList<>();
            this.branchMedicalServiceRepository.deleteByMedicalService_id(medicalService.getId());
            for (BranchResponseWrapper branchWrapper : createRequest.getBranches()) {
                if (branchWrapper.isCheckedBranch()) {
                    Branch branch = this.branchRepository.findOne(branchWrapper.getId());
                    BranchMedicalService branchMedicalService = new BranchMedicalService(branch, medicalService);
                    list.add(branchMedicalService);
                }
            }
            if (HISCoreUtil.isListValid(list)) {
                branchMedicalServiceRepository.save(list);
            }
        }
        if (HISCoreUtil.isListValid(createRequest.getDepartments())) {
            List<DepartmentMedicalService> list = new ArrayList<>();
            this.departmentMedicalServiceRepository.deleteByMedicalService_id(medicalService.getId());
            for (DepartmentWrapper departmentWrapper : createRequest.getDepartments()) {
                if (departmentWrapper.isCheckedDepartment()) {
                    Department department = this.departmentRepository.findOne(departmentWrapper.getId());
                    DepartmentMedicalService departmentMedicalService = new DepartmentMedicalService(department, medicalService);
                    list.add(departmentMedicalService);
                }
            }
            if (HISCoreUtil.isListValid(list)) {
                departmentMedicalServiceRepository.save(list);
            }
        }
        return "";
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteMedicalService(long msId) {
        MedicalService deleteMedicalService = medicalServiceRepository.findOne(msId);
        if (HISCoreUtil.isValidObject(deleteMedicalService)) {
            medicalServiceRepository.delete(deleteMedicalService);
            return true;
        }
        return false;
    }

    @Transactional(rollbackOn = Throwable.class)
    public MedicalServiceWrapper findMedicalServiceById(Long msId) {
        MedicalService mS = medicalServiceRepository.findOne(msId);
        MedicalServiceWrapper mSW = null;
        if (mS != null) {
            mSW = new MedicalServiceWrapper(mS);
            if (mS.getDepartmentMedicalServices() != null) {
                mSW.setCheckedDepartments(new ArrayList<>());
                for (DepartmentMedicalService dMS : mS.getDepartmentMedicalServices()) {
                    if (dMS.getDepartment() != null) {
                        mSW.getCheckedDepartments().add(new DepartmentWrapper(dMS.getDepartment()));
                    }
                }
            }
            if (mS.getBranchMedicalServices() != null) {
                mSW.setCheckedBranches(new ArrayList<>());
                for (BranchMedicalService branchMedicalService : mS.getBranchMedicalServices()) {
                    if (branchMedicalService.getBranch() != null) {
                        mSW.getCheckedBranches().add(new BranchResponseWrapper(branchMedicalService.getBranch()));
                    }
                }
            }
        }
        return mSW;
    }

    public List<MedicalServiceWrapper> searchMedicalServicesByParam(
            Long serviceId,
            String serviceName,
            Long branchId,
            Long departmentId,
            Double serviceFee,
            int pageNo,
            int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize);

        return null; // this.medicalServiceRepository.findAllByParam(serviceId, serviceName, branchId, departmentId, serviceFee, pageable);
    }

    public int countSearchMedicalServicesByParam(
            Long serviceId,
            String serviceName,
            Long branchId,
            Long departmentId,
            Double serviceFee) {

        return 0;//this.medicalServiceRepository.countAllByParam(serviceId, serviceName, branchId, departmentId, serviceFee).size();
    }

}