package bstefanov.transportOrg.dto;

import bstefanov.transportOrg.entity.VehicleType;

import java.math.BigDecimal;

public class VehicleDto {
    private long id;
    private String licensePlate;
    private VehicleType type;
    private String brand;
    private String model;
    private BigDecimal fuelPricePerKM;
    private long companyId;

    public VehicleDto(long id, String licensePlate, VehicleType type, String brand, String model,
                      BigDecimal fuelPricePerKM, long companyId) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.fuelPricePerKM = fuelPricePerKM;
        this.companyId = companyId;
    }

    public VehicleDto(String licensePlate, VehicleType type, String brand, String model,
                      BigDecimal fuelPricePerKM, long companyId) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.fuelPricePerKM = fuelPricePerKM;
        this.companyId = companyId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "VehicleDto{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", type=" + type +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", fuelPricePerKM=" + fuelPricePerKM +
                ", companyId=" + companyId +
                '}';
    }
}
