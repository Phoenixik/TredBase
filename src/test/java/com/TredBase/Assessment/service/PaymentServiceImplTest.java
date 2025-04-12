package com.TredBase.Assessment.service;

import com.TredBase.Assessment.model.Parent;
import com.TredBase.Assessment.model.ParentChild;
import com.TredBase.Assessment.model.Student;
import com.TredBase.Assessment.model.Transaction;
import com.TredBase.Assessment.repository.ParentChildRepository;
import com.TredBase.Assessment.repository.ParentRepository;
import com.TredBase.Assessment.repository.StudentRepository;
import com.TredBase.Assessment.repository.TransactionRepository;
import com.TredBase.Assessment.service.Impl.PaymentServiceImpl;
import com.TredBase.Assessment.vo.response.PaymentResponse;
import com.TredBase.Assessment.vo.response.ServiceResponse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ParentChildRepository parentChildRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private Parent parentA;
    private Parent parentB;
    private Student student;

    @BeforeEach
    void setUp() {
        parentA = new Parent();
        parentA.setId(1L);
        parentA.setFullName("Parent A");
        parentA.setBalance(new BigDecimal("40000"));

        parentB = new Parent();
        parentB.setId(2L);
        parentB.setFullName("Parent B");
        parentB.setBalance(new BigDecimal("100000"));
//        parentB.setBalance(new BigDecimal("10000"));

        student = new Student();
        student.setId(1L);
        student.setFullName("Shared Student");
        student.setBalance(new BigDecimal("-90000"));
    }

    @Test
    void testSuccessSharedPaymentWithFallbackToSecondParent() {
        BigDecimal paymentAmount = new BigDecimal("90000");

        ReflectionTestUtils.setField(paymentService, "fees", 0.05);
        when(parentRepository.findById(1L)).thenReturn(Optional.of(parentA));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(parentChildRepository.listByStudentId(1L)).thenReturn(List.of(
                new ParentChild(1L, 1L),
                new ParentChild(2L, 1L)
        ));
        when(parentRepository.findById(2L)).thenReturn(Optional.of(parentB));

        PaymentResponse paymentResponse = paymentService.makeSchoolFeesPayment(1L, 1L, paymentAmount);

        assertEquals(ServiceResponse.SUCCESS, paymentResponse.getStatus());
    }

    //this works and can be tested
//    @Test
//    void testFailureSharedPaymentWithFallbackToSecondParent() {
//        //to test the case where the second parent does not have enough balance, uncomment line 64 and comment line 63
//        BigDecimal paymentAmount = new BigDecimal("90000");
//
//        ReflectionTestUtils.setField(paymentService, "fees", 0.05);
//        when(parentRepository.findById(1L)).thenReturn(Optional.of(parentA));
//        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(parentChildRepository.listByStudentId(1L)).thenReturn(List.of(
//                new ParentChild(1L, 1L),
//                new ParentChild(2L, 1L)
//        ));
//        when(parentRepository.findById(2L)).thenReturn(Optional.of(parentB));
//
//        PaymentResponse paymentResponse = paymentService.makeSchoolFeesPayment(1L, 1L, paymentAmount);
//
//        assertEquals(ServiceResponse.ERROR, paymentResponse.getStatus());
//    }

    @Test
    void testFailedUniquePayment() {
        BigDecimal paymentAmount = new BigDecimal("90000");
        ReflectionTestUtils.setField(paymentService, "fees", 0.05);
        when(parentRepository.findById(1L)).thenReturn(Optional.of(parentA));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(parentChildRepository.listByStudentId(1L)).thenReturn(List.of(
                new ParentChild(1L, 1L)
        ));

        PaymentResponse paymentResponse = paymentService.makeSchoolFeesPayment(1L, 1L, paymentAmount);

        assertEquals(ServiceResponse.ERROR, paymentResponse.getStatus());
    }
    @Test
    void testSuccessUniquePayment() {
        BigDecimal paymentAmount = new BigDecimal("90000");
        ReflectionTestUtils.setField(paymentService, "fees", 0.05);
        when(parentRepository.findById(2L)).thenReturn(Optional.of(parentB));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(parentChildRepository.listByStudentId(1L)).thenReturn(List.of(
                new ParentChild(2L, 1L)
        ));

        PaymentResponse paymentResponse = paymentService.makeSchoolFeesPayment(2L, 1L, paymentAmount);

        assertEquals(ServiceResponse.SUCCESS, paymentResponse.getStatus());
    }
}
