package bstefanov.transportOrg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Qualification extends BaseEntity{
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @ManyToMany(mappedBy = "qualifications")
    private Set<Employee> employees;

    @ManyToMany(mappedBy = "qualifications")
    private Set<BaseCargo> cargo;
}
