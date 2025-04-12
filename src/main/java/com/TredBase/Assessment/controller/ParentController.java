package com.TredBase.Assessment.controller;

import com.TredBase.Assessment.service.PaymentChildService;
import com.TredBase.Assessment.service.PaymentService;
import com.TredBase.Assessment.vo.request.ParentChildRequest;
import com.TredBase.Assessment.vo.request.TransactionRequest;
import com.TredBase.Assessment.vo.response.ParentChildResponse;
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
@RequestMapping("api/v1/parents")
public class ParentController {


    private static final Logger logger = Logger.getLogger(ParentController.class.getName());
    @Autowired
    private PaymentChildService paymentChildService;

    @PostMapping("addStudents")
    public CompletableFuture<ParentChildResponse> addStudents(@RequestBody ParentChildRequest request) {
        logger.info(String.format("%s - %s", "ADD STUDENTS", request.getParents().get(0).getEmail()));
        ParentChildResponse response = new ParentChildResponse(ServiceResponse.ERROR, ServiceResponse.GENERAL_ERROR_MESSAGE);
        try {
            response = paymentChildService.saveParentChild(request);
        } catch (Exception ex) {
            logger.severe(String.format("%s - %s - %s", "AAD STUDENTS ERROR", request.getParents().get(0).getEmail(), ex.getMessage()));
        }
        return CompletableFuture.completedFuture(response);
    }
}
