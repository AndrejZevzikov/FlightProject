import constants.Pages;
import entities.FlightOrder;
import entities.FlightSchedule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repositories.FlightOrderRepository;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(Pages.LOGIN));
        primaryStage.setTitle("Best Program Ever");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.sizeToScene();
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {

        FlightOrderRepository flightOrderRepository = new FlightOrderRepository();
        List<FlightOrder> flights = flightOrderRepository.findAll();



//        launch(args);
    }
}