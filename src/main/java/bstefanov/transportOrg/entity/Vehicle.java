package bstefanov.transportOrg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Vehicle extends BaseEntity{
    @Column(name="license_plate", nullable = false, length = 15)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable = false, length = 10)
    private VehicleType type;

    @Column(name="brand", nullable = false, length = 20)
    private String brand;

    @Column(name="model", nullable = false, length = 20)
    private String model;

    @Digits(integer = 2, fraction = 4) // Should not exceed 99.9999, that would be absurd
    @Column(name="fuel_price_per_KM", nullable = false)
    private BigDecimal fuelPricePerKM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "vehicle")
    private Set<Transit> transits;

    public Vehicle() {

    }

    public Vehicle(String licensePlate, VehicleType type, String brand, String model,
                   BigDecimal fuelPricePerKM, Company company) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.fuelPricePerKM = fuelPricePerKM;
        this.company = company;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getFuelPricePerKM() {
        return fuelPricePerKM;
    }

    public void setFuelPricePerKM(BigDecimal fuelPricePerKM) {
        this.fuelPricePerKM = fuelPricePerKM;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Transit> getTransits() {
        return transits;
    }

    public void setTransits(Set<Transit> transits) {
        this.transits = transits;
    }
}
