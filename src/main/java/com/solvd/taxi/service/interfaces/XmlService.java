package com.solvd.taxi.service.interfaces;

import com.solvd.taxi.model.PromoCode;
import com.solvd.taxi.model.SupportTicket;
import java.util.List;

public interface XmlService {
    List<PromoCode> parsePromoCodes(String xmlFilePath);
    List<PromoCode> parsePromoCodesWithJaxb(String xmlFilePath);
    List<SupportTicket> parseSupportTickets(String xmlFilePath);
    void exportPromoCodesToXml(List<PromoCode> promoCodes, String xmlFilePath);
    void exportSupportTicketsToXml(List<SupportTicket> tickets, String xmlFilePath);
}