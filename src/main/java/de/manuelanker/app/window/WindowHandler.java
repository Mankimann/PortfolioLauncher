package de.manuelanker.app.window;

import javafx.stage.Stage;

public interface WindowHandler {
	void create(Stage stage, int w, int h, String title, boolean isVisible);
	void resize(int w, int h);
	void dispose();
}
