package com.androidcourse.socialbookstore.entities;

public class Client
{
    private String Name;
    private int YearOfBirth;

    private String EMail;
    private String Password;

    private String Street;
    private Integer NumberOfStreet;
    private Integer Apartment;
    private String City;

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public int getYearOfBirth() {
        return YearOfBirth;
    }
    public void setYearOfBirth(int yearOfBirth) {
        YearOfBirth = yearOfBirth;
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

    public String getStreet() {
        return Street;
    }
    public void setStreet(String street) {
        Street = street;
    }

    public Integer getNumberOfStreet() {
        return NumberOfStreet;
    }
    public void setNumberOfStreet(Integer numberOfStreet) {
        NumberOfStreet = numberOfStreet;
    }

    public Integer getApartment() {
        return Apartment;
    }
    public void setApartment(Integer apartment) {
        Apartment = apartment;
    }

    public String getCity() {
        return City;
    }
    public void setCity(String city) {
        City = city;
    }

    public Client() {
    }
    public Client(String name, int yearOfBirth, String email,String password,String street, Integer numberOfStreet, Integer apartment, String city) {
        Name = name;
        YearOfBirth = yearOfBirth;
        EMail=email;
        Password=password;
        Street = street;
        NumberOfStreet = numberOfStreet;
        Apartment = apartment;
        City = city;
    }
    public Client(Client client) {
        Name=client.getName();
        YearOfBirth = client.getYearOfBirth();
        EMail=client.getEMail();
        Password=client.getPassword();
        Street = client.getStreet();
        NumberOfStreet = client.getNumberOfStreet();
        Apartment = client.getApartment();
        City = client.getCity();
    }

    @Override
    public boolean equals(Object o) {
        return getEMail().equals(((Client)o).getEMail());
    }

    @Override
    public String toString() {
        return getEMail();
    }

    public String toLongString() {
        return
            "Name:\n"+getName()+"\n\n"+
            "Year Of Birth:\n"+String.valueOf(getYearOfBirth())+"\n\n"+
            "EMail (ID):\n"+getEMail()+"\n\n"+
            "Password:\n"+getPassword()+"\n\n"+
            "Street:\n"+getStreet()+"\n\n"+
            "Home Number In Street:\n"+String.valueOf(getNumberOfStreet())+"\n\n"+
            "Apartment Number:\n"+String.valueOf(getApartment())+"\n\n"+
            "City:\n"+getCity();
    }
}