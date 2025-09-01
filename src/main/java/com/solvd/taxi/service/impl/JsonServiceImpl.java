package com.solvd.taxi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.taxi.model.EmergencyContact;
import com.solvd.taxi.model.EmergencyContacts;
import com.solvd.taxi.model.Notification;
import com.solvd.taxi.model.Notifications;
import com.solvd.taxi.service.interfaces.JsonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonServiceImpl implements JsonService {
    private static final String EMERGENCY_CONTACTS_PATH = "src/main/resources/emergency_contacts.json";
    private static final String NOTIFICATIONS_PATH = "src/main/resources/notifications.json";
    private static final Logger logger = LogManager.getLogger(JsonServiceImpl.class);
    private final ObjectMapper objectMapper;

    public JsonServiceImpl() {
        this.objectMapper = new ObjectMapper();
        // To work correctly with LocalDate/LocalDateTime
        objectMapper.findAndRegisterModules();
    }

    @Override
    public List<EmergencyContact> parseEmergencyContacts(String filePath) {
        try {
            EmergencyContacts contacts = objectMapper.readValue(new File(filePath), EmergencyContacts.class);
            logger.info("Parsed {} emergency contacts from JSON", contacts.getEmergencyContacts().size());
            return contacts.getEmergencyContacts();
        } catch (IOException e) {
            logger.error("Failed to parse emergency contacts from JSON: {}", e.getMessage(), e);
            return List.of();
        }
    }

    @Override
    public List<Notification> parseNotifications(String filePath) {
        try {
            Notifications notifications = objectMapper.readValue(new File(filePath), Notifications.class);
            logger.info("Parsed {} notifications from JSON", notifications.getNotifications().size());
            return notifications.getNotifications();
        } catch (IOException e) {
            logger.error("Failed to parse notifications from JSON: {}", e.getMessage(), e);
            return List.of();
        }
    }

    @Override
    public void writeEmergencyContacts(List<EmergencyContact> contacts, String filePath) {
        try {
            EmergencyContacts wrapper = new EmergencyContacts();
            wrapper.setEmergencyContacts(contacts);
            objectMapper.writeValue(new File(filePath), wrapper);
            logger.info("Written {} emergency contacts to JSON", contacts.size());
        } catch (IOException e) {
            logger.error("Failed to write emergency contacts to JSON: {}", e.getMessage(), e);
        }
    }

    @Override
    public void writeNotifications(List<Notification> notifications, String filePath) {
        try {
            Notifications wrapper = new Notifications();
            wrapper.setNotifications(notifications);
            objectMapper.writeValue(new File(filePath), wrapper);
            logger.info("Written {} notifications to JSON", notifications.size());
        } catch (IOException e) {
            logger.error("Failed to write notifications to JSON: {}", e.getMessage(), e);
        }
    }
}