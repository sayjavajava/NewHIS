package com.sd.his.repository;/*
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

import com.sd.his.model.Organization;


import com.sd.his.wrapper.response.OrganizationResponseWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {



    @Query("SELECT new com.sd.his.wrapper.response.OrganizationResponseWrapper(o.id, o.companyName,o.officePhone,o.website,o.email,o.fax,o.address) FROM Organization o")
    List<OrganizationResponseWrapper> findAllByName(Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.response.OrganizationResponseWrapper(o.id, o.companyName,o.officePhone,o.website,o.email,o.fax,o.address) FROM Organization o  WHERE o.id=:id")
    OrganizationResponseWrapper findById(@Param("id") Long id);

    Organization findByCompanyName(String companyName);

    @Query("SELECT o FROM Organization o  WHERE o.id=:id")
    Organization findByIdContains(@Param("id") Long id);

}
