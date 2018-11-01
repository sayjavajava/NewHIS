package com.sd.his.repository;

import com.sd.his.model.AccountConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountConfigRepository  extends JpaRepository<AccountConfig, Long> {
}
