package Proj3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import CircularDoubleLinkedList.CircularDoubleLinkedList;
import QueueList.Queue;
import StackList.Stack;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class Main extends Application {

	CircularDoubleLinkedList list = new CircularDoubleLinkedList();
	File file;

	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();

		Button DevelopersBtn = new Button("Developers");
		bp.setTop(DevelopersBtn); // Set the button at the top of the border pane

		Label welcomeLabel = new Label("Comp242 Project III");
		welcomeLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 27.5));
		welcomeLabel.setStyle("-fx-text-fill: #FFFFFF;");
		Label csaLabel = new Label("Martyrs Data Structure System");
		csaLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		csaLabel.setStyle("-fx-text-fill: #FFFFFF;");

		ImageView logoView = new ImageView("logo2.png");
		logoView.setFitHeight(289.5);
		logoView.setFitWidth(422.5);

		// Event handler for mouse entering the logo view
		logoView.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {

			Glow glow = new Glow();
			glow.setLevel(1.0);
			ColorAdjust colorAdjust = new ColorAdjust();
			colorAdjust.setBrightness(0.3);
			colorAdjust.setContrast(0.3);
			colorAdjust.setSaturation(0.3);
			colorAdjust.setHue(-0.95);
			glow.setInput(colorAdjust);
			logoView.setEffect(glow);
		});

		// Event handler for mouse exiting the logo view
		logoView.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			logoView.setEffect(null);
		});

		VBox vBox = new VBox(10, welcomeLabel, logoView, csaLabel);
		vBox.setAlignment(Pos.CENTER);
		bp.setCenter(vBox); // Set the vbox at the center of the border pane

		// Buttons for different actions
		Button loadMartyr = new Button("Load The Martyrs File");
		Button saveBtn = new Button("Save");
		Button locationBtn = new Button("Location");
		Button statBtn = new Button("Statistics");

		HBox options = new HBox(7, loadMartyr, locationBtn, statBtn, saveBtn);

		options.setAlignment(Pos.CENTER);
		bp.setBottom(options); // Set the options vbox at the bottom of the border pane

		Scene scene = new Scene(bp, 1200, 700);

		// Event handler for save button
		saveBtn.setOnAction(e -> {
			SaveScreen saveScreen = new SaveScreen(stage, scene, list);
			Scene saveScene = new Scene(saveScreen, 1200, 700);
			saveScene.getStylesheets().add("LightMode.css");
			stage.setScene(saveScene);
			stage.centerOnScreen();

		});

		bp.setPadding(new Insets(15, 15, 15, 15)); // Set padding for the border pane

		// Creating a file chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("File Chooser");

		// Creating extension filters
		ExtensionFilter filterCSV = new ExtensionFilter("Text files", "*csv");
		ExtensionFilter filterTXT = new ExtensionFilter("Text files", "*txt");

		// Add the extension filter to the file chooser
		fileChooser.getExtensionFilters().addAll(filterTXT, filterCSV);

		// Event handler for load car button
		loadMartyr.setOnAction(e -> {
			file = fileChooser.showOpenDialog(stage);
			try {
				readMartyrs();
			} catch (FileNotFoundException e2) {

			} catch (Exception e2) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid File Format");
				alert.showAndWait();
			}

		});

		Developers devTeam = new Developers(stage, scene);
		Scene devScene = new Scene(devTeam, 1200, 700);

		// Event handler for developers button
		DevelopersBtn.setOnAction(e -> {
			devScene.getStylesheets().add("LightMode.css");
			stage.setScene(devScene);
			stage.centerOnScreen();

		});

		// Event handler for location button
		locationBtn.setOnAction(e -> {
			LocationScr locationScr = new LocationScr(stage, scene, list);
			Scene locationScene = new Scene(locationScr, 1200, 700);
			locationScene.getStylesheets().add("LightMode.css");
			stage.setScene(locationScene);
			stage.centerOnScreen();
		});

		// Event handler for statistics button
		statBtn.setOnAction(e -> {

			StatScr customerScr = new StatScr(stage, scene, list);
			Scene customerScene = new Scene(customerScr, 1200, 700);
			customerScene.getStylesheets().add("LightMode.css");
			stage.setScene(customerScene);
			stage.centerOnScreen();
		});
		scene.getStylesheets().add("LightMode.css"); // Apply dark mode CSS to the scene
		stage.setScene(scene); // Set the scene to the stage
		stage.setTitle("MartyrsDS"); // Set the title of the stage
		stage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args); // Launch the JavaFX application
	}

	// Read The martyrs from a specific file
	private void readMartyrs() throws IOException {
		list.clear();

		String checker = "Name,Age,location,Date of death,Gender";
		if (file == null)
			throw new FileNotFoundException();

		Scanner sc = new Scanner(file);

		if (!sc.nextLine().trim().equals(checker))
			throw new IOException();

		while (sc.hasNext()) {
			String s = sc.nextLine().trim();
			String[] tkz = s.split(",");

			String name = tkz[0].trim();
			String age = tkz[1].trim();

			String location = tkz[2];

			String[] dateTkz = tkz[3].trim().split("/");
			int month = Integer.parseInt(dateTkz[0].trim());
			int day = Integer.parseInt(dateTkz[1].trim());
			int year = Integer.parseInt(dateTkz[2].trim());

			char gender = tkz[4].charAt(0);
			Martyr martyr = new Martyr(name, age, day, month, year, gender);
			list.insertSort(location, martyr);
		}
	}
}
