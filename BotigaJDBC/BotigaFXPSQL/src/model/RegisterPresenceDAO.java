package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale.Category;

public class RegisterPresenceDAO{
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	private Connection conexionBD;

	public RegisterPresenceDAO(Connection conexionBD) {
		this.conexionBD = conexionBD;
	}

	public boolean save(Presence presence){
		try {
			String sql = "";
			PreparedStatement stmt = null;
			if (this.find(presence.getIdWorker()) == null){
				
				sql = "INSERT INTO asistencia_trabajador VALUES(?,?,?,?)";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, 	presence.getIdWorker());
				stmt.setObject(i++, presence.getDate());
				stmt.setObject(i++, presence.getHourEntry());
				stmt.setObject(i++, presence.getHourExit());

			} else{
				sql = "UPDATE asistencia_trabajador SET fecha_entrada=?,hora_entrada=?,hora_salida=? WHERE id_trabajador = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setObject(i++, presence.getDate());
				stmt.setObject(i++, presence.getHourEntry());
				stmt.setObject(i++, presence.getHourExit());
				stmt.setInt(i++, presence.getIdWorker());
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
				sql = "DELETE FROM asistencia_trabajador WHERE id_trabajador = ?";
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

	public Presence find(Integer id){

		if (id == null || id == 0){
			return null;
		}

		Presence presence = null;
		try (PreparedStatement stmt = conexionBD.prepareStatement("SELECT * FROM asistencia_trabajador WHERE id_trabajador = ?")){
			stmt.setInt(1, id); //informem el primer paràmetre de la consulta amb ?
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				LocalDate fecha_entrada = result.getObject ( "fecha_entrada", LocalDate.class );
				LocalTime hora_entrada =  LocalTime.parse(result.getString( "hora_entrada"));
				LocalTime hora_salida = LocalTime.parse(result.getString( "hora_salida"));
				presence = new Presence(result.getInt("id_trabajador"),fecha_entrada,hora_entrada,hora_salida);
				presence.print();
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return presence;
	}

	public void showAll(){
		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM asistencia_trabajador")) {
			
			while (result.next()) {
				LocalDate fecha_entrada = result.getObject ( "fecha_entrada", LocalDate.class );
				LocalTime hora_entrada =  LocalTime.parse(result.getString( "hora_entrada"));
				LocalTime hora_salida = LocalTime.parse(result.getString( "hora_salida"));
				Presence presence = new Presence(result.getInt("id_trabajador"),fecha_entrada,hora_entrada,hora_salida);
				presence.print();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
