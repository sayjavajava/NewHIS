package com.sd.his.service;

import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.sd.his.utill.HISConstants;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.Patient_OrderWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    AWSService awsService;

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
                      //  patientOrder.setType(tokens[1].toString());

                        byte[] bteObj = multipartFile.getBytes();
                        if(tokens[1].toString().equalsIgnoreCase("PNG") || tokens[1].toString().equalsIgnoreCase("JPG")   || tokens[1].toString().equalsIgnoreCase("JPEG") || tokens[1].toString().equalsIgnoreCase("GIF") || tokens[1].toString().equalsIgnoreCase("BMP") ) {
                            url = userService.saveImageByOrder(bteObj,
                                    HISConstants.S3_USER_ORDER_DIRECTORY_PATH,
                                    orderWrapper.getPatientId()
                                            + "_"
                                            + HISCoreUtil.convertDateToStringUpload(new Date())//patientImageSetup.getId()
                                            + "_"
                                            + HISConstants.S3_USER_ORDER_DIRECTORY_PATH,
                                    orderWrapper.getPatientId()
                                            + "_"
                                            + HISCoreUtil.convertDateToStringUpload(new Date())
                                            + "_"
                                            + HISConstants.S3_USER_ORDER_GRAPHIC_NAME,
                                    "/"
                                            + HISConstants.S3_USER_ORDER_DIRECTORY_PATH
                                            + orderWrapper.getPatientId()
                                            + "_"
                                            + HISCoreUtil.convertDateToStringUpload(new Date())
                                            + "_"
                                            + HISConstants.S3_USER_ORDER_THUMBNAIL_GRAPHIC_NAME);


                        }else{

                           url =    awsService.uploadFile(multipartFile,orderWrapper.getPatientId() );



                        }

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
                patientOrder.setDescription(orderWrapper.getDescription());
                patientOrder.setStatus(orderWrapper.getStatus());
                patientOrder.setDoctorComment(orderWrapper.getDoctorComment());
                this.patientOrderRepository.save(patientOrder);

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
       return this.patientOrderRepository.findOrderById(Id);
       // return null;
    }

    public List<Patient_OrderWrapper> getPaginatedOrder(Pageable pageable, Long patientId) {
       List<Patient_OrderWrapper> order=this.patientOrderRepository.getPaginatedOrder(pageable,patientId);
        List<Patient_OrderWrapper> orderWrapperLst=new ArrayList<Patient_OrderWrapper>();
        for(int i=0;i<order.size();i++){
            Patient_OrderWrapper orderWrapper=new Patient_OrderWrapper();
            orderWrapper.setId(order.get(i).getId());
            orderWrapper.setDescription(order.get(i).getDescription());
            orderWrapper.setUrl(order.get(i).getUrl());
            orderWrapper.setDoctorComment(order.get(i).getDoctorComment());
            orderWrapper.setOrderObj(order.get(i).getOrderObj());
            orderWrapperLst.add(orderWrapper);
        }
       return orderWrapperLst;

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


    public Patient_OrderWrapper  getOrderImageById(long Id) {
        return this.patientOrderRepository.findOrderImageById(Id);
        // return null;
    }


}
