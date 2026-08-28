package de.manuelanker.app.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.yaml.snakeyaml.Yaml;

import de.manuelanker.app.toolbox.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CardEditorController implements Initializable {

	@FXML
	private ImageView previewImage;
	@FXML
	private TextField titleField;
	@FXML
	private TextField statusField;
	@FXML
	private TextArea descField;
	@FXML
	private TextField techField;
	@FXML
	private TextField runCommandField;
	@FXML
	private TextField codePathField;
	@FXML
	private CheckBox showCodeCheck;
	@FXML
	private Label editError;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;

	private File imageFile;

	public int cardIndex = 1;

	public boolean wasSaved = false;

	@Override
	public void initialize(URL url, ResourceBundle res) {

	}
	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;

		if (imageFile != null) {
			Image img = new Image(imageFile.toURI().toString());
			previewImage.setImage(img);
		}
	}

	@SuppressWarnings("unchecked")
	public void loadExisting(Map<String, Object> data) {
		titleField.setText((String) data.get("title"));
		statusField.setText((String) data.get("status"));
		descField.setText((String) data.get("description"));
		runCommandField.setText((String) data.get("runCommand"));
		codePathField.setText((String) data.get("codePath"));

		Boolean showCode = (Boolean) data.get("showCode");
		if (showCode != null) {
			showCodeCheck.setSelected(showCode);
		}

		List<String> techList = (List<String>) data.get("tech");
		if (techList != null) {
			String techText = "";
			for (int i = 0; i < techList.size(); i++) {
				if (i > 0) {
					techText = techText + ", ";
				}
				techText = techText + techList.get(i);
			}
			techField.setText(techText);
		}

		String imagePath = (String) data.get("image");
		if (imagePath != null) {
			imageFile = new File(imagePath);
			previewImage.setImage(new Image(imageFile.toURI().toString()));
		}
	}

	@FXML
	private void onCancel(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void onSave(ActionEvent e) {

		String title = titleField.getText();
		if (title == null) {
			title = "";
		}
		title = title.trim();

		if (title.equals("")) {
			editError.setText("Titel darf nicht leer sein.");
			editError.setVisible(true);
			editError.setManaged(true);
			return;
		}

		String status = statusField.getText();
		if (status == null) {
			status = "";
		}

		String description = descField.getText();
		if (description == null) {
			description = "";
		}

		
		List<String> techList = new ArrayList<String>();
		String techText = techField.getText();
		if (techText != null) {
			String[] parts = techText.split(",");
			for (int i = 0; i < parts.length; i++) {
				String part = parts[i].trim();
				if (!part.equals("")) {
					techList.add(part);
				}
			}
		}

		
		String runCommand = runCommandField.getText();
		if (runCommand == null) {
			runCommand = "";
		}

		String codePath = codePathField.getText();
		if (codePath == null) {
			codePath = "";
		}

		Map<String, Object> cardData = new LinkedHashMap<String, Object>();
		cardData.put("title", title);
		cardData.put("status", status);
		cardData.put("description", description);
		cardData.put("tech", techList);
		cardData.put("runCommand", runCommand);
		cardData.put("codePath", codePath);
		cardData.put("showCode", showCodeCheck.isSelected());

		if (imageFile != null) {
			File imagesFolder = new File(Constants.ImageDataDir);
			if (!imagesFolder.exists()) {
				imagesFolder.mkdirs();
			}

			String originalName = imageFile.getName();
			String extension = "";
			int dotIndex = originalName.lastIndexOf(".");
			if (dotIndex >= 0) {
				extension = originalName.substring(dotIndex);
			}

			File copiedImage = new File(imagesFolder, "card" + cardIndex + extension);
			try {
				Files.copy(imageFile.toPath(), copiedImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
				cardData.put("image", copiedImage.getAbsolutePath());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		String fileName = "card" + cardIndex + ".yml";

		File folder = new File(Constants.CardDataDir);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File file = new File(folder, fileName);

		try {
			FileWriter writer = new FileWriter(file);
			Yaml yaml = new Yaml();
			yaml.dump(cardData, writer);
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			editError.setText("Speichern hat nicht geklappt: " + ex.getMessage());
			editError.setVisible(true);
			editError.setManaged(true);
			return;
		}

		editError.setVisible(false);
		editError.setManaged(false);

		wasSaved = true;

		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}
}
