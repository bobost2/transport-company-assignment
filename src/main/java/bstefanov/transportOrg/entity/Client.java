package bstefanov.transportOrg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Client extends Person{
    @Column(name = "is_legal_entity", nullable = false)
    private boolean isLegalEntity;

    @Column(name = "company_name", length = 60)
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
