package com.example.currencyconvert;

public class User {
    private String mail;
    private String name;
    private String pass;
    private String age;
    private String phone;
    private String gander;

    public User(String mail, String name, String pass, String age, String phone, String gander) {
        this.mail = mail;
        this.name = name;
        this.pass = pass;
        this.age = age;
        this.phone = phone;
        this.gander = gander;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGander() {
        return gander;
    }

    public void setGander(String gander) {
        this.gander = gander;
    }
}
