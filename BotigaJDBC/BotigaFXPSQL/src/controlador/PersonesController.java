package controlador;

import java.io.IOException;
import java.sql.Connection;
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
import model.Persona;
import model.PersonesDAO;

public class PersonesController{
	
	//Definimos y obtenemos las traducciones segun el idioma configurado en el SO
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);


	//Objecte per gestionar la persistència de les dades
	private PersonesDAO persones;

	//Elements gràfics de la UI
	private Stage ventana;
	@FXML private TextField idTextField;
	@FXML private TextField dniTextField;
	@FXML private TextField nameTextField;
	@FXML private TextField lastNameTextField;
	@FXML private DatePicker dateOFbirthPicker;
	@FXML private TextField emailTextField;
	@FXML private TextField phoneTextField;

	private ValidationSupport vs = new ValidationSupport();
	
	public void setConexionBD(Connection conexionBD) {	
		//Crear objecte DAO de persones
		persones = new PersonesDAO(conexionBD);
		
	}


	/**
	 * Inicialitza la classe. JAVA l'executa automàticament després de carregar el fitxer fxml
	 */
	@FXML private void initialize() {

		//Validació dades
		//https://github.com/controlsfx/controlsfx/issues/1148
		//produeix error si no posem a les VM arguments això: --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED --add-opens=javafx.graphics/javafx.scene=org.controlsfx.controls
		
		vs.registerValidator(idTextField, true, Validator.createEmptyValidator(texts.getString("input.id.mandatory")));
		vs.registerValidator(dniTextField, true, Validator.createRegexValidator(texts.getString("input.dni.mandatory"),"^[0-9]{8,8}[A-Za-z]$",Severity.ERROR));
		vs.registerValidator(nameTextField, true, Validator.createEmptyValidator(texts.getString("input.name.mandatory")));
		vs.registerValidator(lastNameTextField, true, Validator.createEmptyValidator(texts.getString("input.lastName.mandatory")));
		vs.registerValidator(dateOFbirthPicker, true, Validator.createEmptyValidator(texts.getString("input.dateOfBirth.mandatory")));
        //https://howtodoinjava.com/regex/java-regex-validate-email-address/
        vs.registerValidator(emailTextField, Validator.createRegexValidator(texts.getString("input.email.mandatory"), "^(.+)@(.+)$", Severity.ERROR));
        vs.registerValidator(phoneTextField, Validator.createRegexValidator(texts.getString("input.phone.mandatory"), "\\d*", Severity.ERROR));
	}

	public Stage getVentana() {
		return ventana;
	}

	public void setVentana(Stage ventana) {
		this.ventana = ventana;
	}

	@FXML private void onKeyPressedId(KeyEvent e) throws IOException {

		if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB){
			//Comprovar si existeix la persona indicada en el control idTextField
			Persona persona = persones.find(Integer.parseInt(idTextField.getText()));
			
			limpiarFormulario();
			
			if(persona instanceof Persona){ 
				//posar els valors per modificarlos
				dniTextField.setText(persona.getDni());
				nameTextField.setText(persona.getName());
				lastNameTextField.setText(persona.getLastName());
				dateOFbirthPicker.setValue(persona.getDateOfBirth());
				emailTextField.setText(persona.getEmail());
				phoneTextField.setText(persona.getPhone());
			}
		}
	}
	 
	@FXML private void onActionGuardar(ActionEvent e) throws IOException {
		//verificar si les dades són vàlides				
		if(isDatosValidos()){
			
			Persona persona = persones.find(Integer.parseInt(idTextField.getText()));
			
			if(!(persona instanceof Persona)) {
				persona = new Persona();
				persona.setId(Integer.parseInt(idTextField.getText()));
			}
			
			
			persona.setDni(dniTextField.getText());
			persona.setName(nameTextField.getText());
			persona.setLastName(lastNameTextField.getText());
			persona.setDateOfBirth(dateOFbirthPicker.getValue());
			persona.setEmail(emailTextField.getText());
			persona.setPhone(phoneTextField.getText());

			persones.save(persona);
			limpiarFormulario();
			persones.showAll();
		}
	}

	@FXML private void onActionEliminar(ActionEvent e) throws IOException {

		if(isDatosValidos()){
			if(persones.delete(Integer.parseInt(idTextField.getText()))){
				limpiarFormulario();
				persones.showAll();
			}
		}
	}

	@FXML private void onActionSortir(ActionEvent e) throws IOException {

		sortir();

		ventana.close();
	}

	public void sortir(){
		persones.showAll();
	}

	private boolean isDatosValidos() {

		//Comprovar si totes les dades són vàlides
		if (vs.isInvalid()) {
			String errors = vs.getValidationResult().getMessages().toString();
			// Mostrar finestra amb els errors
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
		idTextField.setText("");
		dniTextField.setText("");
		nameTextField.setText("");
		lastNameTextField.setText("");
		dateOFbirthPicker.setValue(null);
		emailTextField.setText("");
		phoneTextField.setText("");
	}
}
