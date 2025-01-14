package bstefanov.transportOrg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

@Entity
public class TransitSpendings extends BaseEntity {
    @Digits(integer = 6, fraction = 2)
    @Column(name="amount", nullable = false)
    private BigDecimal amount;

    @Column(name="reason", nullable = false, length = 60)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transit_id", nullable = false)
    private Transit transit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public TransitSpendings() {

    }

    public TransitSpendings(BigDecimal amount, String reason, Transit transit) {
        this.amount = amount;
        this.reason = reason;
        this.transit = transit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Transit getTransit() {
        return transit;
    }

    public void setTransit(Transit transit) {
        this.transit = transit;
    }
}
