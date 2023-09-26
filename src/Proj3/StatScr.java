package Proj3;

import java.util.Date;
import java.util.function.Predicate;

import CircularDoubleLinkedList.CircularDoubleLinkedList;
import CircularDoubleLinkedList.Node;
import QueueList.Queue;
import Trees.AVLDate;
import Trees.AVLNames;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StatScr extends BorderPane {

	Node locationNode;

	TableView<Martyr> carTable;

	AVLNames avlNames;
	AVLDate avlDate;

	public StatScr(Stage stage, Scene scene, CircularDoubleLinkedList list) {

		ComboBox<String> cb = new ComboBox<String>();
		cb.setEditable(true);

		cb.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\D.*"))
				cb.getEditor().setText(newValue.replaceAll("\\d", ""));
		});

		ObservableList<String> items = FXCollections.observableArrayList();

		for (int i = 0; i < list.size(); i++)
			items.add(((LocationRecord) list.get(i)).getLocation());

		// Create a FilteredList wrapping the ObservableList.
		FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);

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

		cb.setItems(filteredItems);

		Button searchButton = new Button("Search");

		Label locLabel = new Label();
		locLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 23));
		locLabel.setStyle("-fx-text-fill: #FFFFFF;");

		HBox searchBox = new HBox(10, locLabel, cb, searchButton);
		searchBox.setAlignment(Pos.CENTER);

		Button next = new Button("Next"), prev = new Button("Previous");

		VBox nextBox = new VBox(next);
		nextBox.setAlignment(Pos.CENTER);

		VBox prevBox = new VBox(prev);
		prevBox.setAlignment(Pos.CENTER);

		VBox vBox = new VBox(10);

		vBox.setAlignment(Pos.CENTER);

		HBox content = new HBox(25, prevBox, vBox, nextBox);
		content.setAlignment(Pos.CENTER);

		setMargin(content, new Insets(65));

		TextArea avl1Area = new TextArea();
		avl1Area.setEditable(false);
		TextArea avl2Area = new TextArea();
		avl2Area.setEditable(false);

		avl1Area.setPrefWidth(405);
		avl2Area.setPrefWidth(405);

		avl1Area.setPrefHeight(300);
		avl2Area.setPrefHeight(300);

		Label hieght1Label = new Label("Height Of AVL 1");
		hieght1Label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		hieght1Label.setStyle("-fx-text-fill: #FFFFFF;");

		Label numLabel = new Label("Number Of Martyrs");
		numLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		numLabel.setStyle("-fx-text-fill: #FFFFFF;");

		TextField hieghtAVL1 = new TextField(), numbersAVL1 = new TextField();
		hieghtAVL1.setEditable(false);
		numbersAVL1.setEditable(false);
		hieghtAVL1.setPrefColumnCount(4);
		hieghtAVL1.setAlignment(Pos.CENTER);
		numbersAVL1.setPrefColumnCount(4);
		numbersAVL1.setAlignment(Pos.CENTER);

		HBox hieghtNumber = new HBox(5, hieght1Label, hieghtAVL1, numLabel, numbersAVL1);
		hieghtNumber.setAlignment(Pos.CENTER);

		Label avl1Label = new Label("AVL Names :");
		avl1Label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		avl1Label.setStyle("-fx-text-fill: #FFFFFF;");

		VBox avl1Box = new VBox(5, avl1Label, avl1Area, hieghtNumber);
		avl1Box.setAlignment(Pos.CENTER);

		Label hieght2Label = new Label("Height Of AVL 2");
		hieght2Label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		hieght2Label.setStyle("-fx-text-fill: #FFFFFF;");

		Label maxLabel = new Label("Max Date");
		maxLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
		maxLabel.setStyle("-fx-text-fill: #FFFFFF;");

		TextField hieghtAVL2 = new TextField(), maxAVL2 = new TextField();
		hieghtAVL2.setEditable(false);
		maxAVL2.setEditable(false);
		hieghtAVL2.setPrefColumnCount(4);
		hieghtAVL2.setAlignment(Pos.CENTER);
		maxAVL2.setPrefColumnCount(8);
		maxAVL2.setAlignment(Pos.CENTER);

		HBox hieghtMax = new HBox(5, hieght2Label, hieghtAVL2, maxLabel, maxAVL2);
		hieghtMax.setAlignment(Pos.CENTER);

		Label avl2Label = new Label("AVL Dates :");
		avl2Label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		avl2Label.setStyle("-fx-text-fill: #FFFFFF;");

		VBox avl2Box = new VBox(5, avl2Label, avl2Area, hieghtMax);
		avl2Box.setAlignment(Pos.CENTER);

		HBox centerBox = new HBox(10, avl1Box, avl2Box);

		// A button that search for a location and load all its statistics when it get
		// pressed
		searchButton.setOnAction(e -> {
			avl1Area.clear();
			avl2Area.clear();

			locationNode = list.findNode(cb.getEditor().getText().trim().toUpperCase());
			if (locationNode != null) {
				LocationRecord locRec = (LocationRecord) locationNode.getElement();
				avlNames = locRec.getAvlNames();
				avlDate = locRec.getAvlDate();

				avlNames.levelOrder(avl1Area , numbersAVL1);
				avlDate.printBack(avl2Area , maxAVL2);

				hieghtAVL1.setText(avlNames.height() + "");
				hieghtAVL2.setText(avlDate.height() + "");

				if (vBox.getChildren().isEmpty()) {
					vBox.getChildren().addAll(centerBox);
				} else {
					vBox.getChildren().clear();
					vBox.getChildren().addAll(centerBox);

				}
				locLabel.setText((((LocationRecord) locationNode.getElement()).getLocation().trim()));
				cb.getEditor().setText(".");
				setCenter(content);

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Can Not Find Location!");
				alert.showAndWait();
				locLabel.setText("");
				cb.getEditor().setText("");
				setCenter(null);
			}

		});

		// A button that loads all the next location statistics when it get pressed
		next.setOnAction(e -> {

			locationNode = locationNode.getNext();
			locLabel.setText((((LocationRecord) locationNode.getElement()).getLocation().trim()));

			try {
				avl1Area.clear();
				avl2Area.clear();

				avlNames = ((LocationRecord) locationNode.getElement()).getAvlNames();
				avlDate = ((LocationRecord) locationNode.getElement()).getAvlDate();
				avlNames.levelOrder(avl1Area , numbersAVL1);
				avlDate.printBack(avl2Area , maxAVL2);
				hieghtAVL1.setText(avlNames.height() + "");
				hieghtAVL2.setText(avlDate.height() + "");

			} catch (Exception e2) {

			}

		});

		// A button that loads all the previous location statistics when it get pressed
		prev.setOnAction(e -> {

			locationNode = locationNode.getPrev();
			locLabel.setText((((LocationRecord) locationNode.getElement()).getLocation().trim()));

			try {
				avl1Area.clear();
				avl2Area.clear();
				avlNames = ((LocationRecord) locationNode.getElement()).getAvlNames();
				avlDate = ((LocationRecord) locationNode.getElement()).getAvlDate();
				avlNames.levelOrder(avl1Area , numbersAVL1);
				avlDate.printBack(avl2Area , maxAVL2);
				hieghtAVL1.setText(avlNames.height() + "");
				hieghtAVL2.setText(avlDate.height() + "");

			} catch (Exception e2) {

			}

		});
		Button back = new Button("Back");

		BorderPane bp = new BorderPane();
		bp.setCenter(searchBox);
		bp.setLeft(back);

		setTop(bp);
		back.setOnAction(e -> {
			stage.setScene(scene);
			stage.centerOnScreen();
		});
		back.setAlignment(Pos.TOP_LEFT);

		setMargin(bp, new Insets(15));

	}
}
