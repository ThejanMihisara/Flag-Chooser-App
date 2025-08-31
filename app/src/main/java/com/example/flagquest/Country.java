package com.example.flagquest;
//Sa24610050 - M A S Dulneth
public class Country {
    private String name;
    private String flagUrl;
    private String capital;

    public Country(String name, String flagUrl, String capital) {
        this.name = name;
        this.flagUrl = flagUrl;
        this.capital = capital;
    }

    public String getName() { return name; }
    public String getFlagUrl() { return flagUrl; }
    public String getCapital() { return capital; }
}