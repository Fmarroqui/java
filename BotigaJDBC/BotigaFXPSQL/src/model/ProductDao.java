package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.Locale.Category;

public class ProductDao{
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	private Connection conexionBD;

	public ProductDao(Connection conexionBD) {
		this.conexionBD = conexionBD;
	}
	

	public boolean save(Product product){
		try {
			String sql = "";
			PreparedStatement stmt = null;
			if (this.find(product.getId()) == null){
				
				sql = "INSERT INTO producto VALUES(?,?,?,?,?,?)";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, 	product.getId());
				stmt.setString(i++, product.getName());
				stmt.setDouble(i++, product.getPrice());
				stmt.setInt(i++, product.getStock());
				stmt.setObject(i++, product.getCatalogStartDate());
				stmt.setObject(i++, product.getCatalogEndDate());

			} else{
				sql = "UPDATE producto SET nombre=?,precio=?,stock=?,fecha_inicio=?,fecha_fin=? WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
		
				stmt.setString(i++, product.getName());
				stmt.setDouble(i++, product.getPrice());
				stmt.setInt(i++, product.getStock());
				stmt.setObject(i++, product.getCatalogStartDate());
				stmt.setObject(i++, product.getCatalogEndDate());
				stmt.setInt(i++, product.getId());
			}
			int rows = stmt.executeUpdate();
			if (rows == 1) {
				return true;
			}
	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean savePack(Pack pack){
		try {
			String sql = "";
			PreparedStatement stmt = null;
			String sqlProductosPack = "";
			PreparedStatement stmtProductosPack = null;
			String sqlDelete = "";
			PreparedStatement stmtDelete = null;
			int respuesta = 0;
			if (this.findPack(pack.getId()) == null){
				
				sql = "INSERT INTO pack VALUES(?,?,?,?,?,?,?)";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, 	pack.getId());
				stmt.setString(i++, pack.getName());
				stmt.setDouble(i++, pack.getPrice());
				stmt.setInt(i++, pack.getStock());
				stmt.setObject(i++, pack.getCatalogStartDate());
				stmt.setObject(i++, pack.getCatalogEndDate());
				stmt.setDouble(i++, pack.getDiscountRate());
				
				respuesta = stmt.executeUpdate();
				
				sqlProductosPack = "INSERT INTO productos_pack VALUES(?,?)";
				stmtProductosPack = conexionBD.prepareStatement(sqlProductosPack);
				
				Iterator<Product> product = pack.getProducts().iterator();
				
				while (product.hasNext()) {
					int j = 1;
					stmtProductosPack.setInt(j++, 	pack.getId());
					stmtProductosPack.setInt(j++, product.next().getId());
					stmtProductosPack.executeUpdate();
				}
				
				if(respuesta == 1) {
					return true;
				}
				
				return false;

			} else{
				
				sql = "UPDATE pack SET nombre=?,precio=?,stock=?,fecha_inicio=?,fecha_fin=?,dto=? WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
		
				stmt.setString(i++, pack.getName());
				stmt.setDouble(i++, pack.getPrice());
				stmt.setInt(i++, pack.getStock());
				stmt.setObject(i++, pack.getCatalogStartDate());
				stmt.setObject(i++, pack.getCatalogEndDate());
				stmt.setDouble(i++, pack.getDiscountRate());
				stmt.setInt(i++, pack.getId());
				
				respuesta = stmt.executeUpdate();
				
				sqlDelete = "DELETE FROM productos_pack WHERE id_pack = ?";
				stmtDelete = conexionBD.prepareStatement(sqlDelete);
				
				int k = 1;
				stmtDelete.setInt(k++, pack.getId());
				stmtDelete.executeUpdate();
				
				sqlProductosPack = "INSERT INTO productos_pack VALUES(?,?)";
				stmtProductosPack = conexionBD.prepareStatement(sqlProductosPack);
				
				Iterator<Product> product = pack.getProducts().iterator();
				
				while (product.hasNext()) {
					int j = 1;
					stmtProductosPack.setInt(j++, 	pack.getId());
					stmtProductosPack.setInt(j++, product.next().getId());
					stmtProductosPack.executeUpdate();
				}
				
				if(respuesta == 1) {
					return true;
				}
				
			}
		
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
				sql = "DELETE FROM producto WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, id);
			}
			int respuesta = stmt.executeUpdate();
			if(respuesta == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean deletePack(Integer id){

		try {
			String sql = "";
			PreparedStatement stmt = null;
			String sql2 = "";
			PreparedStatement stmt2 = null;
			if (this.find(id) != null){
				sql = "DELETE FROM pack WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, id);
				
				sql2 = "DELETE FROM productos_pack WHERE id_pack = ?";
				stmt2 = conexionBD.prepareStatement(sql2);
				int j = 1;
				stmt2.setInt(j++, id);
			}
			
			int respuesta2 = stmt2.executeUpdate();
			int respuesta = stmt.executeUpdate();
			
			if(respuesta == 1 && respuesta2 == 1) {
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public Product find(Integer id){

		if (id == null || id == 0){
			return null;
		}

		Product product = null;
		try (PreparedStatement stmt = conexionBD.prepareStatement("SELECT * FROM producto WHERE id = ?")){
			stmt.setInt(1, id); //informem el primer paràmetre de la consulta amb ?
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				LocalDate fecha_inicio = result.getObject ( "fecha_inicio", LocalDate.class );
				LocalDate fecha_fin = result.getObject ( "fecha_fin", LocalDate.class ); 
				product = new Product(result.getInt("id"), result.getString("nombre"),result.getInt("precio"), result.getInt("stock"),fecha_inicio,fecha_fin);
				product.print();
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return product;
	}
	
	public Pack findPack(Integer id){

		if (id == null || id == 0){
			return null;
		}

		Pack pack = null;
		try (PreparedStatement stmt = conexionBD.prepareStatement("SELECT * FROM pack WHERE id = ?")){
			stmt.setInt(1, id); //informem el primer paràmetre de la consulta amb ?
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				TreeSet<Product> products = new TreeSet<Product>();
				PreparedStatement stmtProducts  = conexionBD.prepareStatement("SELECT id_producto FROM productos_pack WHERE id_pack = ?");
				stmtProducts.setInt(1, result.getInt("id"));
				ResultSet result2 = stmtProducts.executeQuery();
				while (result2.next()) {
					Product product = this.find(result2.getInt("id_producto"));
					products.add(product);
				}
				LocalDate fecha_inicio = result.getObject ( "fecha_inicio", LocalDate.class );
				LocalDate fecha_fin = result.getObject ( "fecha_fin", LocalDate.class ); 
				pack = new Pack(result.getInt("id"), result.getString("nombre"),result.getInt("precio"), result.getInt("stock"),fecha_inicio,fecha_fin,products,result.getDouble("dto"));
				pack.print();
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return pack;
	}


	public void showAll(){

		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM producto")) {
			while (result.next()) {
				LocalDate fecha_inicio = result.getObject ( "fecha_inicio", LocalDate.class );
				LocalDate fecha_fin = result.getObject ( "fecha_fin", LocalDate.class ); 
				Product p = new Product(result.getInt("id"), result.getString("nombre"),result.getInt("precio"), result.getInt("stock"),fecha_inicio,fecha_fin);
				p.print();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM pack")) {
			while (result.next()) {
				//creamos consulta interna para imprimir los productos del pack
				TreeSet<Product> products = new TreeSet<Product>();
				PreparedStatement stmt  = conexionBD.prepareStatement("SELECT id_producto FROM productos_pack WHERE id_pack = ?");
				stmt.setInt(1, result.getInt("id"));
				ResultSet result2 = stmt.executeQuery();
				while (result2.next()) {
					Product p = this.find(result2.getInt("id_producto"));
					products.add(p);
				}
				LocalDate fecha_inicio = result.getObject ( "fecha_inicio", LocalDate.class );
				LocalDate fecha_fin = result.getObject ( "fecha_fin", LocalDate.class ); 
				Pack p = new Pack(result.getInt("id"), result.getString("nombre"),result.getInt("precio"), result.getInt("stock"),fecha_inicio,fecha_fin,products,result.getDouble("dto"));
				p.print();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
