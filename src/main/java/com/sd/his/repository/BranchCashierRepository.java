package com.sd.his.repository;

import com.sd.his.model.Branch;
import com.sd.his.model.BranchCashier;
import com.sd.his.model.BranchDoctor;
import com.sd.his.model.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface BranchCashierRepository extends JpaRepository<BranchCashier, Long> {


     BranchCashier findByBranch(Branch branch);
     BranchCashier findByCashier(Cashier cashier);
     void deleteAllByCashier(Cashier cashier);
     BranchCashier findByCashierAndPrimaryBranchTrue(Cashier cashier);

}

