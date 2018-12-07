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

    public int readExcel(String dataFilePath) throws IllegalStateException, org.apache.poi.openxml4j.exceptions.InvalidFormatException, IOException {
        File file = new File(dataFilePath);
        AtomicInteger records = new AtomicInteger(0);
        Workbook workBook = WorkbookFactory.create(file);

        //Read sheet inside the workbook by its Index
        Sheet excelSheet = workBook.getSheetAt(0);
        LabTestSpeciman labTestSpeciman = null;

        for (Row row : excelSheet) {
            if (row != null && row.getRowNum() > 0 && row.getCell(0) != null) {
                if(row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null)
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
        return records.get();
    }
}
