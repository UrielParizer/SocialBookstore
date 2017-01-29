package com.androidcourse.socialbookstore.entities;

public class Provider_Opinion
{
    private long ID;
    private String ProviderEMail;
    private String CriticOnProvider;
    private Integer GradeOfProvider;


    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }

    public String getProviderEMail() {
        return ProviderEMail;
    }
    public void setProviderEMail(String providerEMail) {
        ProviderEMail = providerEMail;
    }

    public String getCriticOnProvider() {
        return CriticOnProvider;
    }
    public void setCriticOnProvider(String criticOnProvider) {
        CriticOnProvider = criticOnProvider;
    }

    public Integer getGradeOfProvider() {
        return GradeOfProvider;
    }
    public void setGradeOfProvider(Integer gradeOfProvider) {
        GradeOfProvider = gradeOfProvider;
    }


    public Provider_Opinion() {
    }
    public Provider_Opinion(String providerEMail, String criticOnProvider, Integer gradeOfProvider) {
        ProviderEMail = providerEMail;
        CriticOnProvider = criticOnProvider;
        GradeOfProvider = gradeOfProvider;
    }
    public Provider_Opinion(Provider_Opinion provider_opinion){
        this.ID = provider_opinion.getID();
        ProviderEMail = provider_opinion.getProviderEMail();
        CriticOnProvider = provider_opinion.getCriticOnProvider();
        GradeOfProvider = provider_opinion.getGradeOfProvider();
    }

    @Override
    public boolean equals(Object o) {
        Provider_Opinion tempProvider_Opinion=(Provider_Opinion)o;
        return tempProvider_Opinion.getProviderEMail().equals(getProviderEMail())&&
                tempProvider_Opinion.getCriticOnProvider().equals(getCriticOnProvider())&&
                tempProvider_Opinion.getGradeOfProvider()==getGradeOfProvider();
    }

    @Override
    public String toString() {
        return String.valueOf(getID());
    }

    public String toLongString() {
        return
            "ID:\n"+String.valueOf(getID())+"\n\n"+
            "Provider EMail\n"+getProviderEMail()+"\n\n"+
            "Critic On Provider\n"+getCriticOnProvider()+"\n\n"+
            "Grade Of Provider:\n"+String.valueOf(getGradeOfProvider());
    }
}