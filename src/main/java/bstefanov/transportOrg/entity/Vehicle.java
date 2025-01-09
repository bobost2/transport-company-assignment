package bstefanov.transportOrg.entity;

import jakarta.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "vehicle")
    private Set<Transit> transits;
}
