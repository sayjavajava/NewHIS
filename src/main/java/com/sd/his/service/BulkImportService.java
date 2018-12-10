package com.sd.his.service;

import com.sd.his.enums.GenderTypeEnum;
import com.sd.his.enums.ModuleEnum;
import com.sd.his.enums.PatientStatusTypeEnum;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Zain on 12/10/2018.
 */
@Service
public class BulkImportService {


    @Autowired
    private DrugRepository drugRepository;
    @Autowired
    private LabTestSpecimanRepository labTestSpecimanRepository;
    @Autowired
    private ICDCodeRepository codeRepository;
    @Autowired
    private ICDVersionRepository versionRepository;
    @Autowired
    private ICDCodeVersionRepository codeVersionRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HISUtilService hisUtilService;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Value("${spring.http.multipart.location}")
    private String tmpFilePath;

    public int importDrugRecords(String fileName) throws IllegalStateException, InvalidFormatException, IOException {
        File file = new File(this.tmpFilePath + fileName);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);
        Sheet excelSheet = workBook.getSheetAt(0);
        Drug drug = null;

        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null || row.getCell(4) == null || row.getCell(5) == null)
                    continue;
                Drug oldDrug = drugRepository.findDrugByBrandName(row.getCell(0).getStringCellValue() + "");
                if (oldDrug != null)
                    continue;
                drug = new Drug();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 0:
                            drug.setDrugName(row.getCell(j).getStringCellValue());
                            break;
                        case 1:
                            drug.setGenericName(row.getCell(j).getStringCellValue());
                            break;
                        case 2:
                            drug.setCompanyName(row.getCell(j).getStringCellValue());
                            break;
                        case 3:
                            drug.setRoute(row.getCell(j).getStringCellValue());
                            break;
                        case 4:
                            String[] arrSplit = row.getCell(j).getStringCellValue().split("[,]");
                            drug.setStrengths(Arrays.asList(arrSplit));
                            break;
                        case 5:
                            drug.setuOM(row.getCell(j).getStringCellValue());
                            break;
                    }
                }
                drug.setActive(true);
                drugRepository.save(drug);
                System.out.println();
            }
        }
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }

    public int importLabTestRecords(String fileName) throws IllegalStateException, InvalidFormatException, IOException {
        File file = new File(this.tmpFilePath + fileName);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);

        //Read sheet inside the workbook by its Index
        Sheet excelSheet = workBook.getSheetAt(0);
        LabTestSpeciman labTestSpeciman = null;

        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null)
                    continue;
                LabTestSpeciman oldlabTestSpeciman = labTestSpecimanRepository.findDuplicateTestEntry(row.getCell(0).getStringCellValue() + "",
                        row.getCell(1).getStringCellValue() + "", row.getCell(2).getNumericCellValue() + "",
                        row.getCell(3).getNumericCellValue() + "");
                if (oldlabTestSpeciman != null)
                    continue;
                labTestSpeciman = new LabTestSpeciman();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 0:
                            labTestSpeciman.setTestCode(row.getCell(j).getStringCellValue());
                            break;
                        case 1:
                            labTestSpeciman.setTestName(row.getCell(j).getStringCellValue());
                            break;
                        case 2:
                            labTestSpeciman.setMinNormalRange(row.getCell(j).getNumericCellValue() + "");
                            break;
                        case 3:
                            labTestSpeciman.setMaxNormalRange(row.getCell(j).getNumericCellValue() + "");
                            break;
                    }
                }
                labTestSpecimanRepository.save(labTestSpeciman);
                System.out.println();
            }
        }
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }

    @Transactional(rollbackOn = Throwable.class)
    public int importIcdCodeRecords(String fileName) throws IllegalStateException, InvalidFormatException, IOException, ParseException {
        File file = new File(this.tmpFilePath + fileName);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);

        //Read sheet inside the workbook by its Index
        Sheet excelSheet = workBook.getSheetAt(0);
        ICDCode icdCode = null;
        ICDVersion icdVersion = null;
        ICDCodeVersion icdCodeVersion = null;

        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null)
                    continue;

                List<ICDVersion> oldVersions = new ArrayList<>();
                List<ICDVersion> newVersions = new ArrayList<>();

                ICDCode oldIcdCode = codeRepository.findByCode(row.getCell(1).getStringCellValue() + "");
                ICDVersion oldIcdVersion = null;
                String[] arrSplit = row.getCell(0).getStringCellValue().split("[,]");

                for (int a = 0; a < arrSplit.length; a++) {
                    String versionName = arrSplit[a].trim();
                    oldIcdVersion = versionRepository.findByName(versionName);
                    if (oldIcdVersion != null) {
                        oldVersions.add(oldIcdVersion);
                    } else {
                        icdVersion = new ICDVersion();
                        icdVersion.setName(versionName);
                        icdVersion.setStatus(true);
                        versionRepository.save(icdVersion);
                        newVersions.add(icdVersion);
                    }
                }

                if (oldIcdCode != null && oldVersions.size() == arrSplit.length && newVersions.size() < 1) {
                    continue;
                }
                icdCode = new ICDCode();
                for (int j = 1; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 1:
                            icdCode.setCode(row.getCell(j).getStringCellValue());
                            break;
                        case 2:
                            icdCode.setProblem(row.getCell(j).getStringCellValue());
                            break;
                    }
                }

                icdCode.setStatus(true);
                codeRepository.save(icdCode);

                List<ICDVersion> allVersions = new ArrayList<ICDVersion>();
                allVersions.addAll(oldVersions);
                allVersions.addAll(newVersions);

                List<ICDCodeVersion> codeVersionsList = new ArrayList<>();

                for (int y = 0; y < allVersions.size(); y++) {
                    icdCodeVersion = new ICDCodeVersion();
                    icdCodeVersion.setIcd(icdCode);
                    icdCodeVersion.setVersion(allVersions.get(y));
                    codeVersionRepository.save(icdCodeVersion);

                    codeVersionsList.add(icdCodeVersion);
                }

                icdCode.setVersions(codeVersionsList);
                codeRepository.save(icdCode);

                System.out.println();

            }
        }
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }

    public int importPatientRecords(String fileName) throws IllegalStateException, InvalidFormatException, IOException, ParseException {
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        File file = new File(this.tmpFilePath + fileName);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);
        Sheet excelSheet = workBook.getSheetAt(0);

        Patient patient = null;
        Doctor doctor = null;
        Country country = null;
        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null
                        || row.getCell(3) == null || row.getCell(4) == null || row.getCell(5) == null
                        || row.getCell(6) == null || row.getCell(7) == null)
                    continue;
                Patient oldPatient = patientRepository.findDuplicatePatientForBulkImport(row.getCell(2).getStringCellValue() + "",
                        row.getCell(3).getStringCellValue() + "", row.getCell(4).getStringCellValue() + "", row.getCell(5).getDateCellValue());
                if (oldPatient != null)
                    continue;

                doctor = doctorRepository.findOne((long) row.getCell(0).getNumericCellValue());
                if (doctor == null)
                    continue;

                country = countryRepository.findOne((long) row.getCell(7).getNumericCellValue());
                if (country == null) {
//                    continue;
                }
                patient = new Patient();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 0:
                            patient.setPrimaryDoctor(doctor);
                            break;
                        case 1:
                            patient.setTitle(row.getCell(j).getStringCellValue());
                            break;
                        case 2:
                            patient.setFirstName(row.getCell(j).getStringCellValue());
                            break;
                        case 3:
                            patient.setLastName(row.getCell(j).getStringCellValue());
                            break;
                        case 4:
                            patient.setCellPhone(row.getCell(j).getStringCellValue());
                            break;
                        case 5:
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String date = dateFormat.format(row.getCell(j).getDateCellValue());
                            patient.setDob(dateFormat.parse(date));
                            break;
                        case 6:
                            if (row.getCell(j).getStringCellValue().trim().toUpperCase().equals(GenderTypeEnum.MALE.name())) {
                                patient.setGender(GenderTypeEnum.MALE);
                            } else {
                                patient.setGender(GenderTypeEnum.FEMALE);
                            }
                            break;
                        case 7:
                            if (country != null) {
                                patient.setCountry(country.getName());
                            }
                            break;
                    }
                }
                patient.setStatus(PatientStatusTypeEnum.ACTIVE);
                patient.setPatientId(hisUtilService.getPrefixId(ModuleEnum.PATIENT));
                patientRepository.save(patient);
                records.incrementAndGet();
            }
        }
        // Closing the workbook
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }

    public int importAppointmentRecords(String fileName) throws IllegalStateException, InvalidFormatException, IOException, ParseException {
        File file = new File(this.tmpFilePath + fileName);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);

        //Read sheet inside the workbook by its Index
        Sheet excelSheet = workBook.getSheetAt(0);
        Appointment appointment = null;

        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null || row.getCell(4) == null || row.getCell(5) == null)
                    continue;
                String time = new SimpleDateFormat("HH:mm:ss").format(row.getCell(4).getDateCellValue());
                Appointment oldAppointment = appointmentRepository.findConflictInAppointment(row.getCell(1).getStringCellValue() + "",
                        row.getCell(2).getStringCellValue() + "", row.getCell(3).getDateCellValue(), time);
                if (oldAppointment != null)
                    continue;
                appointment = new Appointment();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    switch (j) {
                        case 0:
                            Patient patient = patientRepository.getByEMR(row.getCell(j).getStringCellValue() + "");
                            if (patient == null) {
                                j = row.getLastCellNum() + 1;
                            } else {
                                appointment.setPatient(patient);
                            }
                            break;
                        case 1:
                            Doctor doctor = doctorRepository.getByProfileId(row.getCell(j).getStringCellValue() + "");
                            if (doctor == null) {
                                j = row.getLastCellNum() + 1;
                            } else {
                                appointment.setDoctor(doctor);
                            }
                            break;
                        case 2:
                            Branch branch = branchRepository.getByBranchId(row.getCell(j).getStringCellValue() + "");
                            if (branch == null) {
                                j = row.getLastCellNum() + 1;
                            } else {
                                appointment.setBranch(branch);
                            }
                            break;
                        case 3:
                            String date = new SimpleDateFormat("yyyy-MM-dd").format(row.getCell(j).getDateCellValue());
                            appointment.setSchdeulledDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                            break;
                        case 4:
                            appointment.setStartedOn(new SimpleDateFormat("HH:mm:ss").parse(time));
                            break;
                        case 5:
                            long endTimeInMillis = (long) (new SimpleDateFormat("HH:mm:ss").parse(time).getTime() + (row.getCell(j).getNumericCellValue() * 60000));           // 1 MINUTE = 60000 MiliSeconds
                            appointment.setEndedOn(new Date(endTimeInMillis));
                            appointment.setDuration((int) row.getCell(j).getNumericCellValue());
                            break;
                        case 6:
                            appointment.setType(row.getCell(j).getStringCellValue());
                            break;
                    }
                }
                if (appointment.getPatient() == null || appointment.getDoctor() == null || appointment.getBranch() == null) {
                    continue;
                } else {
                    appointment.setAppointmentId(hisUtilService.getPrefixId(ModuleEnum.APPOINTMENT));
                    appointmentRepository.save(appointment);
                }
                System.out.println();
            }
        }
        workBook.close();
        this.deleteFile(file);
        return records.get();
    }

    public boolean deleteFile(File file) {
        return (file.exists() && file.delete());
    }
}
