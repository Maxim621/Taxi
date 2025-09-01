package com.solvd.taxi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("emergencyContact")
public class EmergencyContact {
    @JsonProperty("contactId")
    private int contactId;

    @JsonProperty("passengerId")
    private int passengerId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    // Constructors, getters, setters, toString...
    public EmergencyContact() {}

    public EmergencyContact(int passengerId, String name, String phone) {
        this.passengerId = passengerId;
        this.name = name;
        this.phone = phone;
    }

    // Getters and setters...
    public int getContactId() { return contactId; }
    public void setContactId(int contactId) { this.contactId = contactId; }

    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "EmergencyContact{" +
                "contactId=" + contactId +
                ", passengerId=" + passengerId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}