package Superbruker;

import Datamaskin.Komponent;
import Datamaskin.KomponentCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Superbruker implements Initializable {

    @FXML
    private TableView tabell3;

    @FXML
    private TableColumn<Komponent, String> navnC3;

    @FXML
    private TableColumn<Komponent, String> komponentC3;

    @FXML
    private TableColumn<Komponent, Integer> prisC3;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField innNavn;

    @FXML
    private TextField innPris;

    @FXML
    private TextField txtFiltrer;

    @FXML
    private Label lblNyttKomponent;

    @FXML
    void lagreEndringer(ActionEvent event) {

    }

    @FXML
    void leggTilKomponent(ActionEvent event) {
        Komponent nyttKomponent= null;
        try {
            nyttKomponent = opprettKomponent();
        }
        catch (NumberFormatException e){
            lblNyttKomponent.setText(e.getMessage());
        }
        if (nyttKomponent != null){
            kColl3.leggTilElement(nyttKomponent);
            resetTextFields();
        }
    }

    KomponentCollection kColl3= new KomponentCollection();

    ArrayList<Komponent> dataListe= new ArrayList<>();

    ObservableList<String> tilgjengeligeValg= FXCollections.observableArrayList("Prosessor", "Skjermkort", "Minne", "Harddisk", "Tastatur", "Mus", "Skjerm");

    Komponent test= new Komponent("Test1", "Minne", 1000);
    Komponent test1= new Komponent("Test2", "Skjermkort", 1600);
    Komponent test2= new Komponent("Test3", "Harddisk", 2000);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kColl3.kobleTilTableView(tabell3);
        kColl3.sorterTableView(tabell3, txtFiltrer);

        choiceBox.setItems(tilgjengeligeValg);

        navnC3.setCellFactory(TextFieldTableCell.forTableColumn());
        komponentC3.setCellFactory(TextFieldTableCell.forTableColumn());
        prisC3.setCellFactory(TextFieldTableCell.<Komponent,Integer>forTableColumn(new IntegerStringConverter()));

        kColl3.leggTilElement(test);
        kColl3.leggTilElement(test1);
        kColl3.leggTilElement(test2);

        tabell3.setEditable(true);
    }

    private Komponent opprettKomponent(){
        String navn= innNavn.getText();
        String komponent= choiceBox.getSelectionModel().getSelectedItem();
        int pris= Integer.parseInt(innPris.getText());

        Komponent nyttKomponent= new Komponent(navn, komponent, pris);

        return nyttKomponent;
    }

    private void resetTextFields(){
        innNavn.setText("");
        innPris.setText("");
        choiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    public void txtNavnEdited(TableColumn.CellEditEvent<Komponent, String> event){
        event.getRowValue().setNavn(event.getNewValue());
    }

    @FXML
    public void txtKomponentEdited(TableColumn.CellEditEvent<Komponent, String> event){
        event.getRowValue().setKomponent(event.getNewValue());
    }

    @FXML
    public void intPrisEdited(TableColumn.CellEditEvent<Komponent, Integer> event){
        if (IntegerStringOmgjøring.omgjøring){
            event.getRowValue().setPris(event.getNewValue());
        }
        tabell3.refresh();
    }
}