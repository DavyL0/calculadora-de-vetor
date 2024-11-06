module vetcalc.calculadoravetor {
    requires javafx.controls;
    requires javafx.fxml;

    exports vetcalc.calculadoravetor.model.main;
    opens vetcalc.calculadoravetor.model.main to javafx.fxml;
    exports vetcalc.calculadoravetor.controller;
    opens vetcalc.calculadoravetor.controller to javafx.fxml;
    exports vetcalc.calculadoravetor.view;
    opens vetcalc.calculadoravetor.view to javafx.fxml;
}