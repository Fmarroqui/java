package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale.Category;

public class SupplierDao{
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	private Connection conexionBD;

	public SupplierDao(Connection conexionBD) {
		this.conexionBD = conexionBD;
	}

	public boolean save(Supplier supplier){
		try {
			String sql = "";
			PreparedStatement stmt = null;
			if (this.find(supplier.getId()) == null){
				
				sql = "INSERT INTO proveedor VALUES(?,?,?,?,?,?,?)";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, 	supplier.getId());
				stmt.setString(i++, supplier.getDni());
				stmt.setString(i++, supplier.getName());
				stmt.setString(i++, supplier.getLastName());
				stmt.setObject(i++, supplier.getDateOfBirth());
				stmt.setString(i++, supplier.getEmail());
				stmt.setString(i++, supplier.getPhone());
			} else{
				sql = "UPDATE proveedor SET dni=?,nombre=?,apellidos=?,fecha_nacimiento=?,email=?,telefon=? WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setString(i++, supplier.getDni());
				stmt.setString(i++, supplier.getName());
				stmt.setString(i++, supplier.getLastName());
				stmt.setObject(i++, supplier.getDateOfBirth());
				stmt.setString(i++, supplier.getEmail());
				stmt.setString(i++, supplier.getPhone());
				stmt.setInt(i++, supplier.getId());
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
				sql = "DELETE FROM proveedor WHERE id = ?";
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

	public Supplier find(Integer id){

		if (id == null || id == 0){
			return null;
		}

		Supplier supplier = null;
		try (PreparedStatement stmt = conexionBD.prepareStatement("SELECT * FROM proveedor WHERE id = ?")){
			stmt.setInt(1, id); //informem el primer paràmetre de la consulta amb ?
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				LocalDate localDate = result.getObject ( "fecha_nacimiento", LocalDate.class ); 
				supplier = new Supplier(result.getInt("id"), result.getString("dni"),result.getString("nombre"), result.getString("apellidos"),localDate,result.getString("email"),result.getString("telefon"));
				supplier.imprimir();
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return supplier;
	}

	public void showAll(){

		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM proveedor")) {
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
