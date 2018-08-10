package com.sd.his.repository;

import com.sd.his.model.Branch;
import com.sd.his.model.BranchCashier;
import com.sd.his.model.BranchReceptionist;
import com.sd.his.model.Receptionist;
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
public interface BranchReceptionistRepository extends JpaRepository<BranchReceptionist, Long> {


     BranchReceptionist findByBranch(Branch branch);
     BranchReceptionist findByReceptionist(Receptionist receptionist);
     void deleteAllByReceptionist(Receptionist receptionist);
     BranchReceptionist findByReceptionistAndPrimaryBranchTrue(Receptionist receptionist);
     @Query("select b from BranchReceptionist br inner join br.branch b where br.receptionist.id=:id")
     List<Branch> getReceptionistBranches(@Param("id") Long id);//id=>recetionist pk id

     void deleteAllByReceptionistAndPrimaryBranchFalse(Receptionist receptionist);
}

