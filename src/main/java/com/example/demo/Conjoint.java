package com.example.demo;

public class Conjoint {
    private final String numPension;
    private final String nomConjoint;
    private final String prenomConjoint;
    private final int montant;

    public Conjoint(String numPension, String nomConjoint, String prenomConjoint, int montant) {
        this.numPension = numPension;
        this.nomConjoint = nomConjoint;
        this.prenomConjoint = prenomConjoint;
        this.montant = montant;
    }

    public String getNumPension() { return numPension; }
    public String getNomConjoint() { return nomConjoint; }
    public String getPrenomConjoint() { return prenomConjoint; }
    public int getMontant() { return montant; }
}