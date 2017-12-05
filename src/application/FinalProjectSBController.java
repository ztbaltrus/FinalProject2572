package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.RadioMenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.Label;

import javafx.scene.control.MenuBar;

import javafx.scene.control.MenuItem;

import javafx.scene.control.ComboBox;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.Menu;

public class FinalProjectSBController extends Main{
	@FXML
	private Button searchButton;
	@FXML
	private ComboBox sortComboBox;
	@FXML
	private TextField searchTextField;
	@FXML
	private ScrollPane eventScrollPane;
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
	private Menu settingsMenuBar;
	@FXML
	private RadioMenuItem darkModeSettingsMenu;
	@FXML
	private Menu helpMenuBar;
	@FXML
	private MenuItem aboutHelpMenu;
	 
	@FXML
	public void initialize() {
		searchTextField.setOnKeyPressed(e ->{
			if(e.getCode() == KeyCode.ENTER) {
				Text text = new Text();
				text.setText(searchTextField.getText());
				eventScrollPane.setContent(text);
			}
		});
	}

	// Event Listener on Button[#searchButton].onMouseClicked
	@FXML
	public void searchForEvents(MouseEvent event) {
		//Staring the location search
		LocationClass.setSearchText(searchTextField.getText());
		LocationClass.apiLocationSearch();
		
		EventClass.apiEventSearch();
		Text txt = new Text();
		StringBuffer sb = new StringBuffer();
		for(HashMap.Entry<Integer, EventClass> e: EventClass.getEventMap().entrySet()) {
			System.out.println(e.getKey() + "  =  " + e.getValue().getDisplayName());
			sb.append(e.getValue().getDisplayName() + "\n");
		}
		txt.setText(sb.toString());
		eventScrollPane.setContent(txt);
	}
	// Event Listener on TextField[#searchTextField].onKeyPressed
	// Event Listener on MenuItem[#closeFileMenu].onAction
	@FXML
	public void closeProgramEvent(ActionEvent event) throws Exception {
		Platform.exit();
	}
	// Event Listener on RadioMenuItem[#darkModeSettingsMenu].onAction
	@FXML
	public void nightModeEvent(ActionEvent event) {
		
		legalLabel.setTextFill(Color.RED);
	}
	// Event Listener on MenuItem[#aboutHelpMenu].onAction
	@FXML
	public void showAboutPageEvent(ActionEvent event) {
		//Add another Window
	}
}
