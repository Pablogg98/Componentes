package dad.javafx.main;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dad.javafx.componentes.DateChooser;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class Controller implements Initializable {

	//model
	
	private ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<LocalDate>();
	
	//view
	
	@FXML
    private DateChooser dateChooser;
	
	@FXML
	private GridPane view;

	public Controller() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ControllerView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fecha.set(LocalDate.now());
	}

	@FXML
	void onConsultarAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Fecha");
		alert.setHeaderText("La fecha seleccionada es: " + fecha.get());
		alert.showAndWait();
	}

	@FXML
	void onIniAction(ActionEvent event) {
		fecha.set(dateChooser.getFecha());
	}

	public GridPane getView() {
		return view;
	}
	
}
