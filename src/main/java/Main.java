import constants.PagesPaths;
import entities.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repositories.UserRepository;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(PagesPaths.LOGIN));
        primaryStage.setTitle("Best Program Ever");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        UserRepository userRepository = new UserRepository();
        userRepository.saveOrUpdate(User.builder().userName("as").password("as").email("as").build());
    }

    public static void main(String[] args) {

        launch(args);
    }
}