package com.sd.his.service;

import com.sd.his.model.*;
import com.sd.his.repository.PermissionRepository;
import com.sd.his.repository.RolePermissionRepository;
import com.sd.his.repository.RoleRepository;
import com.sd.his.repository.UserRepository;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.PermissionWrapper;
import com.sd.his.wrapper.RoleWrapper;
import com.sd.his.wrapper.UserWrapper;
import com.sd.his.wrapper.request.AssignAuthoritiesRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "userService")
@Transactional
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepo;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RolePermissionRepository rolePermissionRepository;

    @Autowired
    PermissionRepository permissionRepository;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndActiveTrue(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getActive(), true, true, true, getAuthorities(user));

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
        List<String> privileges = new ArrayList<>();
        List<Permission> rolePermissions = permissionRepo.findUserPermissionByUserId(user.getId());

        for (Permission per : rolePermissions) {
            privileges.add(per.getName());
        }
        return privileges.stream().collect(Collectors.toList());
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

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public User findByUsername(String userName) {
        return userRepository.findByUsernameAndActiveTrue(userName);
    }
    private List<Permission> getIdenticalUserPermissions(User user) {
     return permissionRepo.findUserPermissionByUserId(user.getId());
    }

    public List<Role> getAllActiveRoles() {
        return roleRepository.findAllByActiveTrue();
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

   /* public Role save(RoleAndPermissionCreateRequest roleRequest) {
        Role role = new Role(roleRequest);
        return roleRepository.save(role);
    }
*/
    @Transactional(rollbackOn = Throwable.class)
    public Boolean assignPermissionsToRole(AssignAuthoritiesRequestWrapper authRequest) {
        Boolean permissionAssigned;
        Role role = roleRepository.findByName(authRequest.getSelectedRole());
        if (HISCoreUtil.isValidObject(role)) {
            rolePermissionRepository.deleteAllByRole_Id(role.getId());
            List<RolePermission> rolePermissions = new ArrayList<>();
            List<Permission> selectedPermissions = permissionRepository.findByIdInAndActiveTrue(authRequest.getPermissionIds());
            for (Permission per : selectedPermissions) {
                RolePermission rp = new RolePermission(role, per);
                rolePermissions.add(rp);
            }
            rolePermissionRepository.save(rolePermissions);
            permissionAssigned = true;
        } else {
            permissionAssigned = false;
        }
        return permissionAssigned;
    }

    public UserWrapper buildLoggedInUserWrapper(User dbUser) {
        UserWrapper user = new UserWrapper(dbUser);
        List<PermissionWrapper> permissionWrappers = new ArrayList<>();
        List<RoleWrapper> roleWrappers = new ArrayList<>();
        List<Permission> userPermissions = getIdenticalUserPermissions(dbUser);

        for (UserRole role : dbUser.getUserRoles()) {
            RoleWrapper roleWrapper = new RoleWrapper(role.getRole());
            roleWrappers.add(roleWrapper);

            String commaSeparatedRoles = "";
            commaSeparatedRoles = (commaSeparatedRoles) + (role.getRole().getName() + ",");
            commaSeparatedRoles = commaSeparatedRoles.substring(0, commaSeparatedRoles.lastIndexOf(","));
            user.setCommaSeparatedRoles(commaSeparatedRoles);
        }

        for (Permission per : userPermissions) {
            PermissionWrapper permissionWrapper = new PermissionWrapper(per);
            permissionWrappers.add(permissionWrapper);
        }
        user.setPermissions(permissionWrappers);
        user.setRoles(roleWrappers);

        return user;
    }


//
//    public User findByUsernameOrEmail(String userName, String email) {
//        return userRepository.findByUsernameOrEmail(userName, email);
//    }
//
//    public User findByUserName(String name) {
//        return userRepository.findByUsername(name);
//    }
//
//    public UserWrapper buildUserWrapper(User dbUser) {
//        UserWrapper user = new UserWrapper(dbUser);
//        List<PermissionWrapper> permissionWrappers = new ArrayList<>();
//        List<Permission> userPermissions = getIdenticalUserPermissions(dbUser);
//
//        for (Permission per : userPermissions) {
//            PermissionWrapper permissionWrapper = new PermissionWrapper(per);
//            permissionWrappers.add(permissionWrapper);
//        }
//        user.setPermissions(permissionWrappers);
//
//        return user;
//    }
//
//    public UserWrapper buildLoggedInUserWrapper(User dbUser) {
//        UserWrapper user = new UserWrapper(dbUser);
//        List<PermissionWrapper> permissionWrappers = new ArrayList<>();
//        List<RoleWrapper> roleWrappers = new ArrayList<>();
//        List<Permission> userPermissions = getIdenticalUserPermissions(dbUser);
//
//        for (UserRole role : dbUser.getRoles()) {
//            RoleWrapper roleWrapper = new RoleWrapper(role.getRole());
//            roleWrappers.add(roleWrapper);
//
//            String commaSeparatedRoles = "";
//            commaSeparatedRoles = (commaSeparatedRoles) + (role.getRole().getName() + ",");
//            commaSeparatedRoles = commaSeparatedRoles.substring(0, commaSeparatedRoles.lastIndexOf(","));
//            user.setCommaSeparatedRoles(commaSeparatedRoles);
//        }
//
//        for (Permission per : userPermissions) {
//            PermissionWrapper permissionWrapper = new PermissionWrapper(per);
//            permissionWrappers.add(permissionWrapper);
//        }
//        user.setPermissions(permissionWrappers);
//        user.setRoles(roleWrappers);
//
//        return user;
//    }
//
//    private List<Permission> getIdenticalUserPermissions(User user) {
//        List<Permission> allPermissions = new ArrayList<>();
//        List<Permission> rolePermissions = permissionRepo.findAllUserRolePermissions(user.getId());
//        List<Permission> userPermissions = permissionRepo.findAllUserPermissions(user.getId());
//        allPermissions.addAll(rolePermissions);
//        allPermissions.addAll(userPermissions);
//
//        Set<Permission> identicalPermissionsSet = new HashSet<>(allPermissions);
//        List<Permission> identicalPermissions = new ArrayList<>(identicalPermissionsSet);
//
//        return identicalPermissions;
//    }
//
//    public User saveUser(UserCreateRequest createRequest) {
//        String usertype = createRequest.getUserType();
//        String pBranch = createRequest.getPrimaryBranch();
//        Branch branch = null;
//        BranchUser branchUser = new BranchUser();
//        UserDutyShift userDutyShift = new UserDutyShift();
//        UserRole userRole = new UserRole();
//        int primarBranchId = Integer.parseInt(pBranch);
//        branch = branchRepository.findById(primarBranchId);
//        String brName = branch.getName();
//        if (brName.equalsIgnoreCase(PropertyEnum.PRIMARY_BRANCH.getValue())) {
//            branch = new Branch();
//            branch.setActive(true);
//            branch.setCreatedOn(new Date());
//            branch.setUpdatedOn(new Date());
//            branch.setOfficePhone(createRequest.getHomePhone());
//            branch.setName("PrimaryBranch" + createRequest.getFirstName());
//            branch.setNoOfRooms(1);
//            branch.setBillingBranchName("PB" + createRequest.getFirstName());
//            branch.setBillingName("PB" + createRequest.getFirstName());
//            branch.setFax("PB" + createRequest.getFirstName());
//            branch.setBillingTaxId(createRequest.getFirstName());
//            branch.setSystemBranch(false);
//            branchRepository.save(branch);
//        }
//        if (usertype.equalsIgnoreCase(UserTypeEnum.CASHIER.toString())) {
//            User user = new User();
//
//            Profile profile = new Profile();
//            user.setUserType(createRequest.getUserType());
//            user.setUsername(createRequest.getUserName());
//            user.setActive(createRequest.isActive());
//            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
//            user.setEmail(createRequest.getEmail());
//            profile.setCreatedOn(createRequest.getCreatedOn());
//            profile.setUpdatedOn(createRequest.getUpdatedOn());
//            profile.setActive(createRequest.isActive());
//            profile.setDeleted(false);
//            profile.setFirstName(createRequest.getFirstName());
//            profile.setLastName(createRequest.getLastName());
//            profile.setAccountExpiry(createRequest.getAccountExpiry());
//            profile.setCellPhone(createRequest.getCellPhone());
//            profile.setHomePhone(createRequest.getHomePhone());
//            profile.setAllowDiscount(createRequest.getAllowDiscount());
//            profile.setSendBillingReport(createRequest.isSendBillingReport());
//            profile.setUseReceptDashBoard(createRequest.isUseReceptDashboard());
//            profile.setOtherDoctorDashBoard(createRequest.isOtherDoctorDashBoard());
//            profile.setOtherDashboard(createRequest.getOtherDashboard());
//            profile.setType(PropertyEnum.USER_TYPE_CASHIER.getValue());
//            user.setProfile(profile);
//            userRepository.save(user);
//            branchUser.setPrimaryBranch(true);
//            branchUser.setBillingBranch(true);
//            branchUser.setUser(user);
//            branchUser.setBranch(branch);
//            List<Long> listbr = Arrays.asList(createRequest.getSelectedVisitBranches());
//            List<Branch> VisitBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
//            List<UserVisitBranches> cashierVisitBranchesData = new ArrayList<>();
//            if (!HISCoreUtil.isListEmpty(VisitBranches)) {
//                for (Branch userVisitBr : VisitBranches) {
//                    UserVisitBranches userVisitBranches = new UserVisitBranches();
//                    userVisitBranches.setBranch(userVisitBr);
//                    userVisitBranches.setUser(user);
//                    cashierVisitBranchesData.add(userVisitBranches);
//                }
//                userVisitBranchesRepository.save(cashierVisitBranchesData);
//            }
//            userRole.setRole(roleFindByName(createRequest.getUserType().toUpperCase()));
//            userRole.setUser(user);
//            userRoleRepository.save(userRole);
//            branchUserRepository.save(branchUser);
//            return user;
//        }
//
//        if (usertype.equalsIgnoreCase(UserTypeEnum.RECEPTIONIST.toString())) {
//            User user = new User();
//            Profile profile = new Profile();
//            user.setUserType(createRequest.getUserType());
//            user.setUsername(createRequest.getUserName());
//            user.setActive(createRequest.isActive());
//            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
//            user.setEmail(createRequest.getEmail());
//            profile.setCreatedOn(createRequest.getCreatedOn());
//            profile.setUpdatedOn(createRequest.getUpdatedOn());
//            profile.setActive(createRequest.isActive());
//            profile.setDeleted(false);
//            profile.setFirstName(createRequest.getFirstName());
//            profile.setLastName(createRequest.getLastName());
//            profile.setAccountExpiry(createRequest.getAccountExpiry());
//            profile.setCellPhone(createRequest.getCellPhone());
//            profile.setHomePhone(createRequest.getHomePhone());
//            profile.setAllowDiscount(createRequest.getAllowDiscount());
//            profile.setSendBillingReport(createRequest.isSendBillingReport());
//            profile.setUseReceptDashBoard(createRequest.isUseReceptDashboard());
//            profile.setOtherDoctorDashBoard(createRequest.isOtherDoctorDashBoard());
//            profile.setOtherDashboard(createRequest.getOtherDashboard());
//            profile.setType(PropertyEnum.USER_TYPE_RECEPTIONIST.getValue());
//            user.setProfile(profile);
//            userRepository.save(user);
//            branchUser.setPrimaryBranch(true);
//            branchUser.setBillingBranch(true);
//            branchUser.setUser(user);
//            branchUser.setBranch(branch);
//
//            List<Branch> VisitBranchesReceptionist = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
//            List<UserVisitBranches> recepVisitBranchesData = new ArrayList<>();
//            for (Branch userVisitBr : VisitBranchesReceptionist) {
//                UserVisitBranches recepVisitBranches = new UserVisitBranches();
//                recepVisitBranches.setBranch(userVisitBr);
//                recepVisitBranches.setUser(user);
//            }
//            userVisitBranchesRepository.save(recepVisitBranchesData);
//            userRole.setRole(roleFindByName(createRequest.getUserType().toUpperCase()));
//            userRole.setUser(user);
//            userRoleRepository.save(userRole);
//
//            branchUserRepository.save(branchUser);
//
//            return user;
//        }
//
//        if (usertype.equalsIgnoreCase(UserTypeEnum.NURSE.toString())) {
//            User user = new User();
//            Profile profile = new Profile();
//            user.setUserType(createRequest.getUserType());
//            user.setUsername(createRequest.getUserName());
//            user.setActive(createRequest.isActive());
//            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
//            user.setEmail(createRequest.getEmail());
//            profile.setCreatedOn(createRequest.getCreatedOn());
//            profile.setUpdatedOn(createRequest.getUpdatedOn());
//            profile.setActive(createRequest.isActive());
//            profile.setDeleted(false);
//            profile.setFirstName(createRequest.getFirstName());
//            profile.setLastName(createRequest.getLastName());
//            profile.setAccountExpiry(createRequest.getAccountExpiry());
//            profile.setCellPhone(createRequest.getCellPhone());
//            profile.setHomePhone(createRequest.getHomePhone());
//            profile.setSendBillingReport(createRequest.isSendBillingReport());
//            profile.setUseReceptDashBoard(createRequest.isUseReceptDashboard());
//            profile.setOtherDoctorDashBoard(createRequest.isOtherDoctorDashBoard());
//            profile.setManagePatientInvoices(createRequest.isManagePatientInvoices());
//            profile.setManagePatientRecords(createRequest.isManagePatientRecords());
//            profile.setOtherDashboard(createRequest.getOtherDashboard());
//            profile.setOtherDashboard(createRequest.getOtherDashboard());
//            profile.setType(PropertyEnum.USER_TYPE_NURSE.getValue());
//            user.setProfile(profile);
//            userRepository.save(user);
//            branchUser.setPrimaryBranch(true);
//            branchUser.setBillingBranch(true);
//            branchUser.setUser(user);
//            branchUser.setBranch(branch);
//
//            List<ClinicalDepartment> clinicalDepartments = clinicalDepartmentRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedDepartment()));
//            List<DepartmentUser> departmentUserListData = new ArrayList<>();
//            for (ClinicalDepartment clinicalDepartment : clinicalDepartments) {
//                DepartmentUser departmentUser = new DepartmentUser();
//                departmentUser.setClinicalDepartment(clinicalDepartment);
//                departmentUser.setUser(user);
//                departmentUser.setCreatedOn(System.currentTimeMillis());
//                departmentUser.setUpdatedOn(System.currentTimeMillis());
//                departmentUser.setDeleted(false);
//                departmentUserListData.add(departmentUser);
//            }
//            departmentUserRepository.save(departmentUserListData);
//
//            List<Branch> userVisitBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
//            List<UserVisitBranches> userVisitBranchesData = new ArrayList<>();
//            for (Branch userVisitBr : userVisitBranches) {
//                UserVisitBranches userVisitBranches1 = new UserVisitBranches();
//                userVisitBranches1.setBranch(userVisitBr);
//                userVisitBranches1.setUser(user);
//                userVisitBranchesData.add(userVisitBranches1);
//            }
//            userVisitBranchesRepository.save(userVisitBranchesData);
//
//            List<User> doctors = userRepository.findAllByIdIn(Arrays.asList(createRequest.getDutyWithDoctors()));
//            List<DutyWithDoctor> dutyWithDoctorsData = new ArrayList<>();
//            for (User docUser : doctors) {
//                DutyWithDoctor dutyWithDoctor1 = new DutyWithDoctor();
//                dutyWithDoctor1.setNurse(user);
//                dutyWithDoctor1.setDoctor(docUser);
//                dutyWithDoctorsData.add(dutyWithDoctor1);
//            }
//            dutyWithDoctorRepository.save(dutyWithDoctorsData);
//
//            userRole.setRole(roleFindByName(createRequest.getUserType().toUpperCase()));
//            userRole.setUser(user);
//
//            userRoleRepository.save(userRole);
//
//            branchUserRepository.save(branchUser);
//
//            return user;
//        }
//        if (usertype.equalsIgnoreCase(UserTypeEnum.DOCTOR.toString())) {
//
//            User user = new User();
//            Profile profile = new Profile();
//            user.setUserType(createRequest.getUserType());
//            user.setUsername(createRequest.getUserName());
//            user.setActive(createRequest.isActive());
//            user.setPassword(new BCryptPasswordEncoder().encode(createRequest.getPassword()));
//            user.setEmail(createRequest.getEmail());
//            profile.setCreatedOn(createRequest.getCreatedOn());
//            profile.setUpdatedOn(createRequest.getUpdatedOn());
//            profile.setActive(createRequest.isActive());
//            profile.setDeleted(false);
//            profile.setFirstName(createRequest.getFirstName());
//            profile.setLastName(createRequest.getLastName());
//            profile.setAccountExpiry(createRequest.getAccountExpiry());
//            profile.setCellPhone(createRequest.getCellPhone());
//            profile.setHomePhone(createRequest.getHomePhone());
//            profile.setSendBillingReport(createRequest.isSendBillingReport());
//            profile.setUseReceptDashBoard(createRequest.isUseReceptDashboard());
//            profile.setOtherDoctorDashBoard(createRequest.isOtherDoctorDashBoard());
//            profile.setCheckUpInterval(createRequest.getInterval());
//            profile.setType(PropertyEnum.USER_TYPE_DOCTOR.getValue());
//            profile.setOtherDashboard(createRequest.getOtherDashboard());
//            List<String> daysList = Arrays.asList(createRequest.getSelectedWorkingDays());
//            profile.setWorkingDays(daysList);
//            LocalTime t = LocalTime.parse(createRequest.getSecondShiftFromTime());
//            DutyShift dutyShift = new DutyShift();
//            dutyShift.setCreatedOn(System.currentTimeMillis());
//            dutyShift.setUpdatedOn(System.currentTimeMillis());
//            dutyShift.setDeleted(false);
//            dutyShift.setDutyTimmingShift1(createRequest.isShift1());
//            dutyShift.setDutyTimmingShift2(createRequest.isShift2());
//            dutyShift.setStartTimeShift1(createRequest.getFirstShiftFromTime());
//            dutyShift.setEndTimeShift1(createRequest.getFirstShiftToTime());
//            dutyShift.setStartTimeShift2(createRequest.getSecondShiftFromTime());
//            dutyShift.setEndTimeShift2(createRequest.getSecondShiftToTime());
//            dutyShiftRepository.save(dutyShift);
//
//            user.setProfile(profile);
//            userRepository.save(user);
//            medicalServicesRepository.findAll();
//
//            List<MedicalService> medicalServiceslist = medicalServicesRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedServices()));
//            List<UserMedicalService> userDetailsServicesData = new ArrayList<>();
//            for (MedicalService mdService : medicalServiceslist) {
//                UserMedicalService userMedicalService = new UserMedicalService();
//                userMedicalService.setMedicalService(mdService);
//                userMedicalService.setUser(user);
//                userDetailsServicesData.add(userMedicalService);
//            }
//            userMedicalServiceRepository.save(userDetailsServicesData);
//
//            List<Branch> docVisitBranches = branchRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedVisitBranches()));
//            List<UserVisitBranches> docVisitBranchesData = new ArrayList<>();
//            for (Branch userVisitBr : docVisitBranches) {
//                UserVisitBranches docVisitBranch = new UserVisitBranches();
//                docVisitBranch.setBranch(userVisitBr);
//                docVisitBranch.setUser(user);
//            }
//            userVisitBranchesRepository.save(docVisitBranchesData);
//
//            List<ClinicalDepartment> clinicalDepartments = clinicalDepartmentRepository.findAllByIdIn(Arrays.asList(createRequest.getSelectedDepartment()));
//            List<DepartmentUser> docDepartmentUser = new ArrayList<>();
//            for (ClinicalDepartment clinicalDepartment : clinicalDepartments) {
//                DepartmentUser departmentUser = new DepartmentUser();
//                departmentUser.setClinicalDepartment(clinicalDepartment);
//                departmentUser.setUser(user);
//                departmentUser.setCreatedOn(System.currentTimeMillis());
//                departmentUser.setUpdatedOn(System.currentTimeMillis());
//                departmentUser.setDeleted(false);
//                docDepartmentUser.add(departmentUser);
//            }
//            departmentUserRepository.save(docDepartmentUser);
//
//            Vacation vacation = new Vacation();
//            vacation.setCreatedOn(System.currentTimeMillis());
//            vacation.setUpdatedOn(System.currentTimeMillis());
//            vacation.setDeleted(false);
//            vacation.setStartDate(HISCoreUtil.convertDateToMilliSeconds(createRequest.getDateFrom()));
//            vacation.setEndDate(HISCoreUtil.convertDateToMilliSeconds(createRequest.getDateTo()));
//            vacation.setStatus(createRequest.isVacation());
//            vacation.setVacation(createRequest.isVacation());
//            vacation.setUser(user);
//            vacationRepository.save(vacation);
//
//            userDutyShift.setUser(user);
//            userDutyShift.setDutyShift(dutyShift);
//
//            branchUser.setPrimaryBranch(true);
//            branchUser.setBillingBranch(true);
//            branchUser.setPrimaryDr(true);
//            branchUser.setUser(user);
//            branchUser.setBranch(branch);
//            branchUser.setPrimaryBranch(true);
//
//            userRole.setRole(roleFindByName(createRequest.getUserType().toUpperCase()));
//            userRole.setUser(user);
//
//            userRoleRepository.save(userRole);
//
//            branchUserRepository.save(branchUser);
//
//            userDutyShiftRepository.save(userDutyShift);
//            return user;
//        }
//        return null;
//    }
//
//
//    private Role roleFindByName(String name) {
//        return roleRepo.findByName(name);
//    }
//
//    public List<UserResponseWrapper> findAllUsers(int offset, int limit) {
//
//        Pageable pageable = new PageRequest(offset, limit);
//        List<UserResponseWrapper> users = new ArrayList<>();
//        BranchWrapper branches = null;
//        List<User> allUser = userRepository.findAllUsers(pageable);
//        for (User user : allUser) {
//            UserResponseWrapper userWrapper = new UserResponseWrapper(user);
//            for (BranchUser branchUser : user.getBranches()) {
//                if (branchUser.isPrimaryBranch()) {
//                    BranchWrapper branchWrapper = new BranchWrapper(branchUser);
//                    branches = branchWrapper;
//                }
//            }
//            userWrapper.setBranch(branches);
//            users.add(userWrapper);
//        }
//        return users;
//    }
//
//    public int totalUser() {
//        return userRepository.countAllByActiveTrue();
//    }
//
//    public User updateUser(UserCreateRequest userCreateRequest, User alreadyExistsUser) {
//        String userType = userCreateRequest.getUserType();
//        Branch primaryBranch = branchRepository.findByName(userCreateRequest.getPrimaryBranch());
//        BranchUser branchUser = branchUserRepository.findByUser(alreadyExistsUser);
//        int primmaryBranchId = Integer.parseInt(userCreateRequest.getPrimaryBranch());
//        if (userType.equalsIgnoreCase(UserTypeEnum.CASHIER.toString())) {
//            alreadyExistsUser.setUsername(userCreateRequest.getUserName());
//            alreadyExistsUser.setActive(userCreateRequest.isActive());
//            alreadyExistsUser.setEmail(userCreateRequest.getEmail());
//            Profile updateProfile = alreadyExistsUser.getProfile();
//            updateProfile.setType(userCreateRequest.getUserType());
//            updateProfile.setLastName(userCreateRequest.getLastName());
//            updateProfile.setFirstName(userCreateRequest.getFirstName());
//            updateProfile.setCellPhone(userCreateRequest.getCellPhone());
//            updateProfile.setHomePhone(userCreateRequest.getHomePhone());
//            updateProfile.setSendBillingReport(userCreateRequest.isSendBillingReport());
//            updateProfile.setUseReceptDashBoard(userCreateRequest.isUseReceptDashboard());
//            updateProfile.setOtherDoctorDashBoard(userCreateRequest.isOtherDoctorDashBoard());
//            updateProfile.setAccountExpiry(userCreateRequest.getAccountExpiry());
//            updateProfile.setActive(userCreateRequest.isActive());
//            updateProfile.setAllowDiscount(userCreateRequest.getAllowDiscount());
//            updateProfile.setOtherDashboard(userCreateRequest.getOtherDashboard());
//            updateProfile.setUpdatedOn(System.currentTimeMillis());
//            alreadyExistsUser.setProfile(updateProfile);
//            List<UserVisitBranches> cashierVisitBranchesData = new ArrayList<>();
//            List<Branch> cashVisitBranches = branchRepository.findAllByIdIn(Arrays.asList(userCreateRequest.getSelectedVisitBranches()));
//            if (!HISCoreUtil.isListEmpty(cashVisitBranches)) {
//                userVisitBranchesRepository.deleteByUser(alreadyExistsUser);
//            }
//            for (Branch userVisitBr : cashVisitBranches) {
//                UserVisitBranches userVisitBranches = new UserVisitBranches();
//                userVisitBranches.setBranch(userVisitBr);
//                userVisitBranches.setUser(alreadyExistsUser);
//                cashierVisitBranchesData.add(userVisitBranches);
//            }
//            branchUser.setBranch(primaryBranch);
//            branchUserRepository.save(branchUser);
//            userVisitBranchesRepository.save(cashierVisitBranchesData);
//            userRepository.save(alreadyExistsUser);
//            return alreadyExistsUser;
//        }
//
//        if (userType.equalsIgnoreCase(UserTypeEnum.RECEPTIONIST.toString())) {
//            alreadyExistsUser.setUsername(userCreateRequest.getUserName());
//            alreadyExistsUser.setActive(userCreateRequest.isActive());
//            alreadyExistsUser.setEmail(userCreateRequest.getEmail());
//
//            Profile updateProfile = alreadyExistsUser.getProfile();
//            updateProfile.setCellPhone(userCreateRequest.getCellPhone());
//            updateProfile.setFirstName(userCreateRequest.getFirstName());
//            updateProfile.setLastName(userCreateRequest.getLastName());
//            updateProfile.setType(userCreateRequest.getUserType());
//            updateProfile.setHomePhone(userCreateRequest.getHomePhone());
//            updateProfile.setSendBillingReport(userCreateRequest.isSendBillingReport());
//            updateProfile.setUseReceptDashBoard(userCreateRequest.isUseReceptDashboard());
//            updateProfile.setOtherDoctorDashBoard(userCreateRequest.isOtherDoctorDashBoard());
//            updateProfile.setOtherDashboard(userCreateRequest.getOtherDashboard());
//            updateProfile.setAccountExpiry(userCreateRequest.getAccountExpiry());
//            updateProfile.setActive(userCreateRequest.isActive());
//            updateProfile.setAllowDiscount(userCreateRequest.getAllowDiscount());
//            updateProfile.setUpdatedOn(System.currentTimeMillis());
//            alreadyExistsUser.setProfile(updateProfile);
//
//            List<Branch> recepVisitBranchesReceptionist = branchRepository.findAllByIdIn(Arrays.asList(userCreateRequest.getSelectedVisitBranches()));
//            List<UserVisitBranches> recepVisitBranchesData = new ArrayList<>();
//            if (!HISCoreUtil.isListEmpty(recepVisitBranchesReceptionist)) {
//                userVisitBranchesRepository.deleteByUser(alreadyExistsUser);
//            }
//            for (Branch userVisitBr : recepVisitBranchesReceptionist) {
//                UserVisitBranches recepVisitBranches = new UserVisitBranches();
//                recepVisitBranches.setBranch(userVisitBr);
//                recepVisitBranches.setUser(alreadyExistsUser);
//            }
//            branchUser.setBranch(primaryBranch);
//            branchUserRepository.save(branchUser);
//            userVisitBranchesRepository.save(recepVisitBranchesData);
//            userRepository.saveAndFlush(alreadyExistsUser);
//            return alreadyExistsUser;
//        }
//
//        if (userType.equalsIgnoreCase(UserTypeEnum.NURSE.toString())) {
//            alreadyExistsUser.setUsername(userCreateRequest.getUserName());
//            alreadyExistsUser.setActive(userCreateRequest.isActive());
//            alreadyExistsUser.setEmail(userCreateRequest.getEmail());
//
//            Profile updateProfile = alreadyExistsUser.getProfile();
//
//            updateProfile.setCellPhone(userCreateRequest.getCellPhone());
//            updateProfile.setFirstName(userCreateRequest.getFirstName());
//            updateProfile.setLastName(userCreateRequest.getLastName());
//            updateProfile.setHomePhone(userCreateRequest.getHomePhone());
//            updateProfile.setSendBillingReport(userCreateRequest.isSendBillingReport());
//            updateProfile.setUseReceptDashBoard(userCreateRequest.isUseReceptDashboard());
//            updateProfile.setOtherDoctorDashBoard(userCreateRequest.isOtherDoctorDashBoard());
//            updateProfile.setAccountExpiry(userCreateRequest.getAccountExpiry());
//            updateProfile.setActive(userCreateRequest.isActive());
//            updateProfile.setUpdatedOn(System.currentTimeMillis());
//            updateProfile.setManagePatientRecords(userCreateRequest.isManagePatientRecords());
//            updateProfile.setManagePatientInvoices(userCreateRequest.isManagePatientInvoices());
//            updateProfile.setOtherDashboard(userCreateRequest.getOtherDashboard());
//
//            alreadyExistsUser.setProfile(updateProfile);
//            branchUser.setBranch(primaryBranch);
//            branchUserRepository.save(branchUser);
//            userRepository.save(alreadyExistsUser);
//            List<ClinicalDepartment> clinicalDepartments = clinicalDepartmentRepository.findAllByIdIn(Arrays.asList(userCreateRequest.getSelectedDepartment()));
//            List<DepartmentUser> departmentUserListData = new ArrayList<>();
//            if (!HISCoreUtil.isListEmpty(clinicalDepartments)) {
//                departmentUserRepository.deleteByUser(alreadyExistsUser);
//            }
//            for (ClinicalDepartment clinicalDepartment : clinicalDepartments) {
//                DepartmentUser departmentUser = new DepartmentUser();
//                departmentUser.setClinicalDepartment(clinicalDepartment);
//                departmentUser.setUser(alreadyExistsUser);
//                departmentUser.setCreatedOn(System.currentTimeMillis());
//                departmentUser.setUpdatedOn(System.currentTimeMillis());
//                departmentUser.setDeleted(false);
//                departmentUserListData.add(departmentUser);
//            }
//            departmentUserRepository.save(departmentUserListData);
//
//            List<Branch> userVisitBranches = branchRepository.findAllByIdIn(Arrays.asList(userCreateRequest.getSelectedVisitBranches()));
//            List<UserVisitBranches> userVisitBranchesData = new ArrayList<>();
//            if (!HISCoreUtil.isListEmpty(userVisitBranches)) {
//                userVisitBranchesRepository.deleteByUser(alreadyExistsUser);
//            }
//            for (Branch userVisitBr : userVisitBranches) {
//                UserVisitBranches userVisitBranches1 = new UserVisitBranches();
//                userVisitBranches1.setBranch(userVisitBr);
//                userVisitBranches1.setUser(alreadyExistsUser);
//                userVisitBranchesData.add(userVisitBranches1);
//            }
//            userVisitBranchesRepository.save(userVisitBranchesData);
//
//            List<User> doctors = userRepository.findAllByIdIn(Arrays.asList(userCreateRequest.getDutyWithDoctors()));
//            List<DutyWithDoctor> listOFData = new ArrayList<>();
//            if (!HISCoreUtil.isListEmpty(doctors)) {
//                dutyWithDoctorRepository.deleteByNurse(alreadyExistsUser);
//            }
//            for (User docUser : doctors) {
//                DutyWithDoctor dutyWithDoctor1 = new DutyWithDoctor();
//                dutyWithDoctor1.setNurse(alreadyExistsUser);
//                dutyWithDoctor1.setDoctor(docUser);
//                listOFData.add(dutyWithDoctor1);
//            }
//            dutyWithDoctorRepository.save(listOFData);
//
//
//            return alreadyExistsUser;
//        }
//
//        if (userType.equalsIgnoreCase(UserTypeEnum.DOCTOR.toString())) {
//            Vacation vacation = vacationRepository.findByUser(alreadyExistsUser);
//            alreadyExistsUser.setUsername(userCreateRequest.getUserName());
//            alreadyExistsUser.setActive(userCreateRequest.isActive());
//            alreadyExistsUser.setEmail(userCreateRequest.getEmail());
//
//            Profile updateProfile = alreadyExistsUser.getProfile();
//
//            updateProfile.setCellPhone(userCreateRequest.getCellPhone());
//            updateProfile.setHomePhone(userCreateRequest.getHomePhone());
//            updateProfile.setSendBillingReport(userCreateRequest.isSendBillingReport());
//            updateProfile.setUseReceptDashBoard(userCreateRequest.isUseReceptDashboard());
//            updateProfile.setOtherDoctorDashBoard(userCreateRequest.isOtherDoctorDashBoard());
//            updateProfile.setAccountExpiry(userCreateRequest.getAccountExpiry());
//            updateProfile.setFirstName(userCreateRequest.getFirstName());
//            updateProfile.setLastName(userCreateRequest.getLastName());
//            updateProfile.setActive(userCreateRequest.isActive());
//            updateProfile.setUpdatedOn(System.currentTimeMillis());
//            updateProfile.setOtherDashboard(userCreateRequest.getOtherDashboard());
//            List<String> daysList = new ArrayList<>(Arrays.asList(userCreateRequest.getSelectedWorkingDays()));
//            updateProfile.setWorkingDays(daysList);
//            branchUser.setBranch(primaryBranch);
//            branchUserRepository.save(branchUser);
//            alreadyExistsUser.setProfile(updateProfile);
//            userRepository.save(alreadyExistsUser);
//            vacation.setVacation(userCreateRequest.isVacation());
//            vacation.setStatus(userCreateRequest.isVacation());
//            vacation.setStartDate(HISCoreUtil.convertDateToMilliSeconds(userCreateRequest.getDateFrom()));
//            vacation.setEndDate(HISCoreUtil.convertDateToMilliSeconds(userCreateRequest.getDateTo()));
//
//            vacationRepository.saveAndFlush(vacation);
//
//            UserDutyShift userDutyShift = userDutyShiftRepository.findByUser(alreadyExistsUser);
//            DutyShift dutyShift = userDutyShift.getDutyShift();
//            dutyShift.setEndTimeShift2(userCreateRequest.getSecondShiftToTime());
//            dutyShift.setStartTimeShift2(userCreateRequest.getSecondShiftFromTime());
//            dutyShift.setEndTimeShift1(userCreateRequest.getFirstShiftToTime());
//            dutyShift.setStartTimeShift1(userCreateRequest.getFirstShiftFromTime());
//            dutyShiftRepository.save(dutyShift);
//
//
//            List<MedicalService> medicalServiceslist = medicalServicesRepository.findAllByIdIn(Arrays.asList(userCreateRequest.getSelectedServices()));
//            List<UserMedicalService> userDetailsServicesData = new ArrayList<>();
//            if (!HISCoreUtil.isListEmpty(medicalServiceslist)) {
//                userMedicalServiceRepository.deleteByUser(alreadyExistsUser);
//            }
//            for (MedicalService mdService : medicalServiceslist) {
//                UserMedicalService userMedicalService = new UserMedicalService();
//                userMedicalService.setMedicalService(mdService);
//                userMedicalService.setUser(alreadyExistsUser);
//                userDetailsServicesData.add(userMedicalService);
//            }
//            userMedicalServiceRepository.save(userDetailsServicesData);
//
//            List<Branch> docVisitBranches = branchRepository.findAllByIdIn(Arrays.asList(userCreateRequest.getSelectedVisitBranches()));
//            List<UserVisitBranches> docVisitBranchesData = new ArrayList<>();
//            if (!HISCoreUtil.isListEmpty(docVisitBranches)) {
//                userVisitBranchesRepository.deleteByUser(alreadyExistsUser);
//            }
//            for (Branch userVisitBr : docVisitBranches) {
//                UserVisitBranches docVisitBranch = new UserVisitBranches();
//                docVisitBranch.setBranch(userVisitBr);
//                docVisitBranch.setUser(alreadyExistsUser);
//                docVisitBranchesData.add(docVisitBranch);
//            }
//            userVisitBranchesRepository.save(docVisitBranchesData);
//
//            List<ClinicalDepartment> doctorClinicalDepartments = clinicalDepartmentRepository.findAllByIdIn(Arrays.asList(userCreateRequest.getSelectedDepartment()));
//            List<DepartmentUser> docDepartmentUser = new ArrayList<>();
//            if (!HISCoreUtil.isListEmpty(doctorClinicalDepartments)) {
//                departmentUserRepository.deleteByUser(alreadyExistsUser);
//            }
//            for (ClinicalDepartment clinicalDepartment : doctorClinicalDepartments) {
//                DepartmentUser departmentUser = new DepartmentUser();
//                departmentUser.setClinicalDepartment(clinicalDepartment);
//                departmentUser.setUser(alreadyExistsUser);
//                departmentUser.setCreatedOn(System.currentTimeMillis());
//                departmentUser.setUpdatedOn(System.currentTimeMillis());
//                departmentUser.setDeleted(false);
//                docDepartmentUser.add(departmentUser);
//            }
//            departmentUserRepository.save(docDepartmentUser);
//            return alreadyExistsUser;
//
//        }
//        return null;
//    }
//
//    public UserResponseWrapper findByIdAndResponse(long id) {
//        User user = userRepository.findAllById(id);
//        String userType = user.getUserType();
//        UserResponseWrapper userResponseWrapper = new UserResponseWrapper(user);
//
//        userResponseWrapper.setProfile(user.getProfile());
//        BranchUser branchUser = branchUserRepository.findByUser(user);
//        //  if (branchUser.isPrimaryBranch()) {
//        BranchWrapper branchWrapper = new BranchWrapper(branchUser);
//        userResponseWrapper.setBranch(branchWrapper);
//        //}
//        List<Branch> visitBranchesList = new ArrayList<>();
//        for (UserVisitBranches userVisitBranches : user.getUserVisitBranches()) {
//            visitBranchesList.add(userVisitBranches.getBranch());
//            userResponseWrapper.setVisitBranches(visitBranchesList);
//        }
//
//        if (userType.equalsIgnoreCase("doctor")) {
//            UserDutyShift userDutyShift = userDutyShiftRepository.findByUser(user);
//            Vacation vacation = vacationRepository.findByUser(user);
//            userResponseWrapper.setDutyShift(userDutyShift.getDutyShift());
//            userResponseWrapper.setVacation(vacation);
//            List<ClinicalDepartment> clinicalDepartmentList = new ArrayList<>();
//            for (DepartmentUser departmentUser : user.getDepartments()) {
//                clinicalDepartmentList.add(departmentUser.getClinicalDepartment());
//                userResponseWrapper.setClinicalDepartments(clinicalDepartmentList);
//            }
//
//        }
//        if (userType.equalsIgnoreCase("nurse")) {
//            List<ClinicalDepartment> nurseDepartList = new ArrayList<>();
//            List<DutyWithDoctor> nurseDutyWithDoctorsList = new ArrayList<>();
//            for (DepartmentUser nurseDeptUser : user.getDepartments()) {
//                nurseDepartList.add(nurseDeptUser.getClinicalDepartment());
//                userResponseWrapper.setClinicalDepartments(nurseDepartList);
//            }
//            for (DutyWithDoctor nurseDutyWithDoctor : user.getDoctor()) {
//                nurseDutyWithDoctorsList.add(nurseDutyWithDoctor);
//                userResponseWrapper.setDutyWithDoctors(nurseDutyWithDoctorsList);
//            }
//        }
//
//        return userResponseWrapper;
//    }
//
//
//    public User findById(long id) {
//        return userRepository.findAllById(id);
//    }
//
//    public User findUserById(long id) {
//        return userRepository.findById(id);
//    }
//
//    public User deleteUser(User user) {
//      //  user.setDeleted(true);
//        return userRepository.save(user);
//    }
//
//    public List<UserWrapper> searchByNameOrEmailOrRole(String name, String email, String role, int offset, int limit) {
//        Pageable pageable = new PageRequest(offset, limit);
//        logger.info("role assigned" + role);
//        Role Assignedrole = roleRepo.findByName(role.toUpperCase());
//        List<User> alluser = userRepository.findAllByUsernameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrRoles_role_nameIgnoreCaseContaining(name, email, role, pageable);
//        List<UserWrapper> userWrapper = new ArrayList<>();
//
//        for (User user : alluser) {
//            UserWrapper userWrapper1 = new UserWrapper(user);
//            userWrapper.add(userWrapper1);
//        }
//        return userWrapper;
//    }
//
    public List<UserWrapper> findByRole(String role) {
        List<UserWrapper> userWrapper = new ArrayList<>();
         List<User> userList = userRepository.findAll();
        for (User  user : userList){
            UserWrapper userWrapper1 = new UserWrapper(user);
            userWrapper.add(userWrapper1);
        }

        return userWrapper;
    }
//
//    public AdminDashboardDataResponseWrapper buildAdminDashboardData() {
//        AdminDashboardDataResponseWrapper adminData = new AdminDashboardDataResponseWrapper();
//
//        //#TODO pass type from UserTypeEnum
//        List<User> patients = userRepository.findAllByRoles_role_name("PATIENT");
//        List<MedicalService> medicalServices = medicalServicesRepository.findAllByDeletedFalse();
//        List<ICDCode> icdCodes = icdCodeRepository.findAllByDeletedFalse();
//
//        adminData.setPatientCount(patients.size());
//        adminData.setAppointmentsCount(appointmentRepository.countAllByActiveTrue());
//        adminData.setMedicalServicesCount(medicalServices.size());
//        adminData.setIcdsCount(icdCodes.size());
//
//        return adminData;
//    }
//
//
//    public List<PatientWrapper> findAllPaginatedUserByUserType(int offset, int limit, String userType) {
//        Pageable pageable = new PageRequest(offset, limit);
//        return userRepository.findAll(pageable, userType);
//    }
//
//    public List<PatientWrapper> searchAllPaginatedUserByUserTypeAndName(int offset, int limit, String userType, String userName) {
//        Pageable pageable = new PageRequest(offset, limit);
//        return userRepository.findAll(pageable, userType, userName);
//    }
//
//    public int countSearchAllPaginatedUserByUserTypeAndName(String userType, String userName) {
//        return userRepository.findAll(userType, userName).size();
//    }
//
//    public int countAllPaginatedPatients(String userType) {
//        return userRepository.findAll(userType).size();
//    }
//
//    public List<PatientWrapper> findAllPatients() {
//        return userRepository.findAll(UserTypeEnum.PATIENT.getValue());
//    }
//
//    public void deletePatientById(long patientId) {
//        User user = this.userRepository.findOne(patientId);
//        if (user != null) {
//           // user.setDeleted(true);
//            user.getProfile().setDeleted(true);
//            user.getProfile().setUpdatedOn(System.currentTimeMillis());
//
//            user.getInsurance().setUpdated(System.currentTimeMillis());
//            user.getInsurance().setDeleted(true);
//            this.userRepository.save(user);
//        }
//    }
//
//    public String savePatient(PatientRequest patientRequest) throws ParseException, Exception {
//        Profile profile = new Profile(patientRequest);
//        UserRole userRole;
//        User selectedDoctor = this.userRepository.findOne(patientRequest.getSelectedDoctor());
//        Insurance insurance = new Insurance(patientRequest);
//        User patient = new User(patientRequest, UserTypeEnum.PATIENT.toString());
//        patient.setPrimaryDoctor(selectedDoctor);
//
//        this.profileRepository.save(profile);
//        this.insuranceRepository.save(insurance);
//        patient.setProfile(profile);
//        patient.setInsurance(insurance);
//        this.userRepository.save(patient);
//        userRole = new UserRole(patient, roleRepo.findByName(UserTypeEnum.PATIENT.getValue()));
//        userRoleRepository.save(userRole);
//
//        /// now saving images against user id
//
//        ///profile photo save
//        String url = null;
//        if (patientRequest.getProfileImg() != null) {
//            url = this.saveImage(patientRequest.getProfileImg(),
//                    HISConstants.S3_USER_PROFILE_DIRECTORY_PATH,
//                    patient.getId()
//                            + "_"
//                            + patient.getInsurance().getId()
//                            + "_"
//                            + HISConstants.S3_USER_PROFILE_THUMBNAIL_GRAPHIC_NAME,
//                    patient.getId()
//                            + "_"
//                            + patient.getInsurance().getId()
//                            + "_"
//                            + HISConstants.S3_USER_PROFILE_GRAPHIC_NAME,
//                    "/"
//                            + HISConstants.S3_USER_PROFILE_DIRECTORY_PATH
//                            + patient.getId()
//                            + "_"
//                            + patient.getInsurance().getId()
//                            + "_"
//                            + HISConstants.S3_USER_PROFILE_THUMBNAIL_GRAPHIC_NAME);
//        }
//
//
//        if (HISCoreUtil.isValidObject(url)) {
//            patient.getProfile().setProfileImgURL(url);
//            this.userRepository.save(patient);
//            url = null;
//        }
//        ///profile photo save
//
//        ///front photo save
//
//        if (patientRequest.getPhotoFront() != null) {
//            url = this.saveImage(patientRequest.getPhotoFront(),
//                    HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH,
//                    patient.getId()
//                            + "_"
//                            + patient.getInsurance().getId()
//                            + "_"
//                            + HISConstants.S3_USER_INSURANCE_FRONT_PHOTO_THUMBNAIL_GRAPHIC_NAME,
//                    patient.getId()
//                            + "_"
//                            + patient.getInsurance().getId()
//                            + "_"
//                            + HISConstants.S3_USER_INSURANCE_FRONT_PHOTO_GRAPHIC_NAME,
//                    "/"
//                            + HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH
//                            + patient.getId()
//                            + "_"
//                            + patient.getInsurance().getId()
//                            + "_"
//                            + HISConstants.S3_USER_INSURANCE_FRONT_PHOTO_THUMBNAIL_GRAPHIC_NAME);
//
//        }
//        if (HISCoreUtil.isValidObject(url)) {
//            patient.getInsurance().setPhotoFrontURL(url);
//            this.userRepository.save(patient);
//            url = null;
//        }
//        ///back photo save
//        if (patientRequest.getPhotoBack() != null) {
//            url = this.saveImage(patientRequest.getPhotoBack(),
//                    HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH,
//                    patient.getId()
//                            + "_"
//                            + patient.getInsurance().getId()
//                            + "_"
//                            + HISConstants.S3_USER_INSURANCE_BACK_PHOTO_THUMBNAIL_GRAPHIC_NAME,
//                    patient.getId()
//                            + "_"
//                            + patient.getInsurance().getId()
//                            + "_"
//                            + HISConstants.S3_USER_INSURANCE_BACK_PHOTO_GRAPHIC_NAME,
//                    "/"
//                            + HISConstants.S3_USER_INSURANCE_DIRECTORY_PATH
//                            + patient.getId()
//                            + "_"
//                            + patient.getInsurance().getId()
//                            + "_"
//                            + HISConstants.S3_USER_INSURANCE_BACK_PHOTO_THUMBNAIL_GRAPHIC_NAME);
//        }
//
//        if (HISCoreUtil.isValidObject(url)) {
//            patient.getInsurance().setPhotoBackURL(url);
//            this.userRepository.save(patient);
//            url = null;
//        }
//
//        return patient.getId() + "";
//
//    }
//
//    public String saveImage(byte[] byteArary,
//                            String directoryPath,
//                            String fullThumbName,
//                            String fullImgName,
//                            String fullPathAndThumbNailGraphicName) throws Exception {
//
//        String imgURL = null;
//
////        byte[] byteArr = Files.readAllBytes(path);
//        InputStream is = new ByteArrayInputStream(byteArary);
//        boolean isSaved = false;
//        isSaved = awsService.uploadImageByUserId(is,
//                directoryPath,
//                fullThumbName,
//                fullImgName);
//        if (isSaved) {
//            imgURL = this.s3KeyGen.getImagePublicURL(fullPathAndThumbNailGraphicName, false);
//        }
//
//        return imgURL;
//    }
//
//    public boolean isUserNameAlreadyExists(String userName) {
//        List<User> users = this.userRepository.findAllByUsername(userName);
//        if (!HISCoreUtil.isListEmpty(users))
//            return true;
//        return false;
//    }
//
//    public boolean isUserNameAlreadyExistsAgainstUserId(long id, String userName) {
//        List<User> users = this.userRepository.findAllByIdNotAndUsername(id, userName);
//        if (!HISCoreUtil.isListEmpty(users))
//            return true;
//        return false;
//    }
//
//    public boolean isEmailAlreadyExists(String email) {
//        List<User> users = this.userRepository.findAllByEmail(email);
//        if (!HISCoreUtil.isListEmpty(users))
//            return true;
//        return false;
//    }
//
//    public boolean isEmailAlreadyExistsAgainstUserId(long id, String email) {
//        List<User> users = this.userRepository.findAllByIdNotAndEmail(id, email);
//        if (!HISCoreUtil.isListEmpty(users))
//            return true;
//        return false;
//    }
//
//    public boolean isUserAlreadyExists(String userName, String email) {
//        List<User> users = this.userRepository.findAllByUsernameOrEmail(userName, email);
//        if (!HISCoreUtil.isListEmpty(users))
//            return true;
//        return false;
//    }
//
//    public boolean isUserAlreadyExistsAgainstUserId(long id, String userName, String email) {
//        List<User> users = this.userRepository.findAllByIdNotAndUsernameOrEmail(id, userName, email);
//        if (!HISCoreUtil.isListEmpty(users))
//            return true;
//        return false;
//    }
//
//    public PatientRequest getUserByUserTypeAndId(long id) {
//        return this.userRepository.findUserById(id);
//    }
//
//    public void updatePatient(PatientRequest patientRequest) throws ParseException, Exception {
//        Profile profile = this.profileRepository.findOne(patientRequest.getProfileId());
//        UserRole userRole;
//        new Profile(profile, patientRequest);
//
//        Insurance insurance = this.insuranceRepository.findOne(patientRequest.getInsuranceId());
//        new Insurance(insurance, patientRequest);
//
//        User patient = this.userRepository.findOne(patientRequest.getUserId());
//        new User(patient, patientRequest);
//
//        User selectedDoctor = this.userRepository.findOne(patientRequest.getSelectedDoctor());
//        patient.setPrimaryDoctor(selectedDoctor);
//
//        this.profileRepository.save(profile);
//        this.insuranceRepository.save(insurance);
//
//        patient.setProfile(profile);
//        patient.setInsurance(insurance);
//
//        this.userRepository.save(patient);
//        userRole = new UserRole(patient, roleRepo.findByName(UserTypeEnum.PATIENT.getValue()));
//        userRoleRepository.save(userRole);
//    }
//
//    public User updateUser(User user) {
//        return userRepository.save(user);
//    }
}
