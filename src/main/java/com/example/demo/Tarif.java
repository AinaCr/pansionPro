package com.example.demo;

public class Tarif {
    private final String numTarif;
    private final String diplome;
    private final String categorie;
    private final int montant;

    public Tarif(String numTarif, String diplome, String categorie, int montant) {
        this.numTarif = numTarif;
        this.diplome = diplome;
        this.categorie = categorie;
        this.montant = montant;
    }

    public String getNumTarif() { return numTarif; }
    public String getDiplome() { return diplome; }
    public String getCategorie() { return categorie; }
    public int getMontant() { return montant; }
}