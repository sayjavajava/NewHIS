package com.sd.his.service;

import com.sd.his.model.Document;
import com.sd.his.model.Patient;
import com.sd.his.repository.DocumentRepository;
import com.sd.his.repository.PatientRepository;
import com.sd.his.utill.HISConstants;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.DocumentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 9/3/2018.
 */
@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserService userService;


    @Transactional
    public String saveDocument(DocumentWrapper documentWrapper) {
        try {

            Document document = null;
            Patient patient = this.patientRepository.findOne(Long.valueOf(documentWrapper.getPatientId()));
            if (patient != null) {
                document = new Document(documentWrapper);
                document.setPatient(patient);
            }

            this.documentRepository.save(document);

            String url = null;
            if (documentWrapper.getImage() != null) {
                url = userService.saveImage(documentWrapper.getImage(),
                        HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH,
                        documentWrapper.getPatientId()
                                + "_"
                                + document.getId()
                                + "_"
                                + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME,
                        documentWrapper.getPatientId()
                                + "_"
                                + document.getId()
                                + "_"
                                + HISConstants.S3_USER_DOCUMENT_GRAPHIC_NAME,
                        "/"
                                + HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH
                                + documentWrapper.getPatientId()
                                + "_"
                                + document.getId()
                                + "_"
                                + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME);


                if (HISCoreUtil.isValidObject(url)) {
                    document.setUrl(url);
                    this.documentRepository.save(document);
                    url = null;
                }
            }


            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Transactional
    public String updateDocument(DocumentWrapper documentWrapper) {
        Document document = this.documentRepository.findOne(documentWrapper.getId());
        try {
            if (document != null) {
                new Document(document, documentWrapper);
                Patient patient = this.patientRepository.findOne(Long.valueOf(documentWrapper.getPatientId()));
                if (patient != null) {
                    document.setPatient(patient);
                } else {
                    return "Patient not found";
                }

                this.documentRepository.save(document);

                String url = null;
                if (documentWrapper.getImage() != null) {
                    url = userService.saveImage(documentWrapper.getImage(),
                            HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH,
                            documentWrapper.getPatientId()
                                    + "_"
                                    + document.getId()
                                    + "_"
                                    + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME,
                            documentWrapper.getPatientId()
                                    + "_"
                                    + document.getId()
                                    + "_"
                                    + HISConstants.S3_USER_DOCUMENT_GRAPHIC_NAME,
                            "/"
                                    + HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH
                                    + documentWrapper.getPatientId()
                                    + "_"
                                    + document.getId()
                                    + "_"
                                    + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME);


                    if (HISCoreUtil.isValidObject(url)) {
                        document.setUrl(url);
                        this.documentRepository.save(document);
                        url = null;
                    }
                }


                return "";
            }
            return "Document not found";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Transactional
    public boolean deleteDocument(long documentId) {
        Document document = this.documentRepository.findOne(documentId);
        if (document != null) {
            this.documentRepository.delete(document);
            return true;
        }
        return false;
    }

    public DocumentWrapper getDocument(long documentId) {
        return this.documentRepository.findDocumentById(documentId);
    }

    public List<DocumentWrapper> getPaginatedDocuments(Pageable pageable) {
        return this.documentRepository.getPaginatedDocuments(pageable);
    }

    public int countPaginatedDocuments() {
        return this.documentRepository.findAll().size();
    }

    public boolean isNameDocumentAvailable(String nameDocument) {
        return this.documentRepository.isNameExists(nameDocument);
    }

    public boolean isNameDocumentAvailableAgainstDocumentId(String nameDocument, Long documentId) {
        return this.documentRepository.isNameExistsAgainstId(nameDocument, documentId);
    }
}
