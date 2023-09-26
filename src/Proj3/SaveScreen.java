package Proj3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import CircularDoubleLinkedList.CircularDoubleLinkedList;
import QueueList.Queue;
import StackList.Stack;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * The Save Screen class represents a screen to save the files again.
 * It extends the BorderPane class
 */

public class SaveScreen extends BorderPane {
	private FileChooser fileChooser;

	public SaveScreen(Stage stage, Scene scene, CircularDoubleLinkedList list) {
		fileChooser = new FileChooser();

		Label martyrLabel = new Label("Save All Martyrs' Records");
		martyrLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
		martyrLabel.setStyle("-fx-text-fill: #FFFFFF;");

		// Create the buttons and set their actions
		Button saveMartyrs = new Button("Save");
		saveMartyrs.setOnAction(e -> saveMartyrsToFile("martyrs.txt" , list));

		VBox martyrBox = new VBox(10, martyrLabel, saveMartyrs);
		martyrBox.setAlignment(Pos.CENTER);

		HBox hBox = new HBox(martyrBox);
		hBox.setAlignment(Pos.CENTER);
		setCenter(hBox);
		
		Button backBtn = new Button("Back");
		backBtn.setOnAction(e -> {
			stage.setScene(scene);
			stage.centerOnScreen();
		});
		setTop(backBtn);

		
		setPadding(new Insets(15));

	}

	private void saveMartyrsToFile(String defaultFileName, CircularDoubleLinkedList list) {
		// Set the initial file name in the file chooser
		File file = new File(defaultFileName);
		fileChooser.setInitialFileName(file.getName());

		// Show the save dialog
		File selectedFile = fileChooser.showSaveDialog(null);

		// Save the data to the selected file

		list.printToFile(selectedFile);

	}


}
