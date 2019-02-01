package com.sd.his;

import com.sd.his.enums.*;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.service.HISUtilService;
import com.sd.his.service.PrefixServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author Irfan Nasim
 * @description To create JAR Packaging
 * @since 05-Jun-2018*/



//
@SpringBootApplication
@EnableJpaAuditing
public class HisApplication {
    private final Logger logger = LoggerFactory.getLogger(HisApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HisApplication.class, args);
    }

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
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private MedicalServiceRepository medicalServiceRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private DoctorMedicalServiceRepository doctorMedicalServiceRepository;
    @Autowired
    private HISUtilService hisUtilService;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private PatientGroupRepository patientGroupRepository;

    @EventListener
    @Transactional(rollbackOn = Throwable.class)
    public void onBootStartup(ApplicationContextEvent event) {


        if (organizationRepository.findOne(1L) == null) {
            Organization organization = new Organization("SolutionDots Hospital", "0421231221", "https://solutiondots.com/",  "riyadh@solutiondots.com", "0421231221", "Gulberg 3 ",true);
            organization.setBucketList(Arrays.asList(new S3Bucket("hisdev", "development bucket", "AKIAJGSNPR3WX7C3EVMA", "4enduKPgokQP43xA9B1Qc/Vrymtai9X9M6AMqfcD", "https://", "s3.amazonaws.com", true, true, organization)));
            organization.setDateFormat(DateFormatEnum.yyyyMMdd.toString());
            organization.setDateFormat(TimeFormatEnum.HHmmss.toString());
//            organization.setCountry(countryRepository.findByCode("PAK"));
//            organization.setCountry(countryRepository.findOne(1L));
            List<Prefix> prefixes = new ArrayList<>();
            prefixes.add(new Prefix("GLR", ModuleEnum.GENERAL_LEDGER.name(), 10000L, 10001L, organization));
            prefixes.add(new Prefix("EMP", ModuleEnum.PROFILE.name(), 10000L, 10003L, organization));
            prefixes.add(new Prefix("APT", ModuleEnum.APPOINTMENT.name(), 10000L, 10001L, organization));
            prefixes.add(new Prefix("PAT", ModuleEnum.PATIENT.name(), 10000L, 10001L, organization));
            prefixes.add(new Prefix("DPT", ModuleEnum.DEPARTMENT.name(), 10000L, 10002L, organization));
            prefixes.add(new Prefix("BRH", ModuleEnum.BRANCH.name(), 10000L, 10002L, organization));
            prefixes.add(new Prefix("TAX", ModuleEnum.TAX.name(), 10000L, 10002L, organization));
            prefixes.add(new Prefix("INV", ModuleEnum.INVOICE.name(), 10000L, 10001L, organization));
            prefixes.add(new Prefix("REF", ModuleEnum.REFUND.name(), 10000L, 10001L, organization));
            prefixes.add(new Prefix("REC", ModuleEnum.PAYMENT.name(), 10000L, 10001L, organization));
            prefixes.add(new Prefix("DRG", ModuleEnum.DRUG.name(), 10000L, 10001L, organization));
            prefixes.add(new Prefix("PAY", ModuleEnum.STAFF_PAYMENT.name(), 10000L, 10001L, organization));
            prefixes.add(new Prefix("LAB", ModuleEnum.LAB_TEST.name(), 10000L, 10001L, organization));
            organization.setPrefixList(prefixes);

            List<Permission> permissions = new ArrayList<>();

            permissions.add(new Permission("Appointment", "Appointment", "/dashboard/appointment/manage", true));
            permissions.add(new Permission("Associate Code Version", "Associate Code Version", "/dashboard/setting/codeVersion", true));
            permissions.add(new Permission("Branch", "Branch", "/dashboard/setting/branch", true));
            permissions.add(new Permission("Code", "Code", "/dashboard/setting/code", true));
            permissions.add(new Permission("Department", "Department", "/dashboard/setting/department", true));
            permissions.add(new Permission("Email Template", "Email Template", "/dashboard/setting/email-template", true));
            permissions.add(new Permission("Invoices", "Invoices", "dashboard/invoice", true));
            permissions.add(new Permission("Medical Services", "Medical Services", "/dashboard/setting/medicalServices", true));
            permissions.add(new Permission("Open/Close Day", "Open/Close Day", "dashboard/ocDay", true));
            permissions.add(new Permission("patient", "patient", "/dashboard/patient/manage", true));
            permissions.add(new Permission("Payments", "Payments", "/dashboard/payment", true));
            permissions.add(new Permission("Refunds", "Refunds", "/dashboard/refund", true));
            permissions.add(new Permission("Reports", "Reports", "/dashboard/report", true));
            permissions.add(new Permission("Roles & Permissions", "Roles & Permissions", "/dashboard/setting/role-permissions", true));
            permissions.add(new Permission("Service Tax", "Service Tax", "/dashboard/setting/service-tax", true));
            permissions.add(new Permission("Staff", "Staff", "/dashboard/setting/staff", true));
            permissions.add(new Permission("Today Appointment", "Today Appointment", "/dashboard/todayAppointment", true));
            permissions.add(new Permission("Version", "version", "/dashboard/setting/version", true));

            permissionRepository.save(permissions);

            List<Role> roles = new ArrayList<>();
            roles.add(new Role(UserTypeEnum.ADMIN.name(), "admin role", true));
            roles.add(new Role(UserTypeEnum.DOCTOR.name(), "doctor role", true));
            roles.add(new Role(UserTypeEnum.NURSE.name(), "nurse role", true));
            roles.add(new Role(UserTypeEnum.RECEPTIONIST.name(), "receptionist role", true));
            roles.add(new Role(UserTypeEnum.CASHIER.name(), "cashier role", true));
            roleRepository.save(roles);

            List<RolePermission> rolePermissions = new ArrayList<>();
            for (Permission permission : permissions) {
                rolePermissions.add(new RolePermission(roles.get(0), permission, true, true, true));
            }

            rolePermissionRepository.save(rolePermissions);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

          //String deptId = hisUtilService.generatePrefix(ModuleEnum.DEPARTMENT);
            Department department = new Department("ENT","DPT10001", "EAR NOSE and THROAT");
            department.setStatus(true);
            departmentRepository.save(department);

            User admin = new User("admin", UserTypeEnum.ADMIN.name(), encoder.encode("admin"), true);
            User doctorU = new User("doctor", UserTypeEnum.DOCTOR.name(), encoder.encode("doctor"), true);

            User nurse = new User("nurse", UserTypeEnum.NURSE.name(), encoder.encode("nurse"), true);
            User receptionist = new User("recep", UserTypeEnum.RECEPTIONIST.name(), encoder.encode("recep"), true);
            User cashier = new User("cash", UserTypeEnum.CASHIER.name(), encoder.encode("cash"), true);

            userRepository.save(Arrays.asList(admin, doctorU, nurse, receptionist, cashier));

            userRoleRepository.save(Arrays.asList(new UserRole(admin, roles.get(0)), new UserRole(doctorU, roles.get(1))));

            Manager manager = new Manager();
            manager.setDob(new Date());
            manager.setFirstName("Admin");
            manager.setLastName("User");
            manager.setGender(GenderTypeEnum.MALE.name());
            manager.setProfileId("EMP10001");
            manager.setUser(admin);
            managerRepository.save(manager);

            Tax tax = new Tax("10%","10 percent", new Date(),new Date());
            tax.setTaxId("TAX10001");
            tax.setRate(10.0);
            tax.setActive(true);
            taxRepository.save(tax);
            MedicalService medicalService = new MedicalService("Eye Checkup","EYE-001",300,200,tax);
            medicalService.setStatus(true);
            medicalServiceRepository.save(medicalService);

            Doctor doctor = new Doctor();
            doctor.setDob(new Date());
            doctor.setFirstName("Tahir");
            doctor.setLastName("Mehmood");
            doctor.setGender(GenderTypeEnum.MALE.name());
            doctor.setProfileId("EMP10002");
            doctor.setUser(doctorU);
            doctor.setEmail("doctor@gmail.com");
            doctor.setHomePhone("923327694385");
            doctor.setCellPhone("923327694385");
            doctor.setAddress("Siddique Trade Center");
            doctor.setCheckUpInterval(20L);
            doctor.setDepartment(department);
            DoctorMedicalService dms = new DoctorMedicalService(doctor,medicalService);
            doctor.setDoctorMedicalServices(Arrays.asList(dms));
            doctorRepository.save(doctor);
            doctorMedicalServiceRepository.save(dms);


            Branch primaryBranch = new Branch("DHA Branch","DHA Phase V", "+42676764564", "+42676764564", "RCD", new Date(), new Date(), true, true, organization);
            primaryBranch.setBranchId("BRH10001");
            branchRepository.save(primaryBranch);
            roomRepository.save(new Room("Checkup Room", primaryBranch, true, true));
            branchDoctorRepository.save(new BranchDoctor(doctor, primaryBranch, true));
            organizationRepository.saveAndFlush(organization);

            statusRepository.save(
                    Arrays.asList(new Status(AppointmentStatusTypeEnum.PENDING.name(),"P",true,"#0DFE4E"),
                            new Status(AppointmentStatusTypeEnum.CONFIRMED.name(),"C",true,"#0D1DFE"),
                            new Status(AppointmentStatusTypeEnum.CHECK_IN.name(),"CH",true,"#AE0DFE"),
                            new Status(AppointmentStatusTypeEnum.IN_SESSION.name(),"IS",true,"#FE0DEE"),
                            new Status(AppointmentStatusTypeEnum.COMPLETE.name(),"CMP",true,"#FED60D"),
                            new Status(AppointmentStatusTypeEnum.COMPLETE.name(),"CN",true,"#FE0D0D")));
        }

    }
}





/**
 * @author waqas kamran
 * @description To create WAR Packaging
 * @since 05-Jun-2018
 */

/*
@SpringBootApplication
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
}
*/



