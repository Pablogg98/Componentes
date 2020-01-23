package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MonthCalendar extends GridPane implements Initializable {

	// model

	private IntegerProperty year = new SimpleIntegerProperty();
	private IntegerProperty month = new SimpleIntegerProperty();
	private String[] nomMeses = new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto",
			"Septiembre", "Octubre", "Noviembre", "Diciembre" };

	// view

	@FXML
	private Label nomMonth;
	
	public MonthCalendar() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MonthCalendarView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		year.addListener((o,ov,nv) -> onCambio(getMonth()));
		month.addListener((o,ov,nv) -> onCambio(nv));
		
		if (LocalDate.now().getYear() == year.get() && LocalDate.now().getMonthValue() == month.get()) {
			System.out.println("hola");
			Label lb = new Label();
			lb = (Label) getChildren().get(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 7);
			lb.getStyleClass().clear();
			lb.getStyleClass().add("today");
		}
	}

	private void onCambio(Number nv) {
		for (int i = 8; i < 50; i++) {
			Label lb = new Label();
			lb = (Label) getChildren().get(i);
			lb.setText("");
		}
		
		nomMonth.setText(nomMeses[nv.intValue()]);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year.get(), month.get(), 0);
		int comienzo = calendar.get(Calendar.DAY_OF_WEEK) + 7;
		int fin = numeroDeDiasMes(getMonth()+1, getYear());
		for (int i = 1; i <= fin; i++) {
			Label lb = new Label();
			lb = (Label) getChildren().get(comienzo);
			lb.setText(""+i);
			comienzo++;
		}
	}
	
	public static int numeroDeDiasMes(int mes,int anio){
		 
	    int numeroDias=-1;
	 
	        switch(mes){
	            case 1:
	            case 3:
	            case 5:
	            case 7:
	            case 8:
	            case 10:
	            case 12:
	                numeroDias=31;
	                break;
	            case 4:
	            case 6:
	            case 9:
	            case 11:
	                numeroDias=30;
	                break;
	            case 2:
	 
	                if(esBisiesto(anio)){
	                    numeroDias=29;
	                }else{
	                    numeroDias=28;
	                }
	                break;
	 
	        }
	 
	    return numeroDias;
	}
	 
	public static boolean esBisiesto(int anio) {
	 
	    GregorianCalendar calendar = new GregorianCalendar();
	    boolean esBisiesto = false;
	    if (calendar.isLeapYear(anio)) {
	        esBisiesto = true;
	    }
	    return esBisiesto;
	 
	}
	
	public final IntegerProperty yearProperty() {
		return this.year;
	}
	

	public final int getYear() {
		return this.yearProperty().get();
	}
	

	public final void setYear(final int year) {
		this.yearProperty().set(year);
	}
	

	public final IntegerProperty monthProperty() {
		return this.month;
	}
	

	public final int getMonth() {
		return this.monthProperty().get();
	}
	

	public final void setMonth(final int month) {
		this.monthProperty().set(month);
	}

}
