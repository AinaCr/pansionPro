package com.example.demo;

import java.time.LocalDate;

public class Personne {
    private String im;
    private String nom;
    private String prenoms;
    private LocalDate datenais;
    private String diplome;
    private String statut;
    private String situation;
    private String contact;
    private String nomConjoint;
    private String prenomConjoint;

    public Personne(String im, String nom, String prenoms, LocalDate datenais,
                    String diplome, String statut, String situation,
                    String contact, String nomConjoint, String prenomConjoint) {
        this.im = im;
        this.nom = nom;
        this.prenoms = prenoms;
        this.datenais = datenais;
        this.diplome = diplome;
        this.statut = statut;
        this.situation = situation;
        this.contact = contact;
        this.nomConjoint = nomConjoint;
        this.prenomConjoint = prenomConjoint;
    }

    public String getContact() { return contact; }
    public String getNomConjoint() { return nomConjoint; }
    public String getPrenomConjoint() { return prenomConjoint; }

    public String getIm() { return im; }
    public String getNom() { return nom; }
    public String getPrenoms() { return prenoms; }
    public LocalDate getDatenais() { return datenais; }
    public String getDiplome() { return diplome; }
    public String getStatut() { return statut; }
    public String getSituation() { return situation; }
}