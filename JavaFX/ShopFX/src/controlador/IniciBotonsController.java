package controlador;

import java.io.IOException;
import java.util.Locale;
import java.util.Locale.Category;

import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class IniciBotonsController extends Application {
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	//Injecció dels panells i controls de la UI definida al fitxer fxml
	@FXML private Button btnPersons;
	@FXML private Button btnExit;
	@FXML private Button btnProducts;
	@FXML private Button btnCustomers;
	@FXML private Button btnSuppliers;
	@FXML private Button btnPresences;

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		//Carrega el fitxer amb la interficie d'usuari inicial (Scene)
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/IniciBotonsView.fxml"));
		loader.setResources(texts);
		
		Scene fm_inici = new Scene(loader.load());

		//Li assigna la escena a la finestra inicial (primaryStage) i la mostra
		primaryStage.setScene(fm_inici);
		primaryStage.setTitle(texts.getString("title.shop"));
		primaryStage.show();
       
	}

	@FXML
	private void onPersonsAction(ActionEvent e) throws Exception {
		changeScene("/vista/PersonsView.fxml", texts.getString("title.persons"),"persons");
	}
	
	@FXML
	private void onProductsAction(ActionEvent e) throws Exception {
		changeScene("/vista/ProductsView.fxml", texts.getString("title.products"),"products");
	}
	
	@FXML
	private void onCustomersAction(ActionEvent e) throws Exception {
		changeScene("/vista/CustomersView.fxml", texts.getString("title.customers"),"customers");
	}
	
	@FXML
	private void onSuppliersAction(ActionEvent e) throws Exception {
		changeScene("/vista/SuppliersView.fxml", texts.getString("title.suppliers"),"suppliers");
	}
	
	@FXML
	private void onPresencesAction(ActionEvent e) throws Exception {
		changeScene("/vista/PresencesView.fxml", texts.getString("title.presences"),"presences");
	}
	
	@FXML
	private void onExitAction(ActionEvent e) throws Exception {
		Platform.exit();
	}
	
	
	
	private void changeScene(String path, String title,String controlador) throws IOException {
		
		//Carrega el fitxer amb la interficie d'usuari
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		
		//Carregar fitxer de textos multiidioma de la localització actual
		Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
		texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
		//fins aquí tot igual, només falta assignar el fitxer de recursos al formulari
		loader.setResources(texts);
		
		
		//Crea una nova finestra i l'obre 
		Stage stage = new Stage();
		Scene fm_scene = new Scene(loader.load());
		stage.setTitle(title);
		stage.setScene(fm_scene);
		stage.show();
		
		loadScene(controlador,loader,stage);
		
	}
	
	private void loadScene(String controlador,FXMLLoader loader,Stage stage) {
		
		switch (controlador) {
		case "persons":
			/************** Modificar ************/
			//Crear un objecte de la clase PersonasController ja que necessitarem accedir al mètodes d'aquesta classe
			PersonesController personasController = loader.getController();
			personasController.setVentana(stage);
			
			
			//Programem l'event que s'executará quan es tanqui la finestra
			stage.setOnCloseRequest((WindowEvent we) -> {
				personasController.sortir();
			});
			break;
			
		case "products":

			ProductsController productsController = loader.getController();
			productsController.setVentana(stage);
			
			stage.setOnCloseRequest((WindowEvent we) -> {
				productsController.sortir();
			});
			break;
			
		case "customers":

			CustomersController customersController = loader.getController();
			customersController.setVentana(stage);
			
			stage.setOnCloseRequest((WindowEvent we) -> {
				customersController.sortir();
			});
			break;
			
		case "suppliers":

			SuppliersController suppliersController = loader.getController();
			suppliersController.setVentana(stage);
			
			stage.setOnCloseRequest((WindowEvent we) -> {
				suppliersController.sortir();
			});
			break;
		case "presences":

			PresencesController presencesController = loader.getController();
			presencesController.setVentana(stage);
			
			stage.setOnCloseRequest((WindowEvent we) -> {
				presencesController.sortir();
			});
			break;
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
