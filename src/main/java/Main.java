import com.itextpdf.text.DocumentException;
import constants.Pages;
import entities.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repositories.FlightOrderRepository;
import repositories.FlightScheduleRepository;
import repositories.PlaneRepository;
import repositories.UserRepository;
import services.TicketPdfPrinter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(Pages.LOGIN));
        primaryStage.setTitle("Best Program Ever");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.show();

//        PlaneRepository planeRepository = new PlaneRepository();
//        planeRepository.saveOrUpdate(new Plane(null,"F546","Wizzair",200));
//        planeRepository.saveOrUpdate(new Plane(null,"T10","Ryanair",100));
//        planeRepository.saveOrUpdate(new Plane(null,"TY8585","Air Baltic",250));
//        planeRepository.saveOrUpdate(new Plane(null,"U87","Turkish Airlines",300));
//        planeRepository.saveOrUpdate(new Plane(null,"F546","KLM",500));

        UserRepository userRepository = new UserRepository();
        userRepository.saveOrUpdate(User.builder().userName("as").password("as").email("as").build());

    }

    public static void main(String[] args) throws DocumentException, IOException, URISyntaxException {

        launch(args);

    }
}