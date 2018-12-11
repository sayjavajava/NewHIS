package com.sd.his.service;

import com.sd.his.model.PaymentType;
import com.sd.his.repository.PaymentTypeRepository;
import com.sd.his.wrapper.response.PaymentTypeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentRepository;


    // Fetch All Record
    public List<PaymentTypeWrapper> getAllPaymentType() {
//        List<PaymentType> paymentType = paymentRepository.findAll();
        return paymentRepository.getAllWithWrapper();
    }

    // Fetch List Record
    public List<PaymentTypeWrapper> getListPaymentType() {
        return paymentRepository.getAllWithWrapper();
    }

    //Delete Record for Payment Delete
    @Transactional(rollbackOn = Throwable.class)
    public void deletePaymentType(long id) {
        paymentRepository.delete(id);
    }

    //Save Record For Payment Type
    @Transactional(rollbackOn = Throwable.class)
    public PaymentType savePaymentAPI(PaymentType paymentType) {
        return paymentRepository.save(paymentType);
    }

    // Update Record////////////////////////////
    @Transactional(rollbackOn = Throwable.class)
    public PaymentType updatePaymentType(PaymentType paymentType) {
        PaymentType paymentEntity = paymentRepository.findOne(Long.valueOf(paymentType.getId()));
       paymentEntity.setPaymentGlAccount(paymentType.getPaymentGlAccount());
        paymentEntity.setPaymentPurpose(paymentType.getPaymentPurpose());
        paymentEntity.setPaymentTitle(paymentType.getPaymentTitle());
        paymentEntity.setPaymentMode(paymentType.getPaymentMode());
       // SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.ENGLISH);
      //  String dateString = format.format(new Date());
     //   paymentEntity.setUpdatedOn(HISCoreUtil.convertToDate(dateString));
        paymentEntity.setActive(paymentType.getActive());
        if(paymentType.getPaymentMode().equalsIgnoreCase("Card")){

            paymentEntity.setMaxCardCharges(paymentType.getMaxCardCharges());
            paymentEntity.setBankGlCharges(paymentType.getBankGlCharges());
            paymentEntity.setPayCredit(paymentType.getPayCredit());
            paymentEntity.setServiceCharges(paymentType.getServiceCharges());

        }
        return paymentRepository.save(paymentEntity);
    }

    public static  Double makeDecimalFormat(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
