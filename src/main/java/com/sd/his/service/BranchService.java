package com.sd.his.service;


import com.sd.his.enums.ModuleEnum;
import com.sd.his.model.Branch;
import com.sd.his.model.Doctor;
import com.sd.his.model.Organization;
import com.sd.his.model.Room;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.CityWrapper;
import com.sd.his.wrapper.CountryWrapper;
import com.sd.his.wrapper.ExamRooms;
import com.sd.his.wrapper.StateWrapper;
import com.sd.his.wrapper.request.BranchRequestWrapper;
import com.sd.his.wrapper.response.BranchResponseWrapper;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
    private NurseRepository nurseRepository;
    @Autowired
    private CashierRepository cashierRepository;
    @Autowired
    private ReceptionistRepository receptionistRepository;
    @Autowired
    private BranchDoctorRepository branchDoctorRepository;
    @Autowired
    private BranchNurseRepository branchNurseRepository;
    @Autowired
    private BranchCashierRepository branchCashierRepository;
    @Autowired
    private BranchReceptionistRepository branchReceptionistRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private  DepartmentRepository departmentRepository;
    @Autowired
    HISUtilService hisUtilService;
    @Autowired
    private  CountryRepository countryRepository;
    @Autowired
    private  StateRepository stateRepository;
    @Autowired
    private  CityRepository cityRepository;

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
        branch.setStatus(true);
        branch.setAddress(branchRequestWrapper.getAddress());
        branch.setSystemBranch(false);
        branch.setOfficePhone(branchRequestWrapper.getOfficePhone());
       // branch.setNoOfRooms(branchRequestWrapper.getNoOfExamRooms());
        branch.setOfficeStartTime(HISCoreUtil.convertToTime(branchRequestWrapper.getOfficeHoursStart()));
        branch.setOfficeEndTime(HISCoreUtil.convertToTime(branchRequestWrapper.getOfficeHoursEnd()));
        branch.setFax(branchRequestWrapper.getFax());
        branch.setBranchId(hisUtilService.generatePrefix(ModuleEnum.BRANCH));
        branch.setAddress(branchRequestWrapper.getAddress());
        branch.setCity(cityRepository.findOne(branchRequestWrapper.getCityId()));
        branch.setFlow(branchRequestWrapper.getFlow());
        Organization organization = organizationRepository.findOne(1L);
        branch.setOrganization(organization);
        branch.setBranchId(String.valueOf(branchRepository.getMaxBranchId() + 1));          //TODO: Unable to set it to auto
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
        /*User drUser= userRepository.findOne(branchRequestWrapper.getPrimaryDoctor());
        Doctor doctor = doctorRepository.findByUser(drUser);
        BranchDoctor branchDoctor =new BranchDoctor();
        branchDoctor.setBranch(branch);
        branchDoctor.setDoctor(doctor);
        branchDoctor.setPrimaryBranch(true);
        branchDoctorRepository.save(branchDoctor);
        */
        return branch;

    }
    public Branch findByBranchName(String name) {
        return branchRepository.findByName(name);
    }

    public List<BranchResponseWrapper> findAllBranches(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);;
        List<BranchResponseWrapper> list =  branchRepository.findAllByActive(pageable);
        List<BranchResponseWrapper> uniqueBranches = new ArrayList<>();
        int index=0;//to do for list
        StringBuilder sb = new StringBuilder();
        for(BranchResponseWrapper branchResponseWrapper :list){

          Optional<BranchResponseWrapper> exist =  uniqueBranches.stream().filter(x->x.getId() == branchResponseWrapper.getId()).findAny();
           if(exist.isPresent()){
            /*BranchResponseWrapper branchResponseWrapper1  = uniqueBranches.stream().filter(x->x.getFirstName() ==exist.get().getFirstName()).findAny().orElse(null);
              if(branchResponseWrapper1 !=null){
               String s = branchResponseWrapper1.getFirstName()+","+ sb.append(exist.get().getFirstName());
                branchResponseWrapper1.setFirstName(s);
              }*/
               continue;}
            uniqueBranches.add(branchResponseWrapper);
        }
        return uniqueBranches;
    }

    public List<Doctor> getDoctorsWithBranch(long branchId) {
        return branchDoctorRepository.getBranchesDoctors(branchId);
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

    public Branch deleteBranch(Branch branch) {
        //  branch.setDeleted(true);
        if(branch.getBranchCashiers().size() >0 || branch.getBranchDoctors().size() >0 || branch.getBranchDepartments().size() > 0
                || branch.getBranchNurses().size() > 0 || branch.getBranchReceptionists().size() > 0){
            return null;
        }
        branchRepository.delete(branch);
        return  branch;
    }
    /*public Boolean checkBranchExist(Branch branch){

        branch.getBranchCashiers().;
        if (userType.equalsIgnoreCase(UserTypeEnum.DOCTOR.name())) {
            Doctor doctor = doctorRepository.findByUser(user);
            BranchDoctor branchDoctor =branchDoctorRepository.findByDoctorAndPrimaryBranchTrue(doctor);
            if(HISCoreUtil.isValidObject(branchDoctor))
            return true;
          }
        if (userType.equalsIgnoreCase(UserTypeEnum.NURSE.name())) {
            Nurse nurse = nurseRepository.findByUser(user);
            BranchNurse branchCheck =branchNurseRepository.findByNurseAndPrimaryBranchTrue(nurse);
            if(HISCoreUtil.isValidObject(branchCheck))
                return true;
        }
        if (userType.equalsIgnoreCase(UserTypeEnum.CASHIER.name())) {
            Cashier cashier = cashierRepository.findByUser(user);
            BranchCashier branchCheck =branchCashierRepository.findByCashierAndPrimaryBranchTrue(cashier);
            if(HISCoreUtil.isValidObject(branchCheck))
                return true;
        }
        if (userType.equalsIgnoreCase(UserTypeEnum.RECEPTIONIST.name())) {
            Receptionist receptionist = receptionistRepository.findByUser(user);
            BranchReceptionist branchCheck = branchReceptionistRepository.findByReceptionistAndPrimaryBranchTrue(receptionist);
            if(HISCoreUtil.isValidObject(branchCheck))
                return true;
        }
        return false;
  }
*/
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
    @Transactional
    public Branch updateBranch(BranchRequestWrapper branchRequestWrapper, Branch branch) {

        branch.setName(branchRequestWrapper.getBranchName());
        branch.setStatus(true);
    //    branch.setNoOfRooms(branchRequestWrapper.getNoOfExamRooms());
        branch.setAddress(branchRequestWrapper.getAddress());
        branch.setFlow(branchRequestWrapper.getFlow());
        //    branch.setSystemBranch(false);
        branch.setOfficePhone(branchRequestWrapper.getOfficePhone());
        branch.setOfficeStartTime(HISCoreUtil.convertToTime(branchRequestWrapper.getOfficeHoursStart()));
        branch.setOfficeEndTime(HISCoreUtil.convertToTime(branchRequestWrapper.getOfficeHoursEnd()));
        branch.setFax(branchRequestWrapper.getFax());
        branch.setAddress(branchRequestWrapper.getAddress());
        branchRepository.save(branch);
        List<ExamRooms> exRooms = new ArrayList<>(Arrays.asList(branchRequestWrapper.getExamRooms()));
        /*if(!HISCoreUtil.isListEmpty(exRooms)){ // delete branches room for future
             roomRepository.deleteAllByBranch(branch);
        }*/
        /*for (ExamRooms ex : exRooms) {
            Room room = new Room();
            room.setAllowOnlineScheduling(ex.isAllowOnlineScheduling());
            room.setRoomName(ex.getRoomName());
            room.setBranch(branch);
            room.setActive(true);
            roomRepository.save(room);
        }*/

       /* Doctor doctor = doctorRepository.getOne(branchRequestWrapper.getPrimaryDoctor());
        BranchDoctor branchDoctor =branchDoctorRepository.findByBranch(branch);
        branchDoctor.setBranch(branch);
        branchDoctor.setDoctor(doctor);
        branchDoctor.setPrimaryBranch(true);
        branchDoctorRepository.save(branchDoctor);
*/
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


    */
    public List<BranchResponseWrapper> searchByBranchNameAndDepartment(Long name, int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        logger.info("searching branches");
        Branch branch1 =  branchRepository.findOne(name);
        List<BranchResponseWrapper> branches = branchRepository.findByNameAndBranchDepartments(branch1.getName(),pageable);
        return branches;
    }
    public boolean isBranchNameOrIdExistsAlready(String name, long brId) {
        return branchRepository.findByNameAndIdNot(name,brId) == null;        // Simplified from  (this==null?false:true)
    }


    public List<BranchResponseWrapper> getAllActiveBranches() {
        List<BranchResponseWrapper> list = branchRepository.findAllByActiveTrue();
        return branchRepository.findAllByActiveTrue();
    }

    public Set<BranchResponseWrapper> getAllActiveBranchesWithDoctors() {
        return new HashSet<BranchResponseWrapper>(branchRepository.findByBranchAndBranchDoctors());
    }
    private String generateEmail(String domain, int length) {
        return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyz") + "@" + domain;
    }

   /* public List<Room> getTotalRoomsByBrId(Long branchId){
        return roomRepository.findByBranchId(branchId);
    }*/
    public List<Room> getTotalRoomsByBrId(Long branchId){
        return null;//roomRepository.findByBranchId(branchId)
    }

    public CityWrapper getCityByBrId(Long branchId){
        return branchRepository.findCityByBranchId(branchId);
    }

    public StateWrapper getStateByBrId(Long branchId){
        return branchRepository.findStateByBranchId(branchId);
    }

    public CountryWrapper getCountryByBrId(Long branchId){
        return branchRepository.findCountryByBranchId(branchId);
    }

    public List<CountryWrapper> getAllCountries(){
        return countryRepository.getAllCountries();
    }

    public List<StateWrapper> getStatesByCntryId(Long countryId){
        return stateRepository.getAllStatesByCountry(countryId);
    }

    public List<CityWrapper> getCitiesByStateId(Long stateId){
        return cityRepository.getAllCitiesByStateId(stateId);
    }
}