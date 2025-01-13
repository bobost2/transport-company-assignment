package bstefanov.transportOrg.entity;

import bstefanov.transportOrg.validator.Coordinates;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
public class Company extends BaseEntity {
    @NotBlank(message = "The company name cannot be blank!")
    @Size(max = 40, message = "The company name cannot be longer than 40 characters!")
    @Pattern(regexp = "^([A-Z]).*", message = "Company name has to start with a capital letter!")
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @NotBlank(message = "The company address cannot be blank!")
    @Size(max = 30, message = "The company address cannot be longer than 30 characters!")
    @Coordinates
    @Column(name = "address", nullable = false, length = 30)
    private String address;

    @OneToMany(mappedBy = "company")
    private Set<Employee> employees;

    @OneToMany(mappedBy = "company")
    private Set<Vehicle> vehicles;

    @OneToMany(mappedBy = "company")
    private Set<Transit> transits;

    public Company() {
    }

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Set<Transit> getTransits() {
        return transits;
    }

    public void setTransits(Set<Transit> transits) {
        this.transits = transits;
    }
}
