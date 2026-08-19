package com.example.demo;

import java.time.LocalDate;


public class Paiement {
    private final String im;
    private final String numTarif;
    private final String nom;
    private final String prenoms;
    private final int montant;
    private final LocalDate datePaiement;
    private final String statut;

    public Paiement(String im, String numTarif, String nom, String prenoms, int montant, LocalDate datePaiement, String statut) {
        this.im = im;
        this.numTarif = numTarif;
        this.nom = nom;
        this.prenoms = prenoms;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statut = statut;
    }

    public String getIm() { return im; }
    public String getNumTarif() { return numTarif; }
    public String getNom() { return nom; }
    public String getPrenoms() { return prenoms; }
    public int getMontant() { return montant; }
    public LocalDate getDatePaiement() { return datePaiement; }
    public String getStatut() { return statut; }
}