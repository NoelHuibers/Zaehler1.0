package ZahlerFX;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Die Klasse GUI2 ist das Hauptfenster welches den Zähler und damit zusammenhängende Elemente enthält.
 *
 * @author Noel Huibers, Jan Reuter, Boris Hyacinte Kemadjou Djeunou
 * @version 0.1.1
 */

public class GUI2 {



    //Class variables
    private BorderPane layout;
    private GridPane centerL;
    private FlowPane topL;
    private ListView<CountingObject> leftL;
    private VBox rightL;
    private Stage stage2;
    private Spinner<Integer> step;
    private Button backButton;
    private Button addButton;
    private Button resetButton;
    private Button plus;
    private Button minus;
    private Label name;
    private Label zahl;


    /**
     * Diese ist der Konstruktor der Klasse GUI2. Diese Funktion wird beim erstellen des Objekts ausgeführt und initialisiert die einzelnen Methoden.
     * @param counter;
     * @param database;
     */
    public GUI2(Counter counter, Database database){
        stageTitle();
        makeBackButton(database);
        makeResetButton(counter);
        makeAddButton(database);
        makePlusButton(counter);
        makeMinusButton(counter);
        makeNameLabel(counter);
        makeCounterLabel(counter);
        makeStepCount(counter);
        makeCenter();
        makeTop();
        makeLeft(counter);
        makeRight();
        makeBorderpane();
        setScene();
    }


    /**
     * Diese Funktion erstellt die stage und gibt der stage einen Namen.
     */
    private void stageTitle() {
        stage2 = new Stage();
        stage2.setTitle("Second Window");
    }


    /**
     * Diese Funktion erstellt einen Button mit dem man eine neue Instanz von GUI1 aufruft und die GUI2 Instanz schließt.
     * @param database;
     */
    private void makeBackButton(Database database) {
        backButton = new Button("Back");
        backButton.setPrefSize(70,40);
        backButton.setOnAction(e -> {
            new GUI1(database);
            stage2.close();
        });
    }


    /**
     * Diese Funktion erstellt einen Button der eine neue Instanz von GUI1 aufruft um ein neues Objekt zu erstellen.
     * @param database;
     */
    private void makeAddButton(Database database) {
        addButton = new Button("+ Add");
        addButton.setPrefSize(70,40);
        addButton.setOnAction(e -> new GUI1(database));
    }


    /**
     * Diese Funktion erstellt einen Button mit dem man den Zähler auf 0 setzten kann.
     * @param counter;
     */
    private void makeResetButton(Counter counter) {
        resetButton = new Button("Reset");
        resetButton.setPrefWidth(100);
        resetButton.setPrefHeight(50);
        resetButton.setOnAction(e -> {
            counter.reset();
            zahl.setText(String.valueOf(counter.number));
        });
    }


    /**
     * Diese Funktion erstellt einen Button mit dem man den Zähler erhöht.
     * @param counter;
     */
    private void makePlusButton(Counter counter) {
        plus = new Button("+\n");
        plus.setPrefWidth(50);
        plus.setPrefHeight(50);
        plus.setMaxWidth(Double.MAX_VALUE);
        plus.setOnAction(e -> {
            counter.countUp();
            zahl.setText(String.valueOf(counter.number));
        });
        GridPane.setConstraints(plus,4,13);
    }


    /**
     * Diese Funktion erstellt einen Button mit dem man den Zähler vermindert.
     * @param counter;
     */
    private void makeMinusButton(Counter counter) {
        minus = new Button("-\n");
        minus.setPrefWidth(50);
        minus.setPrefHeight(50);
        minus.setMaxWidth(Double.MAX_VALUE);
        minus.setOnAction(e -> {
            counter.countDown();
            zahl.setText(String.valueOf(counter.number));
        });
        GridPane.setConstraints(minus,3,13);
    }


    /**
     * Diese Funktion erstellt ein Label, welches den derzeitigen Objektnamen anzeigt.
     * @param counter;
     */
    private void makeNameLabel(Counter counter) {
        name = new Label(counter.name + " :");
        name.setPrefWidth(200);
        name.setPrefHeight(100);
        name.setMaxWidth(Double.MAX_VALUE);
        name.getStyleClass().addAll("label-bold", "label-big");
        GridPane.setConstraints(name,0,11);
    }


    /**
     * Diese Funktion erstellt ein Label, welches den derzeitigen Wert des Zählers anzeigt.
     * @param counter;
     */
    private void makeCounterLabel(Counter counter) {
        zahl = new Label(String.valueOf(counter.number));
        zahl.setPrefWidth(200);
        zahl.setPrefHeight(40);
        zahl.setMaxWidth(Double.MAX_VALUE);
        zahl.getStyleClass().add("label-big");
        GridPane.setConstraints(zahl,5,11);
    }


    /**
     * Diese Funktion erstellt einen Spinner mit dem man die Schrittzahl anpassen kann.
     * @param counter;
     */
    private void makeStepCount(Counter counter) {
        step = new Spinner<>(1,1000000,counter.stepCount);
        step.setPrefWidth(100);
        step.setPrefHeight(50);
        step.setEditable(true);
        step.valueProperty().addListener((v, oldValue, newValue) -> counter.setStepCount(newValue));
    }


    /**
     * Diese Funktion fügt die Elemente Plusbutton, Minusbutton, Namelabel und Counterlabel der Center Area des Borderpanes hinzu.
     */
    private void makeCenter() {
        centerL = new GridPane();
        centerL.setPadding(new Insets(10,10,10,10));
        centerL.setVgap(10);
        centerL.setHgap(10);
        //centerL.setGridLinesVisible(true);
        centerL.getChildren().addAll(plus, minus, name, zahl);
    }


    /**
     * Diese Funktion fügt die Elemente Backbutton und Addbutton der Top Area des Borderpanes hinzu.
     */
    private void makeTop() {
        topL = new FlowPane();
        topL.setHgap(25);
        topL.setPadding(new Insets(20,10,10,20));
        topL.setPrefHeight(80);
        topL.getChildren().addAll(backButton, addButton);
    }


    /**
     * Diese Funktion ertellt eine Listview in der Left Area des Borderpanes welches die einzelnen Objekte anzeigt.
     * Bei einem Linksclick öffnet sich ein Menuitem mit dem man das jeweilige Objekt löschen kann.
     * @param counter;
     */
    private void makeLeft(Counter counter) {
        //creates listview
        leftL = new ListView<>();
        leftL.setPrefWidth(250);
        leftL.getItems().addAll(CountingObject.BIRD, CountingObject.LANGESWORT12);
        //creates cell factory for listview
        leftL.setCellFactory(lv -> {
            ListCell<CountingObject> cell = new ListCell<>();

            ContextMenu context = new ContextMenu();

            //adds item with name "delete" and sets function to delete the selected item
            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("Delete", cell.itemProperty()));
            deleteItem.setOnAction(event -> leftL.getItems().remove(cell.getItem()));
            context.getItems().add(deleteItem);

            cell.textProperty().bind(Bindings.createStringBinding(
                    () -> Objects.toString(cell.getItem(), ""),
                    cell.itemProperty()));

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (!isNowEmpty) {
                    cell.setContextMenu(context);
                }
            });

            //Change name and counter to the values in the database (if possible)
            cell.setOnMouseClicked(e -> {
                if(!cell.isEmpty()) {
                    name.setText("Hallo");
                    zahl.setText("100");
                }
            });
            return cell ;
        });
    }


    /**
     * Diese Funktion fügt die Elemente Stepcount und Resetbutton der Right Area des Borderpanes hinzu.
     */
    private void makeRight() {
        rightL = new VBox();
        rightL.setPadding(new Insets(10,10,10,10));
        rightL.setSpacing(40);
        rightL.setPrefWidth(250);
        rightL.getChildren().addAll(step, resetButton);
    }


    /**
     * Diese Funktion erstellt das Borderpane Layout mit den verschiedenen Areas.
     */
    private void makeBorderpane() {
        layout = new BorderPane();
        layout.setCenter(centerL);
        centerL.setAlignment(Pos.CENTER_LEFT);
        layout.setTop(topL);
        layout.setLeft(leftL);
        layout.setRight(rightL);
    }


    /**
     * Diese Funktion erstellt die Scene des Fensters.
     */
    private void setScene() {
        Scene scene2 = new Scene(layout,1100,700);
        scene2.getStylesheets().add("ZahlerFX/Optik.css");
        stage2.setScene(scene2);
        stage2.setResizable(false);
        stage2.show();
    }



}
