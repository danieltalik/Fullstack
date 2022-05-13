package com.danieltalik.fullStackApp.Models.SOAP;

import com.danieltalik.fullStackApp.handlers.LocalDateXmlHandler;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="",propOrder = {
        "age",
        "birthday",
        "firstName",
        "lastName",
        "nickName"
})

@XmlRootElement(name="person")
public class PersonXML {
    @XmlElement(name = "age")
    protected int age;
    @XmlElement(name="birthday")
    @XmlJavaTypeAdapter(value = LocalDateXmlHandler.class)
    private LocalDate birthday;
    @XmlElement(name="firstName")
    protected String firstName;
    @XmlElement(name = "lastName")
    protected String lastName;
    @XmlElement(name = "nickName")
    protected String nickName;


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthday(){
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }


    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickName() {
        return nickName;
    }


}
