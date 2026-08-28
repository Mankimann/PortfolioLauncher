module Examples {
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires org.yaml.snakeyaml;

	opens de.manuelanker.app to javafx.graphics, javafx.fxml;
	opens de.manuelanker.app.controller to javafx.fxml;
	exports de.manuelanker.app;
}
