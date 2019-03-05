package com.ripple.lendmoney.model;

import java.io.Serializable;
import java.util.List;

public class ContactsBean implements Serializable {
    private String name;
    private List<String> phoneNmus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneNmus() {
        return phoneNmus;
    }

    public void setPhoneNmus(List<String> phoneNmus) {
        this.phoneNmus = phoneNmus;
    }

    public ContactsBean() {
    }

    public ContactsBean(String name, List<String> phoneNmus) {
        this.name = name;
        this.phoneNmus = phoneNmus;
    }

    @Override
    public String toString() {
        return "ContactsBean{" +
                "name='" + name + '\'' +
                ", phoneNmus=" + phoneNmus +
                '}';
    }
}
