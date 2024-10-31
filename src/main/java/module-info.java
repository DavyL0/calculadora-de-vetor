module vetcalc.calculadoravetor {
    requires javafx.controls;
    requires javafx.fxml;


    opens vetcalc.calculadoravetor to javafx.fxml;
    exports vetcalc.calculadoravetor;
}