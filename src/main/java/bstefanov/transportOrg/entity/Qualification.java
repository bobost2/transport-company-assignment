package bstefanov.transportOrg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
public class Qualification extends BaseEntity{
    @NotBlank(message = "The qualification name cannot be empty!")
    @Size(max = 30, message = "The qualification name cannot be longer than 30 characters!")
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @ManyToMany(mappedBy = "qualifications")
    private Set<Employee> employees;

    @ManyToMany(mappedBy = "qualifications")
    private Set<BaseCargo> cargo;

    public Qualification() {
    }

    public Qualification(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<BaseCargo> getCargo() {
        return cargo;
    }

    public void setCargo(Set<BaseCargo> cargo) {
        this.cargo = cargo;
    }
}
