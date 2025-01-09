package bstefanov.transportOrg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
public class NormalCargo extends BaseCargo {
    @Positive
    @Column(name = "weight", nullable = false)
    @Digits(integer = 4, fraction = 3)
    private BigDecimal weight;

    public NormalCargo() {
    }

    public NormalCargo(Transit transit, BigDecimal weight) {
        super(transit);
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
