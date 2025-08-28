package com.solvd.taxi.service.impl;

import com.solvd.taxi.service.interfaces.XmlService;
import com.solvd.taxi.model.PromoCode;
import com.solvd.taxi.model.SupportTicket;
import com.solvd.taxi.util.PromoCodeSaxHandler;
import com.solvd.taxi.util.SupportTicketSaxHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

/**
 * Service implementation for XML operations using SAX parser
 * Handles parsing of PromoCode and SupportTicket XML files
 */
public class XmlServiceImpl implements XmlService {

    private final JaxbService jaxbService = new JaxbService();

    // Parses promocodes from XML file using SAX parser
    @Override
    public List<PromoCode> parsePromoCodes(String xmlFilePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PromoCodeSaxHandler handler = new PromoCodeSaxHandler();

            saxParser.parse(new File(xmlFilePath), handler);
            return handler.getPromoCodes();

        } catch (Exception e) {
            throw new RuntimeException("Error parsing promocodes XML: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PromoCode> parsePromoCodesWithJaxb(String xmlFilePath) {
        return jaxbService.parsePromoCodesWithValidation(xmlFilePath);
    }

    // Parses support tickets from XML file using SAX parser
    @Override
    public List<SupportTicket> parseSupportTickets(String xmlFilePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SupportTicketSaxHandler handler = new SupportTicketSaxHandler();

            saxParser.parse(new File(xmlFilePath), handler);
            return handler.getSupportTickets();

        } catch (Exception e) {
            throw new RuntimeException("Error parsing support tickets XML: " + e.getMessage(), e);
        }
    }

    @Override
    public void exportPromoCodesToXml(List<PromoCode> promoCodes, String xmlFilePath) {
        throw new UnsupportedOperationException("Export functionality not implemented yet");
    }

    @Override
    public void exportSupportTicketsToXml(List<SupportTicket> tickets, String xmlFilePath) {
        throw new UnsupportedOperationException("Export functionality not implemented yet");
    }
}