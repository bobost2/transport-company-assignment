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

    @OneToMany(mappedBy = "transit")
    private Set<TransitClient> clientTransits;

    public Transit() {
    }

    public Transit(String startingPoint, String destinationPoint, int distance, LocalDateTime departureTime,
                   LocalDateTime arrivalTime, Company company, Vehicle vehicle, Employee employee) {
        this.startingPoint = startingPoint;
        this.destinationPoint = destinationPoint;
        this.distance = distance;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.company = company;
        this.vehicle = vehicle;
        this.employee = employee;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<TransitSpendings> getTransitSpendings() {
        return transitSpendings;
    }

    public void setTransitSpendings(Set<TransitSpendings> transitSpendings) {
        this.transitSpendings = transitSpendings;
    }

    public Set<BaseCargo> getCargo() {
        return cargo;
    }

    public void setCargo(Set<BaseCargo> cargo) {
        this.cargo = cargo;
    }

    public Set<TransitClient> getClientTransits() {
        return clientTransits;
    }

    public void setClientTransits(Set<TransitClient> clientTransits) {
        this.clientTransits = clientTransits;
    }
}
