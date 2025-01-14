package bstefanov.transportOrg.dto;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDto {
    private long id;
    private String firstName;
    private String lastName;
    private BigDecimal payRateByHour;
    private List<QualificationDto> qualifications;
    private long companyId;

    public EmployeeDto(long id, String firstName, String lastName, BigDecimal payRateByHour,
                       List<QualificationDto> qualifications, long companyId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.payRateByHour = payRateByHour;
        this.qualifications = qualifications;
        this.companyId = companyId;
    }

    public EmployeeDto(String firstName, String lastName, BigDecimal payRateByHour,
                       List<QualificationDto> qualifications, long companyId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.payRateByHour = payRateByHour;
        this.qualifications = qualifications;
        this.companyId = companyId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public BigDecimal getPayRateByHour() {
        return payRateByHour;
    }

    public void setPayRateByHour(BigDecimal payRateByHour) {
        this.payRateByHour = payRateByHour;
    }

    public List<QualificationDto> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<QualificationDto> qualifications) {
        this.qualifications = qualifications;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", payRateByHour=" + payRateByHour +
                ", qualifications=" + qualifications +
                ", companyId=" + companyId +
                '}';
    }
}
