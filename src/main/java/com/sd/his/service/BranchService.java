package com.sd.his.service;


import com.sd.his.model.*;
import com.sd.his.repository.BranchDoctorRepository;
import com.sd.his.repository.BranchRepository;
import com.sd.his.repository.DoctorRepository;
import com.sd.his.repository.RoomRepository;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.ExamRooms;
import com.sd.his.wrapper.request.BranchRequestWrapper;
import com.sd.his.wrapper.response.BranchResponseWrapper;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
@Service
public class BranchService {

    private final Logger logger = LoggerFactory.getLogger(BranchService.class);

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private BranchDoctorRepository branchDoctorRepository;
    @Autowired
    private RoomRepository roomRepository;
    /*private BranchUserRepository branchUserRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;
*/
   /* public BranchService(BranchRepository branchRepository, BranchUserRepository branchUserRepository,
                         UserRepository userRepository, RoomRepository roomRepository) {
        this.branchRepository = branchRepository;
        this.branchUserRepository = branchUserRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }
*/
    public Branch saveBranch(BranchRequestWrapper branchRequestWrapper) {
        Branch branch = new Branch();
        branch.setName(branchRequestWrapper.getBranchName());
        branch.setActive(true);
        branch.setNoOfRooms(branchRequestWrapper.getNoOfExamRooms());
        branch.setAddress(branchRequestWrapper.getAddress());
        branch.setCity(branchRequestWrapper.getCity());
        branch.setSystemBranch(false);
        branch.setState(branchRequestWrapper.getState());
        branch.setZipCode(branchRequestWrapper.getZipCode());
        branch.setOfficePhone(branchRequestWrapper.getOfficePhone());
        branch.setNoOfRooms(branchRequestWrapper.getNoOfExamRooms());
        branch.setOfficeStartTime(HISCoreUtil.convertToTime(branchRequestWrapper.getOfficeHoursStart()));
        branch.setOfficeEndTime(HISCoreUtil.convertToTime(branchRequestWrapper.getOfficeHoursEnd()));
        branch.setCountry(branchRequestWrapper.getCountry());
        branch.setBillingName(branchRequestWrapper.getBillingName());
        branch.setBillingTaxId(branchRequestWrapper.getBillingTaxID());
        branch.setBillingBranchName(branchRequestWrapper.getBillingBranch());
        branch.setAllowOnlineSchedule(branchRequestWrapper.isAllowOnlineSchedulingInBranch());
        branch.setShowBranchInfoOnline(branchRequestWrapper.isShowBranchOnline());
        branch.setFax(branchRequestWrapper.getFax());
        branch.setAddress(branchRequestWrapper.getAddress());
        branchRepository.save(branch);

        List<ExamRooms> exRooms = new ArrayList<>(Arrays.asList(branchRequestWrapper.getExamRooms()));
        for (ExamRooms ex : exRooms) {
            Room room = new Room();
            room.setAllowOnlineScheduling(ex.isAllowOnlineScheduling());
            room.setRoomName(ex.getRoomName());
            room.setBranch(branch);
            room.setActive(true);
            roomRepository.save(room);
        }


        Doctor doctor = doctorRepository.getOne(branchRequestWrapper.getPrimaryDoctor());
        BranchDoctor branchDoctor =new BranchDoctor();
        branchDoctor.setBranch(branch);
        branchDoctor.setDoctor(doctor);
        branchDoctor.setPrimaryBranch(true);
        branchDoctorRepository.save(branchDoctor);

        return branch;

    }
    public Branch findByBranchName(String name) {
        return branchRepository.findByName(name);
    }

    public List<BranchResponseWrapper> findAllBranches(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return branchRepository.findAllByActive(pageable);
    }

    public int totalBranches() {
        return ((int) branchRepository.count());
    }

    public BranchResponseWrapper getById(Long id) {
        return branchRepository.findAllById(id);
        }
        public  Branch findBranchById(Long id){
        return  branchRepository.findOne(id);
        }

  public void deleteBranch(Branch branch) {
      //  branch.setDeleted(true);
      branchDoctorRepository.delete(branchDoctorRepository.findByBranch(branch));
      branchRepository.delete(branch);

    }

    /*   public BranchResponseWrapper findByID(long id) {
         Branch branch = branchRepository.findByIdAndDeletedFalse(id);
         BranchResponseWrapper branchResponseWrapper = new BranchResponseWrapper(branch);
         List<Room> roomListData = new ArrayList<>();
         for (Room room : branch.getRooms()) {
             Room room1 = new Room(room.getExamName(), room.isAllowOnlineScheduling());
             roomListData.add(room1);
         }
         branchResponseWrapper.setExamRooms(roomListData);
         BranchUser branchUser = branchUserRepository.findByBranch(branch);
         branchResponseWrapper.setUser(branchUser.getUser());

         return branchResponseWrapper;
     }
 */
    public Branch updateBranch(BranchRequestWrapper branchRequestWrapper, Branch branch) {

        branch.setName(branchRequestWrapper.getBranchName());
        branch.setActive(true);
        branch.setNoOfRooms(branchRequestWrapper.getNoOfExamRooms());
        branch.setAddress(branchRequestWrapper.getAddress());
        branch.setCity(branchRequestWrapper.getCity());
        branch.setSystemBranch(false);
        branch.setState(branchRequestWrapper.getState());
        branch.setZipCode(branchRequestWrapper.getZipCode());
        branch.setOfficePhone(branchRequestWrapper.getOfficePhone());
        branch.setNoOfRooms(branchRequestWrapper.getNoOfExamRooms());
        branch.setOfficeStartTime(HISCoreUtil.convertToTime(branchRequestWrapper.getOfficeHoursStart()));
        branch.setOfficeEndTime(HISCoreUtil.convertToTime(branchRequestWrapper.getOfficeHoursEnd()));
        branch.setCountry(branchRequestWrapper.getCountry());
        branch.setBillingName(branchRequestWrapper.getBillingName());
        branch.setBillingTaxId(branchRequestWrapper.getBillingTaxID());
        branch.setBillingBranchName(branchRequestWrapper.getBillingBranch());
        branch.setAllowOnlineSchedule(branchRequestWrapper.isAllowOnlineSchedulingInBranch());
        branch.setShowBranchInfoOnline(branchRequestWrapper.isShowBranchOnline());
        branch.setFax(branchRequestWrapper.getFax());
        branch.setAddress(branchRequestWrapper.getAddress());
        branchRepository.save(branch);

        List<ExamRooms> exRooms = new ArrayList<>(Arrays.asList(branchRequestWrapper.getExamRooms()));
        for (ExamRooms ex : exRooms) {
            Room room = new Room();
            room.setAllowOnlineScheduling(ex.isAllowOnlineScheduling());
            room.setRoomName(ex.getRoomName());
            room.setBranch(branch);
            room.setActive(true);
            roomRepository.save(room);
        }

        Doctor doctor = doctorRepository.getOne(branchRequestWrapper.getPrimaryDoctor());
        BranchDoctor branchDoctor =branchDoctorRepository.findByBranch(branch);
        branchDoctor.setBranch(branch);
        branchDoctor.setDoctor(doctor);
        branchDoctor.setPrimaryBranch(true);
        branchDoctorRepository.save(branchDoctor);

        return branch;

    }

/*
    public List<String> findAllBranchName() {
        List<Branch> allBranches = branchRepository.findAllByActiveTrueAndDeletedFalse();
        List<String> branchNames = allBranches.stream()
                .filter(x -> x.getName() != null)
                .map(x -> x.getName())
                .collect(Collectors.toList());

        return branchNames;

    }

    public List<BranchResponseWrapper> searchByBranchNameAndDepartment(String name, String department, int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        logger.info("branch name" + department);

        List<Branch> allBranches = branchRepository.findByNameIgnoreCaseContainingAndActiveTrueAndDeletedFalseOrClinicalDepartments_clinicalDpt_nameIgnoreCaseContaining(name, department, pageable);

        List<BranchResponseWrapper> branchResponseWrapper = new ArrayList<>();
        for (Branch branch : allBranches) {

            BranchResponseWrapper brw = new BranchResponseWrapper(branch.getId(), branch.getName(), branch.getCountry(), branch.getCity(), branch.getNoOfRooms());
            branchResponseWrapper.add(brw);
        }
        return branchResponseWrapper;
    }

    public List<BranchResponseWrapper> getAllActiveBranches() {
        List<Branch> branch = branchRepository.findAllByActiveTrueAndDeletedFalse();
        //  return branchRepository.findAllByActiveTrueAndDeletedFalse();
        List<BranchResponseWrapper> list = new ArrayList<>();

        for (Branch branch1 : branch) {
            BranchResponseWrapper branchResponseWrapper = new BranchResponseWrapper(branch1);
            list.add(branchResponseWrapper);
        }
        return list;
    }
*/
    private String generateEmail(String domain, int length) {
        return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyz") + "@" + domain;
    }
}