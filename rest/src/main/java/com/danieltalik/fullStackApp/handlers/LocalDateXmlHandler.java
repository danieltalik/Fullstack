package com.danieltalik.fullStackApp.handlers;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateXmlHandler extends XmlAdapter<String, LocalDate> {
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
