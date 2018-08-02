package com.sd.his.repository;

import com.sd.his.model.Branch;
import com.sd.his.model.BranchCashier;
import com.sd.his.model.BranchNurse;
import com.sd.his.model.Nurse;
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
public interface BranchNurseRepository extends JpaRepository<BranchNurse, Long> {


     BranchNurse findByBranch(Branch branch);
     BranchNurse findByNurse(Nurse nurse);
     void deleteAllByNurse(Nurse nurse);
     BranchNurse findByNurseAndPrimaryBranchTrue(Nurse nurse);


}

