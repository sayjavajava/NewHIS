package com.sd.his.service;/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.*
 * @FileName  : UserAuthAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */



import com.sd.his.model.Branch;
import com.sd.his.model.Organization;
import com.sd.his.repository.BranchRepository;
import com.sd.his.repository.OrganizationRepository;
import com.sd.his.repository.TimezoneRepository;
import com.sd.his.repository.UserRepository;
import com.sd.his.wrapper.TimezoneWrapper;
import com.sd.his.wrapper.request.OrganizationRequestWrapper;
import com.sd.his.wrapper.response.OrganizationResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TimezoneRepository timezoneRepository;
  /*  @Autowired
    private BranchRepository branchRepository;*/
   /* @Autowired
    private BranchUserRepository branchUserRepository;*/


    public List<TimezoneWrapper> getAllTimeZone() {
        return timezoneRepository.findAllByCountryCode();
    }

/*    public List<OrganizationResponseWrapper> getAllActiveOrganizations() {
        List<OrganizationResponseWrapper> organizationResponseWrappers = new ArrayList<>();
        for (Organization organization : organizationRepository.findAllByActiveTrueAndDeletedFalse()) {
            OrganizationResponseWrapper organizationResponseWrapper = new OrganizationResponseWrapper(organization);
            organizationResponseWrappers.add(organizationResponseWrapper);
        }
        return organizationResponseWrappers;
    }*/

    public List<OrganizationResponseWrapper> findAllPaginatedOrganization(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return organizationRepository.findAllByName(pageable);
    }

    public int totalOrganizations() {
        return (int) organizationRepository.count();
    }

   /* public OrganizationResponseWrapper getOrganizationByIdWithResponse(long id) {
        Organization organization = organizationRepository.findOne(id);
        OrganizationSpecialty organizationSpecialty = organizationSpecialtyRepository.findByOrganization(organization);
        Speciality speciality = organizationSpecialty.getSpeciality();
        OrganizationResponseWrapper organizationResponseWrapper = new OrganizationResponseWrapper(organization);
        organizationResponseWrapper.setSpeciality(speciality);
        return organizationResponseWrapper;
    }

    public Organization getByID(long id) {
        return organizationRepository.getOne(id);
    }

    public OrganizationRequestWrapper updateOrganization(OrganizationRequestWrapper organizationRequestWrapper, Organization organization) {
        organization.setCompanyName(organizationRequestWrapper.getCompanyName());
        organization.setWebsite(organizationRequestWrapper.getWebsite());
        organization.setHomePhone(organizationRequestWrapper.getHomePhone());
        organization.setCellPhone(organizationRequestWrapper.getCellPhone());
        organization.setOfficePhone(organizationRequestWrapper.getOfficePhone());
        organization.setAptSerialStart(organizationRequestWrapper.getAppointmentSerial());
        organization.setDefaultBranch(organizationRequestWrapper.getDefaultBranch());
        organization.setDurationOFExam(organizationRequestWrapper.getDurationOfExam());
        organization.setDurationFollowUp(organizationRequestWrapper.getFollowUpExam());
        organization.setTimezone(organizationRequestWrapper.getTimeZone());

        OrganizationSpecialty organizationSpecialty = organizationSpecialtyRepository.findByOrganization(organization);
        Speciality speciality = organizationSpecialty.getSpeciality();
        speciality.setName(organizationRequestWrapper.getSpecialty());
        speciality.setDescription(organizationRequestWrapper.getSpecialty());
        specialtyRepository.save(speciality);
        organizationRepository.save(organization);
        return organizationRequestWrapper;
    }
*/
    public Organization findOrgnazatinoByCompanyName(String companyName) {
        return organizationRepository.findByCompanyName(companyName);
    }
}