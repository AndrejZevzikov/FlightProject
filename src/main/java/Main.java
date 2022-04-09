
import constants.Pages;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(Pages.LOGIN));
        primaryStage.setTitle("Best Program Ever");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.sizeToScene();
        primaryStage.setResizable(true);
        primaryStage.show();

    }


    public static void main(String[] args) throws IOException {

        launch(args);
    }
}
