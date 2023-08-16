package ru.vantsyn.it.planet.model.dto.account;

import ru.vantsyn.it.planet.model.entity.Role;

public class AccountResultDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public AccountResultDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role.getName();
    }
}
