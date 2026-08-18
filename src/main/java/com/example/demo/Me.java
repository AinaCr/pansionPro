package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import java.time.LocalDate;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class Me {
    @FXML
    private StackPane page1;
    @FXML
    private StackPane page2;

    @FXML private TextField txtNumeroIM;
    @FXML private TextField txtName;
    @FXML private  TextField  txtPrenoms;
    @FXML private TextField txtNomConjoint;
    @FXML private TextField txtPrenomConjoint;
    @FXML private DatePicker txtDateNaissance;
    @FXML private  MenuButton menuDiplome;
    @FXML private  TextField txtContact;
    @FXML private MenuButton menuSituationMatrimoniale;
    @FXML private MenuButton menuStatuts;
    @FXML private  MenuButton menuSituation;


    @FXML private TextField champRecherche;
    @FXML private MenuButton menuStatut;          // filtre statut (liste)
    @FXML private Button btnFiltrerPersonnes;
    @FXML private Label lblAffichage;
    @FXML private Label lblEffectifTotal;

    // Données
    private ObservableList<Personne> listePersonnesComplete = FXCollections.observableArrayList();
    private FilteredList<Personne> personnesFiltrees;

    @FXML
    private StackPane tableau;

    @FXML
    StackPane tarif;

    @FXML
    StackPane paiment;

    @FXML
    private StackPane personne;

    @FXML
    private StackPane addAction;

    @FXML
    private Pane bord1;
    @FXML
    private Pane bord2;
    @FXML
    private Pane bord3;
    @FXML
    private Pane bord4;

    @FXML private TableView<Tarif> tableTarifs;
    @FXML private TableColumn<Tarif, String> colCodeTarif;
    @FXML private TableColumn<Tarif, String> colNiveauDiplome;
    @FXML private TableColumn<Tarif, String> colCategorieCivile;
    @FXML private TableColumn<Tarif, Number> colMontantBase;
    @FXML private TableColumn<Tarif, Void> colActionsTarif;

    @FXML private TableView<Personne> tablePersonnes;
    @FXML private TableColumn<Personne, String> colIM;
    @FXML private TableColumn<Personne, String> colNomPersonne;
    @FXML private TableColumn<Personne, String> colPrenomsPersonne;
    @FXML private TableColumn<Personne, LocalDate> colDateNaissance;
    @FXML private TableColumn<Personne, String> colDiplome;
    @FXML private TableColumn<Personne, String> colStatutPersonne;
    @FXML private TableColumn<Personne, String> colSituation;
    @FXML private TableColumn<Personne, Void> colActions;






    private static final String COULEUR_ACTIVE = "-fx-background-color: #FFD933;";
    private static final String COULEUR_INACTIVE = "-fx-background-color: transparent;";

    private List<Pane> tousLesBords;
    private List<StackPane> everyPane;

    @FXML
    public void initialize() {

        tousLesBords = List.of(bord1, bord2, bord3, bord4);
        everyPane = List.of(tableau, personne, addAction, tarif, paiment);

        // ===== TABLE TARIFS =====
        colCodeTarif.setCellValueFactory(
                new PropertyValueFactory<>("numTarif")
        );

        colNiveauDiplome.setCellValueFactory(
                new PropertyValueFactory<>("diplome")
        );

        colCategorieCivile.setCellValueFactory(
                new PropertyValueFactory<>("categori")
        );

        colMontantBase.setCellValueFactory(
                new PropertyValueFactory<>("montant")
        );

        ajouterColonneActions();

        // ===== TABLE PERSONNES =====
        configurerColonnesPersonnes();

        // Chargement initial
        chargerTarifs();
        chargerPersonnes();

        if (champRecherche != null) {
            champRecherche.textProperty().addListener((obs, ancienne, nouvelle) -> appliquerFiltres());
        }
        if (btnFiltrerPersonnes != null) {
            btnFiltrerPersonnes.setOnAction(e -> appliquerFiltres());
        }
    }


    private void configurerColonnesPersonnes() {
        colIM.setCellValueFactory(new PropertyValueFactory<>("im"));
        colNomPersonne.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenomsPersonne.setCellValueFactory(new PropertyValueFactory<>("prenoms"));
        colDateNaissance.setCellValueFactory(new PropertyValueFactory<>("datenais"));
        colDiplome.setCellValueFactory(new PropertyValueFactory<>("diplome"));
        colStatutPersonne.setCellValueFactory(new PropertyValueFactory<>("statut"));
        colSituation.setCellValueFactory(new PropertyValueFactory<>("situation"));

        ajouterColonneActionsPersonnes();
    }

    private void ajouterColonneActionsPersonnes() {
        colActions.setCellFactory(col -> new TableCell<>() {
            private final Button btnModifier = new Button("✏");
            private final Button btnSupprimer = new Button("🗑");
            private final HBox conteneur = new HBox(8, btnModifier, btnSupprimer);

            {
                btnModifier.setOnAction(e -> {
                    Personne p = getTableView().getItems().get(getIndex());
                    // TODO : ouvrir la popup de modification pré-remplie avec "p"
                });
                btnSupprimer.setOnAction(e -> {
                    Personne p = getTableView().getItems().get(getIndex());
                    supprimerPersonne(p);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : conteneur);
            }
        });
    }

    private void chargerPersonnes() {
        listePersonnesComplete.clear();
        String sql = "SELECT IM, nom, prenoms, datenais, diplome, statut, situation FROM PERSONNE";

        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listePersonnesComplete.add(new Personne(
                        rs.getString("IM"),
                        rs.getString("nom"),
                        rs.getString("prenoms"),
                        rs.getDate("datenais") != null ? rs.getDate("datenais").toLocalDate() : null,
                        rs.getString("diplome"),
                        rs.getString("statut"),
                        rs.getString("situation")
                ));
            }

            personnesFiltrees = new FilteredList<>(listePersonnesComplete, p -> true);
            SortedList<Personne> triee = new SortedList<>(personnesFiltrees);
            triee.comparatorProperty().bind(tablePersonnes.comparatorProperty());
            tablePersonnes.setItems(triee);

            appliquerFiltres();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void appliquerFiltres() {
        if (personnesFiltrees == null) return;

        String recherche = champRecherche != null && champRecherche.getText() != null
                ? champRecherche.getText().trim().toLowerCase()
                : "";
        String statutChoisi = menuStatut != null ? menuStatut.getText() : "Tous";
        String situationChoisie = menuSituation != null ? menuSituation.getText() : "Tous";

        personnesFiltrees.setPredicate(p -> {
            boolean correspondRecherche = recherche.isEmpty()
                    || (p.getNom() != null && p.getNom().toLowerCase().contains(recherche))
                    || (p.getPrenoms() != null && p.getPrenoms().toLowerCase().contains(recherche))
                    || (p.getIm() != null && p.getIm().toLowerCase().contains(recherche));

            boolean correspondStatut = statutChoisi.equals("Tous")
                    || statutChoisi.equals(p.getStatut());

            boolean correspondSituation = situationChoisie.equals("Tous")
                    || situationChoisie.equals(p.getSituation());

            return correspondRecherche && correspondStatut && correspondSituation;
        });

        mettreAJourCompteurs();
    }

    private void mettreAJourCompteurs() {
        int total = listePersonnesComplete.size();
        int affiches = personnesFiltrees != null ? personnesFiltrees.size() : total;

        if (lblAffichage != null) {
            lblAffichage.setText("Affichage 1-" + affiches + " sur " + affiches + " personnes");
        }
        if (lblEffectifTotal != null) {
            lblEffectifTotal.setText("Effectif total : " + total + " personnes enregistrées");
        }
    }

    private void supprimerPersonne(Personne p) {
        String sql = "DELETE FROM PERSONNE WHERE IM = ?";
        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, p.getIm());
            ps.executeUpdate();
            listePersonnesComplete.remove(p);   // <-- retire de la source, pas de tablePersonnes.getItems()
            mettreAJourCompteurs();
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

    @FXML
    private void choisirStatutFiltre(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        menuStatut.setText(item.getText());
        appliquerFiltres();
    }

    @FXML
    private void choisirStatut(ActionEvent event) {

        MenuItem item = (MenuItem) event.getSource();

        menuStatuts.setText(item.getText());

        System.out.println("Statut sélectionné : " + item.getText());
    }


    @FXML
    private void choisirSituation(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        menuSituation.setText(item.getText());
        appliquerFiltres();
    }

    @FXML
    private void choisirSituationMatrimoniale(ActionEvent event) {

        MenuItem item = (MenuItem) event.getSource();

        menuSituationMatrimoniale.setText(item.getText());

        System.out.println(
                "Situation matrimoniale : " + item.getText()
        );
    }

    @FXML
    private void choisirDiplome(ActionEvent event) {

        MenuItem item = (MenuItem) event.getSource();

        menuDiplome.setText(item.getText());

        System.out.println(
                "Diplôme sélectionné : " + item.getText()
        );
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

    private String genererNumTarif() {
        int nombre = 1000 + (int) (Math.random() * 9000);
        return "IM-" + nombre;
    }

    private void chargerTarifs() {
        ObservableList<Tarif> liste = FXCollections.observableArrayList();
        String sql = "SELECT num_tarif, diplome, categori, montant FROM TARIF";

        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Tarif(
                        rs.getString("num_tarif"),
                        rs.getString("diplome"),
                        rs.getString("categori"),
                        rs.getInt("montant")
                ));
            }

            tableTarifs.setItems(liste);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Appelée après l'enregistrement d'un tarif depuis AjoutTarifController */
    public void rafraichirTableau() {
        chargerTarifs();
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

    private void afficherAlerte(String message) {

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }



    @FXML
    private void returnList(){
        activeBtn(personne);
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

        chargerPersonnes();
        // TODO: afficher la vue "Personne"
    }

    @FXML
    private void setTarifs() {
        activerBord(bord3);
        activeBtn(tarif);
        chargerTarifs();
        // TODO: afficher la vue "Tarifs"
    }

    @FXML
    private void setPaiements() {
        activerBord(bord4);
        activeBtn(paiment);
        // TODO: afficher la vue "Paiements"
    }



    @FXML
    private void setConnexion() {
        page1.setVisible(false);
        page1.setManaged(false);
        page2.setVisible(true);
        page2.setManaged(true);
    }
    @FXML
    private void addPersonne(){
        activeBtn(addAction);
        String matricule = genererNumTarif();

        txtNumeroIM.setText(matricule);

    }



    @FXML
    private void savePerson() {

        String name = txtName.getText().trim();
        String prenom = txtPrenoms.getText().trim();
        String matricule = txtNumeroIM.getText().trim();
        LocalDate dateNaissance = txtDateNaissance.getValue();
        String contact = txtContact.getText().trim();
        String diplome = menuDiplome.getText();
        String statut = menuStatuts.getText();
        String matrimonial = menuSituationMatrimoniale.getText();
        String conjoint = txtNomConjoint.getText().trim();
        String conjointPrenom = txtPrenomConjoint.getText().trim();

        // =========================
        // VALIDATION
        // =========================

        if (matricule.isBlank()) {
            afficherAlerte("Le numéro IM est obligatoire.");
            return;
        }

        if (name.isBlank()) {
            afficherAlerte("Veuillez saisir le nom.");
            return;
        }

        if (prenom.isBlank()) {
            afficherAlerte("Veuillez saisir le prénom.");
            return;
        }

        if (dateNaissance == null) {
            afficherAlerte("Veuillez sélectionner une date de naissance.");
            return;
        }

        if (diplome.equals("Sélectionner...")) {
            afficherAlerte("Veuillez sélectionner un diplôme.");
            return;
        }

        if (statut.equals("Vivant") == false && statut.equals("Décédé") == false) {
            afficherAlerte("Veuillez sélectionner le statut.");
            return;
        }

        if (matrimonial.equals("Sélectionner...")) {
            afficherAlerte("Veuillez sélectionner la situation matrimoniale.");
            return;
        }

        // =========================
        // INSERTION
        // =========================

        String sql = """
            INSERT INTO PERSONNE
            (
                IM,
                nom,
                prenoms,
                datenais,
                diplome,
                contact,
                statut,
                situation,
                nomconjoint,
                prenomconjoint
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (
                Connection cnx = Database.getConnection();
                PreparedStatement ps = cnx.prepareStatement(sql)
        ) {

            ps.setString(1, matricule);
            ps.setString(2, name);
            ps.setString(3, prenom);
            ps.setObject(4, dateNaissance);
            ps.setString(5, diplome);
            ps.setString(6, contact);
            ps.setString(7, statut);
            ps.setString(8, matrimonial);
            ps.setString(9, conjoint);
            ps.setString(10, conjointPrenom);

            ps.executeUpdate();

            System.out.println("Personne enregistrée : " + matricule);

            afficherAlerte("Personne enregistrée avec succès.");

            // Retour à la liste
            chargerPersonnes();
            activeBtn(personne);

        } catch (SQLException e) {

            e.printStackTrace();

            afficherAlerte(
                    "Erreur lors de l'enregistrement :\n"
                            + e.getMessage()
            );
        }
    }
    @FXML
    private void ouvrirPopupTarif() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("popUp.fxml")
            );

            Parent racine = loader.load();

            AjoutTarifController controller = loader.getController();
            controller.setMeController(this);

            Scene scene = new Scene(racine);
            scene.setFill(Color.TRANSPARENT);

            Stage popup = new Stage();

            // Fenêtre transparente
            popup.initStyle(StageStyle.TRANSPARENT);

            // La fenêtre principale devient le propriétaire du popup
            Stage fenetrePrincipale =
                    (Stage) tableau.getScene().getWindow();

            popup.initOwner(fenetrePrincipale);

            // Modale par rapport à la fenêtre principale
            popup.initModality(Modality.WINDOW_MODAL);

            popup.setScene(scene);
            popup.setResizable(false);

            popup.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}