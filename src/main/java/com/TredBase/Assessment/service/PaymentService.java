package com.TredBase.Assessment.service;

import com.TredBase.Assessment.vo.response.PaymentResponse;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentResponse makeSchoolFeesPayment(Long parentId, Long studentId, BigDecimal paymentAmount);
}
