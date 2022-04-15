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
import model.Supplier;
import model.SupplierDao;

public class SuppliersController{
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);

	private SupplierDao suppliers;

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
		suppliers = new SupplierDao(conexionBD);
		
	}

	@FXML private void initialize() {
		
		vs.registerValidator(idTextField, true, Validator.createEmptyValidator(texts.getString("input.id.mandatory")));
		vs.registerValidator(dniTextField, true, Validator.createRegexValidator(texts.getString("input.dni.mandatory"),"^[0-9]{8,8}[A-Za-z]$",Severity.ERROR));
		vs.registerValidator(nameTextField, true, Validator.createEmptyValidator(texts.getString("input.name.mandatory")));
		vs.registerValidator(lastNameTextField, true, Validator.createEmptyValidator(texts.getString("input.lastName.mandatory")));
		vs.registerValidator(dateOFbirthPicker, true, Validator.createEmptyValidator(texts.getString("input.dateOfBirth.mandatory")));
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

			Supplier supplier = suppliers.find(Integer.parseInt(idTextField.getText()));
			
			limpiarFormulario();
			
			if(supplier instanceof Supplier){ 

				dniTextField.setText(supplier.getDni());
				nameTextField.setText(supplier.getName());
				lastNameTextField.setText(supplier.getLastName());
				dateOFbirthPicker.setValue(supplier.getDateOfBirth());
				emailTextField.setText(supplier.getEmail());
				phoneTextField.setText(supplier.getPhone());
			}
		}
	}
	 
	@FXML private void onActionGuardar(ActionEvent e) throws IOException {
				
		if(isDatosValidos()){
			Supplier supplier = new Supplier(Integer.parseInt(idTextField.getText()), dniTextField.getText(), nameTextField.getText(), lastNameTextField.getText(),
					dateOFbirthPicker.getValue(), emailTextField.getText(), phoneTextField.getText());

			suppliers.save(supplier);
			limpiarFormulario();
			suppliers.showAll();
		}
	}

	@FXML private void onActionEliminar(ActionEvent e) throws IOException {

		if(isDatosValidos()){
			if(suppliers.delete(Integer.parseInt(idTextField.getText()))){
				limpiarFormulario();
				suppliers.showAll();
			}
		}
	}

	@FXML private void onActionSortir(ActionEvent e) throws IOException {
		sortir();
		ventana.close();
	}

	public void sortir(){
		suppliers.showAll();
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
		idTextField.setText("");
		dniTextField.setText("");
		nameTextField.setText("");
		lastNameTextField.setText("");
		dateOFbirthPicker.setValue(null);
		emailTextField.setText("");
		phoneTextField.setText("");
	}
}
