package com.sd.his.service;

import com.sd.his.model.Branch;
import com.sd.his.repositiories.BranchRepository;
import com.sd.his.wrapper.BranchWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 14-May-18
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
 * @Package   : com.sd.his.service
 * @FileName  : $BranchService
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Service
public class BranchService {

    @Autowired
    BranchRepository branchRepository;

    public List<BranchWrapper> getAllActiveBranches() {
        List<Branch> dbBranch = branchRepository.findAllByActiveTrueAndDeletedFalse();
        List<BranchWrapper> branchWrappers = new ArrayList<>();

        for (Branch branch : dbBranch) {
            BranchWrapper branchWrapper = new BranchWrapper(branch);
            branchWrappers.add(branchWrapper);
        }
        return branchWrappers;
    }
}
