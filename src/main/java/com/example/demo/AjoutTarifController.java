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

    @FXML
    private MenuButton menuDiplome;

    @FXML
    private MenuButton menuCategorie;

    @FXML
    private TextField champMontant;

    // Référence vers le contrôleur principal Me
    private Me meController;

    /**
     * Reçoit le contrôleur Me depuis la fenêtre principale.
     */
    public void setMeController(Me meController) {
        this.meController = meController;
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

        if (diplome == null
                || diplome.isBlank()
                || diplome.equals("Diplôme")) {

            afficherAlerte("Veuillez sélectionner un diplôme.");
            return;
        }

        if (categori == null
                || categori.isBlank()
                || categori.equals("Catégorie")) {

            afficherAlerte("Veuillez sélectionner une catégorie.");
            return;
        }

        if (montantTexte == null || montantTexte.isBlank()) {

            afficherAlerte("Veuillez saisir un montant.");
            return;
        }

        // =========================
        // CONVERSION DU MONTANT
        // =========================

        int montant;

        try {
            montant = Integer.parseInt(montantTexte.trim());

        } catch (NumberFormatException e) {

            afficherAlerte("Le montant doit être un nombre entier.");
            return;
        }

        // =========================
        // GÉNÉRATION DU NUMÉRO
        // =========================

        String numTarif = genererNumTarif();

        // =========================
        // INSERTION SQL
        // =========================

        String sql = """
                INSERT INTO TARIF
                (num_tarif, diplome, categori, montant)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection cnx = Database.getConnection();
                PreparedStatement ps = cnx.prepareStatement(sql)
        ) {

            ps.setString(1, numTarif);
            ps.setString(2, diplome);
            ps.setString(3, categori);
            ps.setInt(4, montant);

            ps.executeUpdate();

            System.out.println(
                    "Tarif enregistré : "
                            + numTarif
                            + " | "
                            + diplome
                            + " | "
                            + categori
                            + " | "
                            + montant
            );

            // =========================
            // RAFRAÎCHIR LE TABLEAU
            // =========================

            if (meController != null) {
                meController.rafraichirTableau();
            }

            // =========================
            // FERMER LA POPUP
            // =========================

            fermerPopup();

        } catch (SQLException e) {

            e.printStackTrace();

            afficherAlerte(
                    "Erreur lors de l'enregistrement du tarif : "
                            + e.getMessage()
            );
        }
    }

    /**
     * Génère un numéro de tarif basé sur l'horodatage.
     */
    private String genererNumTarif() {

        return "TAR-" + System.currentTimeMillis();
    }

    /**
     * Affiche une alerte.
     */
    private void afficherAlerte(String message) {

        Alert alert = new Alert(
                Alert.AlertType.WARNING,
                message
        );

        alert.showAndWait();
    }

    /**
     * Ferme la fenêtre popup.
     */
    @FXML
    private void fermerPopup() {

        Stage stage =
                (Stage) champMontant.getScene().getWindow();

        stage.close();
    }
}