package com.TredBase.Assessment.vo.request;

import java.math.BigDecimal;

public class ParentRequest {

    private String fullName;
    private String email;
    private String phoneNumber;
    private BigDecimal parentBalance;

    public ParentRequest() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getParentBalance() {
        return parentBalance;
    }

    public void setParentBalance(BigDecimal parentBalance) {
        this.parentBalance = parentBalance;
    }

    @Override
    public String toString() {
        return "ParentRequest{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", parentBalance=" + parentBalance +
                '}';
    }
}
