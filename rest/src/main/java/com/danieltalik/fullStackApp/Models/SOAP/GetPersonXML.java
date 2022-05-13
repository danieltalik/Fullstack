package com.danieltalik.fullStackApp.Models.SOAP;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="",propOrder = {
        "person"
})

@XmlRootElement(name="getPerson")
public class GetPersonXML{

    @XmlElement(name = "person")
    protected PersonXML person;

    public PersonXML getPerson() {
        return person;
    }

    public void setPerson(PersonXML person) {
        this.person = person;
    }
}
