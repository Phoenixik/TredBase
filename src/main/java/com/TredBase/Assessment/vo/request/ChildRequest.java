package com.TredBase.Assessment.vo.request;

import java.math.BigDecimal;

//@Getter
//@Setter
//@NoArgsConstructor
public class ChildRequest {
    private String name;

    private BigDecimal balance; //always negative, setting the balance each student owes at the beginning of the session/term

    public ChildRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ChildRequest{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
