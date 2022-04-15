package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale.Category;

public class CustomerDao{
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	private Connection conexionBD;

	public CustomerDao(Connection conexionBD) {
		this.conexionBD = conexionBD;
	}

	public boolean save(Customer customer){
		try {
			String sql = "";
			PreparedStatement stmt = null;
			if (this.find(customer.getId()) == null){
				
				sql = "INSERT INTO cliente VALUES(?,?,?,?,?,?,?)";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, 	customer.getId());
				stmt.setString(i++, customer.getDni());
				stmt.setString(i++, customer.getName());
				stmt.setString(i++, customer.getLastName());
				stmt.setObject(i++, customer.getDateOfBirth());
				stmt.setString(i++, customer.getEmail());
				stmt.setString(i++, customer.getPhone());
			} else{
				sql = "UPDATE cliente SET dni=?,nombre=?,apellidos=?,fecha_nacimiento=?,email=?,telefon=? WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setString(i++, customer.getDni());
				stmt.setString(i++, customer.getName());
				stmt.setString(i++, customer.getLastName());
				stmt.setObject(i++, customer.getDateOfBirth());
				stmt.setString(i++, customer.getEmail());
				stmt.setString(i++, customer.getPhone());
				stmt.setInt(i++, customer.getId());
			}
			int rows = stmt.executeUpdate();
			if (rows == 1) return true;
			else return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean delete(Integer id){

		try {
			String sql = "";
			PreparedStatement stmt = null;
			if (this.find(id) != null){
				sql = "DELETE FROM cliente WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, id);
			}
			int rows = stmt.executeUpdate();
			if (rows == 1) return true;
			else return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public Customer find(Integer id){

		if (id == null || id == 0){
			return null;
		}

		Customer customer = null;
		try (PreparedStatement stmt = conexionBD.prepareStatement("SELECT * FROM cliente WHERE id = ?")){
			stmt.setInt(1, id); //informem el primer paràmetre de la consulta amb ?
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				LocalDate localDate = result.getObject ( "fecha_nacimiento", LocalDate.class ); 
				customer = new Customer(result.getInt("id"), result.getString("dni"),result.getString("nombre"), result.getString("apellidos"),localDate,result.getString("email"),result.getString("telefon"));
				customer.imprimir();
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return customer;
	}

	public void showAll(){

		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM cliente")) {
			while (result.next()) {
				LocalDate localDate = result.getObject ( "fecha_nacimiento", LocalDate.class ); 
				Persona p = new Persona(result.getInt("id"), result.getString("dni"),result.getString("nombre"), result.getString("apellidos"),localDate,result.getString("email"),result.getString("telefon"));
				p.imprimir();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
