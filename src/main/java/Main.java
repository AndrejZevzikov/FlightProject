
import constants.Pages;
import entities.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repositories.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
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

        launch(args);

        UserRepository userRepository = new UserRepository();

        User user1 = User.builder().build();
        User user2 = User.builder().build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        userRepository.saveOrUpdate(users);

    }
}
