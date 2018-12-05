package com.sd.his.service;

import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.utill.HISConstants;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.DocumentWrapper;
import com.sd.his.wrapper.Patient_OrderWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import com.sd.his.model.Patient;
import com.sd.his.repository.PatientRepository;
import org.springframework.web.multipart.MultipartFile;



@Service
public class PatientOrderService {

    @Autowired
    private PatientOrderRepository patientOrderRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserService userService;
    @Autowired
    PatientImageRepository patientImageRepository;

    @Transactional
    public String saveOrder(Patient_OrderWrapper orderWrapper) {
        try {

            Patient_Order patientOrder = null;
            Patient patient = this.patientRepository.findOne(Long.valueOf(orderWrapper.getPatientId()));
            PatientImageSetup patientImageSetup=patientImageRepository.findOne(Long.valueOf(orderWrapper.getPatientImageId()));
            if (patient != null) {
                patientOrder= new Patient_Order(orderWrapper);
                patientOrder.setPatient(patient);
            }
            if(patientImageSetup!=null){
                patientOrder.setOrder(patientImageSetup);
            }

         //   this.patientOrderRepository.save(patientOrder);
            List<String> imageUrls=new ArrayList<>();
            String url = null;
            if (orderWrapper.getListOfFiles().length > 0 ) {

                    for (MultipartFile multipartFile : orderWrapper.getListOfFiles()) {

                        String fileName = multipartFile.getOriginalFilename();
                        String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
                        patientOrder.setType(tokens[1].toString());

                        byte[] bteObj = multipartFile.getBytes();

                        url = userService.saveImage(bteObj,
                                HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH,
                                orderWrapper.getPatientId()
                                        + "_"
                                        + patientImageSetup.getId()
                                        + "_"
                                        + HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH,
                                orderWrapper.getPatientId()
                                        + "_"
                                        + patientImageSetup.getId()
                                        + "_"
                                        + HISConstants.S3_USER_DOCUMENT_GRAPHIC_NAME,
                                "/"
                                        + HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH
                                        + orderWrapper.getPatientId()
                                        + "_"
                                        + patientImageSetup.getId()
                                        + "_"
                                        + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME);

                        imageUrls.add(url);

                    }
                    System.out.println(url);
                if (!HISCoreUtil.isListEmpty(imageUrls)) {
                    patientOrder.setUrl(imageUrls);
                    this.patientOrderRepository.save(patientOrder);
                    url = null;
                }
                    return "";
                }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Transactional
    public String updateDocument(Patient_OrderWrapper orderWrapper) {
        Patient_Order patientOrder  = this.patientOrderRepository.findOne(orderWrapper.getId());
        try {
            if (patientOrder != null) {
                new Patient_Order(patientOrder, orderWrapper);
                Patient patient = this.patientRepository.findOne(Long.valueOf(orderWrapper.getPatientId()));
                if (patient != null) {
                    patientOrder.setPatient(patient);
                } else {
                    return "patient not found";
                }

                this.patientOrderRepository.save(patientOrder);

               /* String url = null;
                if (orderWrapper.getImage() != null) {
                    url = userService.saveImage(orderWrapper.getImage(),
                            HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH,
                            orderWrapper.getPatientId()
                                    + "_"
                                    + patientOrder.getId()
                                    + "_"
                                    + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME,
                            orderWrapper.getPatientId()
                                    + "_"
                                    + patientOrder.getId()
                                    + "_"
                                    + HISConstants.S3_USER_DOCUMENT_GRAPHIC_NAME,
                            "/"
                                    + HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH
                                    + orderWrapper.getPatientId()
                                    + "_"
                                    + patientOrder.getId()
                                    + "_"
                                    + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME);


                    if (HISCoreUtil.isValidObject(url)) {
                        patientOrder.setUrl(url);
                        this.patientOrderRepository.save(patientOrder);
                        url = null;
                    }
                }*/
                return "";
            }
            return "Order  not found";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Transactional
    public boolean deleteDocument(long documentId) {
        Patient_Order patientOrder  = this.patientOrderRepository.findOne(documentId);
        if (patientOrder != null) {
            this.patientOrderRepository.delete(patientOrder);
            return true;
        }
        return false;
    }

    public Patient_OrderWrapper  getOrderById(long Id) {
      //  return this.patientOrderRepository.findOrderById(Id);
        return null;
    }

    public List<Patient_OrderWrapper> getPaginatedOrder(Pageable pageable, Long patientId) {
       // List<Patient_OrderWrapper> order=this.patientOrderRepository.getPaginatedOrder(pageable,patientId);
        /*List<Patient_OrderWrapper> orderWrapperLst=new ArrayList<Patient_OrderWrapper>();
        for(int i=0;i<order.size();i++){
            Patient_OrderWrapper orderWrapper=new Patient_OrderWrapper();
            orderWrapper.setPatientId(order.get(i).getId());
            orderWrapper.setDescription(order.get(i).getDescription());
            orderWrapper.setUrl(order.get(i).getUrl());
            orderWrapper.setDoctorComment(order.get(i).getDoctorComment());
            orderWrapper.setOrderObj(order.get(i).getOrderObj());
            orderWrapperLst.add(orderWrapper);
        }*/
      //  return orderWrapperLst;
        return null;
    }

    public int countPaginatedDocuments() {
        return this.patientOrderRepository.findAll().size();
    }

    public boolean isNameDocumentAvailableByPatientId(String nameDocument,Long patientId) {
        return this.patientOrderRepository.isNameExists(nameDocument,patientId);
    }

    public boolean isNameDocumentAvailableAgainstDocumentIdAndPatientId(String nameDocument, Long documentId, Long patientId) {
        return this.patientOrderRepository.isNameExistsAgainstId(nameDocument, documentId,patientId);
    }
}
