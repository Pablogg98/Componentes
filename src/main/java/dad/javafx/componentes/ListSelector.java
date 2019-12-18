package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;

public class ListSelector<T> extends GridPane implements Initializable {

	//model
	
	private ListProperty<T> left = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<T> right = new SimpleListProperty<>(FXCollections.observableArrayList());
	private StringProperty leftTitle = new  SimpleStringProperty();
	private StringProperty rightTitle = new  SimpleStringProperty();
	
	//view
	
	@FXML
	private Label leftLb, rightLb;

	@FXML
	private ListView<T> leftList,rightList;

	@FXML
	private Button moveToRigthBtn, moveAllToRigthBtn, moveToLeftBtn, moveAllToLeftBtn;

	public ListSelector() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListSelectorView.fxml"));
			loader.setController(this);
			loader.setRoot(this); // establecemos la raiz de la vista
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		leftList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		rightList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		leftList.itemsProperty().bind(left);
		rightList.itemsProperty().bind(right);
		
		leftTitle.bind(leftLb.textProperty());
		rightTitle.bind(rightLb.textProperty());
	}

	@FXML
	void onMoveAllToLeftAcion(ActionEvent event) {
		left.addAll(right);
		right.clear();
	}

	@FXML
	void onMoveAllToRightAcion(ActionEvent event) {
		right.addAll(left);
		left.clear();
	}

	@FXML
	void onMoveToLeftAcion(ActionEvent event) {
		left.addAll(rightList.getSelectionModel().getSelectedItems());
		right.removeAll(rightList.getSelectionModel().getSelectedItems());
	}

	@FXML
	void onMoveToRightAcion(ActionEvent event) {
		right.addAll(leftList.getSelectionModel().getSelectedItems());
		left.removeAll(leftList.getSelectionModel().getSelectedItems());
	}

	public final ListProperty<T> leftProperty() {
		return this.left;
	}
	

	public final ObservableList<T> getLeft() {
		return this.leftProperty().get();
	}
	

	public final void setLeft(final ObservableList<T> left) {
		this.leftProperty().set(left);
	}
	

	public final ListProperty<T> rightProperty() {
		return this.right;
	}
	

	public final ObservableList<T> getRight() {
		return this.rightProperty().get();
	}
	

	public final void setRight(final ObservableList<T> right) {
		this.rightProperty().set(right);
	}
	

	public final StringProperty leftTitleProperty() {
		return this.leftTitle;
	}
	

	public final String getLeftTitle() {
		return this.leftTitleProperty().get();
	}
	

	public final void setLeftTitle(final String leftTitle) {
		this.leftTitleProperty().set(leftTitle);
	}
	

	public final StringProperty rightTitleProperty() {
		return this.rightTitle;
	}
	

	public final String getRightTitle() {
		return this.rightTitleProperty().get();
	}
	

	public final void setRightTitle(final String rightTitle) {
		this.rightTitleProperty().set(rightTitle);
	}
	
}
