package com.solvd.taxi.service.interfaces;

import com.solvd.taxi.model.EmergencyContact;
import com.solvd.taxi.model.Notification;
import java.util.List;

public interface JsonService {
    List<EmergencyContact> parseEmergencyContacts(String filePath);
    List<Notification> parseNotifications(String filePath);
    void writeEmergencyContacts(List<EmergencyContact> contacts, String filePath);
    void writeNotifications(List<Notification> notifications, String filePath);
}