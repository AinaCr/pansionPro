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
import javafx.util.StringConverter;
import java.util.stream.Collectors;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import java.time.LocalDate;
import javafx.scene.layout.VBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import javafx.stage.FileChooser;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.LocalDate;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import java.util.Map;
import java.util.LinkedHashMap;

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



    @FXML private Button btnGenerer;
    @FXML private StackPane ici;
    @FXML private Label labelTitreRecu;
    @FXML private Label labelReference;
    @FXML private Label labelNumeroIM;
    @FXML private Label labelNom;
    @FXML private Label labelPrenoms;
    @FXML private Label labelPeriode;
    @FXML private Label labelMontant;
    @FXML private Label labelDate;
    @FXML private Button btnTelechargerPdf;

    @FXML private Label lblTotalPensionnaires;
    @FXML private Label lblTrendTotal;
    @FXML private Label lblPensionsCeMois;
    @FXML private Label lblTrendPensions;
    @FXML private TableColumn<Paiement, Void> colActionsPaiement;
    @FXML private Label lblConjoints;
    @FXML private Label lblTrendConjoints;
    @FXML private Label lblDecesSignales;
    @FXML private Label lblTrendDeces;

    @FXML private BarChart<String, Number> barChartCategories;
    @FXML private CategoryAxis axeCategories;
    @FXML private NumberAxis axeEffectifs;

    @FXML private PieChart pieChartStatut;
    @FXML private Label lblVivantDetail;
    @FXML private Label lblDecedeDetail;

    @FXML private TableView<Personne> tablePersonnes;
    @FXML private TableColumn<Personne, String> colIM;
    @FXML private TableColumn<Personne, String> colNomPersonne;
    @FXML private TableColumn<Personne, String> colPrenomsPersonne;
    @FXML private TableColumn<Personne, LocalDate> colDateNaissance;
    @FXML private TableColumn<Personne, String> colDiplome;
    @FXML private TableColumn<Personne, String> colStatutPersonne;
    @FXML private TableColumn<Personne, String> colSituation;
    @FXML private TableColumn<Personne, Void> colActions;



    @FXML private ComboBox<Personne> comboRetraite;
    @FXML private ComboBox<String> comboMois;
    @FXML private ComboBox<Integer> comboAnnee;
    @FXML private Label lblApercuNom;
    @FXML private Label lblApercuDiplome;
    @FXML private Label lblApercuMontant;
    @FXML private Label lblAvertissement;
    @FXML
    private VBox boxInformationsConjoint;

    @FXML private TableView<Paiement> tablePaiements;
    @FXML private TableColumn<Paiement, String> colNumeroIM;
    @FXML private TableColumn<Paiement, String> colNom;
    @FXML private TableColumn<Paiement, String> colPrenoms;
    @FXML private TableColumn<Paiement, Number> colMontant;
    @FXML private TableColumn<Paiement, LocalDate> colDatePaiement;
    @FXML private TableColumn<Paiement, String> colStatut;
    @FXML private DatePicker dateDebutFiltre;
    @FXML private DatePicker dateFinFiltre;
    @FXML private Button btnFiltrer;


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
        chargerTableauDeBord();
        configurerFormulairePaiement();

        LocalDate dateLimite = LocalDate.now().minusYears(60);

        txtDateNaissance.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                setDisable(empty || date.isAfter(dateLimite));
            }
        });

        if (champRecherche != null) {
            champRecherche.textProperty().addListener((obs, ancienne, nouvelle) -> appliquerFiltres());
        }
        if (btnFiltrerPersonnes != null) {
            btnFiltrerPersonnes.setOnAction(e -> appliquerFiltres());
        }

        colNumeroIM.setCellValueFactory(new PropertyValueFactory<>("im"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenoms.setCellValueFactory(new PropertyValueFactory<>("prenoms"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colDatePaiement.setCellValueFactory(new PropertyValueFactory<>("datePaiement"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        chargerPaiements();

        if (btnFiltrer != null) {
            btnFiltrer.setOnAction(e -> {
                LocalDate debut = dateDebutFiltre.getValue();
                LocalDate fin = dateFinFiltre.getValue();
                chargerPaiements(debut, fin);
            });
        }

        if (champRecherche != null) {
            champRecherche.setOnAction(e -> rechercherPersonneParLike(champRecherche.getText()));
            // le listener en direct existant reste pour le filtre local, inchangé
            champRecherche.textProperty().addListener((obs, ancienne, nouvelle) -> appliquerFiltres());
        }

        colActionsPaiement.setCellFactory(col -> new TableCell<>() {
            private final Button btnSupprimer = new Button("🗑");

            {
                btnSupprimer.setOnAction(e -> {
                    Paiement paiement = getTableView().getItems().get(getIndex());
                    supprimerPaiement(paiement);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnSupprimer);
            }
        });
    }

    private String imEnCoursModification = null; // null = mode création, sinon = mode modification




    private void configurerFormulairePaiement() {

        // --- Mois ---
        comboMois.setItems(FXCollections.observableArrayList(
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        ));

        // --- Année (5 dernières années) ---
        int anneeCourante = LocalDate.now().getYear();
        ObservableList<Integer> annees = FXCollections.observableArrayList();
        for (int a = anneeCourante; a >= anneeCourante - 5; a--) annees.add(a);
        comboAnnee.setItems(annees);

        // --- Retraité : affichage "IM — NOM Prénoms" ---
        comboRetraite.setConverter(new StringConverter<>() {
            @Override
            public String toString(Personne p) {
                return p == null ? "" : p.getIm() + " — " + p.getNom() + " " + p.getPrenoms();
            }
            @Override
            public Personne fromString(String s) { return comboRetraite.getValue(); }
        });

        comboRetraite.setItems(FXCollections.observableArrayList(listePersonnesComplete));

        // Filtrage en direct pendant la frappe
        comboRetraite.getEditor().textProperty().addListener((obs, ancien, nouveau) -> {
            if (comboRetraite.getValue() != null
                    && comboRetraite.getConverter().toString(comboRetraite.getValue()).equals(nouveau)) {
                return; // évite de refiltrer juste après une sélection
            }
            String recherche = nouveau.toLowerCase();
            ObservableList<Personne> filtres = listePersonnesComplete.stream()
                    .filter(p -> p.getIm().toLowerCase().contains(recherche)
                            || p.getNom().toLowerCase().contains(recherche)
                            || p.getPrenoms().toLowerCase().contains(recherche))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            comboRetraite.setItems(filtres);
            if (!filtres.isEmpty() && !recherche.isBlank()) comboRetraite.show();
        });

        comboRetraite.setOnAction(e -> mettreAJourApercu());
        comboMois.setOnAction(e -> validerFormulairePaiement());
        comboAnnee.setOnAction(e -> validerFormulairePaiement());
    }


    private static class InfoTarif {
        String numTarif;
        int montant;
    }

    private InfoTarif chargerInfoTarifParDiplome(String diplome) {
        String sql = "SELECT num_tarif, montant FROM TARIF WHERE diplome = ? LIMIT 1";
        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, diplome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    InfoTarif info = new InfoTarif();
                    info.numTarif = rs.getString("num_tarif");
                    info.montant = rs.getInt("montant");
                    return info;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    private void mettreAJourApercu() {
        Personne p = comboRetraite.getValue();
        if (p == null) return;

        Integer montant = chargerMontantParDiplome(p.getDiplome());

        lblApercuNom.setText(p.getNom() + " " + p.getPrenoms());
        lblApercuDiplome.setText(p.getDiplome() != null ? p.getDiplome() : "—");

        if (montant != null) {
            lblApercuMontant.setText(String.format("%,d Ariary", montant).replace(',', ' '));
            lblAvertissement.setVisible(false);
            lblAvertissement.setManaged(false);
        } else {
            lblApercuMontant.setText("—");
            lblAvertissement.setText("⚠ Aucun tarif trouvé pour le diplôme \"" + p.getDiplome() + "\"");
            lblAvertissement.setVisible(true);
            lblAvertissement.setManaged(true);
        }

        validerFormulairePaiement();
    }

    private void validerFormulairePaiement() {
        boolean ok = comboRetraite.getValue() != null
                && comboMois.getValue() != null
                && comboAnnee.getValue() != null
                && chargerMontantParDiplome(comboRetraite.getValue().getDiplome()) != null;
        btnGenerer.setDisable(!ok);
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
                    ouvrirModificationPersonne(p);
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

    private void ouvrirModificationPersonne(Personne p) {
        imEnCoursModification = p.getIm();

        txtNumeroIM.setText(p.getIm());
        txtName.setText(p.getNom());
        txtPrenoms.setText(p.getPrenoms());
        txtDateNaissance.setValue(p.getDatenais());
        menuDiplome.setText(p.getDiplome() != null ? p.getDiplome() : "Sélectionner...");
        menuStatuts.setText(p.getStatut() != null ? p.getStatut() : "Vivant");
        menuSituationMatrimoniale.setText(p.getSituation() != null ? p.getSituation() : "Sélectionner...");

        txtContact.setText(p.getContact() != null ? p.getContact() : "");
        txtNomConjoint.setText(p.getNomConjoint() != null ? p.getNomConjoint() : "");
        txtPrenomConjoint.setText(p.getPrenomConjoint() != null ? p.getPrenomConjoint() : "");

        activeBtn(addAction);
    }



    private void ouvrirPopupModificationTarif(Tarif tarif) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popUp.fxml"));
            Parent racine = loader.load();

            AjoutTarifController controller = loader.getController();
            controller.setMeController(this);
            controller.preremplirPourModification(tarif);

            Scene scene = new Scene(racine);
            scene.setFill(Color.TRANSPARENT);

            Stage popup = new Stage();
            popup.initStyle(StageStyle.UNDECORATED);

            Stage fenetrePrincipale = (Stage) tableau.getScene().getWindow();
            popup.initOwner(fenetrePrincipale);
            popup.initModality(Modality.WINDOW_MODAL);

            popup.setScene(scene);
            popup.setResizable(false);
            popup.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chargerPersonnes() {
        listePersonnesComplete.clear();
        String sql = "SELECT IM, nom, prenoms, datenais, diplome, statut, situation, contact, nomconjoint, prenomconjoint FROM PERSONNE";

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
                        rs.getString("situation"),
                        rs.getString("contact"),
                        rs.getString("nomconjoint"),
                        rs.getString("prenomconjoint")
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

    private void supprimerPaiement(Paiement paiement) {
        String sql = "DELETE FROM PAYER WHERE im = ? AND num_tarif = ? AND date_paiement = ?";
        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, paiement.getIm());
            ps.setString(2, paiement.getNumTarif());
            ps.setObject(3, paiement.getDatePaiement());
            ps.executeUpdate();

            tablePaiements.getItems().remove(paiement);

        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de la suppression du paiement : " + e.getMessage());
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
            rafraichirComboRetraite();
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
                    ouvrirPopupModificationTarif(tarif);
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


    private void insererConjointSiDeces(String im, String statut, String nomConjoint,
                                        String prenomConjoint, String diplome) {

        if (!"Décédé".equals(statut)) return;
        if (nomConjoint == null || nomConjoint.isBlank()) return;
        if (prenomConjoint == null || prenomConjoint.isBlank()) return;

        Integer montantPension = chargerMontantParDiplome(diplome);
        if (montantPension == null) return;

        int montantConjoint = (int) Math.round(montantPension * 0.40);

        // Évite le doublon si la personne est déjà enregistrée comme décédée
        if (conjointExiste(im)) return;

        String sql = "INSERT INTO CONJOINT (numPension, NomConjoint, PrenomConjoint, montant) VALUES (?, ?, ?, ?)";
        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, im);
            ps.setString(2, nomConjoint);
            ps.setString(3, prenomConjoint);
            ps.setInt(4, montantConjoint);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de l'enregistrement du conjoint : " + e.getMessage());
        }
    }


    private void rechercherPersonneParLike(String terme) {
        if (terme == null || terme.isBlank()) {
            chargerPersonnes(); // recharge tout si recherche vide
            return;
        }

        listePersonnesComplete.clear();
        String motif = "%" + terme.trim() + "%";
        String sql = """
        SELECT IM, nom, prenoms, datenais, diplome, statut, situation, contact, nomconjoint, prenomconjoint
        FROM PERSONNE
        WHERE nom LIKE ? OR prenoms LIKE ? OR IM LIKE ?
        """;

        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, motif);
            ps.setString(2, motif);
            ps.setString(3, motif);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listePersonnesComplete.add(new Personne(
                            rs.getString("IM"),
                            rs.getString("nom"),
                            rs.getString("prenoms"),
                            rs.getDate("datenais") != null ? rs.getDate("datenais").toLocalDate() : null,
                            rs.getString("diplome"),
                            rs.getString("statut"),
                            rs.getString("situation"),
                            rs.getString("contact"),
                            rs.getString("nomconjoint"),
                            rs.getString("prenomconjoint")
                    ));
                }
            }

            personnesFiltrees = new FilteredList<>(listePersonnesComplete, p -> true);
            SortedList<Personne> triee = new SortedList<>(personnesFiltrees);
            triee.comparatorProperty().bind(tablePersonnes.comparatorProperty());
            tablePersonnes.setItems(triee);

            appliquerFiltres(); // réapplique les filtres statut/situation par-dessus le résultat LIKE
            mettreAJourCompteurs();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private boolean conjointExiste(String im) {
        String sql = "SELECT 1 FROM CONJOINT WHERE numPension = ?";
        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, im);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void chargerPaiements() {
        chargerPaiements(null, null);
    }

    private void chargerPaiements(LocalDate debut, LocalDate fin) {
        ObservableList<Paiement> liste = FXCollections.observableArrayList();

        StringBuilder sql = new StringBuilder("""
        SELECT pay.im, pay.num_tarif, per.nom, per.prenoms, tar.montant, pay.date_paiement, per.statut
        FROM PAYER pay
        JOIN PERSONNE per ON per.IM = pay.im
        JOIN TARIF tar ON tar.num_tarif = pay.num_tarif
        WHERE 1=1
        """);

        if (debut != null) sql.append(" AND pay.date_paiement >= ?");
        if (fin != null) sql.append(" AND pay.date_paiement <= ?");
        sql.append(" ORDER BY pay.date_paiement DESC");

        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql.toString())) {

            int index = 1;
            if (debut != null) ps.setObject(index++, debut);
            if (fin != null) ps.setObject(index++, fin);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(new Paiement(
                            rs.getString("im"),
                            rs.getString("num_tarif"),
                            rs.getString("nom"),
                            rs.getString("prenoms"),
                            rs.getInt("montant"),
                            rs.getDate("date_paiement").toLocalDate(),
                            rs.getString("statut")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tablePaiements.setItems(liste);
    }

    private void chargerTableauDeBord() {
        if (listePersonnesComplete == null) return;

        int total = listePersonnesComplete.size();
        long vivants = listePersonnesComplete.stream()
                .filter(p -> "Vivant".equals(p.getStatut())).count();
        long decedes = total - vivants;
        long conjoints = listePersonnesComplete.stream()
                .filter(p -> "Marié(e)".equals(p.getSituation())).count();

        // ----- KPI -----
        lblTotalPensionnaires.setText(String.valueOf(total));
        lblConjoints.setText(String.valueOf(conjoints));
        lblDecesSignales.setText(String.valueOf(decedes));

        // "Pensions ce mois" = nombre de vivants, car ce sont les pensions actuellement dues
        lblPensionsCeMois.setText(String.valueOf(vivants));

        // Tendances : pas de données historiques disponibles -> on masque plutôt que d'afficher un faux "+0%"
        lblTrendTotal.setText(total + " au total");
        lblTrendPensions.setText(vivants + " pension(s) active(s)");
        lblTrendConjoints.setText(conjoints + " conjoint(s) marié(e)");
        lblTrendDeces.setText(decedes + " décès enregistré(s)");

        // ----- BarChart : pensions par diplôme -----
        Map<String, Integer> effectifsParDiplome = new LinkedHashMap<>();
        for (Personne p : listePersonnesComplete) {
            String diplome = p.getDiplome() != null ? p.getDiplome() : "Non renseigné";
            effectifsParDiplome.merge(diplome, 1, Integer::sum);
        }

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Effectif");
        effectifsParDiplome.forEach((diplome, effectif) ->
                serie.getData().add(new XYChart.Data<>(diplome, effectif)));

        barChartCategories.getData().clear();
        barChartCategories.getData().add(serie);

        // ----- PieChart : répartition Vivant / Décédé -----
        pieChartStatut.getData().clear();
        pieChartStatut.getData().add(new PieChart.Data("Vivant", vivants));
        pieChartStatut.getData().add(new PieChart.Data("Décédé", decedes));

        lblVivantDetail.setText(vivants + " personne(s)");
        lblDecedeDetail.setText(decedes + " personne(s)");
    }






    @FXML
    private void genererRecu() {
        Personne p = comboRetraite.getValue();
        String mois = comboMois.getValue();
        Integer annee = comboAnnee.getValue();
        if (p == null || mois == null || annee == null) return;

        InfoTarif info = chargerInfoTarifParDiplome(p.getDiplome());
        if (info == null) {
            afficherAlerte("Impossible de générer : aucun tarif pour ce diplôme.");
            return;
        }

        // Enregistrement du paiement
        String sql = "INSERT INTO PAYER (im, num_tarif, date_paiement) VALUES (?, ?, ?)";
        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, p.getIm());
            ps.setString(2, info.numTarif);
            ps.setObject(3, LocalDate.now());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de l'enregistrement du paiement : " + e.getMessage());
            return;
        }

        labelReference.setText("Réf: RC-" + annee + "-O-" + p.getIm().replace("IM-", ""));
        labelNumeroIM.setText(p.getIm());
        labelNom.setText(p.getNom() != null ? p.getNom().toUpperCase() : "");
        labelPrenoms.setText(p.getPrenoms());
        labelPeriode.setText(mois + " " + annee);
        labelMontant.setText(String.format("%,d Ariary (Ar)", info.montant).replace(',', ' '));

        DateTimeFormatter formatteur = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
        labelDate.setText("Fait à Ambositra, le " + LocalDate.now().format(formatteur));

        chargerPaiements(); // rafraîchit l'historique si l'utilisateur y retourne
    }


    @FXML
    private void telechargerRecu() {
        if (labelNumeroIM.getText() == null || labelNumeroIM.getText().isBlank()) {
            afficherAlerte("Veuillez d'abord générer le reçu.");
            return;
        }

        Personne p = comboRetraite.getValue();
        String nomFichier = "recu_" + (p != null ? p.getIm() : labelNumeroIM.getText()) + ".pdf";

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le reçu");
        fileChooser.setInitialFileName(nomFichier);
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Document PDF", "*.pdf")
        );

        File fichier = fileChooser.showSaveDialog(ici.getScene().getWindow());
        if (fichier == null) return;

        try {
            // 1. Capture du reçu affiché à l'écran
            WritableImage snapshot = ici.snapshot(null, null);
            BufferedImage imageAwt = SwingFXUtils.fromFXImage(snapshot, null);

            // 2. Création du document PDF
            try (PDDocument document = new PDDocument()) {

                float largeurImage = imageAwt.getWidth();
                float hauteurImage = imageAwt.getHeight();

                PDPage page = new PDPage(new PDRectangle(largeurImage, hauteurImage));
                document.addPage(page);

                PDImageXObject imagePdf = LosslessFactory.createFromImage(document, imageAwt);

                try (PDPageContentStream contenu = new PDPageContentStream(document, page)) {
                    contenu.drawImage(imagePdf, 0, 0, largeurImage, hauteurImage);
                }

                document.save(fichier);
            }

            afficherAlerte("Reçu PDF enregistré avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de l'enregistrement du PDF : " + e.getMessage());
        }
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






    private Integer chargerMontantParDiplome(String diplome) {
        String sql = "SELECT montant FROM TARIF WHERE diplome = ? LIMIT 1";
        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, diplome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("montant");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

        String situation = item.getText();

        menuSituationMatrimoniale.setText(situation);

        boolean marie = situation.equals("Marié(e)");

        boxInformationsConjoint.setVisible(marie);
        boxInformationsConjoint.setManaged(marie);

        System.out.println("Situation matrimoniale : " + situation);
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
            if ("23503".equals(e.getSQLState())) {
                // Violation de contrainte de clé étrangère (code SQLState standard)
                afficherAlerte("Impossible de supprimer ce tarif : il est déjà utilisé dans un ou plusieurs paiements enregistrés.");
            } else {
                afficherAlerte("Erreur lors de la suppression du tarif : " + e.getMessage());
            }
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);

        if (tableau != null && tableau.getScene() != null) {
            alert.initOwner(tableau.getScene().getWindow());
        }

        alert.showAndWait();
    }

    private void rafraichirComboRetraite() {
        if (comboRetraite != null) {
            comboRetraite.setItems(FXCollections.observableArrayList(listePersonnesComplete));
        }
    }



    @FXML
    private void returnList(){
        imEnCoursModification = null;
        activeBtn(personne);
    }


    @FXML
    private void setAccueil() {
        activerBord(bord1);
        activeBtn(tableau);
        chargerTableauDeBord();
        chargerPaiements();
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
        tableau.setVisible(true);
        tableau.setManaged(true);

    }
    @FXML
    private void addPersonne(){
        imEnCoursModification = null;

        txtNumeroIM.setText(genererNumTarif());
        txtName.clear();
        txtPrenoms.clear();
        txtDateNaissance.setValue(null);
        menuDiplome.setText("Sélectionner...");
        menuStatuts.setText("Vivant");
        menuSituationMatrimoniale.setText("Sélectionner...");
        txtContact.clear();
        txtNomConjoint.clear();
        txtPrenomConjoint.clear();

        activeBtn(addAction);
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

        // ========================= VALIDATION (inchangée) =========================
        if (matricule.isBlank()) { afficherAlerte("Le numéro IM est obligatoire."); return; }
        if (name.isBlank()) { afficherAlerte("Veuillez saisir le nom."); return; }
        if (prenom.isBlank()) { afficherAlerte("Veuillez saisir le prénom."); return; }
        if (dateNaissance == null) { afficherAlerte("Veuillez sélectionner une date de naissance."); return; }
        if (diplome.equals("Sélectionner...")) { afficherAlerte("Veuillez sélectionner un diplôme."); return; }
        if (!statut.equals("Vivant") && !statut.equals("Décédé")) { afficherAlerte("Veuillez sélectionner le statut."); return; }
        if (matrimonial.equals("Sélectionner...")) { afficherAlerte("Veuillez sélectionner la situation matrimoniale."); return; }

        boolean modeModification = imEnCoursModification != null;

        if (modeModification) {
            modifierPersonneEnBase(matricule, name, prenom, dateNaissance, diplome, contact,
                    statut, matrimonial, conjoint, conjointPrenom);
        } else {
            creerPersonneEnBase(matricule, name, prenom, dateNaissance, diplome, contact,
                    statut, matrimonial, conjoint, conjointPrenom);
        }
    }

    private void creerPersonneEnBase(String matricule, String name, String prenom, LocalDate dateNaissance,
                                     String diplome, String contact, String statut, String matrimonial,
                                     String conjoint, String conjointPrenom) {

        String sql = """
        INSERT INTO PERSONNE
        (IM, nom, prenoms, datenais, diplome, contact, statut, situation, nomconjoint, prenomconjoint)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

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

            insererConjointSiDeces(matricule, statut, conjoint, conjointPrenom, diplome);

            afficherAlerte("Personne enregistrée avec succès.");
            chargerPersonnes();
            rafraichirComboRetraite();
            activeBtn(personne);

        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de l'enregistrement :\n" + e.getMessage());
        }
    }

    private void modifierPersonneEnBase(String matricule, String name, String prenom, LocalDate dateNaissance,
                                        String diplome, String contact, String statut, String matrimonial,
                                        String conjoint, String conjointPrenom) {

        String sql = """
        UPDATE PERSONNE
        SET nom = ?, prenoms = ?, datenais = ?, diplome = ?, contact = ?,
            statut = ?, situation = ?, nomconjoint = ?, prenomconjoint = ?
        WHERE IM = ?
        """;

        try (Connection cnx = Database.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, prenom);
            ps.setObject(3, dateNaissance);
            ps.setString(4, diplome);
            ps.setString(5, contact);
            ps.setString(6, statut);
            ps.setString(7, matrimonial);
            ps.setString(8, conjoint);
            ps.setString(9, conjointPrenom);
            ps.setString(10, matricule);
            ps.executeUpdate();

            // Déclenche le transfert au conjoint si le statut vient de passer à "Décédé"
            insererConjointSiDeces(matricule, statut, conjoint, conjointPrenom, diplome);

            afficherAlerte("Personne modifiée avec succès.");
            imEnCoursModification = null;
            chargerPersonnes();
            rafraichirComboRetraite();
            activeBtn(personne);

        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de la modification :\n" + e.getMessage());
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
            popup.initStyle(StageStyle.UNDECORATED);

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