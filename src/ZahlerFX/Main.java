package ZahlerFX;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  {
        Database database = new Database();
        Counter counter = new Counter(CountingObject.CAR);
        new GUI2(counter, database);
        new DBConnection();

    }
}