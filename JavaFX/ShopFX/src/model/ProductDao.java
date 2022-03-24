package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Locale.Category;

public class ProductDao implements Persistable<Product>{
	
	private TreeMap<Integer, Product> productList = new TreeMap<Integer,Product>();
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	public void add(Product obj) {
		productList.put(obj.getId(),obj);
	}
		
	public boolean exists(Integer id) {
		return this.productList.containsKey(id); 
	}
		
	public Product search(Integer id) {
		return this.productList.get(id);
	}
		
	public boolean delete(Integer id) {
		this.productList.remove(id);
		
		return this.exists(id);
	}

	public void showAll(){

		System.out.println("-------------------");
		System.out.println(texts.getString("title.print.products"));
		System.out.println("-------------------");
		
		for (Product product : productList.values()) {
			product.print();
			
		    System.out.println("-------------------");
		}
	}
	
	public void deleteProductPack(Product product) {
		// si el producto eliminado lo contiene un pack se elimina ese producto del pack
		
		for (Product prod : productList.values()) {
			Pack pack;
			if(prod instanceof Pack){
				pack = (Pack) prod;
				
				if(pack.getProducts().contains(product)) {
					pack.getProducts().remove(product);
				}
			}
		}
	}

	
	public void save() {
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.dat"))) {
			oos.writeObject(productList);
	        oos.close();
		} catch (IOException e) {
			System.out.println(texts.getString("error.save.file"));
		}
        
	}
	

	@SuppressWarnings("unchecked")
	public void load() {
		File file = new File("products.dat");
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
				productList = (TreeMap<Integer, Product>) ois.readObject();
			} catch (Exception e) {
				System.out.println(texts.getString("error.load.file"));
			}
		}
	}
	
}
