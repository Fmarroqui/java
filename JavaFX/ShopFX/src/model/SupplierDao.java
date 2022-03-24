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

public class SupplierDao{
	
	TreeMap<Integer, Supplier> supplierList=new TreeMap<Integer, Supplier>();
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	public boolean save(Supplier supplier){
		supplierList.put(supplier.getId(), supplier);
		return true;
	}

	public boolean delete(Integer id){

		if (supplierList.containsKey(id)){
			supplierList.remove(id);
			return true;
		}

		return false;
	}

	public Supplier find(Integer id){

		if (id == null || id == 0){
			return null;
		}

		return supplierList.get(id);
	}

	public void saveAll(){

		try (ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream("supplier.dat"))) {
			oo.writeObject(supplierList);
		} catch (IOException e) {
			System.out.println(texts.getString("error.save.file"));
		}

	}

	@SuppressWarnings("unchecked")
	public void openAll(){

		File file = new File("supplier.dat");
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
				supplierList = (TreeMap<Integer, Supplier>) ois.readObject();
			} catch (Exception e) {
				System.out.println(texts.getString("error.load.file"));
			}
		}
	}

	public void showAll(){

		System.out.println("-------------------");
		System.out.println(texts.getString("title.print.suppliers"));
		System.out.println("-------------------");
		
		for (Supplier supplier : supplierList.values()) {
			supplier.imprimir();
		    System.out.println("-------------------");
		}
	}
}
