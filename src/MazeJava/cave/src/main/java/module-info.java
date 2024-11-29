module ru.dobrochan.cave {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens ru.dobrochan.cave to javafx.fxml;
    exports ru.dobrochan.cave;
}