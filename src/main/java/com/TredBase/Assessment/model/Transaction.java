package com.TredBase.Assessment.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


//@Getter
//@Setter
@Entity
//@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;
    private Long studentId;
    private BigDecimal paymentAmount;
    private BigDecimal finalAmount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    private String transactionType;

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", studentId=" + studentId +
                ", paymentAmount=" + paymentAmount +
                ", finalAmount=" + finalAmount +
                ", transactionDate=" + transactionDate +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
