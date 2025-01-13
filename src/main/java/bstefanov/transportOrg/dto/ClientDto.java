package bstefanov.transportOrg.dto;

public class ClientDto {
    private long id;
    private String firstName;
    private String lastName;
    private boolean isLegalEntity;
    private String companyName;

    public ClientDto(long id, String firstName, String lastName, String companyName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isLegalEntity = true;
        this.companyName = companyName;
    }

    public ClientDto(String firstName, String lastName, String companyName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isLegalEntity = true;
        this.companyName = companyName;
    }

    public ClientDto(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isLegalEntity = false;
    }

    public ClientDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isLegalEntity = false;
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

    @Override
    public String toString() {
        return "ClientDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isLegalEntity=" + isLegalEntity +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
