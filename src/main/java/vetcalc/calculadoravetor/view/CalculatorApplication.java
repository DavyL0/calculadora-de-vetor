package vetcalc.calculadoravetor.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Murilo Nunes, Davy Lopes Oliveira, Hartur Sales, Pedro Henrique, Bruno Martins
 * @date 31/10/2024
 * @brief Class Main
 */

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vetcalc/calculadoravetor/vetores.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());

        stage.getIcons().add(new Image(Objects.requireNonNull(CalculatorApplication.class.getResourceAsStream("/images/icon.png"))));
        stage.setTitle("Calculadora de Vetor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}