package dad.javafx.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DateChoosetApp extends Application {

	private Controller root;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new Controller();
		
		Scene scene = new Scene(root.getView());
		
		primaryStage.setTitle("Prueba DateChooser");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
