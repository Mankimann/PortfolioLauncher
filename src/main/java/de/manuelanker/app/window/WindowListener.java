package de.manuelanker.app.window;

import javafx.stage.Stage;

public abstract class WindowListener implements WindowHandler {
	
	@Override
	public void create(Stage stage,int w, int h, String title, boolean isVisible) {}
	
	@Override
	public void resize(int w, int h) {}
	
	@Override
	public void dispose() {}
	
	

}
