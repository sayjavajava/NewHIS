package com.sd.his.service;

import com.sd.his.model.Branch;
import com.sd.his.model.BranchUser;
import com.sd.his.model.Room;
import com.sd.his.model.User;
import com.sd.his.repositiories.BranchRepository;
import com.sd.his.repositiories.BranchUserRepository;
import com.sd.his.repositiories.RoomRepository;
import com.sd.his.repositiories.UserRepository;
import com.sd.his.request.BranchRequestWrapper;
import com.sd.his.response.BranchResponseWrapper;
import com.sd.his.response.UserResponseWrapper;
import com.sd.his.wrapper.BranchWrapper;
import com.sd.his.wrapper.ExamRooms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
@Service
public class BranchService {

    private final Logger logger = LoggerFactory.getLogger(BranchService.class);

    private BranchRepository branchRepository;
    private BranchUserRepository branchUserRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;

    public BranchService(BranchRepository branchRepository, BranchUserRepository branchUserRepository,
                         UserRepository userRepository, RoomRepository roomRepository) {
        this.branchRepository = branchRepository;
        this.branchUserRepository = branchUserRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public Branch saveBranch(BranchRequestWrapper branchRequestWrapper) {

        Branch branch = new Branch();

        branch.setName(branchRequestWrapper.getBranchName());
        branch.setCreatedOn(System.currentTimeMillis());
        branch.setUpdatedOn(System.currentTimeMillis());
        branch.setActive(true);
        branch.setDeleted(false);
        branch.setNoOfRooms(branchRequestWrapper.getNoOfExamRooms());
        branch.setAddress(branchRequestWrapper.getAddress());
        branch.setCity(branchRequestWrapper.getCity());
        branch.setState(branchRequestWrapper.getState());
        branch.setZipCode(branchRequestWrapper.getZipCode());
        branch.setOfficePhone(branchRequestWrapper.getOfficePhone());
        branch.setOfficeStartTime(branchRequestWrapper.getOfficeHoursStart());
        branch.setOfficeEndTime(branchRequestWrapper.getOfficeHoursEnd());
        branch.setCountry(branchRequestWrapper.getCountry());
        branch.setBillingName(branchRequestWrapper.getBillingName());
        branch.setBillingTaxId(branchRequestWrapper.getBillingTaxID());
        branch.setBillingBranchName(branchRequestWrapper.getBillingBranch());
        branch.setAllowOnlineSchedule(branchRequestWrapper.isAllowOnlineSchedulingInBranch());
        branch.setShowBranchInfoOnline(branchRequestWrapper.isShowBranchOnline());
        branch.setFax(branchRequestWrapper.getFax());
        branch.setFormattedAddress(branchRequestWrapper.getFormattedAddress());

        int userId = Integer.parseInt(branchRequestWrapper.getPrimaryDoctor());
        User user = userRepository.findById(userId);
        List<ExamRooms> exRooms = new ArrayList<>(Arrays.asList(branchRequestWrapper.getExamRooms()));

        BranchUser branchUser = branchUserRepository.findByUser(user);

        branchRepository.save(branch);

        for (ExamRooms ex : exRooms) {
            Room room = new Room();

            room.setAllowOnlineScheduling(ex.isAllowOnlineScheduling());
            room.setExamName(ex.getExamName());
            room.setActive(true);
            room.setDeleted(false);
            room.setCreatedOn(System.currentTimeMillis());
            room.setUpdatedOn(System.currentTimeMillis());
            room.setBranch(branch);
            roomRepository.save(room);
        }
        if (branchUser != null) {
            branchUser.setPrimaryDr(true);
            branchUser.setPrimaryBranch(true);
            branchUser.setBillingBranch(true);
            branchUser.setBranch(branch);
            branchUserRepository.save(branchUser);
        }

        return branch;
    }

    public Branch findByBranchName(String name) {
        return branchRepository.findByName(name);
    }

    public List<BranchResponseWrapper> findAllBranches(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return branchRepository.findAllByNameAndActiveTrueAndDeletedFalse(pageable);
    }

    public int totalBranches() {
        return ((int) branchRepository.count());
    }

    public Branch findById(long id) {
        return branchRepository.findByIdAndDeletedFalse(id);
    }

    public void deleteBranch(Branch branch) {
        branch.setDeleted(true);
        branchRepository.save(branch);
    }

    public BranchResponseWrapper findByID(long id) {
        Branch branch = branchRepository.findByIdAndDeletedFalse(id);
        BranchResponseWrapper branchResponseWrapper = new BranchResponseWrapper(branch);

        List<Room> finalRooms = branch.getRooms().stream()
                .filter(x -> x.getExamName() != null)
                .map(x -> new Room(x.getExamName(), x.isAllowOnlineScheduling()))
                .collect(Collectors.toList());

        branchResponseWrapper.setExamRooms(finalRooms);

        List<UserResponseWrapper> userWrapper = new ArrayList<>();
        List<User> userRoles = userRepository.findAllByRoles_role_name("doctor");
        for (User user : userRoles) {
            UserResponseWrapper userResponseWrapper = new UserResponseWrapper(user);
            userWrapper.add(userResponseWrapper);
        }
        branchResponseWrapper.setUser(userWrapper);
        return branchResponseWrapper;
    }

    public Branch updateBranch(BranchRequestWrapper branchRequestWrapper, Branch branch) {

        branch.setName(branchRequestWrapper.getBranchName());
        branch.setUpdatedOn(System.currentTimeMillis());
        branch.setActive(true);
        branch.setDeleted(false);
        branch.setNoOfRooms(branchRequestWrapper.getNoOfExamRooms());
        branch.setAddress(branchRequestWrapper.getAddress());
        branch.setCity(branchRequestWrapper.getCity());
        branch.setState(branchRequestWrapper.getState());
        branch.setZipCode(branchRequestWrapper.getZipCode());
        branch.setOfficePhone(branchRequestWrapper.getOfficePhone());
        branch.setOfficeStartTime(branchRequestWrapper.getOfficeHoursStart());
        branch.setOfficeEndTime(branchRequestWrapper.getOfficeHoursEnd());
        branch.setCountry(branchRequestWrapper.getCountry());
        branch.setBillingName(branchRequestWrapper.getBillingName());
        branch.setBillingTaxId(branchRequestWrapper.getBillingTaxID());
        branch.setBillingBranchName(branchRequestWrapper.getBillingName());
        branch.setAllowOnlineSchedule(branchRequestWrapper.isAllowOnlineSchedulingInBranch());
        branch.setShowBranchInfoOnline(branchRequestWrapper.isShowBranchOnline());
        branch.setFax(branchRequestWrapper.getFax());
        branch.setFormattedAddress(branchRequestWrapper.getFormattedAddress());

        List<ExamRooms> exRooms = new ArrayList<>(Arrays.asList(branchRequestWrapper.getExamRooms()));
        for (ExamRooms ex : exRooms) {
            Room room = new Room();
            room.setAllowOnlineScheduling(ex.isAllowOnlineScheduling());
            room.setExamName(ex.getExamName());
            room.setUpdatedOn(System.currentTimeMillis());
            room.setBranch(branch);
            roomRepository.save(room);
        }

        branchRepository.save(branch);
        return branch;
    }

    public List<String> findAllBranchName() {
        List<Branch> allBranches = branchRepository.findAll();
        List<String> branchNames = allBranches.stream()
                .filter(x -> x.getName() != null)
                .map(x -> x.getName())
                .collect(Collectors.toList());

        return branchNames;
    }

    public List<BranchResponseWrapper> searchByBranchNameAndDepartment(String name,String department, int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        logger.info("branch name" + department);

        List<Branch> allBranches = branchRepository.findByNameIgnoreCaseContainingAndActiveTrueAndDeletedFalseOrClinicalDepartments_clinicalDpt_nameIgnoreCaseContaining(name,department, pageable);

        List<BranchResponseWrapper> branchResponseWrapper = new ArrayList<>();
        //(long id, String name, String country, String city, int rooms)
        for(Branch branch:allBranches){

            BranchResponseWrapper brw = new BranchResponseWrapper(branch.getId(),branch.getName(),branch.getCountry(),branch.getCity(),branch.getNoOfRooms());
            branchResponseWrapper.add(brw);
        }
        return branchResponseWrapper;
    }

    public List<BranchResponseWrapper> getAllActiveBranches() {
       return branchRepository.findAllByActiveTrueAndDeletedFalse();
    }
}