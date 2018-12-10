package com.sd.his.service;

import com.sd.his.model.LabTestSpeciman;
import com.sd.his.repository.LabTestSpecimanRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LabTestSpecimanService {

    @Autowired
    LabTestSpecimanRepository labTestSpecimanRepository;

    public List<LabTestSpeciman> getAll(){
        return labTestSpecimanRepository.getAll();
    }

    public void saveConfiguration(LabTestSpeciman labTestSpeciman){
        labTestSpecimanRepository.save(labTestSpeciman);
    }

}
