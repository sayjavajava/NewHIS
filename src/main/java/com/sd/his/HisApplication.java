package com.sd.his;

import com.sd.his.enums.GenderTypeEnum;
import com.sd.his.enums.ModuleEnum;
import com.sd.his.enums.UserTypeEnum;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/*import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;*/

/**
 * @author Irfan Nasim
 * @description To create JAR Packaging
 * @since 05-Jun-2018
 */
@SpringBootApplication
@EnableJpaAuditing
public class HisApplication {
    private final Logger logger = LoggerFactory.getLogger(HisApplication.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private S3BucketRepository bucketRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BranchDoctorRepository branchDoctorRepository;
    @Autowired
    private RoomRepository roomRepository;

    public static void main(String[] args) {
        SpringApplication.run(HisApplication.class, args);
    }

/*    @EventListener
    @Transactional(rollbackOn = Throwable.class)
    public void onBootStartup(ApplicationContextEvent event) {


        if(organizationRepository.findOne(1L) == null) {
            Organization organization = new Organization("SolutionDots Hospital", 30L, "Asia/Karachi", 15L, "+96645484654", "+964547854", "+456498465", "https://solutiondots.com/", "General", "imran@solutiondots.net");
            organization.setBucketList(Arrays.asList(new S3Bucket("hisdev", "development bucket", "AKIAJGSNPR3WX7C3EVMA", "4enduKPgokQP43xA9B1Qc/Vrymtai9X9M6AMqfcD", "https://", "s3.amazonaws.com", true, true, organization)));
            List<Prefix> prefixes = new ArrayList<>();
            prefixes.add(new Prefix("P", ModuleEnum.PROFILE, 10000L, 10003L, organization));
            prefixes.add(new Prefix("APT", ModuleEnum.APPOINTMENT, 10000L, 10000L, organization));
            prefixes.add(new Prefix("PAT", ModuleEnum.PATIENT, 10000L, 10000L, organization));
            organization.setPrefixList(prefixes);


            List<Permission> permissions = new ArrayList<>();
            permissions.add(new Permission("Create Patient", "to create patient", "/patient", true));
            permissions.add(new Permission("Version", "fasdfa", "/asdfasdf", true));


            permissionRepository.save(permissions);

            List<Role> roles = new ArrayList<>();
            roles.add(new Role(UserTypeEnum.MANAGER.name(), "manager role", true));
            roles.add(new Role(UserTypeEnum.DOCTOR.name(), "doctor role", true));
            roles.add(new Role(UserTypeEnum.NURSE.name(), "nurse role", true));
            roles.add(new Role(UserTypeEnum.RECEPTIONIST.name(), "nurse role", true));
            roles.add(new Role(UserTypeEnum.CASHIER.name(), "cashier role", true));
            roleRepository.save(roles);

            List<RolePermission> rolePermissions = new ArrayList<>();
            rolePermissions.add(new RolePermission(roles.get(0), permissions.get(0), true, true, true));
            rolePermissions.add(new RolePermission(roles.get(0), permissions.get(1), true, true, true));
            rolePermissions.add(new RolePermission(roles.get(1), permissions.get(0), true, true, true));
            rolePermissions.add(new RolePermission(roles.get(2), permissions.get(1), true, true, true));

            rolePermissionRepository.save(rolePermissions);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User user1 = new User("admin", UserTypeEnum.MANAGER, encoder.encode("admin"), true);
            User user2 = new User("doctor", UserTypeEnum.DOCTOR, encoder.encode("doctor"), true);
            userRepository.save(Arrays.asList(user1, user2));

            userRoleRepository.save(Arrays.asList(new UserRole(user1, roles.get(0)), new UserRole(user2, roles.get(1))));

            Manager manager = new Manager();
            manager.setDob(new Date());
            manager.setFirstName("Manager");
            manager.setGender(GenderTypeEnum.MALE);
            manager.setProfileId("P-10001");
            manager.setUser(user1);
            managerRepository.save(manager);

            Doctor doctor = new Doctor();
            doctor.setDob(new Date());
            doctor.setFirstName("Doctor");
            doctor.setGender(GenderTypeEnum.MALE);
            doctor.setProfileId("P-10002");
            doctor.setUser(user2);
            doctorRepository.save(doctor);



            Branch primaryBranch = new Branch("DHA Branch", 1L, "DHA Branch", "DHA Branch", "Lahore", "Pakistan", "H Block", "+9245786468", "+9284657867", "Punjab", new Date(), new Date(), "TX0564512387", true, true, 1345464797, true, true, organization);
            branchRepository.save(primaryBranch);
            roomRepository.save(new Room("Room1",primaryBranch,true,true));
            branchDoctorRepository.save(new BranchDoctor(doctor, primaryBranch, true));
            organizationRepository.saveAndFlush(organization);
        }
    }*/
}

/**
 * @author Irfan Nasim
 * @description To create WAR Packaging
 * @since 05-Jun-2018
 */
/*@SpringBootApplication
  @EnableJpaAuditing
public class HisApplication extends SpringBootServletInitializer {
    private final Logger logger = LoggerFactory.getLogger(HisApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HisApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HisApplication.class);
    }
}*/
