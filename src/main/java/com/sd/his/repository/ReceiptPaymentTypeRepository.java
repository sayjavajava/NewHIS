package com.sd.his.repository;

import com.sd.his.model.ReceiptPaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptPaymentTypeRepository extends JpaRepository<ReceiptPaymentType, Long> {


}
