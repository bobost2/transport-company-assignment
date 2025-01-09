package bstefanov.transportOrg.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class BaseCargo extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "transit_id", nullable = false)
    private Transit transit;

    @ManyToMany
    @JoinTable(
            name = "CargoRequiresQualification",
            joinColumns = @JoinColumn(name = "cargo_id"),
            inverseJoinColumns = @JoinColumn(name = "qualification_id")
    )
    private Set<Qualification> qualifications = new HashSet<>();
}
