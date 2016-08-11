package com.example.amadeus.app.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Person class.
 *
 * The node name of a Person object is defined "Person" in XML format.
 *
 * Using XmlAccessType.NONE because we want to define the attribute names
 * manually.
 *
 * @author TheNhan
 */
@XmlRootElement(name = "Person", namespace = "")
@XmlAccessorType(XmlAccessType.NONE)
public class Person {

    // Using the annotation XmlAttribute instead of XmlElement because of the constraint of the input XML format
    @XmlAttribute(name = "age")
    private int age;

    @XmlAttribute(name = "sex")
    private Gender sex;

    @XmlAttribute(name = "firstName")
    private String firstName;

    public Person() {
    }

    public Person(int age, Gender sex, String firstName) {
        this.age = age;
        this.sex = sex;
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
