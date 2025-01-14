package bstefanov.transportOrg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;

@Entity
public class PeopleTransport extends BaseCargo {
    @Positive
    @Column(name = "passengers")
    private int passengers;

    public PeopleTransport() {
    }

    public PeopleTransport(int passengers) {
        this.passengers = passengers;
    }

    public PeopleTransport(Transit transit, int passengers) {
        super(transit);
        this.passengers = passengers;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }
}
