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
}
