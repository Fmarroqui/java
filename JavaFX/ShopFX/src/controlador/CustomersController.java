package controlador;

import java.io.IOException;
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
import model.Customer;
import model.CustomerDao;

public class CustomersController{
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);

	private CustomerDao customers;

	private Stage ventana;
	@FXML private TextField idTextField;
	@FXML private TextField dniTextField;
	@FXML private TextField nameTextField;
	@FXML private TextField lastNameTextField;
	@FXML private DatePicker dateOFbirthPicker;
	@FXML private TextField emailTextField;
	@FXML private TextField phoneTextField;

	private ValidationSupport vs = new ValidationSupport();


	@FXML private void initialize() {

		customers = new CustomerDao();
		customers.openAll();
		
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

			Customer customer = customers.find(Integer.parseInt(idTextField.getText()));
			
			limpiarFormulario();
			
			if(customer instanceof Customer){ 

				dniTextField.setText(customer.getDni());
				nameTextField.setText(customer.getName());
				lastNameTextField.setText(customer.getLastName());
				dateOFbirthPicker.setValue(customer.getDateOfBirth());
				emailTextField.setText(customer.getEmail());
				phoneTextField.setText(customer.getPhone());
			}
		}
	}
	 
	@FXML private void onActionGuardar(ActionEvent e) throws IOException {
				
		if(isDatosValidos()){
			Customer customer = new Customer(Integer.parseInt(idTextField.getText()), dniTextField.getText(), nameTextField.getText(), lastNameTextField.getText(),
					dateOFbirthPicker.getValue(), emailTextField.getText(), phoneTextField.getText());

			customers.save(customer);
			limpiarFormulario();
			customers.showAll();
		}
	}

	@FXML private void onActionEliminar(ActionEvent e) throws IOException {

		if(isDatosValidos()){
			if(customers.delete(Integer.parseInt(idTextField.getText()))){
				limpiarFormulario();
				customers.showAll();
			}
		}
	}

	@FXML private void onActionSortir(ActionEvent e) throws IOException {

		sortir();

		ventana.close();
	}

	public void sortir(){
		customers.saveAll();
		customers.showAll();
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
