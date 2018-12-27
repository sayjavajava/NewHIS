package com.sd.his.controller;

import com.sd.his.enums.InvoiceMessageEnum;
import com.sd.his.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/fileDownload")
public class FileDownloadAPI {

    private final Logger logger = LoggerFactory.getLogger(FileDownloadAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "Print Refund Receipt",
            notes = "This method will Print Refund Receipt",
            produces = "application/json", nickname = "Refund Receipt",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/icdCodeSample", method = RequestMethod.GET)
    public ResponseEntity<?> icdCodeSampleFileDownload() {
        logger.info("icdCodeSampleFileDownload method initialized successfully...");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            URL resource = getClass().getClassLoader().getResource("sample_files/icd.xlsx");
            if (resource == null) {
                logger.error("ICD Code sample file not found...");
                return this.sampleFileNotFound();
            }

            File file = new File(resource.getPath());
            if (!file.exists()) {
                logger.error("ICD Code sample file not found...");
                return this.sampleFileNotFound();
            }

            Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getCanonicalPath());
            p.waitFor();
            System.out.println(file.getCanonicalPath());

            response.setResponseMessage(messageBundle.getString("sample.file.download.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("ICD Code sample file downloaded successfully...");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("ICD Code sample file download Failed...", ex.fillInStackTrace());
            return this.sampleFileDownloadFailed(ex);
        }
    }

    private ResponseEntity<GenericAPIResponse> sampleFileNotFound(){
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
        response.setResponseMessage(messageBundle.getString("sample.file.download.no.record"));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<GenericAPIResponse> sampleFileDownloadFailed(Exception ex){
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
        response.setErrorMessageData(ex.getMessage());
        response.setResponseMessage(messageBundle.getString("sample.file.download.failed"));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
