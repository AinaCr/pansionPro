package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TarifController {

    @FXML private TableView<Tarif> tableTarifs;
    @FXML private TableColumn<Tarif, String> colCodeTarif;
    @FXML private TableColumn<Tarif, String> colNiveauDiplome;
    @FXML private TableColumn<Tarif, String> colCategorieCivile;
    @FXML private TableColumn<Tarif, Number> colMontantBase;
    @FXML private TableColumn<Tarif, Void> colActionsTarif;

    @FXML
    public void initialize() {
        // Lier chaque colonne à un attribut du modèle Tarif
        colCodeTarif.setCellValueFactory(new PropertyValueFactory<>("numTarif"));
        colNiveauDiplome.setCellValueFactory(new PropertyValueFactory<>("diplome"));
        colCategorieCivile.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colMontantBase.setCellValueFactory(new PropertyValueFactory<>("montant"));

        ajouterColonneActions();
        chargerTarifs();
    }

    private void chargerTarifs() {
        ObservableList<Tarif> liste = FXCollections.observableArrayList();
        String sql = "SELECT num_tarif, diplome, categorie, montant FROM TARIF";

        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Tarif(
                        rs.getString("num_tarif"),
                        rs.getString("diplome"),
                        rs.getString("categorie"),
                        rs.getInt("montant")
                ));
            }

            tableTarifs.setItems(liste);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ajouterColonneActions() {
        colActionsTarif.setCellFactory(col -> new TableCell<>() {
            private final Button btnModifier = new Button("✏");
            private final Button btnSupprimer = new Button("🗑");
            private final HBox conteneur = new HBox(8, btnModifier, btnSupprimer);

            {
                btnModifier.setOnAction(e -> {
                    Tarif tarif = getTableView().getItems().get(getIndex());
                    // TODO : ouvrir la popup de modification pré-remplie avec "tarif"
                });
                btnSupprimer.setOnAction(e -> {
                    Tarif tarif = getTableView().getItems().get(getIndex());
                    supprimerTarif(tarif);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : conteneur);
            }
        });
    }

    private void supprimerTarif(Tarif tarif) {
        String sql = "DELETE FROM TARIF WHERE num_tarif = ?";
        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, tarif.getNumTarif());
            ps.executeUpdate();
            tableTarifs.getItems().remove(tarif);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Appelée après l'enregistrement d'un tarif depuis AjoutTarifController */
    public void rafraichirTableau() {
        chargerTarifs();
    }
}