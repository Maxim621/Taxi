package com.solvd.taxi.service.impl;

import com.solvd.taxi.model.Promocodes;
import com.solvd.taxi.model.PromoCode;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.List;

public class JaxbService {

    private static final String XSD_SCHEMA = "src/main/resources/promocodes.xsd";

    public List<PromoCode> parsePromoCodesWithValidation(String xmlFilePath) {
        try {
            // Validation before parsing
            validateXmlAgainstXsd(xmlFilePath);

            // Parsing with JAXB
            JAXBContext context = JAXBContext.newInstance(Promocodes.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Promocodes promocodes = (Promocodes) unmarshaller.unmarshal(new File(xmlFilePath));
            return promocodes.getPromoCodes();

        } catch (Exception e) {
            throw new RuntimeException("JAXB parsing failed: " + e.getMessage(), e);
        }
    }

    private void validateXmlAgainstXsd(String xmlFilePath) throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = factory.newSchema(new File(XSD_SCHEMA));

        JAXBContext context = JAXBContext.newInstance(Promocodes.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);

        // Validation occurs during unmarshal
        unmarshaller.unmarshal(new File(xmlFilePath));
    }

    public void exportPromoCodesToXml(List<PromoCode> promoCodes, String xmlFilePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(Promocodes.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Promocodes wrapper = new Promocodes(promoCodes);
            marshaller.marshal(wrapper, new File(xmlFilePath));

        } catch (JAXBException e) {
            throw new RuntimeException("JAXB export failed: " + e.getMessage(), e);
        }
    }
}