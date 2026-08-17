package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjoutTarifController {

    @FXML
    private MenuButton menuDiplome;
    @FXML
    private MenuButton menuCategorie;
    @FXML
    private TextField champMontant;

    @FXML
    private void choisirDiplome(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        menuDiplome.setText(item.getText());
    }

    @FXML
    private void choisirCategorie(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        menuCategorie.setText(item.getText());
    }

    @FXML
    private void enregistrerTarif() {
        String diplome = menuDiplome.getText();
        String categorie = menuCategorie.getText();
        String montant = champMontant.getText();

        // TODO: valider les champs et enregistrer le tarif (base de données, liste, etc.)
        System.out.println("Diplôme: " + diplome + " | Catégorie: " + categorie + " | Montant: " + montant);

        fermerPopup();
    }

    @FXML
    private void fermerPopup() {
        Stage stage = (Stage) champMontant.getScene().getWindow();
        stage.close();
    }
}