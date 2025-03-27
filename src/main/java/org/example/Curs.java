package org.example;

import java.util.ArrayList;

public class Curs<T extends Student> {
    private String denumire;
    private int capacitateMaxima;
    private ArrayList<T> studentiInrolati;

    public Curs() {
        this.studentiInrolati = new ArrayList<>();
    }

    public Curs(String denumire, int capacitateMaxima) {
        this.denumire = denumire;
        this.capacitateMaxima = capacitateMaxima;
        this.studentiInrolati = new ArrayList<>();
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getCapacitateMaxima() {
        return capacitateMaxima;
    }

    public void setCapacitateMaxima(int capacitateMaxima) {
        this.capacitateMaxima = capacitateMaxima;
    }

    public ArrayList<T> getStudentiInrolati() {
        return studentiInrolati;
    }

}
