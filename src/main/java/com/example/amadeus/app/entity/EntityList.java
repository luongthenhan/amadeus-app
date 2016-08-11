package com.example.amadeus.app.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * A wrapper class for a data list type.
 *
 * @author TheNhan
 * @param <T>
 */
@XmlRootElement(name = "data")
@XmlSeeAlso({Person.class})
public class EntityList<T> {

    @XmlAnyElement(lax = true)
    private List<T> entityList;

    public EntityList() {
        entityList = new ArrayList<>();
    }

    public EntityList(List<T> entityList) {
        this.entityList = entityList;
    }

    public List<T> getItems() {
        return entityList;
    }
}
