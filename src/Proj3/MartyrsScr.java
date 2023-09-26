package Proj3;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.function.Predicate;

import CircularDoubleLinkedList.CircularDoubleLinkedList;
import Tests.EditableTableViewExample.Person;
import Tests.TabTest.CharacterStringConverter;
import Trees.AVLDate;
import Trees.AVLNames;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

/*
 * The MartyrsScr class represents the screen that displays and manages martyrs information.
 * It extends the BorderPane class to provide a layout container for its components.
 */

public class MartyrsScr extends BorderPane {

	private ObservableList<Martyr> obsList = FXCollections.observableArrayList();

	private ObservableList<Martyr> filteredData = FXCollections.observableArrayList();

	TableView<Martyr> martyrTable;
	AVLNames avlNames;
	AVLDate avlDate;

	public MartyrsScr(Stage stage, Scene scene, AVLNames avlNames, AVLDate avlDate, CircularDoubleLinkedList list) {
		this.avlNames = avlNames;
		this.avlDate = avlDate;
		Button back = new Button("Back");

		back.setOnAction(e -> {
			LocationScr locationScr = new LocationScr(stage, scene, list);
			Scene locationScene = new Scene(locationScr, 1200, 700);
			locationScene.getStylesheets().add("LightMode.css");
			stage.setScene(locationScene);
			stage.centerOnScreen();
		});
		setTop(back);
		setMargin(back, new Insets(0, 0, 20, 0));
		setPadding(new Insets(20));

		avlNames.inOrder(obsList);

		filteredData.addAll(obsList);
		martyrTable = createTable();

		Label search = new Label("Search");
		search.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		search.setStyle("-fx-text-fill: #FFFFFF;");

		TextField martyrName = new TextField();
		martyrName.setPromptText("Martyr Name");

		TextField martyrAge = new TextField();
		martyrAge.setPromptText("Martyr Age");

		TextField martyrDay = new TextField();
		martyrDay.setPrefColumnCount(5);
		martyrDay.setPromptText("Day");

		TextField martyrMonth = new TextField();
		martyrMonth.setPrefColumnCount(5);
		martyrMonth.setPromptText("Month");

		TextField martyrYear = new TextField();
		martyrYear.setPrefColumnCount(5);
		martyrYear.setPromptText("Year");

		TextField martyrGender = new TextField();
		martyrGender.setPromptText("Gender");

		HBox filters = new HBox(10, search, martyrName, martyrAge, martyrMonth, martyrDay, martyrYear, martyrGender);
		filters.setAlignment(Pos.CENTER);

		ObjectProperty<Predicate<Martyr>> nameFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Martyr>> ageFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Martyr>> dayFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Martyr>> monthFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Martyr>> yearFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Martyr>> genderFilter = new SimpleObjectProperty<>();

		nameFilter.bind(Bindings.createObjectBinding(
				() -> martyr -> martyr.getName().toLowerCase().trim().startsWith(martyrName.getText().toLowerCase()),
				martyrName.textProperty()));

		ageFilter.bind(Bindings.createObjectBinding(
				() -> martyr -> martyr.getAge().toLowerCase().trim().startsWith(martyrAge.getText().toLowerCase()),
				martyrAge.textProperty()));

		dayFilter.bind(Bindings.createObjectBinding(() -> martyr -> String.valueOf(martyr.getDate().get(Calendar.DATE))
				.startsWith(martyrDay.getText().toLowerCase()), martyrDay.textProperty()));

		monthFilter.bind(
				Bindings.createObjectBinding(() -> martyr -> String.valueOf(martyr.getDate().get(Calendar.MONTH) + 1)
						.startsWith(martyrMonth.getText().toLowerCase()), martyrMonth.textProperty()));

		yearFilter.bind(Bindings.createObjectBinding(() -> martyr -> String.valueOf(martyr.getDate().get(Calendar.YEAR))
				.startsWith(martyrYear.getText().toLowerCase()), martyrYear.textProperty()));

		genderFilter
				.bind(Bindings
						.createObjectBinding(
								() -> martyr -> String.valueOf(martyr.getGender()).toLowerCase().trim()
										.startsWith(martyrGender.getText().toLowerCase()),
								martyrGender.textProperty()));

		FilteredList<Martyr> filteredCars = new FilteredList<>(obsList);
		martyrTable.setItems(filteredCars);

		filteredCars.predicateProperty()
				.bind(Bindings.createObjectBinding(
						() -> nameFilter.get().and(ageFilter.get()).and(dayFilter.get()).and(monthFilter.get())
								.and(yearFilter.get().and(genderFilter.get())),
						nameFilter, ageFilter, dayFilter, monthFilter, yearFilter, genderFilter));
		VBox centerBox = new VBox(10, filters, martyrTable);
		setCenter(centerBox);

		setMargin(centerBox, new Insets(0, 100, 0, 100));

		TextField nameTF = new TextField();
		nameTF.setPromptText("Martyr Name");

		nameTF.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("[a-zA-Z'\" ]*")) {
				nameTF.setText(newValue.replaceAll("[^a-zA-Z'\" ]", ""));
			}
			if (newValue.matches(".*\\s{2,}.*")) {
				nameTF.setText(newValue.replaceAll("\\s{2,}", " "));
			}
		});

		ComboBox<String> ageComboBox = new ComboBox<>();
		ageComboBox.setPromptText("Martyr Age");
		ageComboBox.getItems().add("Unknown");
		ageComboBox.getItems().addAll(getAgeRange(1, 100));

		DatePicker datePicker = new DatePicker();
		datePicker.setEditable(false);
		datePicker.setPromptText("Date Of Death");

		ComboBox<Character> genderComboBox = new ComboBox<>();
		genderComboBox.setPromptText("Martyr Gender");
		genderComboBox.getItems().addAll('M', 'F', '?');

		Button addButton = new Button("Add");

		HBox data = new HBox(10, nameTF, ageComboBox, datePicker, genderComboBox, addButton);
		data.setAlignment(Pos.CENTER);

		Button deleteButton = new Button("Delete");
		Button clear = new Button("Delete All");

		HBox options = new HBox(10, deleteButton, clear);
		options.setAlignment(Pos.CENTER);

		VBox vBox = new VBox(10, data, options);
		vBox.setAlignment(Pos.CENTER);
		setBottom(vBox);

		addButton.setOnAction(e -> {
			try {

				Martyr martyr = new Martyr(nameTF.getText().trim(),
						ageComboBox.getSelectionModel().getSelectedItem().toString().trim(),
						datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(),
						datePicker.getValue().getYear(), genderComboBox.getSelectionModel().getSelectedItem());

				avlNames.add(martyr);
				avlDate.add(martyr);

				obsList.add(martyr);
				filteredData.add(martyr);
				obsList.sort(Comparator.comparing(Martyr::getName));
				filteredData.sort(Comparator.comparing(Martyr::getName));

				nameTF.clear();
				ageComboBox.getSelectionModel().clearSelection();
				datePicker.getEditor().clear();
				genderComboBox.getSelectionModel().clearSelection();

			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invaild Inputs!");
				alert.showAndWait();
			}

		});

		deleteButton.setOnAction(e -> {
			try {
				Martyr selectedMartyr = martyrTable.getSelectionModel().getSelectedItem();
				if (selectedMartyr != null) {
					obsList.remove(selectedMartyr);
					filteredData.remove(selectedMartyr);

					avlNames.remove(selectedMartyr);
					avlDate.deleteMartyr(selectedMartyr);
					
					// Update the table view after removing the item
					martyrTable.refresh();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				if (martyrTable.getItems().isEmpty())
					alert.setHeaderText("There is no cars to remove");
				else
					alert.setHeaderText("Please select a car to remove");

				alert.showAndWait();
			}

		});

		clear.setOnAction(e -> {
			if (obsList.isEmpty())
				new IntegerStringConverter().showAlert("No Data", "There is no data to remove");
			else {
				Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
				confirmAlert.setTitle("Confirmation");
				confirmAlert.setHeaderText("Delete Confirmation");
				confirmAlert.setContentText("Are you sure you want to delete all martyrs?");
				Optional<ButtonType> result = confirmAlert.showAndWait();

				if (result.isPresent() && result.get() == ButtonType.OK) {

					obsList.clear();
					filteredData.clear();
					avlNames.clear();
					avlDate.clear();

					// Update the table view after removing the item
					martyrTable.refresh();

				}
			}
		});

	}

	public TableView<Martyr> createTable() {
		TableView<Martyr> tableView = new TableView<>();
		tableView.setEditable(true);

		TableColumn<Martyr, String> martyrName = new TableColumn<Martyr, String>("Martyr Name");
		TableColumn<Martyr, String> martyrAge = new TableColumn<Martyr, String>("Martyr Age");
		TableColumn<Martyr, GregorianCalendar> dateOfDeath = new TableColumn<Martyr, GregorianCalendar>(
				"Date Of Death");
		TableColumn<Martyr, Character> martyrGender = new TableColumn<Martyr, Character>("Martyr Gender");

		double columnWidth = 200;
		dateOfDeath.setPrefWidth(columnWidth);
		martyrAge.setPrefWidth(columnWidth);
		martyrName.setPrefWidth(columnWidth);
		martyrGender.setPrefWidth(columnWidth);

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.getColumns().addAll(martyrName, martyrAge, dateOfDeath, martyrGender);

		dateOfDeath.setCellValueFactory(new PropertyValueFactory<Martyr, GregorianCalendar>("date"));
		martyrName.setCellValueFactory(new PropertyValueFactory<Martyr, String>("name"));
		martyrAge.setCellValueFactory(new PropertyValueFactory<Martyr, String>("age"));
		martyrGender.setCellValueFactory(new PropertyValueFactory<Martyr, Character>("gender"));

		martyrName.setCellFactory(TextFieldTableCell.<Martyr>forTableColumn());

		martyrName.setOnEditCommit((CellEditEvent<Martyr, String> t) -> {
			Martyr martyr = (Martyr) t.getTableView().getItems().get(t.getTablePosition().getRow());
			avlNames.remove(martyr);
			martyr.setName(t.getNewValue());
			avlNames.add(martyr);					
		});

		martyrAge.setCellFactory(column -> createEditableAgeCell());

		martyrGender.setCellFactory(column -> createEditableGenderCell());
		dateOfDeath.setCellFactory(column -> createEditableDateCell());

		tableView.setItems(filteredData);
		return tableView;

	}

	private TableCell<Martyr, Character> createEditableGenderCell() {
		return new TableCell<>() {
			private final ComboBox<Character> comboBox = new ComboBox<>();

			{
				comboBox.getItems().addAll('M', 'F', '?');

				comboBox.setOnAction(event -> {
					char selectedGender = comboBox.getValue();
					commitEdit(selectedGender);

					// Update the value in the Person object
					Martyr martyr = getTableView().getItems().get(getIndex());
					martyr.setGender(selectedGender);
				});

				setGraphic(comboBox);
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			}

			@Override
			protected void updateItem(Character item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setGraphic(null);
				} else {
					comboBox.setValue(item);
					setGraphic(comboBox);
					setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				}
			}
		};
	}

	private TableCell<Martyr, String> createEditableAgeCell() {
		return new TableCell<>() {
			private final ComboBox<String> comboBox = new ComboBox<>();

			{
				comboBox.getItems().add("Unknown");
				for (int i = 1; i <= 100; i++)
					comboBox.getItems().add(String.valueOf(i));

				comboBox.setOnAction(event -> {
					String selectedAge = comboBox.getValue();
					commitEdit(selectedAge);

					// Update the value in the Person object
					Martyr martyr = getTableView().getItems().get(getIndex());
					martyr.setAge(selectedAge);
				});

				setGraphic(comboBox);
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			}

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setGraphic(null);
				} else {
					comboBox.setValue(item);
					setGraphic(comboBox);
					setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				}
			}
		};
	}

	private TableCell<Martyr, GregorianCalendar> createEditableDateCell() {
		return new TableCell<>() {
			private final DatePicker datePicker = new DatePicker();

			{
				datePicker.setEditable(false);
				datePicker.setOnAction(event -> {
					LocalDate selectedDate = datePicker.getValue();
					GregorianCalendar newDate = new GregorianCalendar(selectedDate.getYear(),
							selectedDate.getMonthValue() - 1, selectedDate.getDayOfMonth());
					commitEdit(newDate);
					Martyr martyr = getTableView().getItems().get(getIndex());
					avlDate.deleteMartyr(martyr);
					martyr.setDate(selectedDate.getDayOfMonth(), selectedDate.getMonthValue(), selectedDate.getYear());
					avlDate.add(martyr);

				});

				setGraphic(datePicker);
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			}

			@Override
			protected void updateItem(GregorianCalendar item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setGraphic(null);
				} else {
					datePicker.setValue(LocalDate.of(item.get(Calendar.YEAR), item.get(Calendar.MONTH) + 1,
							item.get(Calendar.DAY_OF_MONTH)));
					setGraphic(datePicker);
					setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				}
			}
		};
	}

	private String[] getAgeRange(int startAge, int endAge) {
		int numYears = endAge - startAge + 1;
		String[] years = new String[numYears];
		for (int i = 0; i < numYears; i++) {
			years[i] = startAge + i + "";
		}
		return years;
	}

	public static class IntegerStringConverter extends StringConverter<Integer> {

		@Override
		public String toString(Integer value) {
			if (value == null) {
				return "";
			}
			return value.toString();
		}

		@Override
		public Integer fromString(String value) {
			if (isValidInteger(value)) {
				return Integer.parseInt(value);
			} else {
				showAlert("Invalid Input", "Please enter a valid integer value.");
				return null;
			}
		}

		boolean isValidInteger(String value) {
			try {
				Integer.parseInt(value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		void showAlert(String title, String message) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.showAndWait();
		}
	}

	public static class CharacterStringConverter extends javafx.util.StringConverter<Character> {
		@Override
		public String toString(Character object) {
			return String.valueOf(object);
		}

		@Override
		public Character fromString(String string) {
			if (string != null && !string.isEmpty()) {
				return string.charAt(0);
			} else {
				return null;
			}
		}
	}

}
