package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import java.util.List;

public class Me {
    @FXML
    private StackPane page1;
    @FXML
    private StackPane page2;

    @FXML
    private StackPane tableau;

    @FXML
    private StackPane personne;

    @FXML
    private Pane bord1;
    @FXML
    private Pane bord2;
    @FXML
    private Pane bord3;
    @FXML
    private Pane bord4;
    @FXML
    private Pane bord5;

    private static final String COULEUR_ACTIVE = "-fx-background-color: #FFD933;";
    private static final String COULEUR_INACTIVE = "-fx-background-color: transparent;";

    private List<Pane> tousLesBords;
    private List<StackPane> everyPane;

    @FXML
    public void initialize() {
        tousLesBords = List.of(bord1, bord2, bord3, bord4, bord5);
        everyPane =List.of(tableau, personne);
    }


    // Active une seule barre à la fois, éteint les autres
    private void activerBord(Pane bordActif) {
        for (Pane bord : tousLesBords) {
            bord.setStyle(bord == bordActif ? COULEUR_ACTIVE : COULEUR_INACTIVE);
        }
    }

    private void activeBtn(StackPane paneActif){
        for (StackPane pane: everyPane){
            pane.setVisible(pane == paneActif ? true : false);
            pane.setManaged(pane == paneActif ? true : false);
        }
    }


    @FXML
    private void setAccueil() {
        activerBord(bord1);
        activeBtn(tableau);
        // TODO: afficher la vue "Tableau de bord"
    }

    @FXML
    private void setPersonne() {
        activerBord(bord2);
        activeBtn(personne);
        // TODO: afficher la vue "Personne"
    }

    @FXML
    private void setTarifs() {
        activerBord(bord3);
        // TODO: afficher la vue "Tarifs"
    }

    @FXML
    private void setPaiements() {
        activerBord(bord4);
        // TODO: afficher la vue "Paiements"
    }

    @FXML
    private void setRecherche() {
        activerBord(bord5);
        // TODO: afficher la vue "Recherche"
    }

    @FXML
    private void setConnexion() {
        page1.setVisible(false);
        page1.setManaged(false);
        page2.setVisible(true);
        page2.setManaged(true);
    }
}