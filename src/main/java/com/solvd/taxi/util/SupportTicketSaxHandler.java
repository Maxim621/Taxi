package com.solvd.taxi.util;
import com.solvd.taxi.model.SupportTicket;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SAX handler for parsing SupportTicket objects from XML
 * Processes supporttickets.xml file and converts XML elements to SupportTicket objects
 */
public class SupportTicketSaxHandler extends DefaultHandler {
    private List<SupportTicket> supportTickets;
    private SupportTicket currentTicket;
    private StringBuilder currentValue;

    /**
     * Called when XML parsing starts
     * Initializes collections and buffers
     */
    @Override
    public void startDocument() {
        supportTickets = new ArrayList<>();
        currentValue = new StringBuilder();
    }

    // Called when a new XML element is encountered
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentValue.setLength(0); // Clear the buffer

        if ("ticket".equals(qName)) {
            currentTicket = new SupportTicket();
        }
    }

    // Processes character data inside XML elements
    @Override
    public void characters(char[] ch, int start, int length) {
        currentValue.append(ch, start, length);
    }

    /**
     * Called when an XML element ends
     * Maps XML data to SupportTicket object properties
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        String value = currentValue.toString().trim();

        switch (qName) {
            case "ticket":
                supportTickets.add(currentTicket);
                break;
            case "ticketId":
                currentTicket.setTicketId(Integer.parseInt(value));
                break;
            case "passengerId":
                currentTicket.setPassengerId(Integer.parseInt(value));
                break;
            case "subject":
                currentTicket.setSubject(value);
                break;
            case "message":
                currentTicket.setMessage(value);
                break;
            case "status":
                currentTicket.setStatus(value);
                break;
            case "createdAt":
                currentTicket.setCreatedAt(LocalDateTime.parse(value));
                break;
            case "resolvedAt":
                if (!value.isEmpty()) {
                    currentTicket.setResolvedAt(LocalDateTime.parse(value));
                }
                break;
        }
    }

    // Returns the list of parsed SupportTicket objects
    public List<SupportTicket> getSupportTickets() {
        return supportTickets;
    }
}