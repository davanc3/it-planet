package ru.vantsyn.it.planet.model.dto.account;

import jakarta.validation.constraints.Min;

public class AccountSearchFilterDTO {
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    @Min(0)
    private int from = 0;
    @Min(1)
    private int size = 10;

    public AccountSearchFilterDTO() {
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

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "AccountSearchDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", from=" + from +
                ", size=" + size +
                '}';
    }
}
