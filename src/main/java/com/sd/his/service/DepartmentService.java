package com.sd.his.service;

import com.sd.his.model.Department;
import com.sd.his.repository.DepartmentRepository;
import com.sd.his.wrapper.DepartmentWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/*
 * @author    : Arif Heer
 * @Date      : 04/5/2018
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
 * @FileName  : DepartmentService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    public List<DepartmentWrapper> getDepartmentsActive() {
        List<Department> dpts = departmentRepository.findAll();
        List<DepartmentWrapper> dptsWrappers = new ArrayList<>();

        for (Department cd : dpts) {
            if (cd.getActive()) {
                DepartmentWrapper dpt = new DepartmentWrapper(cd);
                dptsWrappers.add(dpt);
            }
        }
        return dptsWrappers;
    }


    public List<DepartmentWrapper> getPaginatedAllDepartments(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<Department> dpts = departmentRepository.findAllByOrderByNameAsc(pageable);
        List<DepartmentWrapper> dptsWrappers = new ArrayList<>();

        for (Department cd : dpts) {
            //#TODO need to set branch id
            DepartmentWrapper dpt = new DepartmentWrapper(cd);
            dptsWrappers.add(dpt);
        }
        return dptsWrappers;
    }

    public int countPaginatedAllDepartments() {
        return departmentRepository.findAll().size();
    }

    public Department findDepartmentById(long dptId) {
        return departmentRepository.findOne(dptId);
    }

    public boolean isDepartmentByNameAndNotIdExist(String name, long dptId) {
        return departmentRepository.findByNameAndIdNot(name, dptId) == null ? true : false;
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteDepartment(long id) {
        departmentRepository.delete(id);
        return true;
    }

    public int countSearchedClinicalDepartments(String name) {
        return departmentRepository.findByName(name).size();
    }

    public List<DepartmentWrapper> getPageableSearchedDepartment(int offset, int limit, String name) {
        Pageable pageable = new PageRequest(offset, limit);
        return departmentRepository.findByName(pageable, name);
    }

    @Transactional(rollbackOn = Throwable.class)
    public Department saveDepartment(DepartmentWrapper createRequest) {
        Department dpt = new Department(createRequest);
        return departmentRepository.save(dpt);
    }

    @Transactional(rollbackOn = Throwable.class)
    public Department updateDepartment(DepartmentWrapper updateRequest) {
        Department dpt = departmentRepository.findOne(updateRequest.getId());
        dpt.setName(updateRequest.getName());
        dpt.setDescription(updateRequest.getDescription());
        dpt.setActive(updateRequest.isActive());

        return departmentRepository.save(dpt);
    }

    public DepartmentWrapper findDepartmentByName(String name) {
        return departmentRepository.findByNameAndActiveNotNull(name);
    }


    public boolean hasChild(long dptId) {
        Department department = this.departmentRepository.findOne(dptId);
        if (department != null && department.getDepartmentMedicalServices() != null && department.getDepartmentMedicalServices().size() > 0) {
            return true;
        }
        if (department != null && department.getBranchDepartments() != null && department.getBranchDepartments().size() > 0) {
            return true;
        }
        return false;
    }
}