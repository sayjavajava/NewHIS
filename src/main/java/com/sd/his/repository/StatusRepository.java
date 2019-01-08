package com.sd.his.repository;


import com.sd.his.model.Cashier;
import com.sd.his.model.Status;
import com.sd.his.model.User;
import com.sd.his.wrapper.StatusWrapper;
import com.sd.his.wrapper.response.StaffResponseWrapper;
import com.sd.his.wrapper.response.StaffWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
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
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findBy(Pageable pageable);
    int countByStatusTrue();
    Status findByName(String name);
    Status findByNameAndIdNot(String name ,Long id);//Long id, String name, String abbreviation, boolean active, String colorHash
    @Query("SELECT new com.sd.his.wrapper.StatusWrapper(st.id,st.name, st.abbreviation,st.status,st.hashColor,st.systemStatus) FROM Status st WHERE st.status = TRUE and st.name LIKE CONCAT('%',:name,'%')")
    List<StatusWrapper> findByNameAndStatusTrue(@Param("name")String name , Pageable pageable);
}