package com.sd.his.service;

import com.sd.his.model.SmsTemplate;
import com.sd.his.repository.SmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class SmsTemplateService {

    @Autowired
    private SmsTemplateRepository smsTemplateRepository;


    @Transactional
    public void saveConfiguration(SmsTemplate templateData){
        SmsTemplate smsTemplate;
        if(templateData.getId() == null){
            smsTemplate = new SmsTemplate();
        }
        smsTemplate = templateData;
        smsTemplateRepository.save(smsTemplate);
    }


    public List<SmsTemplate> getAll(){
        return smsTemplateRepository.findAll();
    }

    public void delete(long id){
        smsTemplateRepository.delete(id);
    }

    public SmsTemplate getById(long id){
       return smsTemplateRepository.findOne(id);
    }
}
