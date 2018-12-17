package com.sd.his.service;

import com.sd.his.model.Organization;
import com.sd.his.repository.OrganizationRepository;
import com.sd.his.repository.PatientRepository;
import com.sd.his.wrapper.reports.AdvancePaymentReportWrapper;
import com.sd.his.wrapper.reports.PatientPaymentReportWrapper;
import com.sd.his.wrapper.reports.RefundReceiptReportWrapper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Service
public class ReportPrintService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Value("${spring.http.multipart.location}")
    private String tmpFilePath;

    private String path = "./WEB-INF/reports/";

    public RefundReceiptReportWrapper getRefundReceiptData(String refundId) {
        return patientRepository.getOneRefundData(refundId);
    }

    public AdvancePaymentReportWrapper getAdvancePaymentReceiptData(String paymentId) {
        return patientRepository.getOneAdvancePaymentData(paymentId);
    }

    public PatientPaymentReportWrapper getPatientPaymentInvoiceData(String paymentId) {
        return patientRepository.getOneInvoicePaymentData(paymentId);
    }

    public String generateReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException, IOException, InterruptedException {
        String reportPath = path + reportName + ".jrxml";
        String reportId = (String) parameters.getOrDefault("invoiceId", (String) parameters.getOrDefault("paymentId", (String) parameters.get("transId")));
        String pdfPath = tmpFilePath + reportName + "_" + reportId + ".pdf";
        JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource((Collection<?>) parameters.get("beanDS"));
        parameters.put("beanCoDataSource", beanColDataSource);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

//        String printFileName = null;
//        printFileName=JasperFillManager.fillReportToFile(reportPath, parameters, beanColDataSource);

//        JRPdfExporter exporter = new JRPdfExporter();
//        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfPath);
//        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(pdfPath));
//        exporter.exportReport();

        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
        File pdfFile = new File(pdfPath);
        if (pdfFile.exists()) {
            Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfFile.getCanonicalPath());
            p.waitFor();
        }
        return pdfFile.getCanonicalPath();
    }

    public Map<String, Object> createParamMap(PrintReportsEnum refundReceipt, Object wrapperObject) {
        Map<String, Object> map = new HashMap<>();
        Organization org = organizationRepository.findOne(1L);
        List<Object> collection = new ArrayList<>();
        map.put("logoImg", path + "logo.gif");
        map.put("companyName", org.getCompanyName());
        map.put("officePhone", org.getOfficePhone());
        map.put("email", org.getEmail());

        if (refundReceipt == PrintReportsEnum.REFUND_RECEIPT) {
            RefundReceiptReportWrapper refundReceiptReportWrapper = (RefundReceiptReportWrapper) wrapperObject;
            collection.add(refundReceiptReportWrapper);

            map.put("transId", refundReceiptReportWrapper.getTransId());
            map.put("fullName", refundReceiptReportWrapper.getFullName());
            map.put("paymentMethod", refundReceiptReportWrapper.getPaymentMethod());
            map.put("refundDate", refundReceiptReportWrapper.getRefundDate());
            map.put("patientEMR", refundReceiptReportWrapper.getPatientEMR());
            map.put("refundType", refundReceiptReportWrapper.getRefundType());
            map.put("paidAmount", refundReceiptReportWrapper.getPaidAmount());

        } else if (refundReceipt == PrintReportsEnum.ADVANCE_PAYMENT_RECEIPT) {
            AdvancePaymentReportWrapper advancePaymentReportWrapper = (AdvancePaymentReportWrapper) wrapperObject;
            collection.add(advancePaymentReportWrapper);

            map.put("paymentId", advancePaymentReportWrapper.getPaymentId());
            map.put("fullName", advancePaymentReportWrapper.getFullName());
            map.put("paymentDate", advancePaymentReportWrapper.getPaymentDate());
            map.put("patientEMR", advancePaymentReportWrapper.getPatientEMR());
            map.put("description", advancePaymentReportWrapper.getDescription());
            map.put("paidAmount", advancePaymentReportWrapper.getPaidAmount());

        } else if (refundReceipt == PrintReportsEnum.PATIENT_PAYMENT_INVOICE) {
            PatientPaymentReportWrapper patientPaymentReportWrapper = (PatientPaymentReportWrapper) wrapperObject;
            collection.add(patientPaymentReportWrapper);

            map.put("paymentId", patientPaymentReportWrapper.getPaymentId());
            map.put("fullName", patientPaymentReportWrapper.getFullName());
            map.put("paymentDate", patientPaymentReportWrapper.getPaymentDate());
            map.put("patientEMR", patientPaymentReportWrapper.getPatientEMR());
            map.put("paidAmount", patientPaymentReportWrapper.getPaidAmount());
            map.put("paymentMode", "Cash");
            map.put("invoiceId", patientPaymentReportWrapper.getInvoiceId());
            map.put("invoiceAmount", patientPaymentReportWrapper.getInvoiceAmount());
            map.put("discountAmount", patientPaymentReportWrapper.getDiscountAmount());
            map.put("advance", patientPaymentReportWrapper.getAdvance());
            map.put("appliedAmount", patientPaymentReportWrapper.getAppliedAmount());
            map.put("balance", patientPaymentReportWrapper.getBalance());
        } else if (refundReceipt == PrintReportsEnum.PATIENT_INVOICE) {
            PatientPaymentReportWrapper patientPaymentReportWrapper = (PatientPaymentReportWrapper) wrapperObject;
            collection.add(patientPaymentReportWrapper);

            map.put("paymentId", patientPaymentReportWrapper.getPaymentId());
            map.put("fullName", patientPaymentReportWrapper.getFullName());
            map.put("paymentDate", patientPaymentReportWrapper.getPaymentDate());
            map.put("patientEMR", patientPaymentReportWrapper.getPatientEMR());
            map.put("paidAmount", patientPaymentReportWrapper.getPaidAmount());
            map.put("paymentMode", "Cash");
            map.put("invoiceId", patientPaymentReportWrapper.getInvoiceId());
            map.put("invoiceAmount", patientPaymentReportWrapper.getInvoiceAmount());
            map.put("discountAmount", patientPaymentReportWrapper.getDiscountAmount());
            map.put("advance", patientPaymentReportWrapper.getAdvance());
            map.put("appliedAmount", patientPaymentReportWrapper.getAppliedAmount());
            map.put("balance", patientPaymentReportWrapper.getBalance());
        }

        map.put("beanDS", collection);
        return map;
    }


    public enum PrintReportsEnum {
        REFUND_RECEIPT, ADVANCE_PAYMENT_RECEIPT, PATIENT_PAYMENT_INVOICE, PATIENT_INVOICE
    }
}
