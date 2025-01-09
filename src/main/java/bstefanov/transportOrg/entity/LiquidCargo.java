package bstefanov.transportOrg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
public class LiquidCargo extends NormalCargo{
    @Positive
    @Column(name = "liters", nullable = false)
    private int liters;

    public LiquidCargo() {
    }

    public LiquidCargo(Transit transit, BigDecimal weight, int liters) {
        super(transit, weight);
        this.liters = liters;
    }

    public int getLiters() {
        return liters;
    }

    public void setLiters(int liters) {
        this.liters = liters;
    }
}
