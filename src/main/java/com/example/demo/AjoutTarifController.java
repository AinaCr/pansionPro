package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AjoutTarifController {

    @FXML private MenuButton menuDiplome;
    @FXML private MenuButton menuCategorie;
    @FXML private TextField champMontant;

    private Me meController;
    private String numTarifEnCoursModification = null; // null = création, sinon = modification

    private static final String TEXTE_DEFAUT_DIPLOME = "Ex: Licence, Master, Doctorat...";
    private static final String TEXTE_DEFAUT_CATEGORIE = "Ex: Catégorie A, B, C, D";

    public void setMeController(Me meController) {
        this.meController = meController;
    }

    /** Pré-remplit le popup pour modifier un tarif existant. */
    public void preremplirPourModification(Tarif tarif) {
        numTarifEnCoursModification = tarif.getNumTarif();
        menuDiplome.setText(tarif.getDiplome());
        menuCategorie.setText(tarif.getCategorie());
        champMontant.setText(String.valueOf(tarif.getMontant()));
    }

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
        String categori = menuCategorie.getText();
        String montantTexte = champMontant.getText();

        // =========================
        // VALIDATION DES CHAMPS
        // =========================

        if (diplome == null || diplome.isBlank() || diplome.equals(TEXTE_DEFAUT_DIPLOME)) {
            afficherAlerte("Veuillez sélectionner un diplôme.");
            return;
        }

        if (categori == null || categori.isBlank() || categori.equals(TEXTE_DEFAUT_CATEGORIE)) {
            afficherAlerte("Veuillez sélectionner une catégorie.");
            return;
        }

        if (montantTexte == null || montantTexte.isBlank()) {
            afficherAlerte("Veuillez saisir un montant.");
            return;
        }

        int montant;
        try {
            montant = Integer.parseInt(montantTexte.trim());
        } catch (NumberFormatException e) {
            afficherAlerte("Le montant doit être un nombre entier.");
            return;
        }

        if (numTarifEnCoursModification != null) {
            modifierTarif(numTarifEnCoursModification, diplome, categori, montant);
        } else {
            creerTarif(diplome, categori, montant);
        }
    }

    private void creerTarif(String diplome, String categori, int montant) {
        String numTarif = genererNumTarif();
        String sql = "INSERT INTO TARIF (num_tarif, diplome, categori, montant) VALUES (?, ?, ?, ?)";

        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, numTarif);
            ps.setString(2, diplome);
            ps.setString(3, categori);
            ps.setInt(4, montant);
            ps.executeUpdate();

            System.out.println("Tarif enregistré : " + numTarif + " | " + diplome + " | " + categori + " | " + montant);

            if (meController != null) meController.rafraichirTableau();
            fermerPopup();

        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de l'enregistrement du tarif : " + e.getMessage());
        }
    }

    private void modifierTarif(String numTarif, String diplome, String categori, int montant) {
        String sql = "UPDATE TARIF SET diplome = ?, categori = ?, montant = ? WHERE num_tarif = ?";

        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, diplome);
            ps.setString(2, categori);
            ps.setInt(3, montant);
            ps.setString(4, numTarif);
            ps.executeUpdate();

            System.out.println("Tarif modifié : " + numTarif + " | " + diplome + " | " + categori + " | " + montant);

            if (meController != null) meController.rafraichirTableau();
            fermerPopup();

        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de la modification du tarif : " + e.getMessage());
        }
    }

    private String genererNumTarif() {
        return "TAR-" + System.currentTimeMillis();
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
    }

    @FXML
    private void fermerPopup() {
        Stage stage = (Stage) champMontant.getScene().getWindow();
        stage.close();
    }
}