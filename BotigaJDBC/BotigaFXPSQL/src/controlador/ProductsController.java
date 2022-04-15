package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.Locale.Category;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Pack;
import model.Product;
import model.ProductDao;

public class ProductsController{

	private ProductDao products;
	
	//Definimos y obtenemos las traducciones segun el idioma configurado en el SO
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);


	//Elementos gráficos
	private Stage ventana;
	@FXML private TextField idTextField;
	@FXML private TextField priceTextField;
	@FXML private TextField nameTextField;
	@FXML private TextField stockTextField;
	@FXML private DatePicker dateStartPicker;
	@FXML private DatePicker dateEndPicker;
	@FXML private CheckBox packCheckBox;
	@FXML private TextArea productsTextArea;
	@FXML private TextField discountTextField;
	@FXML private Label lblDiscountTextField;
	@FXML private Label lblproductsTextArea;

	//Validación de datos
	private ValidationSupport vs = new ValidationSupport();
	
	public void setConexionBD(Connection conexionBD) {	
		//Crear objecte DAO de persones
		products = new ProductDao(conexionBD);
		
	}


	@FXML private void initialize() {
		
		
		//Definimos la validación de datos
		vs.registerValidator(idTextField, true, Validator.createEmptyValidator(texts.getString("input.id.mandatory")));
		vs.registerValidator(priceTextField, true, Validator.createRegexValidator(texts.getString("input.price.mandatory"),"\\d*(\\.\\d*)?", Severity.ERROR));
		vs.registerValidator(nameTextField, true, Validator.createEmptyValidator(texts.getString("input.name.mandatory")));
		vs.registerValidator(dateStartPicker, true, Validator.createEmptyValidator(texts.getString("input.dateStart.mandatory")));
		vs.registerValidator(dateEndPicker, true, Validator.createEmptyValidator(texts.getString("input.dateEnd.mandatory")));
        vs.registerValidator(stockTextField, Validator.createRegexValidator(texts.getString("input.stock.mandatory"), "\\d*", Severity.ERROR));
        
        //Ocultamos los campos de pack por defecto (en el caso que sea pack se visualizarán)
		hideDataPack();
        
	}

	public Stage getVentana() {
		return ventana;
	}

	public void setVentana(Stage ventana) {
		this.ventana = ventana;
	}

	@FXML private void onKeyPressedId(KeyEvent e) throws IOException {
		
		if(idTextField.getText() == "") {
			limpiarFormulario();
		}
		
		if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB){

			if(packCheckBox.isSelected()) {
				//Obtenemos el objeto Product
				Pack pack = products.findPack(Integer.parseInt(idTextField.getText()));
				
				//En el caso de que exista cargamos los datos guardados en los correspondientes campos
				if(pack instanceof Pack){ 
				
					priceTextField.setText(String.valueOf(pack.getPrice()));
					nameTextField.setText(pack.getName());
					stockTextField.setText(String.valueOf(pack.getStock()));
					stockTextField.setEditable(false);
					dateStartPicker.setValue(pack.getCatalogStartDate());
					dateEndPicker.setValue(pack.getCatalogEndDate());
					packCheckBox.setSelected(true);
					discountTextField.setText(String.valueOf(pack.getDiscountRate()));
					productsTextArea.setText(String.valueOf(pack.getProductsIds()));
				}
			}else {
				//Obtenemos el objeto Product
				Product product = products.find(Integer.parseInt(idTextField.getText()));
				
				//En el caso de que exista cargamos los datos guardados en los correspondientes campos
				if(product instanceof Product){ 
				
					priceTextField.setText(String.valueOf(product.getPrice()));
					nameTextField.setText(product.getName());
					stockTextField.setText(String.valueOf(product.getStock()));
					stockTextField.setEditable(false);
					dateStartPicker.setValue(product.getCatalogStartDate());
				}
			}
			
			showDataPack(null);
		}
	}
	
	@FXML private void showDataPack(ActionEvent e){
		//Ocultamos campos pack por defecto en el caso que se active se visualizarán y se validarán
		hideDataPack();
		if(packCheckBox.isSelected()) {
			
			lblDiscountTextField.setVisible(true);
			lblproductsTextArea.setVisible(true);
        	productsTextArea.setVisible(true);
        	discountTextField.setVisible(true);
        	vs.registerValidator(productsTextArea, Validator.createEmptyValidator(texts.getString("input.products.mandatory")));
        	vs.registerValidator(discountTextField, Validator.createRegexValidator(texts.getString("input.discount.mandatory"), "\\d*(\\.\\d*)?", Severity.ERROR));
        }
	}
	
	 private void hideDataPack(){
		lblDiscountTextField.setVisible(false);
		lblproductsTextArea.setVisible(false);
		productsTextArea.setVisible(false);
    	discountTextField.setVisible(false);
	}
	 

	@FXML private void onActionGuardar(ActionEvent e) throws IOException {
		//Verificar si los datos son correctos antes de proceder a crear/modificar Product/Pack				
		if(isDatosValidos()){
			//NOTA:para actualizar o buscar(rellenar datos) de pack se tiene que introducir id input y mercar check volver al input y darle al tab o enter para que cargue datos si es que existe
			//si existe actualizara datos y si no lo insertara.
			if(packCheckBox.isSelected()) {
				TreeSet<Product> productsPack = new TreeSet<Product>();
				String[] idsProducts = productsTextArea.getText().split(",");
				for (String id : idsProducts) {
					if(products.find(Integer.parseInt(id)) != null) {
						productsPack.add(products.find(Integer.parseInt(id)));
					}
				}
				
				Pack pack = new Pack(Integer.parseInt(idTextField.getText()), nameTextField.getText(), Double.parseDouble(priceTextField.getText()),Integer.parseInt(stockTextField.getText()),
						dateStartPicker.getValue(),dateEndPicker.getValue(),productsPack,Double.parseDouble(discountTextField.getText()));

				products.savePack(pack);
			}else {
	
				Product product = new Product(Integer.parseInt(idTextField.getText()), nameTextField.getText(), Double.parseDouble(priceTextField.getText()),Integer.parseInt(stockTextField.getText()),
						dateStartPicker.getValue(),dateEndPicker.getValue());
				products.save(product);
			}
			
			limpiarFormulario();
			products.showAll();
		}
	}

	@FXML private void onActionEliminar(ActionEvent e) throws IOException {

		if(isDatosValidos()){
			if(packCheckBox.isSelected()) {
				Product pack = products.findPack(Integer.parseInt(idTextField.getText()));
				if(products.deletePack(pack.getId())){
					limpiarFormulario();
					products.showAll();
				}
			}else {
				Product product = products.find(Integer.parseInt(idTextField.getText()));
				if(products.delete(product.getId())){
					limpiarFormulario();
					products.showAll();
				}
			}
			
		}
	}
	

	@FXML private void onActionSortir(ActionEvent e) throws IOException {
		sortir();
		ventana.close();
	}

	public void sortir(){
		products.showAll();
	}

	private boolean isDatosValidos() {

		//Comprobar si los datos son válidos
		if (vs.isInvalid()) {
			String errors = vs.getValidationResult().getMessages().toString();
			// Mostrar ventana en el caso que haya errores
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
		priceTextField.setText("");
		nameTextField.setText("");
		stockTextField.setText("");
		stockTextField.setEditable(true);
		dateStartPicker.setValue(null);
		dateEndPicker.setValue(null);
		packCheckBox.setSelected(false);
		discountTextField.setText("");
		productsTextArea.setText("");
	}
}
