package bstefanov.transportOrg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public class Person extends BaseEntity {
    @NotBlank(message = "The first name cannot be blank!")
    @Size(max = 25, message = "The first name cannot be longer than 25 characters!")
    @Pattern(regexp = "^([A-Z]).*", message = "The first name has to start with a capital letter!")
    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @NotBlank(message = "The last name cannot be blank!")
    @Size(max = 25, message = "The last name cannot be longer than 25 characters!")
    @Pattern(regexp = "^([A-Z]).*", message = "The last name has to start with a capital letter!")
    @Column(name = "last_name", nullable = false, length = 25)
    private String lastName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
}
