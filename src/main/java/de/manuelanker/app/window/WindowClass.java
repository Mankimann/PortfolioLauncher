package de.manuelanker.app.window;

import java.io.FileNotFoundException;
import java.net.URL;

import de.manuelanker.app.toolbox.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowClass extends WindowListener{

	private static boolean isDisplayShouldClose = false;
	
	@FXML
	private Parent root;
	@FXML
	private Scene scene;
	
	@FXML
	public Stage stage;
	
	public static WindowClass instance;

	@Override
	public void create(Stage stage, int w, int h, String title, boolean isVisible) {
		
		this.stage = stage;
		root = loadPage("PortfolioView.fxml");
		scene = new Scene(root, w, h);
		
		stage = new Stage();
		stage.setTitle(title);
		stage.centerOnScreen();
		stage.setScene(scene);
		
		if(isVisible || !isDisplayShouldClose) {
			stage.show();
		}
		else if (isDisplayShouldClose) {
			stage.close();
		}
	
	}
	
	@Override
	public void resize(int w, int h) {
	
	}
	
	@Override
	public void dispose() {
		if(!isDisplayShouldClose) System.exit(1);
	}
	
    public Parent loadPage(String name) {
        try {
            URL fileUrl = WindowClass.class.getResource(Constants.FXMLPath + name);

            if(fileUrl == null) {
                throw new FileNotFoundException("FXML file cant be found");
            }

            root = FXMLLoader.load(fileUrl);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }
	
	public static WindowClass getInstance()
	{
		return WindowClass.instance;
	}
	


}
