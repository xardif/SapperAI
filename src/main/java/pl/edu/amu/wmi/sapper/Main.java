package pl.edu.amu.wmi.sapper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.amu.wmi.sapper.ui.Controller;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController();
        SapperAI sapperAI = new SapperAI(controller);

        primaryStage.setTitle("SapperAI");
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread sapperAIThread = new Thread(sapperAI);
        sapperAIThread.setDaemon(true);
        sapperAIThread.start();
    }

    public static void main(String[] args){
        launch(args);
    }

}
