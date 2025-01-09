package bstefanov.transportOrg.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Transit extends BaseEntity {
    @Column(name = "starting_point", nullable = false, length = 100)
    private String startingPoint;

    @Column(name = "destination_point", nullable = false, length = 100)
    private String destinationPoint;

    @Column(name = "distance", nullable = false)
    private int distance;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "transit")
    private Set<TransitSpendings> transitSpendings;

    @OneToMany(mappedBy = "transit")
    private Set<BaseCargo> cargo;
}
