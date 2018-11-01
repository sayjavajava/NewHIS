package com.sd.his.repository;

import com.sd.his.model.SmsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsTemplateRepository extends JpaRepository<SmsTemplate, Long> {

  //  SmsTemplate findByServerType(String serverType);
}
