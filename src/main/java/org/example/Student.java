package org.example;

import java.util.ArrayList;

public class Student {
    private String nume;
    private double medie;
    private String denumireCursAsignat;
    private ArrayList<String> preferinteCursuri;

    public Student() {
        this.preferinteCursuri = new ArrayList<>();
    }

    public Student(String nume, double medie, String denumireCursAsignat) {
        this.nume = nume;
        this.medie = medie;
        this.denumireCursAsignat = denumireCursAsignat;
        this.preferinteCursuri = new ArrayList<>();
    }

    public Student(String nume) {
        this.nume = nume;
        this.preferinteCursuri = new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public String getDenumireCursAsignat() {
        return denumireCursAsignat;
    }

    public void setDenumireCursAsignat(String denumireCursAsignat) {
        this.denumireCursAsignat = denumireCursAsignat;
    }

    public ArrayList<String> getPreferinteCursuri() {
        return preferinteCursuri;
    }

    public void setPreferinteCursuri(ArrayList<String> preferinteCursuri) {
        this.preferinteCursuri = preferinteCursuri;
    }
}
