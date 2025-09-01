// Wrapper classes for correct parsing of JSON with a root element
package com.solvd.taxi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class EmergencyContacts {
    @JsonProperty("emergencyContacts")
    private List<EmergencyContact> emergencyContacts;

    public EmergencyContacts() {}

    public List<EmergencyContact> getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(List<EmergencyContact> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }
}