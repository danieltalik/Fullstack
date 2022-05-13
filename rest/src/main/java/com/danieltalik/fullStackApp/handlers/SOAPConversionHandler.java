package com.danieltalik.fullStackApp.handlers;

import com.danieltalik.fullStackApp.Models.SOAP.GetPersonXML;
import com.danieltalik.fullStackApp.Models.SOAP.PersonXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SOAPConversionHandler {

    public PersonXML convertToPOJO(String request) {
        SOAPMessage message = soapMessageCreation(request);
        JAXBContext context = jaxbContextCreation();
        Unmarshaller unmarshaller = unmarshallerCreation(context);
        GetPersonXML rootContext = rootElementCreation(unmarshaller, message);
        PersonXML result = rootContext.getPerson();
        return result;
    }

    private SOAPMessage soapMessageCreation(String request){
        try{
            return MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(request.getBytes()));
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private JAXBContext jaxbContextCreation() {
        try{
            return JAXBContext.newInstance(GetPersonXML.class);
        }
        catch (JAXBException e){
            e.printStackTrace();
        }
        return null;
    }
    private Unmarshaller unmarshallerCreation(JAXBContext context){
        try {
            return context.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private GetPersonXML rootElementCreation(Unmarshaller unmarshaller, SOAPMessage message) {
        try{
            return (GetPersonXML) unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
        }
        catch (SOAPException | JAXBException e){
            e.printStackTrace();
        }
        return null;
    }
}
