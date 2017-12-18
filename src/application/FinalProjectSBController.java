package application;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.RadioMenuItem;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Menu;

public class FinalProjectSBController extends Main{
	
	//All of the Data Structure Fields ---------------------------------------------------
	protected static LinkedList<EventClass> finalEventList = new LinkedList<EventClass>();
	public HashSet<String> searchSet = new HashSet<String>();
	public Stack<String> favStack = new Stack<String>();
	//All of the Data Structure Fields ---------------------------------------------------
	
	//Items from the FXML ----------------------------------------------------------------
	@FXML
	private Button searchButton;
	@FXML
	private ComboBox<String> sortComboBox;
	@FXML
	private ComboBox<String> searchTextField;
	@FXML
	private TableView<EventClass> eventTableView;
	@FXML
	private TableColumn<EventClass, String> tableEventName;
	@FXML
	private TableColumn<EventClass, Object> tableDate;
	@FXML
	private TableColumn<EventClass, Object> tablePopularity;
	@FXML
	private Label sortByLabel;
	@FXML
	private Label pbskLabel;
	@FXML
	private Label legalLabel;
	@FXML
	private MenuBar menuBar;
	@FXML
	private Menu fileMenuBar;
	@FXML
	private MenuItem closeFileMenu;
	@FXML
	private MenuItem favMenu;
	@FXML
	private Menu settingsMenuBar;
	@FXML
	private RadioMenuItem darkModeSettingsMenu;
	@FXML
	private Menu helpMenuBar;
	@FXML
	private MenuItem aboutHelpMenu;
	
	private final ObservableList<EventClass> data = FXCollections.observableArrayList();
	
	//Items from the FXML ----------------------------------------------------------------
	 
	@FXML
	public void initialize() {
		searchTextField.setEditable(true);
		
		tableEventName.setCellValueFactory(new PropertyValueFactory<EventClass, String>("preformanceName"));
		tableDate.setCellValueFactory(new PropertyValueFactory<EventClass, Object>("venueName"));
		tablePopularity.setCellValueFactory(new PropertyValueFactory<EventClass, Object>("date"));
		
		//Combo Box Arguments --------------------------------------------
		sortComboBox.getItems().addAll("Event Date", "Name", "Popularity");
		sortComboBox.setValue("Event Date");
		//Combo Box Arguments --------------------------------------------
		
		
		//When pressing enter, the search will run to find events.
		searchTextField.setOnKeyPressed(e ->{
			if(e.getCode() == KeyCode.ENTER) {
				runSearch();
			}
		});
		
		eventTableView.setOnMouseClicked( e -> {
			if (e.getClickCount() == 2) {
				EventClass selectedEvent = eventTableView.getSelectionModel().getSelectedItem();
				favStack.push(selectedEvent.getPreformanceName());
			}
			});
		
	}

	// Event Listener on Button[#searchButton].onMouseClicked
	@FXML
	public void searchForEvents(MouseEvent event) throws InterruptedException {
		runSearch();
	}
	// Event Listener on TextField[#searchTextField].onKeyPressed
	// Event Listener on MenuItem[#closeFileMenu].onAction
	@FXML
	public void closeProgramEvent(ActionEvent event) throws Exception {
		Platform.exit();
	}
	// Event Listener on RadioMenuItem[#darkModeSettingsMenu].onAction
	@FXML
	public void showFavEvent(ActionEvent event) {
		Stage secondStage = new Stage();
		VBox pane = new VBox();
		Text text = new Text("Favorites:");
		text.setFill(Color.CORNFLOWERBLUE);
		text.setX(120);
		pane.getChildren().add(text);
		
		for(int i = 0; i < favStack.size(); i++) {
			Text j = new Text(favStack.get(i));
			VBox.setMargin(j, new Insets(0, 0, 0, 8));
			pane.getChildren().add(j);
		}
		Scene scene = new Scene(pane, 400, 300);
		secondStage.setScene(scene);
		secondStage.setTitle("Favorites");
		secondStage.show();
	}
	public void nightModeEvent(ActionEvent event) {
		root.setStyle("-fx-background-color: grey");
		
		legalLabel.setTextFill(Color.RED);
	}
	// Event Listener on MenuItem[#aboutHelpMenu].onAction
	@FXML
	public void showAboutPageEvent(ActionEvent event) {
		Stage secondStage = new Stage();
		Pane pane = new Pane();
		Text text = new Text("Made By Zachary Baltrus \nDO NOT COPY!");
		text.setX(120);
		text.setY(150);
		pane.getChildren().add(text);
		Scene scene = new Scene(pane, 400, 300);
		secondStage.setScene(scene);
		secondStage.show();
	}
	
	public void runSearch() {
		data.clear();
		LocationClass.setSearchText(searchTextField.getSelectionModel().getSelectedItem().toString()); //Setting the text field
		LocationClass.apiLocationSearch(); // Starting the location search
		EventClass eventClass = new EventClass(); // Creating a EventClass class
		
		eventClass.apiEventSearch(); // Starting the event search
		
		finalEventList = eventClass.getEventArray();
		searchSet.add(searchTextField.getSelectionModel().getSelectedItem().toString());
		
		
		if(sortComboBox.getSelectionModel().getSelectedItem().equals("Event Date")) {
			for(int i = 0; i < finalEventList.size(); i++) {
				EventClass eventObject = finalEventList.get(i);
				data.add(eventObject);
			}
		}
		else if(sortComboBox.getSelectionModel().getSelectedItem().equals("Name")) {
			Runnable nameSort = new NameSort(finalEventList);
			Thread nameThread = new Thread(nameSort);
			nameThread.start();
			try {
				nameThread.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			for(int i = 0; i < finalEventList.size(); i++) {
				EventClass eventObject = finalEventList.get(i);
				data.add(eventObject);
			}
		}
		else if(sortComboBox.getSelectionModel().getSelectedItem().equals("Popularity")) {
			Runnable popSort = new PopularSort(finalEventList);
			Thread popThread = new Thread(popSort);
			popThread.start();
			try {
				popThread.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			for(int i = 0; i < finalEventList.size(); i++) {
				EventClass eventObject = finalEventList.get(i);
				data.add(eventObject);
			}
		}
		eventTableView.setItems(data);
		searchTextField.getItems().removeAll(searchSet);
		searchTextField.getItems().addAll(searchSet);
	}
}
