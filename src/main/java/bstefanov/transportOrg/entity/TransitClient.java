package bstefanov.transportOrg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
public class TransitClient extends BaseEntity{
    @Positive
    @Column(name = "amount_to_pay", nullable = false)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amountToPay;

    @ColumnDefault("false")
    @Column(name = "has_paid", nullable = false)
    private boolean hasPaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transit_id", nullable = false)
    private Transit transit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public TransitClient() {
    }

    public TransitClient(BigDecimal amountToPay, boolean hasPaid, Transit transit, Client client) {
        this.amountToPay = amountToPay;
        this.hasPaid = hasPaid;
        this.transit = transit;
        this.client = client;
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public Transit getTransit() {
        return transit;
    }

    public void setTransit(Transit transit) {
        this.transit = transit;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
