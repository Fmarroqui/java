package controlador;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale.Category;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Presence;
import model.RegisterPresenceDAO;

public class PresencesController{

	private RegisterPresenceDAO presences;
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);


	private Stage ventana;
	@FXML private TextField idWorkerTextField;
	@FXML private DatePicker datePicker;
	@FXML private TextField hourEntryTextField;
	@FXML private TextField hourExitTextField;
	
	private ValidationSupport vs = new ValidationSupport();


	@FXML private void initialize() {
		
		presences = new RegisterPresenceDAO();
		
		presences.load();

		vs.registerValidator(idWorkerTextField, true, Validator.createEmptyValidator(texts.getString("input.idWorker.mandatory")));
		vs.registerValidator(datePicker, true, Validator.createEmptyValidator(texts.getString("input.date.mandatory")));		
		vs.registerValidator(hourEntryTextField, Validator.createRegexValidator(texts.getString("input.hourEntry.mandatory"), "([01]?[0-9]|2[0-3]):[0-5][0-9]$", Severity.ERROR));
        vs.registerValidator(hourExitTextField, Validator.createRegexValidator(texts.getString("input.hourExit.mandatory"), "([01]?[0-9]|2[0-3]):[0-5][0-9]$", Severity.ERROR));
        
	}

	public Stage getVentana() {
		return ventana;
	}

	public void setVentana(Stage ventana) {
		this.ventana = ventana;
	}

	@FXML private void onKeyPressedId(KeyEvent e) throws IOException {
		
		if(idWorkerTextField.getText() == "") {
			limpiarFormulario();
		}
		
		if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB){
			
			Presence presence = presences.search(Integer.parseInt(idWorkerTextField.getText()));
			
			if(presence instanceof Presence){ 
			
				datePicker.setValue((presence.getDate()));
				hourEntryTextField.setText(String.valueOf(presence.getHourEntry()));
				hourExitTextField.setText(String.valueOf(presence.getHourExit()));
			}	
		}
	}

	 
	@SuppressWarnings("null")
	@FXML private void onActionGuardar(ActionEvent e) throws IOException {
			
		if(isDatosValidos()){
	
			Presence presence = new Presence(Integer.parseInt(idWorkerTextField.getText()),datePicker.getValue(), LocalTime.parse(hourEntryTextField.getText()), LocalTime.parse(hourExitTextField.getText()));
			presences.add(presence);
			
			
			limpiarFormulario();
			presences.showAll();
		}
	}

	@FXML private void onActionEliminar(ActionEvent e) throws IOException {

		if(isDatosValidos()){
			Presence presence = presences.search(Integer.parseInt(idWorkerTextField.getText()));

			if(presences.delete(presence.getIdWorker())){
				limpiarFormulario();
				presences.showAll();
			}
		}
	}
	

	@FXML private void onActionSortir(ActionEvent e) throws IOException {

		sortir();
		ventana.close();
	}

	public void sortir(){
		presences.save();
		presences.showAll();
	}

	private boolean isDatosValidos() {

		if (vs.isInvalid()) {
			String errors = vs.getValidationResult().getMessages().toString();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(ventana);
			alert.setTitle(texts.getString("title.error"));
			alert.setHeaderText(texts.getString("info.error"));
			alert.setContentText(errors);
			alert.showAndWait();
		
			return false;
		}

		return true;

	}

	private void limpiarFormulario(){
		idWorkerTextField.setText("");
		datePicker.setValue(null);
		hourEntryTextField.setText("");
		hourExitTextField.setText("");

	}
}
