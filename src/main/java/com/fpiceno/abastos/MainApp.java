package com.fpiceno.abastos;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Productos.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/principal.css");
        
        stage.setTitle("Contro de Productos Abastos");
        stage.setScene(scene);
        stage.show();
        
             /* initialize log4j */
    BasicConfigurator.configure();
    PropertyConfigurator.configure("src/main/resources/log4j.properties");
    
//     stage.setOnCloseRequest(e -> Platform.exit()  );
    
          stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
            });
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
