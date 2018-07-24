package com.sd.his.repository;

import com.sd.his.model.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration,Long> {

    SystemConfiguration findById(Long id);

}
