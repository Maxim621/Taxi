package com.solvd.taxi.util;

import com.solvd.taxi.model.PromoCode;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class PromoCodeSaxHandler extends DefaultHandler {
    private List<PromoCode> promoCodes;
    private PromoCode currentPromoCode;
    private StringBuilder currentValue;

    @Override
    public void startDocument() {
        promoCodes = new ArrayList<>();
        currentValue = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentValue.setLength(0);
        if ("promocode".equals(qName)) {
            currentPromoCode = new PromoCode();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        currentValue.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "promocode":
                promoCodes.add(currentPromoCode);
                break;
            case "id":
                currentPromoCode.setPromoId(Integer.parseInt(currentValue.toString().trim()));
                break;
            case "code":
                currentPromoCode.setCode(currentValue.toString().trim());
                break;
            case "discount":
                currentPromoCode.setDiscount(Double.parseDouble(currentValue.toString().trim()));
                break;
            case "expiryDate":
                currentPromoCode.setExpiryDate(java.time.LocalDate.parse(currentValue.toString().trim()));
                break;
        }
    }

    public List<PromoCode> getPromoCodes() {
        return promoCodes;
    }
}