package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
	
	private Connection conexionBD;
	
	//Injecció dels panells i controls de la UI definida al fitxer fxml
	@FXML private Button btnPersons;
	@FXML private Button btnExit;
	@FXML private Button btnProducts;
	@FXML private Button btnCustomers;
	@FXML private Button btnSuppliers;
	@FXML private Button btnPresences;
	
	public IniciBotonsController() {
		try{
			//Establir la connexio amb la BD
			String urlBaseDades = "jdbc:postgresql://localhost/AgendaFX_JDBC_1";
			String usuari = "postgres";
			String contrasenya = "root";
			conexionBD = DriverManager.getConnection(urlBaseDades , usuari, contrasenya);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

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
			personasController.setConexionBD(conexionBD);
			personasController.setVentana(stage);
			
			
			//Programem l'event que s'executará quan es tanqui la finestra
			stage.setOnCloseRequest((WindowEvent we) -> {
				try {
					personasController.sortir();
					this.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			break;
			
		case "products":

			ProductsController productsController = loader.getController();
			productsController.setConexionBD(conexionBD);
			productsController.setVentana(stage);
			
			stage.setOnCloseRequest((WindowEvent we) -> {
				
				try {
					productsController.sortir();
					this.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			break;
			
		case "customers":

			CustomersController customersController = loader.getController();
			customersController.setConexionBD(conexionBD);
			customersController.setVentana(stage);
			
			stage.setOnCloseRequest((WindowEvent we) -> {
				try {
					customersController.sortir();
					this.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			break;
			
		case "suppliers":

			SuppliersController suppliersController = loader.getController();
			suppliersController.setConexionBD(conexionBD);
			suppliersController.setVentana(stage);
			
			stage.setOnCloseRequest((WindowEvent we) -> {
				try {
					suppliersController.sortir();
					this.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			break;
		case "presences":

			PresencesController presencesController = loader.getController();
			presencesController.setConexionBD(conexionBD);
			presencesController.setVentana(stage);
			
			stage.setOnCloseRequest((WindowEvent we) -> {
				try {
					presencesController.sortir();
					this.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			break;
		}
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		
		try {
			if (conexionBD != null) conexionBD.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
