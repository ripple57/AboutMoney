package com.ripple.lendmoney.model;

import java.io.Serializable;
import java.util.List;

public class ContactsBean implements Serializable {
    private String phoneName;
    private List<String> phoneNmus;

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
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
        this.phoneName = name;
        this.phoneNmus = phoneNmus;
    }

    @Override
    public String toString() {
        return "ContactsBean{" +
                "name='" + phoneName + '\'' +
                ", phoneNmus=" + phoneNmus +
                '}';
    }
}
