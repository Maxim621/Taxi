package com.solvd.taxi.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE;

    @Override
    public LocalDate unmarshal(String dateString) throws Exception {
        return dateString != null ? LocalDate.parse(dateString, DATE_FORMAT) : null;
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate != null ? localDate.format(DATE_FORMAT) : null;
    }
}
