package com.TredBase.Assessment.vo.request;

import java.math.BigDecimal;

//@Getter
//@Setter
//@NoArgsConstructor
public class TransactionRequest {

    private Long parentId;
    private Long studentId;
    private BigDecimal paymentAmount;

    public TransactionRequest() {
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

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "parentId=" + parentId +
                ", studentId=" + studentId +
                ", paymentAmount=" + paymentAmount +
                '}';
    }
}
