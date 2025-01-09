package bstefanov.transportOrg.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="cargo_type", discriminatorType = DiscriminatorType.STRING)
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

    public BaseCargo() {
    }

    public BaseCargo(Transit transit) {
        this.transit = transit;
    }

    public BaseCargo(Transit transit, Set<Qualification> qualifications) {
        this.transit = transit;
        this.qualifications = qualifications;
    }

    public Transit getTransit() {
        return transit;
    }

    public void setTransit(Transit transit) {
        this.transit = transit;
    }

    public Set<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
    }
}
