package com.solvd.taxi.model;

import java.time.LocalDateTime;

public class SupportTicket {
    private int ticketId;
    private int passengerId;
    private String subject;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    public SupportTicket() {}

    public SupportTicket(int passengerId, String subject, String message) {
        this.passengerId = passengerId;
        this.subject = subject;
        this.message = message;
        this.status = "open";
        this.createdAt = LocalDateTime.now();
    }

    public SupportTicket(int ticketId, int passengerId, String subject,
                         String message, String status, LocalDateTime createdAt) {
        this.ticketId = ticketId;
        this.passengerId = passengerId;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        if ("resolved".equals(status) && resolvedAt == null) {
            this.resolvedAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    // Business logic
    public boolean isOpen() {
        return "open".equals(status);
    }

    public boolean isResolved() {
        return "resolved".equals(status);
    }

    public void resolve() {
        setStatus("resolved");
    }

    public void reopen() {
        setStatus("open");
        this.resolvedAt = null;
    }

    public long getResolutionTimeInHours() {
        if (isResolved() && resolvedAt != null && createdAt != null) {
            return java.time.Duration.between(createdAt, resolvedAt).toHours();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "ticketId=" + ticketId +
                ", passengerId=" + passengerId +
                ", subject='" + subject + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", resolvedAt=" + resolvedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupportTicket that = (SupportTicket) o;
        return ticketId == that.ticketId &&
                passengerId == that.passengerId;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(ticketId, passengerId);
    }
}