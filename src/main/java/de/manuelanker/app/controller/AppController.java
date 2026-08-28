package de.manuelanker.app.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.yaml.snakeyaml.Yaml;

import de.manuelanker.app.window.WindowClass;
import static de.manuelanker.app.toolbox.Constants.*;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AppController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private StackPane cardSlot;

	@FXML
	private StackPane dropOverlay;
	
	@FXML
	private Button prevButton;
	@FXML
	private Label projectCount;
	@FXML
	private Button nextButton;
	@FXML
	private HBox tickBar;
	
	
	public CardController cardController;
	
	public boolean doneLoading = false;

	public static WindowClass instance = WindowClass.getInstance();


	private void openCardEditorWindow(File droppedImage) {
		try {
			FXMLLoader editorLoader = new FXMLLoader(getClass().getResource(FXMLPath + "CardEditor.fxml"));
			Parent editorRoot = editorLoader.load();

			CardEditorController editorController = editorLoader.getController();
			editorController.setImageFile(droppedImage);
			editorController.cardIndex = currentCount;

			Stage editorStage = new Stage();
			editorStage.setTitle("Karte anlegen");
			editorStage.initOwner(root.getScene().getWindow());
			editorStage.initModality(Modality.WINDOW_MODAL);
			editorStage.setScene(new Scene(editorRoot));
			editorStage.showAndWait();

			if (editorController.wasSaved) {
				showCard(currentCount);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showCard(int target) {
	    index = target;
	    try {
	        FXMLLoader loader = new FXMLLoader(
	            getClass().getResource(FXMLPath + "ProjectCard.fxml"));
	        Node card = loader.load();
	        cardController = loader.getController();
	        cardController.appController = this;

	        cardSlot.getChildren().setAll(card);

	        File yamlFile = new File(CardDataDir, "card" + target + ".yml");
	        if (yamlFile.exists()) {
	            loadCardData(yamlFile);
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }

	}

	@SuppressWarnings("unchecked")
	private void loadCardData(File yamlFile) {
		try {
			FileReader reader = new FileReader(yamlFile);
			Yaml yaml = new Yaml();
			
			Map<String, Object> data = (Map<String, Object>) yaml.load(reader);
			reader.close();

			cardController.titleLabel.setText((String) data.get("title"));
			cardController.statusChip.setText((String) data.get("status"));
			cardController.descLabel.setText((String) data.get("description"));

			cardController.techFlow.getChildren().clear();
			List<String> techList = (List<String>) data.get("tech");
			if (techList != null) {
				for (int i = 0; i < techList.size(); i++) {
					Label chip = new Label(techList.get(i));
					chip.getStyleClass().add("chip");
					cardController.techFlow.getChildren().add(chip);
				}
			}

			String imagePath = (String) data.get("image");
			if (imagePath != null) {
				File imageFile = new File(imagePath);
				if (imageFile.exists()) {
					cardController.previewImage.setImage(new Image(imageFile.toURI().toString()));
				}
			}

			String runCommand = (String) data.get("runCommand");
			if (runCommand == null) {
				runCommand = "";
			}
			cardController.runCommand = runCommand;

			String codePath = (String) data.get("codePath");
			if (codePath == null) {
				codePath = "";
			}
			cardController.codePath = codePath;

			Boolean showCode = (Boolean) data.get("showCode");
			cardController.showCode = (showCode != null && showCode);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void editCurrentCard() {
		try {
			FXMLLoader editorLoader = new FXMLLoader(getClass().getResource(FXMLPath + "CardEditor.fxml"));
			Parent editorRoot = editorLoader.load();

			CardEditorController editorController = editorLoader.getController();
			editorController.cardIndex = currentCount;

			File yamlFile = new File(CardDataDir, "card" + currentCount + ".yml");
			if (yamlFile.exists()) {
				FileReader reader = new FileReader(yamlFile);
				Yaml yaml = new Yaml();
				@SuppressWarnings("unchecked")
				Map<String, Object> data = (Map<String, Object>) yaml.load(reader);
				reader.close();
				editorController.loadExisting(data);
			}

			Stage editorStage = new Stage();
			editorStage.setTitle("Karte bearbeiten");
			editorStage.initOwner(root.getScene().getWindow());
			editorStage.initModality(Modality.WINDOW_MODAL);
			editorStage.setScene(new Scene(editorRoot));
			editorStage.showAndWait();

			if (editorController.wasSaved) {
				showCard(currentCount);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void prevCard(ActionEvent e)
	{
		goToCard(currentCount - 1);
	}

	@FXML
	private void nextCard(ActionEvent e)
	{
		goToCard(currentCount + 1);
	}

	private void goToCard(int target)
	{
		if(target < 1 || target > maxCount || target == currentCount)
			return;

		boolean goingForward = target > currentCount;
		currentCount = target;

		double distance = cardSlot.getWidth();
		if (distance <= 0) {
			distance = 480;
		}

		double outX;
		if (goingForward) {
			outX = -distance;
		} else {
			outX = distance;
		}

		TranslateTransition slideOut = new TranslateTransition(Duration.millis(220), cardSlot);
		slideOut.setToX(outX);
		slideOut.setOnFinished(e -> {

			showCard(currentCount);
			cardSlot.setTranslateX(-outX);
			
			TranslateTransition slideIn = new TranslateTransition(Duration.millis(220), cardSlot);
			slideIn.setToX(0);
			slideIn.play();
		});
		slideOut.play();

		projectCount.setText(String.valueOf(currentCount));
		setActiveTick(currentCount);
	}
	
	

	private void setActiveTick(int index)
	{
		for(int i = 0; i < tickBar.getChildren().size(); i++)
		{
			Region tick = (Region) tickBar.getChildren().get(i);
			tick.getStyleClass().remove("tick-active");
		}

		Region active = (Region) tickBar.getChildren().get(index - 1);
		active.getStyleClass().add("tick-active");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle res) {
		  if(doneLoading)
		   {
			  
		   }
		showCard(currentCount);
		for(int i = 0; i < tickBar.getChildren().size(); i++)
		{
			final int target = i + 1;
			Node tick = tickBar.getChildren().get(i);
			tick.setCursor(Cursor.HAND);
			tick.setOnMouseClicked(e -> goToCard(target));
		}

		root.setOnDragOver(e -> {
	        if (e.getDragboard().hasFiles()) {
	            e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	        }
	        e.consume();
	    });

	    root.setOnDragEntered(e -> {
	        if (e.getDragboard().hasFiles()) {
	            dropOverlay.setVisible(true);
	        }
	        e.consume();
	    });

	    root.setOnDragExited(e -> {
	        dropOverlay.setVisible(false);
	        e.consume();
	    });

	    root.setOnDragDropped(e -> {
	    	Dragboard db = e.getDragboard();
	    	boolean done = false;

	        if (db.hasFiles()) {
	            File file = db.getFiles().get(0);
	            cardController.previewImage.setImage(new Image(file.toURI().toString()));
	            openCardEditorWindow(file);
	            done = true;
	        }

	        dropOverlay.setVisible(false);
	        e.setDropCompleted(done);
	        e.consume();
	    });
	}

}
