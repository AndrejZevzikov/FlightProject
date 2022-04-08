import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.Pages;
import entities.FlightSchedule;
import entities.Plane;
import entities.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repositories.FlightScheduleRepository;
import repositories.PlaneRepository;
import services.FlightScheduleServices;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource(Pages.LOGIN));
        primaryStage.setTitle("Best Program Ever");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.sizeToScene();
        primaryStage.show();

    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
