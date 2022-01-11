package ZahlerFX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Die Klasse GUI1 ist das Fenster mit dem man ein neues Zahlungsobjekt erstellen kann.
 *
 * @author Noel Huibers, Jan Reuter, Boris Hyacinte Kemadjou Djeunou
 * @version 0.1.1
 */

public class GUI1 {


    //Class variables
    private GridPane layout;
    private Stage stage1;
    private Button button;
    private ComboBox<CountingObject> box;
    private TextField startField;
    private Label errorLabel;
    private Label objectLabel;
    private Label startLabel;


    /**
     * Diese ist der Konstruktor der Klasse GUI1. Diese Funktion wird beim erstellen des Objekts ausgeführt und initialisiert die einzelnen Methoden.
     * @param database;
     */
    public GUI1(Database database) {
        stageTitle();
        layout();
        makeObjectBox();
        makeObjectLabel();
        makeStartField();
        makeStartLabel();
        makeButton();
        makeErrorLabel();
        setChildren();
        setScene();
    }


    /**
     * Diese Funktion erstellt die stage und gibt der stage einen Namen.
     */
    private void stageTitle() {
        stage1 = new Stage();
        stage1.setTitle("First Window");
    }


    /**
     * Diese Funktion bestimmt das Layout der Scene, hier ein GridPane Layout.
     */
    private void layout() {
        layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(10);
        layout.setHgap(8);
    }


    /**
     * Diese Funktion erstellt eine Combobox zum festlegen des Objekts.
     */
    private void makeObjectBox() {
        box = new ComboBox<>();
        box.getItems().addAll(CountingObject.values());
        GridPane.setConstraints(box,1,0);
    }


    /**
     * Diese Funktion erstellt ein Label mit dem Inhalt "Choose an Object:".
     */
    private void makeObjectLabel() {
        objectLabel = new Label("Choose an object:");
        GridPane.setConstraints(objectLabel,0,0);
    }


    /**
     * Diese Funktion erstellt ein Textfeld was standartmäßig auf 0 anfängt.
     */
    //Doesnt work yet
    private void makeStartField() {
        Counter counter = new Counter(box.getValue());
        startField = new TextField(String.valueOf(counter.number));
        GridPane.setConstraints(startField,1,1);
    }


    /**
     * Diese Funktion erstellt ein Label mit dem Inhalt "Choose a starting value:".
     */
    private void makeStartLabel() {
        startLabel = new Label("Choose a starting value:");
        GridPane.setConstraints(startLabel,0,1);

    }


    /**
     * Diese Funktion erstellt den Confirm Button, welcher eine neue Instanz von GUI2 erstellt. Sollte man kein Objekt ausgewählt haben
     * erscheint stattdessen ein Errorlabel und es wird keine neue Instanz erstellt.
     */
    private void makeButton() {
        button = new Button("Confirm");
        button.setOnAction(e -> {
            Counter counter = new Counter(box.getValue());
            Database database = new Database();
            if(box.getValue() != null) {
                new GUI2(counter, database);
                stage1.close();
            } else {
                errorLabel.setVisible(true);
            }
        });
        GridPane.setConstraints(button,0,6);
    }


    /**
     * Diese Funktion erstellt das Errorlabel welches erscheint sollte man kein Objekt ausgewählt haben und man den Confirm Button drückt.
     */
    private void makeErrorLabel() {
        errorLabel = new Label("Choose an object!");
        errorLabel.setVisible(false);
        errorLabel.getStyleClass().add("label-red");
        GridPane.setConstraints(errorLabel,0,5);

    }


    /**
     * Diese Funktion fügt alle Elemente dem Layout hinzu.
     */
    private void setChildren() {
        layout.getChildren().addAll(box,objectLabel,startField,startLabel,button,errorLabel);

    }


    /**
     * Diese Funktion erstellt die Scene des Fensters.
     */
    private void setScene() {
        Scene scene1 = new Scene(layout, 350, 300);
        scene1.getStylesheets().add("ZahlerFX/Optik.css");
        stage1.setScene(scene1);
        stage1.setResizable(false);
        stage1.show();
    }



}
