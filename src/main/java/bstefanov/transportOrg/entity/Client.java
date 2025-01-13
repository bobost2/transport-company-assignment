package bstefanov.transportOrg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
public class Client extends Person{
    @Column(name = "is_legal_entity", nullable = false)
    private boolean isLegalEntity;

    @Size(max = 40, message = "The company name cannot be longer than 40 characters!")
    @Pattern(regexp = "^([A-Z]).*", message = "Company name has to start with a capital letter!")
    @Column(name = "company_name", length = 40)
    private String companyName;

    @OneToMany(mappedBy = "client")
    private Set<TransitClient> clientTransits;

    public Client() {
    }

    public Client(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Client(String firstName, String lastName, String companyName) {
        super(firstName, lastName);
        this.isLegalEntity = true;
        this.companyName = companyName;
    }

    public boolean isLegalEntity() {
        return isLegalEntity;
    }

    public void setLegalEntity(boolean legalEntity) {
        isLegalEntity = legalEntity;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<TransitClient> getClientTransits() {
        return clientTransits;
    }

    public void setClientTransits(Set<TransitClient> clientTransits) {
        this.clientTransits = clientTransits;
    }
}
