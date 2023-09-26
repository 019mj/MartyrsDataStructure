package Proj3;

import java.util.List;
import java.util.Optional;

import CircularDoubleLinkedList.CircularDoubleLinkedList;
import Trees.AVLDate;
import Trees.AVLNames;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/*
 * The LocationScr class represents the screen that displays and manages location information.
 * It extends the BorderPane class to provide a layout container for its components.
 */

public class LocationScr extends BorderPane {

	// Constructor for the LocationScr class.
	public LocationScr(Stage stage, Scene scene, CircularDoubleLinkedList list) {

		// Create a ComboBox for selecting location names.
		ComboBox<String> cb = new ComboBox<String>();
		cb.setEditable(true);

		// Restrict the input in the ComboBox to non-digit characters.
		cb.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\D.*"))
				cb.getEditor().setText(newValue.replaceAll("\\d", ""));
		});

		// Create an ObservableList to store the location names.
		ObservableList<String> items = FXCollections.observableArrayList();

		// Populate the ObservableList with location names from the
		// CirculerDoubleLinkedList.
		for (int i = 0; i < list.size(); i++)
			items.add(((LocationRecord) list.get(i)).getLocation());

		// Create a FilteredList wrapping the ObservableList.
		FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);

		// Add a listener to the text property of the ComboBox editor.
		cb.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
			final TextField editor = cb.getEditor();
			final String selected = cb.getSelectionModel().getSelectedItem();

			Platform.runLater(() -> {
				if (selected == null || !selected.equals(editor.getText())) {
					filteredItems.setPredicate(item -> {
						if (item.toUpperCase().startsWith(newValue.trim().toUpperCase())) {
							cb.show();
							return true;
						} else {
							return false;
						}
					});
				}
			});
		});
		TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[a-zA-Z -]*")) {
				return change;
			} else {
				return null; // Reject the change
			}
		});

		// Set the TextFormatter to the TextField
		cb.getEditor().setTextFormatter(textFormatter);

		// Set the items of the ComboBox to the filtered items.
		cb.setItems(filteredItems);

		// Create buttons for insert, delete, update, and search operations.
		Button insertButton = new Button("Insert");
		Button deleteButton = new Button("Delete");
		Button updateButton = new Button("Update");
		Button searchButton = new Button("Search");

		// Create an HBox to hold the buttons.
		HBox options = new HBox(10, insertButton, deleteButton, updateButton, searchButton);
		options.setAlignment(Pos.CENTER);

		// Create a VBox to hold the ComboBox and the options HBox.
		VBox vBox = new VBox(10, cb, options);
		vBox.setAlignment(Pos.CENTER);

		// Set the center of the BorderPane to the VBox.
		setCenter(vBox);

		// Event handler for the insertButton.
		insertButton.setOnAction(e -> {
			String location = cb.getEditor().getText().trim();
			if (!location.isEmpty()) {
				if (list.contains(location)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Location Already Exists");
					alert.showAndWait();
				} else {
					list.insertSort(location, null);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Successful");
					alert.setHeaderText("Locatoin Inserted Successfully");
					alert.showAndWait();
					items.add(location);
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Locatoin Cannot Be Empty");
				alert.showAndWait();
			}
		});

		// Event handler for the deleteButton.
		deleteButton.setOnAction(e -> {
			String locatoin = cb.getSelectionModel().getSelectedItem();
			if (locatoin != null && !locatoin.isEmpty()) {
				locatoin = locatoin.trim();
				Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
				confirmAlert.setTitle("Confirmation");
				confirmAlert.setHeaderText("Delete Confirmation");
				confirmAlert.setContentText("Are you sure you want to delete this location?");
				Optional<ButtonType> result = confirmAlert.showAndWait();

				if (result.isPresent() && result.get() == ButtonType.OK) {
					if (list.remove(locatoin)) {
						Alert successAlert = new Alert(AlertType.INFORMATION);
						successAlert.setTitle("Successful");
						successAlert.setHeaderText("Location Deleted Successfully");
						successAlert.showAndWait();
						items.remove(locatoin);
						cb.getEditor().clear();
					} else {
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setTitle("Error");
						errorAlert.setHeaderText("Location Not Found");
						errorAlert.showAndWait();
					}
				}
			} else {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Error");
				errorAlert.setHeaderText("Location Cannot Be Empty");
				errorAlert.showAndWait();
			}
		});

		// Event handler for the updateButton.
		updateButton.setOnAction(e -> {
			String location = cb.getSelectionModel().getSelectedItem();
			if (location != null && !location.isEmpty()) {
				LocationRecord locRec = (LocationRecord) list.get(location);
				if (locRec != null) {
					Stage updateStage = new Stage();

					Label info = new Label("Update " + location + " Location :");
					info.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
					info.setStyle("-fx-text-fill: #FFFFFF;");

					TextField updateTF = new TextField();
					updateTF.textProperty().addListener((observable, oldValue, newValue) -> {
						if (!newValue.matches("[a-zA-Z'\" ]*")) {
							updateTF.setText(newValue.replaceAll("[^a-zA-Z'\" ]", ""));
						}
						if (newValue.matches(".*\\s{2,}.*")) {
							updateTF.setText(newValue.replaceAll("\\s{2,}", " "));
						}
					});

					
					
					Button updateButt = new Button("Update");
					VBox updateBox = new VBox(10, info, updateTF, updateButt);
					updateBox.setPadding(new Insets(30));
					updateBox.setAlignment(Pos.CENTER);

					updateButt.setOnAction(ex -> {
						String newLocation = updateTF.getText().trim();
						if (newLocation == null || newLocation.isEmpty()) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("Locatoin Can not be empty");
							alert.showAndWait();
						} else if (list.contains(newLocation)) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("Locatoin Already Exists");
							alert.showAndWait();
						} else {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Successful");
							alert.setHeaderText("Location Updated Successfully");
							alert.showAndWait();

							items.remove(location);
							locRec.setLocation(newLocation);
							list.sort(locRec);
							items.add(newLocation);

							updateStage.close();
						}
					});

					Scene updateScene = new Scene(updateBox);
					updateScene.getStylesheets().add("LightMode.css");
					updateStage.setScene(updateScene);
					updateStage.show();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Location Does Not Found");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Location Cannot Be Empty");
				alert.showAndWait();
			}
		});

		// Event handler for the searchButton.
		searchButton.setOnAction(e -> {
			String location = cb.getSelectionModel().getSelectedItem();
			if (location != null && !location.isEmpty()) {
				location = location.trim().toUpperCase();
				LocationRecord locRec = (LocationRecord) list.get(location);
				if (locRec != null) {
					AVLNames avlNames = locRec.getAvlNames();
					AVLDate avlDate = locRec.getAvlDate();
					MartyrsScr saveScreen = new MartyrsScr(stage, scene, avlNames, avlDate, list);
					Scene saveScene = new Scene(saveScreen, 1200, 700);
					saveScene.getStylesheets().add("LightMode.css");
					stage.setScene(saveScene);
					stage.centerOnScreen();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Location Does Not Found");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Location Cannot Be Empty");
				alert.showAndWait();
			}
		});

		// Button for navigating back to the previous scene.
		Button back = new Button("Back");

		back.setOnAction(e -> {
			stage.setScene(scene);
			stage.centerOnScreen();
		});

		setTop(back);

		setPadding(new Insets(15));
	}
}
