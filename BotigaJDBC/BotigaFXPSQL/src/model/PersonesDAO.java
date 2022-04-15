package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale.Category;

public class PersonesDAO {
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	private Connection conexionBD;

	public PersonesDAO(Connection conexionBD) {
		this.conexionBD = conexionBD;
	}

	public boolean save(Persona persona){
		try {
			String sql = "";
			PreparedStatement stmt = null;
			if (this.find(persona.getId()) == null){
				
				sql = "INSERT INTO persones VALUES(?,?,?,?,?,?,?)";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, 	persona.getId());
				stmt.setString(i++, persona.getDni());
				stmt.setString(i++, persona.getName());
				stmt.setString(i++, persona.getLastName());
				stmt.setObject(i++, persona.getDateOfBirth());
				stmt.setString(i++, persona.getEmail());
				stmt.setString(i++, persona.getPhone());
			} else{
				sql = "UPDATE persones SET dni=?,nombre=?,apellidos=?,fecha_nacimiento=?,email=?,telefon=? WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setString(i++, persona.getDni());
				stmt.setString(i++, persona.getName());
				stmt.setString(i++, persona.getLastName());
				stmt.setObject(i++, persona.getDateOfBirth());
				stmt.setString(i++, persona.getEmail());
				stmt.setString(i++, persona.getPhone());
				stmt.setInt(i++, persona.getId());
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
				sql = "DELETE FROM persones WHERE id = ?";
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

	public Persona find(Integer id){

		if (id == null || id == 0){
			return null;
		}

		Persona p = null;
		try (PreparedStatement stmt = conexionBD.prepareStatement("SELECT * FROM persones WHERE id = ?")){
			stmt.setInt(1, id); //informem el primer paràmetre de la consulta amb ?
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				LocalDate localDate = result.getObject ( "fecha_nacimiento", LocalDate.class ); 
				p = new Persona(result.getInt("id"), result.getString("dni"),result.getString("nombre"), result.getString("apellidos"),localDate,result.getString("email"),result.getString("telefon"));
				p.imprimir();
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return p;
	}

	public void showAll(){

		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM persones")) {
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

