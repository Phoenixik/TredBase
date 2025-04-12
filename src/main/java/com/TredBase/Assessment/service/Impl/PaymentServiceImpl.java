package com.TredBase.Assessment.service.Impl;


import com.TredBase.Assessment.model.Parent;
import com.TredBase.Assessment.model.ParentChild;
import com.TredBase.Assessment.model.Student;
import com.TredBase.Assessment.model.Transaction;
import com.TredBase.Assessment.model.enums.TransactionType;
import com.TredBase.Assessment.repository.ParentChildRepository;
import com.TredBase.Assessment.repository.ParentRepository;
import com.TredBase.Assessment.repository.StudentRepository;
import com.TredBase.Assessment.repository.TransactionRepository;
import com.TredBase.Assessment.service.PaymentService;
import com.TredBase.Assessment.vo.response.PaymentResponse;
import com.TredBase.Assessment.vo.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = Logger.getLogger(PaymentServiceImpl.class.getName());

    @Value("${fee}")
    private double fees;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentChildRepository parentChildRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public PaymentResponse makeSchoolFeesPayment(Long parentId, Long studentId, BigDecimal paymentAmount) {
        PaymentResponse response = new PaymentResponse(ServiceResponse.ERROR, ServiceResponse.GENERAL_ERROR_MESSAGE);
        try {
            if(paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
                return new PaymentResponse(ServiceResponse.ERROR, "Invalid payment amount");
            }
            logger.info("fees " + fees);
            BigDecimal dynamicRate = BigDecimal.valueOf(fees); // 5% fee
            BigDecimal finalAmount = paymentAmount.multiply(BigDecimal.ONE.add(dynamicRate));
            logger.info("Final amount after fees: " + finalAmount);
            logger.info("Payment amount: " + paymentAmount);

            Parent payer = parentRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Parent not found for parent ID: " + parentId));
            Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found for student ID: " + studentId));
            if(student.getBalance().compareTo(BigDecimal.ZERO) == 0) {
                return new PaymentResponse(ServiceResponse.ERROR, "Student has no outstanding payments");
            }
            if(paymentAmount.compareTo(student.getBalance().abs()) > 0) {
                return new PaymentResponse(ServiceResponse.ERROR, "Payment amount exceeds student balance");
            }

            List<ParentChild> parentChildren = parentChildRepository.listByStudentId(studentId);
            //call transfer Api, if successful, continue else return error message
            if (parentChildren.size() > 1) { // check is done assuming parents can just  be 1 or 2
                Parent parentA = payer;
                Long secondParentId = parentChildren.stream()
                        .filter(pc -> !pc.getParentId().equals(parentId))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Second parent not found"))
                        .getParentId();

                Parent parentB = parentRepository.findById(secondParentId)
                        .orElseThrow(() -> new RuntimeException("Parent not found for ID: " + secondParentId));

                // Determine how much each can pay
                BigDecimal aCanPay = parentA.getBalance().min(finalAmount);
                BigDecimal bNeedsToPay = finalAmount.subtract(aCanPay);

                // Make sure B can cover the remainder
                if (parentB.getBalance().compareTo(bNeedsToPay) < 0) {
                    logger.info("Combined balance is insufficient for parent ID: " + parentId + " with balance: " + payer.getBalance() + " and final amount: " + finalAmount);
                    return new PaymentResponse(ServiceResponse.ERROR, "Combined balance is insufficient for parent ID: " + parentId + " with balance: " + payer.getBalance() + " and final amount: " + finalAmount  );
                }

                // Deduct amounts
                parentA.setBalance(parentA.getBalance().subtract(aCanPay));
                parentB.setBalance(parentB.getBalance().subtract(bNeedsToPay));

                // Save updated balances
                parentRepository.save(parentA);
                parentRepository.save(parentB);
            } else {
                if(payer.getBalance().compareTo(finalAmount) < 0) {
                    logger.info("Insufficient balance for parent ID: " + parentId + " with balance: " + payer.getBalance() + " and final amount: " + finalAmount);
                    return new PaymentResponse(ServiceResponse.ERROR, "Insufficient balance for parent ID: " + parentId);
                }
                payer.setBalance(payer.getBalance().subtract(finalAmount));
                parentRepository.save(payer);
            }
            student.setBalance(student.getBalance().add(paymentAmount));
            studentRepository.save(student);
            logger.info("Student details updated");

            Transaction transaction = new Transaction();
            transaction.setParentId(parentId);
            transaction.setStudentId(studentId);
            transaction.setPaymentAmount(paymentAmount);
            transaction.setFinalAmount(finalAmount);
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType(TransactionType.SCHOOL_FEES.name());
            transactionRepository.save(transaction);

            logger.info("Payment for school fees for studentID " + studentId + " was made with amount " + paymentAmount + " by parentId " + parentId + " with final amount " + finalAmount);
            //make an email implementation to either both/single parents, {paymentAmount} was deducted from single/both parents . this {parentID} initiated the request
            //Audit Trail Implementation as well
            return new PaymentResponse(ServiceResponse.SUCCESS, ServiceResponse.GENERAL_SUCCESS_MESSAGE);
        } catch (Exception e ) {
            logger.info("Error occurred while making payment " + e.getMessage());
            return response;
        }
    }
}
