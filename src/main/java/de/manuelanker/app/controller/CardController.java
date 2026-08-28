
package de.manuelanker.app.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

public class CardController implements Initializable
{
	@FXML
	public Label statusChip;
	@FXML
	public ImageView previewImage;
	@FXML
	public Label titleLabel;
	@FXML
	public Label descLabel;
	@FXML
	public FlowPane techFlow;

	public AppController appController;
	public String runCommand = "";
	public String codePath = "";
	public boolean showCode = false;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), statusChip);
		fadeTransition.setFromValue(0.0D);
		fadeTransition.setToValue(1.0D);
		fadeTransition.setAutoReverse(true);
		fadeTransition.setInterpolator(Interpolator.TANGENT(Duration.ZERO, 1.0D));
		fadeTransition.setCycleCount(Animation.INDEFINITE);
		fadeTransition.play();

	}

	@FXML
	private void onEditClicked(ActionEvent e) {
		if (showCode) {
			try {
				java.awt.Desktop.getDesktop().open(new File(codePath));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			appController.editCurrentCard();
		}
	}

	@FXML
	private void onOpenClicked(ActionEvent e) {
		if (runCommand.equals("")) {
			return;
		}

		try {
			String os = System.getProperty("os.name").toLowerCase();
			ProcessBuilder builder;
			if (os.contains("win")) {
				builder = new ProcessBuilder("cmd.exe", "/c", runCommand);
			} else {
				builder = new ProcessBuilder("/bin/sh", "-c", runCommand);
			}
			builder.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
