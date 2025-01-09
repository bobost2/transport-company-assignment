package bstefanov.transportOrg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee extends Person{
    @Positive
    @Digits(integer = 3, fraction = 2)
    @Column(name = "pay_rate_by_hour", nullable = false)
    private BigDecimal payRateByHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "EmployeeHasQualification",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "qualification_id")
    )
    private Set<Qualification> qualifications = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<Transit> transits;

    public Employee() {
    }

    public Employee(String firstName, String lastName, BigDecimal payRateByHour, Company company) {
        super(firstName, lastName);
        this.payRateByHour = payRateByHour;
        this.company = company;
    }

    public BigDecimal getPayRateByHour() {
        return payRateByHour;
    }

    public void setPayRateByHour(BigDecimal payRateByHour) {
        this.payRateByHour = payRateByHour;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public Set<Transit> getTransits() {
        return transits;
    }

    public void setTransits(Set<Transit> transits) {
        this.transits = transits;
    }
}
