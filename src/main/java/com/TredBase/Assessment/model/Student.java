package com.TredBase.Assessment.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

//@Getter
//@Setter
@Entity
//@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;
    private String fullName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", balance=" + balance +
                ", fullName='" + fullName + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
