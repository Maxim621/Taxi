package com.solvd.taxi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("notification")
public class Notification {
    @JsonProperty("notificationId")
    private int notificationId;

    @JsonProperty("userId")
    private int userId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("read")
    private boolean isRead;

    // Constructors, getters, setters, toString...
    public Notification() {}

    public Notification(int userId, String message) {
        this.userId = userId;
        this.message = message;
        this.isRead = false;
    }

    // Getters and setters...
    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}