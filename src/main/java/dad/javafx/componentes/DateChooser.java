package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Year;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class DateChooser extends GridPane implements Initializable {

	// model

	private ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<LocalDate>();
	private SimpleListProperty<String> months = new SimpleListProperty<String>(FXCollections.observableArrayList());

	// view

	@FXML
	private ComboBox<String> dayCB;

	@FXML
	private ComboBox<String> monthCB;

	@FXML
	private ComboBox<String> yearCB;

	public DateChooser() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DateChooserView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for (int i = LocalDate.now().getYear(); i >= 1900; i--) {
			yearCB.getItems().add("" + i);
		}

		months.addAll(new DateFormatSymbols().getMonths());
		months.remove(12);
		for (int i = 0; i < months.getSize(); i++) {
			monthCB.getItems().add(months.get(i).substring(0, 1).toUpperCase() + months.get(i).substring(1));
		}

		fecha.set(LocalDate.now());
		for (int i = 1; i <= 31; i++) {
			dayCB.getItems().add("" + i);
		}
		dayCB.getSelectionModel().select(LocalDate.now().getDayOfMonth() - 1);
		monthCB.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);
		yearCB.getSelectionModel().selectFirst();

		dayCB.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
			cambioFecha(nv);
		});
		monthCB.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
			cambioFecha(nv);
			bisiesto();
		});
		yearCB.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
			cambioFecha(nv);
			bisiesto();
		});
		yearCB.getEditor().focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (isNowFocused != null) {
				try {
					int year = 0;
					year = Integer.parseInt(yearCB.getEditor().getText());
					if (year >= 1900 && year <= LocalDate.now().getYear()) {
						yearCB.getSelectionModel().select(""+year);;
					} else {
						yearCB.getSelectionModel().selectFirst();
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Error de formato.");
						alert.setContentText("Los valores introducidos no son válidos.");
						alert.showAndWait();
					}
				} catch (Exception e) {
					yearCB.getSelectionModel().selectFirst();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Error de formato.");
					alert.setContentText("Los valores introducidos no son válidos.");
					alert.showAndWait();
				}
				
			}
		});
	}

	private void cambioFecha(String nv) {
		if (nv != null) {
			try {
				LocalDate localDate = LocalDate.parse(yearCB.getSelectionModel().getSelectedItem() + "-"
						+ (String.format("%02d", (monthCB.getSelectionModel().getSelectedIndex() + 1))) + "-"
						+ (String.format("%02d", Integer.parseInt(dayCB.getSelectionModel().getSelectedItem()))));
				fecha.set(localDate);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error de formato.");
				alert.setContentText("Los valores introducidos no son válidos.");
			}
		}
	}

	private void bisiesto() {
		int selectedM = monthCB.getSelectionModel().getSelectedIndex();
		int ultimoValor = Integer.parseInt(dayCB.getSelectionModel().getSelectedItem());
		dayCB.getItems().clear();
		if (selectedM == 1) {
			if (Year.of(Integer.parseInt(yearCB.getSelectionModel().getSelectedItem())).isLeap()) {
				for (int i = 1; i <= 29; i++) {
					dayCB.getItems().add("" + i);
				}
				if (ultimoValor > 29) {
					dayCB.getSelectionModel().select(28);
				} else {
					dayCB.getSelectionModel().select(ultimoValor - 1);
				}
			} else {
				for (int i = 1; i <= 28; i++) {
					dayCB.getItems().add("" + i);
				}
				if (ultimoValor > 28) {
					dayCB.getSelectionModel().select(27);
				} else {
					dayCB.getSelectionModel().select(ultimoValor - 1);
				}
			}
		} else if (selectedM == 0 || selectedM == 2 || selectedM == 4 || selectedM == 6 || selectedM == 7
				|| selectedM == 9 || selectedM == 11) {
			for (int i = 1; i <= 31; i++) {
				dayCB.getItems().add("" + i);
			}
			dayCB.getSelectionModel().select(ultimoValor - 1);
		} else {
			for (int i = 1; i <= 30; i++) {
				dayCB.getItems().add("" + i);
			}
			if (ultimoValor > 30) {
				dayCB.getSelectionModel().select(29);
			} else {
				dayCB.getSelectionModel().select(ultimoValor - 1);
			}
		}
	}

	public final ObjectProperty<LocalDate> fechaProperty() {
		return this.fecha;
	}
	

	public final LocalDate getFecha() {
		return this.fechaProperty().get();
	}
	

	public final void setFecha(final LocalDate fecha) {
		this.fechaProperty().set(fecha);
	}
	

	public final SimpleListProperty<String> monthsProperty() {
		return this.months;
	}
	

	public final ObservableList<String> getMonths() {
		return this.monthsProperty().get();
	}
	

	public final void setMonths(final ObservableList<String> months) {
		this.monthsProperty().set(months);
	}

}
