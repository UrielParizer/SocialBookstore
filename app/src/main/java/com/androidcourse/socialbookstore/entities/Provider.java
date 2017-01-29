package com.androidcourse.socialbookstore.entities;

public class Provider
{
    private String Name;
    private String City;

    private String EMail;
    private String Password;

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }
    public void setCity(String city) {
        City = city;
    }

    public String getEMail() {
        return EMail;
    }
    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }

    public Provider() {
    }
    public Provider(String name, String city, String email,String password) {
        Name = name;
        City = city;
        EMail=email;
        Password=password;
    }
    public Provider(Provider provider){
        Name = provider.getName();
        City = provider.getCity();
        EMail=provider.getEMail();
        Password=provider.getPassword();
    }

    @Override
    public boolean equals(Object o) {
        return (((Provider)o).getEMail()).equals(getEMail());
    }

    @Override
    public String toString() {
        return getEMail();
    }

    public String toLongString() {
        return
            "Name:\n"+getName()+"\n\n"+
            "City:\n"+getCity()+"\n\n"+
            "EMail (ID):\n"+getEMail()+"\n\n"+
            "Password:\n"+getPassword();
    }
}