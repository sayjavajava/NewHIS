package com.sd.his.repository;

import com.sd.his.model.LabOrder;
import com.sd.his.model.LabTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(name = "LabOrderProjection", types = LabOrder.class)
public interface LabOrderProjection {
    @Value("#{target.id}")
    Integer getId();
    String getStatus();
    String getComments();
    Date getDateTest();
    List<LabTest> getLabTests();
}