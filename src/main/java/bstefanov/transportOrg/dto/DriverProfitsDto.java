package bstefanov.transportOrg.dto;

import java.math.BigDecimal;

public class DriverProfitsDto {
    private String firstName;
    private String lastName;
    private BigDecimal profits;

    public DriverProfitsDto(String firstName, String lastName, BigDecimal profits) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profits = profits;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getProfits() {
        return profits;
    }

    public void setProfits(BigDecimal profits) {
        this.profits = profits;
    }

    @Override
    public String toString() {
        return "DriverProfitsDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profits=" + profits +
                '}';
    }
}
