package com.TredBase.Assessment.controller;

import com.TredBase.Assessment.service.Impl.PaymentServiceImpl;
import com.TredBase.Assessment.service.PaymentService;
import com.TredBase.Assessment.vo.request.TransactionRequest;
import com.TredBase.Assessment.vo.response.PaymentResponse;
import com.TredBase.Assessment.vo.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private static final Logger logger = Logger.getLogger(TransactionController.class.getName());
    @Autowired
    private PaymentService paymentService;

    @PostMapping("makeSchoolFeesPayment")
    public CompletableFuture<PaymentResponse> makeSchoolFeesPayment(@RequestBody TransactionRequest request) {
        logger.info(String.format("%s - %s, %s", "INITIATE SCHOOL FEES PAYMENT", request.getParentId(), request.getStudentId()));
        PaymentResponse response = new PaymentResponse(ServiceResponse.ERROR, ServiceResponse.GENERAL_ERROR_MESSAGE);
        try {
            response = paymentService.makeSchoolFeesPayment(request.getParentId(), request.getStudentId(), request.getPaymentAmount());
        } catch (Exception ex) {
            logger.severe(String.format("%s - %s - %s", "INITIATE SCHOOL FEES PAYMENT ERROR", request.getStudentId(), ex.getMessage()));
        }
        return CompletableFuture.completedFuture(response);
    }
}
