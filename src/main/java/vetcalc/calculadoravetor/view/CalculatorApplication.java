package vetcalc.calculadoravetor.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Murilo Nunes, Davy Lopes Oliveira, Hartur Sales, Pedro Henrique, Bruno Martins
 * @date 31/10/2024
 * @brief Class CalculatorApplication
 */

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(Objects.requireNonNull(CalculatorApplication.class.getResource("/font/Gabarito-Regular.ttf")).toExternalForm(), 10);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vetcalc/calculadoravetor/vetores.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 450, 450);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());

        stage.getIcons().add(new Image(Objects.requireNonNull(CalculatorApplication.class.getResourceAsStream("/images/icon.png"))));
        stage.setTitle("Calculadora de Vetor");
        stage.setScene(scene);
        //desativa a opçao de redimensionar
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}