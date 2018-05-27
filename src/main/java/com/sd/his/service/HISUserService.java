package com.sd.his.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.his.enums.UserEnum;
import com.sd.his.model.*;
import com.sd.his.repositiories.*;
import com.sd.his.request.WorkingDaysOfDoctor;
import com.sd.his.response.UserResponseWrapper;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "userService")
@Transactional
public class HISUserService implements UserDetailsService {

    private UserRepository userRepository;
    private PermissionRepository permissionRepo;
    private RoleRepository roleRepo;
    private BranchRepository branchRepository;
    private BranchUserRepository branchUserRepository;
    private UserRoleRepository userRoleRepository;
    private DutyShiftRepository dutyShiftRepository;
    private UserDutyShiftRepository userDutyShiftRepository;
    private VacationRepository vacationRepository;
    private UserMedicalServiceRepository userMedicalServiceRepository;
    private MedicalServicesRepository medicalServicesRepository;
    private ClinicalDepartmentRepository clinicalDepartmentRepository;
    private DepartmentUserRepository departmentUserRepository;
    private DutyWithDoctorRepository dutyWithDoctorRepository;
    private UserVisitBranchesRepository userVisitBranchesRepository;
    private final Logger logger = LoggerFactory.getLogger(HISUserService.class);

    @Autowired
    HISUserService(UserRepository userRepository, PermissionRepository permissionRepo, RoleRepository roleRepo, BranchRepository branchRepository, BranchUserRepository branchUserRepository,
                   UserRoleRepository userRoleRepository, VacationRepository vacationRepository,
                   UserDutyShiftRepository userDutyShiftRepository, DutyShiftRepository dutyShiftRepository,UserMedicalServiceRepository userMedicalServiceRepository,
                   MedicalServicesRepository medicalServicesRepository,
                   ClinicalDepartmentRepository clinicalDepartmentRepository,
                   DepartmentUserRepository departmentUserRepository,
                   DutyWithDoctorRepository dutyWithDoctorRepository,
                   UserVisitBranchesRepository userVisitBranchesRepository
    ) {
        this.userRepository = userRepository;
        this.permissionRepo = permissionRepo;
        this.roleRepo = roleRepo;
        this.branchRepository = branchRepository;
        this.branchUserRepository = branchUserRepository;
        this.userRoleRepository = userRoleRepository;
        this.userDutyShiftRepository = userDutyShiftRepository;
        this.vacationRepository = vacationRepository;
        this.dutyShiftRepository = dutyShiftRepository;
        this.userMedicalServiceRepository =userMedicalServiceRepository;
        this.clinicalDepartmentRepository=clinicalDepartmentRepository;
        this.medicalServicesRepository = medicalServicesRepository;
        this.departmentUserRepository = departmentUserRepository;
        this.dutyWithDoctorRepository=dutyWithDoctorRepository;
        this.userVisitBranchesRepository=userVisitBranchesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(userNameOrEmail, userNameOrEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isActive(), true, true, true, getAuthorities(user));

    }

    private List<SimpleGrantedAuthority> getRoles(List<Role> authList) {
        return authList.stream()
                .map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(Collectors.toList());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {

        List<String> perm = getPrivileges(user);
        return getGrantedAuthorities(perm);
    }

    private List<String> getPrivileges(User user) {
        List<String> privileges = user.getPermissions().stream().map(object -> Objects.toString(object.getPermission().getName(), null)).
                collect(Collectors.toList());
        List<Permission> allPermissions = new ArrayList<>();
        List<Permission> rolePermissions = permissionRepo.findAllUserRolePermissions(user.getId());
        List<Permission> userPermissions = permissionRepo.findAllUserPermissions(user.getId());
        allPermissions.addAll(rolePermissions);
        allPermissions.addAll(userPermissions);

        for (Permission per : rolePermissions) {
            privileges.add(per.getName());
        }
        Set<String> identicalPermissions = new HashSet<>(privileges);
        return identicalPermissions.stream().collect(Collectors.toList());
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public User findByUsernameOrEmailAndActiveTrueAndDeletedFalse(String userName, String email) {
        return userRepository.findByUsernameOrEmailAndActiveTrueAndDeletedFalse(userName, email);
    }

    public User findByUserName(String name) {
        return userRepository.findByUsername(name);
    }

    public UserWrapper buildUserWrapper(User dbUser) {
        UserWrapper user = new UserWrapper(dbUser);
        List<PermissionWrapper> permissionWrappers = new ArrayList<>();
        List<Permission> userPermissions = getIdenticalUserPermissions(dbUser);

        for (Permission per : userPermissions) {
            PermissionWrapper permissionWrapper = new PermissionWrapper(per);
            permissionWrappers.add(permissionWrapper);
        }
        user.setPermissions(permissionWrappers);

        return user;
    }

    private List<Permission> getIdenticalUserPermissions(User user) {
        List<Permission> allPermissions = new ArrayList<>();
        List<Permission> rolePermissions = permissionRepo.findAllUserRolePermissions(user.getId());
        List<Permission> userPermissions = permissionRepo.findAllUserPermissions(user.getId());
        allPermissions.addAll(rolePermissions);
        allPermissions.addAll(userPermissions);

        Set<Permission> identicalPermissionsSet = new HashSet<>(allPermissions);
        List<Permission> identicalPermissions = new ArrayList<>(identicalPermissionsSet);

        return identicalPermissions;
    }

    public User saveUser(UserCreateRequest createRequest) {
        String usertype = createRequest.getUserType();
        BranchUser branchUser = new BranchUser();
        UserDutyShift userDutyShift = new UserDutyShift();
        UserRole userRole = new UserRole();
        if (usertype.equalsIgnoreCase(UserEnum.CASHIER.toString())) {
            User user = new User();

            Profile profile = new Profile();
            user.setUserType(createRequest.getUserType());
            user.setUsername(createRequest.getUserName());
            user.setActive(createRequest.isActive());
            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
            user.setEmail(createRequest.getEmail());
            Branch branch = branchRepository.findByName(createRequest.getPrimaryBranch());
            profile.setCreatedOn(createRequest.getCreatedOn());
            profile.setUpdatedOn(createRequest.getUpdatedOn());
            profile.setActive(createRequest.isActive());
            profile.setOtherDashboard(createRequest.getOtherDashboard());
            profile.setDeleted(false);
            profile.setFirstName(createRequest.getFirstName());
            profile.setLastName(createRequest.getLastName());
            profile.setAccountExpiry(createRequest.getAccountExpiry());
            profile.setCellPhone(createRequest.getCellPhone());
            profile.setHomePhone(createRequest.getHomePhone());
            profile.setAllowDiscount(createRequest.getAllowDiscount());
            profile.setSendBillingReport(createRequest.isSendBillingReport());
            profile.setUseReceptDashBoard(createRequest.isUseReceptDashboard());
            profile.setOtherDoctorDashBoard(createRequest.isOtherDoctorDashBoard());
            user.setProfile(profile);
            userRepository.save(user);

            branchUser.setUser(user);
            branchUser.setBranch(branch);

            userRole.setRole(roleFindByName(createRequest.getUserType().toUpperCase()));
            userRole.setUser(user);

            userRoleRepository.save(userRole);

            branchUserRepository.save(branchUser);

            return user;
        }

        if (usertype.equalsIgnoreCase(UserEnum.RECEPTIONIST.toString())) {
            User user = new User();
            Profile profile = new Profile();
            user.setUserType(createRequest.getUserType());
            user.setUsername(createRequest.getUserName());
            user.setActive(createRequest.isActive());
            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
            user.setEmail(createRequest.getEmail());
            Branch branch = branchRepository.findByName(createRequest.getPrimaryBranch());
            profile.setCreatedOn(createRequest.getCreatedOn());
            profile.setUpdatedOn(createRequest.getUpdatedOn());
            profile.setActive(createRequest.isActive());
            profile.setDeleted(false);
            profile.setFirstName(createRequest.getFirstName());
            profile.setLastName(createRequest.getLastName());
            profile.setAccountExpiry(createRequest.getAccountExpiry());
            profile.setCellPhone(createRequest.getCellPhone());
            profile.setHomePhone(createRequest.getHomePhone());
            profile.setAllowDiscount(createRequest.getAllowDiscount());
            profile.setSendBillingReport(createRequest.isSendBillingReport());
            profile.setOtherDashboard(createRequest.getOtherDashboard());
            profile.setUseReceptDashBoard(createRequest.isUseReceptDashboard());
            profile.setOtherDoctorDashBoard(createRequest.isOtherDoctorDashBoard());
            user.setProfile(profile);
            userRepository.save(user);

            branchUser.setUser(user);
            branchUser.setBranch(branch);


            userRole.setRole(roleFindByName(createRequest.getUserType().toUpperCase()));
            userRole.setUser(user);

            userRoleRepository.save(userRole);

            branchUserRepository.save(branchUser);

            return user;
        }

        if (usertype.equalsIgnoreCase(UserEnum.NURSE.toString())) {
            User user = new User();
            Profile profile = new Profile();
            user.setUserType(createRequest.getUserType());
            user.setUsername(createRequest.getUserName());
            user.setActive(createRequest.isActive());
            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
            user.setEmail(createRequest.getEmail());
            Branch branch = branchRepository.findByName(createRequest.getPrimaryBranch());
            profile.setCreatedOn(createRequest.getCreatedOn());
            profile.setUpdatedOn(createRequest.getUpdatedOn());
            profile.setActive(createRequest.isActive());
            profile.setDeleted(false);
            profile.setFirstName(createRequest.getFirstName());
            profile.setLastName(createRequest.getLastName());
            profile.setAccountExpiry(createRequest.getAccountExpiry());
            profile.setCellPhone(createRequest.getCellPhone());
            profile.setHomePhone(createRequest.getHomePhone());

            profile.setSendBillingReport(createRequest.isSendBillingReport());
            profile.setUseReceptDashBoard(createRequest.isUseReceptDashboard());
            profile.setOtherDoctorDashBoard(createRequest.isOtherDoctorDashBoard());
            profile.setManagePatientInvoices(createRequest.isManagePatientInvoices());
            profile.setManagePatientRecords(createRequest.isManagePatientRecords());
            profile.setOtherDashboard(createRequest.getOtherDashboard());

            user.setProfile(profile);
            userRepository.save(user);

            branchUser.setUser(user);
            branchUser.setBranch(branch);

            List<ClinicalDepartment> clinicalDepartments = clinicalDepartmentRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedDepartment()));
            for (ClinicalDepartment clinicalDepartment :clinicalDepartments){
                DepartmentUser departmentUser =new DepartmentUser();
                departmentUser.setClinicalDepartment(clinicalDepartment);
                departmentUser.setUser(user);
                departmentUser.setCreatedOn(System.currentTimeMillis());
                departmentUser.setUpdatedOn(System.currentTimeMillis());
                departmentUser.setDeleted(false);
                departmentUserRepository.save(departmentUser);
            }
            List<Branch> visitBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
            for (Branch visitBr :visitBranches){
                UserVisitBranches userVisitBranches =new UserVisitBranches();
                userVisitBranches.setBranch(visitBr);
                userVisitBranches.setUser(user);
                userVisitBranchesRepository.save(userVisitBranches);
            }

            List<User> doctors= userRepository.findAllByIdIn(Arrays.asList(createRequest.getDutyWithDoctors()));
            for (User docuser:doctors) {
                DutyWithDoctor dutyWithDoctor = new DutyWithDoctor();
                dutyWithDoctor.setDoctor(docuser);
                dutyWithDoctor.setNurse(user);
                dutyWithDoctorRepository.save(dutyWithDoctor);
            }
            userRole.setRole(roleFindByName(createRequest.getUserType().toUpperCase()));
            userRole.setUser(user);

            userRoleRepository.save(userRole);

            branchUserRepository.save(branchUser);

            return user;
        }
        if (usertype.equalsIgnoreCase(UserEnum.DOCTOR.toString())) {

            User user = new User();
            Profile profile = new Profile();
            user.setUserType(createRequest.getUserType());
            user.setUsername(createRequest.getUserName());
            user.setActive(createRequest.isActive());
            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
            user.setEmail(createRequest.getEmail());
            Branch branch = branchRepository.findByName(createRequest.getPrimaryBranch());
            profile.setCreatedOn(createRequest.getCreatedOn());
            profile.setUpdatedOn(createRequest.getUpdatedOn());
            profile.setActive(createRequest.isActive());
            profile.setOtherDashboard(createRequest.getOtherDashboard());
            profile.setDeleted(false);
            profile.setFirstName(createRequest.getFirstName());
            profile.setLastName(createRequest.getLastName());
            profile.setAccountExpiry(createRequest.getAccountExpiry());
            profile.setCellPhone(createRequest.getCellPhone());
            profile.setHomePhone(createRequest.getHomePhone());
            profile.setSendBillingReport(createRequest.isSendBillingReport());
            profile.setUseReceptDashBoard(createRequest.isUseReceptDashboard());
            profile.setOtherDoctorDashBoard(createRequest.isOtherDoctorDashBoard());
            profile.setCheckUpInterval(createRequest.getInterval());

            List<String> daysList = Arrays.asList(createRequest.getSelectedWorkingDays());
            profile.setWorkingDays(daysList);

            LocalTime t = LocalTime.parse(createRequest.getSecondShiftFromTime());

            DutyShift dutyShift = new DutyShift();
            dutyShift.setCreatedOn(System.currentTimeMillis());
            dutyShift.setUpdatedOn(System.currentTimeMillis());
            dutyShift.setDeleted(false);
            dutyShift.setDutyTimmingShift1(createRequest.isShift1());
            dutyShift.setDutyTimmingShift2(createRequest.isShift2());
            dutyShift.setStartTimeShift1(createRequest.getFirstShiftFromTime());
            dutyShift.setEndTimeShift1(createRequest.getFirstShiftToTime());

            dutyShift.setStartTimeShift2(createRequest.getSecondShiftFromTime());
            dutyShift.setEndTimeShift2(createRequest.getSecondShiftToTime());

            dutyShiftRepository.save(dutyShift);
            profile.setCheckUpInterval(createRequest.getInterval());


            user.setProfile(profile);
            userRepository.save(user);
            medicalServicesRepository.findAll();

           List<MedicalService> medicalServiceslist = medicalServicesRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedServices()));
             for (MedicalService mdService :medicalServiceslist){
                 UserMedicalService userMedicalService =new UserMedicalService();
                 userMedicalService.setMedicalService(mdService);
                 userMedicalService.setUser(user);
                 userMedicalServiceRepository.save(userMedicalService);
            }


            List<ClinicalDepartment> clinicalDepartments = clinicalDepartmentRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedDepartment()));
            for (ClinicalDepartment clinicalDepartment :clinicalDepartments){
                DepartmentUser departmentUser =new DepartmentUser();
                departmentUser.setClinicalDepartment(clinicalDepartment);
                departmentUser.setUser(user);
                departmentUser.setCreatedOn(System.currentTimeMillis());
                departmentUser.setUpdatedOn(System.currentTimeMillis());
                departmentUser.setDeleted(false);
                departmentUserRepository.save(departmentUser);
            }

            Vacation vacation = new Vacation();
            vacation.setCreatedOn(System.currentTimeMillis());
            vacation.setUpdatedOn(System.currentTimeMillis());
            vacation.setDeleted(false);

            vacation.setStartDate(HISCoreUtil.convertDateToMilliSeconds(createRequest.getDateFrom()));
            vacation.setEndDate(HISCoreUtil.convertDateToMilliSeconds(createRequest.getDateTo()));

            vacation.setStatus(createRequest.isVacation());
            vacation.setVacation(createRequest.isVacation());
            vacation.setUser(user);
            vacationRepository.save(vacation);

            userDutyShift.setUser(user);
            userDutyShift.setDutyShift(dutyShift);

            branchUser.setUser(user);
            branchUser.setBranch(branch);
            branchUser.setPrimaryBranch(true);

            userRole.setRole(roleFindByName(createRequest.getUserType().toUpperCase()));
            userRole.setUser(user);

            userRoleRepository.save(userRole);

            branchUserRepository.save(branchUser);

            userDutyShiftRepository.save(userDutyShift);
            return user;
        }
        return null;
    }


    private Role roleFindByName(String name) {
        return roleRepo.findByName(name);
    }


    public List<UserResponseWrapper> findAllUsers(int offset, int limit) {

        Pageable pageable = new PageRequest(offset, limit);
        List<UserResponseWrapper> users = new ArrayList<>();
        BranchWrapper branches = null;
        List<User> allUser = userRepository.findAllUsers(pageable);
        for (User user : allUser) {
            UserResponseWrapper userWrapper = new UserResponseWrapper(user);
            for (BranchUser branchUser : user.getBranches()) {
                if (branchUser.isPrimaryBranch()) {
                    BranchWrapper branchWrapper = new BranchWrapper(branchUser);
                    branches = branchWrapper;
                }
            }
            userWrapper.setBranch(branches);
            users.add(userWrapper);
        }
        return users;
    }

    public int totalUser() {
        return  userRepository.countAllByActiveTrueAndDeletedFalse();
    }

    public User updateUser(UserCreateRequest userCreateRequest, User alreadyExistsUser) {
        String userType = userCreateRequest.getUserType();
        if (userType.equalsIgnoreCase(UserEnum.CASHIER.toString())) {
            alreadyExistsUser.setUsername(userCreateRequest.getUserName());
            alreadyExistsUser.setActive(userCreateRequest.isActive());
            //   alreadyExistsUser.setPassword(new BCryptPasswordEncoder().encode(userCreateRequest.getPassword()));
            alreadyExistsUser.setEmail(userCreateRequest.getEmail());


            Profile updateProfile = alreadyExistsUser.getProfile();
            updateProfile.setType(userCreateRequest.getUserType());
            updateProfile.setLastName(userCreateRequest.getLastName());
            updateProfile.setFirstName(userCreateRequest.getFirstName());
            updateProfile.setCellPhone(userCreateRequest.getCellPhone());
            updateProfile.setHomePhone(userCreateRequest.getHomePhone());
            updateProfile.setSendBillingReport(userCreateRequest.isSendBillingReport());
            updateProfile.setUseReceptDashBoard(userCreateRequest.isUseReceptDashboard());
            updateProfile.setOtherDoctorDashBoard(userCreateRequest.isOtherDoctorDashBoard());
            updateProfile.setAccountExpiry(userCreateRequest.getAccountExpiry());
            updateProfile.setActive(userCreateRequest.isActive());
            updateProfile.setAllowDiscount(userCreateRequest.getAllowDiscount());
            updateProfile.setUpdatedOn(System.currentTimeMillis());
            alreadyExistsUser.setProfile(updateProfile);

            userRepository.save(alreadyExistsUser);
            return alreadyExistsUser;
        }

        if (userType.equalsIgnoreCase(UserEnum.RECEPTIONIST.toString())) {
            alreadyExistsUser.setUsername(userCreateRequest.getUserName());
            alreadyExistsUser.setActive(userCreateRequest.isActive());
            alreadyExistsUser.setEmail(userCreateRequest.getEmail());

            Profile updateProfile = alreadyExistsUser.getProfile();
            updateProfile.setCellPhone(userCreateRequest.getCellPhone());
            updateProfile.setFirstName(userCreateRequest.getFirstName());
            updateProfile.setLastName(userCreateRequest.getLastName());
            updateProfile.setType(userCreateRequest.getUserType());
            updateProfile.setHomePhone(userCreateRequest.getHomePhone());
            updateProfile.setSendBillingReport(userCreateRequest.isSendBillingReport());
            updateProfile.setUseReceptDashBoard(userCreateRequest.isUseReceptDashboard());
            updateProfile.setOtherDoctorDashBoard(userCreateRequest.isOtherDoctorDashBoard());
            updateProfile.setAccountExpiry(userCreateRequest.getAccountExpiry());
            updateProfile.setActive(userCreateRequest.isActive());
            updateProfile.setAllowDiscount(userCreateRequest.getAllowDiscount());
            updateProfile.setUpdatedOn(System.currentTimeMillis());
            alreadyExistsUser.setProfile(updateProfile);

            userRepository.saveAndFlush(alreadyExistsUser);
            return alreadyExistsUser;
        }

        if (userType.equalsIgnoreCase(UserEnum.NURSE.toString())) {
            alreadyExistsUser.setUsername(userCreateRequest.getUserName());
            alreadyExistsUser.setActive(userCreateRequest.isActive());
            alreadyExistsUser.setEmail(userCreateRequest.getEmail());

            Profile updateProfile = alreadyExistsUser.getProfile();

            updateProfile.setCellPhone(userCreateRequest.getCellPhone());
            updateProfile.setFirstName(userCreateRequest.getFirstName());
            updateProfile.setLastName(userCreateRequest.getLastName());
            updateProfile.setHomePhone(userCreateRequest.getHomePhone());
            updateProfile.setSendBillingReport(userCreateRequest.isSendBillingReport());
            updateProfile.setUseReceptDashBoard(userCreateRequest.isUseReceptDashboard());
            updateProfile.setOtherDoctorDashBoard(userCreateRequest.isOtherDoctorDashBoard());
            updateProfile.setAccountExpiry(userCreateRequest.getAccountExpiry());
            updateProfile.setActive(userCreateRequest.isActive());
            updateProfile.setUpdatedOn(System.currentTimeMillis());
            updateProfile.setManagePatientRecords(userCreateRequest.isManagePatientRecords());
            updateProfile.setManagePatientInvoices(userCreateRequest.isManagePatientInvoices());

            alreadyExistsUser.setProfile(updateProfile);

            userRepository.save(alreadyExistsUser);
            return alreadyExistsUser;
        }

        if (userType.equalsIgnoreCase(UserEnum.DOCTOR.toString())) {
            Vacation vacation = vacationRepository.findByUser(alreadyExistsUser);
            alreadyExistsUser.setUsername(userCreateRequest.getUserName());
            alreadyExistsUser.setActive(userCreateRequest.isActive());
            // alreadyExistsUser.setPassword(new BCryptPasswordEncoder().encode(userCreateRequest.getPassword()));
            alreadyExistsUser.setEmail(userCreateRequest.getEmail());

            Profile updateProfile = alreadyExistsUser.getProfile();

            updateProfile.setCellPhone(userCreateRequest.getCellPhone());
            updateProfile.setHomePhone(userCreateRequest.getHomePhone());
            updateProfile.setSendBillingReport(userCreateRequest.isSendBillingReport());
            updateProfile.setUseReceptDashBoard(userCreateRequest.isUseReceptDashboard());
            updateProfile.setOtherDoctorDashBoard(userCreateRequest.isOtherDoctorDashBoard());
            updateProfile.setAccountExpiry(userCreateRequest.getAccountExpiry());
            updateProfile.setActive(userCreateRequest.isActive());
            updateProfile.setUpdatedOn(System.currentTimeMillis());

            List<String> daysList = new ArrayList<>(Arrays.asList(userCreateRequest.getSelectedWorkingDays()));
            updateProfile.setWorkingDays(daysList);

            alreadyExistsUser.setProfile(updateProfile);

            vacation.setVacation(userCreateRequest.isVacation());
            vacation.setStatus(userCreateRequest.isVacation());

            vacation.setStartDate(HISCoreUtil.convertDateToMilliSeconds(userCreateRequest.getDateFrom()));
            vacation.setEndDate(HISCoreUtil.convertDateToMilliSeconds(userCreateRequest.getDateTo()));

            vacationRepository.saveAndFlush(vacation);

            UserDutyShift userDutyShift = userDutyShiftRepository.findByUser(alreadyExistsUser);
            DutyShift dutyShift = userDutyShift.getDutyShift();

            dutyShift.setEndTimeShift2(userCreateRequest.getSecondShiftToTime());
            dutyShift.setStartTimeShift2(userCreateRequest.getSecondShiftFromTime());

            dutyShift.setEndTimeShift1(userCreateRequest.getFirstShiftToTime());
            dutyShift.setStartTimeShift1(userCreateRequest.getFirstShiftFromTime());

            dutyShiftRepository.save(dutyShift);

            userRepository.save(alreadyExistsUser);

            return alreadyExistsUser;

        }
        return null;
    }

    public UserResponseWrapper findByIdAndResponse(long id) {
        User user = userRepository.findAllById(id);
        UserResponseWrapper userResponseWrapper = new UserResponseWrapper(user);

        userResponseWrapper.setProfile(user.getProfile());
        UserDutyShift userDutyShift = userDutyShiftRepository.findByUser(user);
        Vacation vacation = vacationRepository.findByUser(user);

        userResponseWrapper.setDutyShift(userDutyShift.getDutyShift());
        userResponseWrapper.setVacation(vacation);
        List<ClinicalDepartment> clinicalDepartmentsList = new ArrayList<>();
        for(DepartmentUser departmentUser:user.getDepartments()){
            clinicalDepartmentsList.add(departmentUser.getClinicalDepartment());
        }
        userResponseWrapper.setClinicalDepartment(clinicalDepartmentsList);
        return userResponseWrapper;
    }


    public User findById(long id) {
        return userRepository.findAllById(id);
    }


    public User deleteUser(User user) {
        user.setDeleted(true);
        return userRepository.save(user);
    }

    public List<UserWrapper> searchByNameOrEmailOrRole(String name, String email, String role, int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        logger.info("role assigned" + role);
        Role Assignedrole = roleRepo.findByName(role.toUpperCase());
        List<User> alluser = userRepository.findAllByUsernameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrRoles_role_nameIgnoreCaseContaining(name, email, role, pageable);
        List<UserWrapper> userWrapper = new ArrayList<>();

        for (User user : alluser) {
            UserWrapper userWrapper1 = new UserWrapper(user);
            userWrapper.add(userWrapper1);
        }
        return userWrapper;
    }

    public List<UserResponseWrapper> findByRole(String role) {
        List<UserResponseWrapper> userWrapper = new ArrayList<>();
        List<User> userRoles = userRepository.findAllByRoles_role_name(role);
        for (User user : userRoles) {
            UserResponseWrapper userResponseWrapper = new UserResponseWrapper(user);
            userWrapper.add(userResponseWrapper);
        }
        return userWrapper;
    }
}
