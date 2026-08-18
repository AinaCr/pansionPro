package com.example.demo;

import java.time.LocalDate;

public class Personne {
    private final String im;
    private final String nom;
    private final String prenoms;
    private final LocalDate datenais;
    private final String diplome;
    private final String statut;
    private final String situation;

    public Personne(String im, String nom, String prenoms, LocalDate datenais,
                    String diplome, String statut, String situation) {
        this.im = im;
        this.nom = nom;
        this.prenoms = prenoms;
        this.datenais = datenais;
        this.diplome = diplome;
        this.statut = statut;
        this.situation = situation;
    }

    public String getIm() { return im; }
    public String getNom() { return nom; }
    public String getPrenoms() { return prenoms; }
    public LocalDate getDatenais() { return datenais; }
    public String getDiplome() { return diplome; }
    public String getStatut() { return statut; }
    public String getSituation() { return situation; }
}