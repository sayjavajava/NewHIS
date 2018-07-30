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



import com.sd.his.enums.OrganizationFormTypeEnum;
import com.sd.his.model.Branch;
import com.sd.his.model.Organization;
import com.sd.his.model.User;
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

    public OrganizationResponseWrapper getOrganizationByIdWithResponse(long id) {
        return organizationRepository.findById(id);
    }
        public Organization getByID(long id) {
            return organizationRepository.getOne(id);
        }

        public OrganizationRequestWrapper updateOrganization(OrganizationRequestWrapper organizationRequestWrapper, Organization organization) {

        if(organizationRequestWrapper.getFormName().equalsIgnoreCase(OrganizationFormTypeEnum.PROFILE.name())){

            organization.setWebsite(organizationRequestWrapper.getWebsite());
            organization.setOfficePhone(organizationRequestWrapper.getOfficePhone());
            organization.setFax(organizationRequestWrapper.getFax());
            organization.setAddress(organizationRequestWrapper.getAddress());
            organization.setSpecialty(organizationRequestWrapper.getSpecialty());
            organization.setEmail(organizationRequestWrapper.getCompanyEmail());
            //  organizationRepository.save(organization);
            return organizationRequestWrapper;
        }

/*
            String defaultBranch;     //geenral form
            String durationOfExam;
            String durationFollowUp;
            String prefixSerialPatient;
            String prefixSerialUser;
            String prefixSerialDepartment;
            String prefixSerialAppointment;
            String prefixSerialInvoices;
*/

            if(organizationRequestWrapper.getFormName().equalsIgnoreCase(OrganizationFormTypeEnum.GENERAL.name())){

                organization.setDurationFollowUp(organizationRequestWrapper.getDurationFollowUp());
                organization.setDurationOFExam(organizationRequestWrapper.getDurationOfExam());
                //  organizationRepository.save(organization);
                return organizationRequestWrapper;
            }

            if(organizationRequestWrapper.getFormName().equalsIgnoreCase(OrganizationFormTypeEnum.ACCOUNT.name())){

               User user = userRepository.getOne(1L);
             //   user.setFi(organizationRequestWrapper.get);
                //  organizationRepository.save(organization);
                return organizationRequestWrapper;
            }

            return null;
    }
    public Organization findOrgnazatinoByCompanyName(String companyName) {
        return organizationRepository.findByCompanyName(companyName);
    }
}