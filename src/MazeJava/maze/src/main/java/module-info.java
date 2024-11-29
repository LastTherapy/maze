module ru.dobrochan.maze {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens ru.dobrochan.maze to javafx.fxml;
    exports ru.dobrochan.maze;
}