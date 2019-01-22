package com.sd.his.service;

import com.sd.his.model.ImportFile;
import com.sd.his.repository.ImportFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImportFileService {

    @Autowired
    private ImportFileRepository importFileRepository;

    public void deleteImportFileData(Long id){
        importFileRepository.delete(id);
    }

    public List<ImportFile> getAll(){
        return importFileRepository.findAll();
    }

    public ImportFile saveImportFileData(ImportFile importFile){
        return importFileRepository.save(importFile);
    }

    public ImportFile saveImportFileData(String fileName, String dupRecOp, String charEncoding){
//        fileName = this.tmpFilePath + fileName;
//        ImportFile importFile = new ImportFile(this.tmpFilePath + fileName, dupRecOp, charEncoding);
        return importFileRepository.save(new ImportFile(fileName, dupRecOp, charEncoding));
    }

    public ImportFile saveImportFile(ImportFile importFile){
        return this.importFileRepository.save(importFile);
    }

    public ImportFile getImportFileDataById(Long importFileId) {
        return importFileRepository.getOne(importFileId);
    }

}
