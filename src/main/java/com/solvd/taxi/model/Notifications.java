// Wrapper classes for correct parsing of JSON with a root element
package com.solvd.taxi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Notifications {
    @JsonProperty("notifications")
    private List<Notification> notifications;

    public Notifications() {}

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}