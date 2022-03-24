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

public class CustomerDao{
	
	TreeMap<Integer, Customer> customerList=new TreeMap<Integer,Customer>();
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	public boolean save(Customer customer){
		customerList.put(customer.getId(), customer);
		return true;
	}

	public boolean delete(Integer id){

		if (customerList.containsKey(id)){
			customerList.remove(id);
			return true;
		}

		return false;
	}

	public Customer find(Integer id){

		if (id == null || id == 0){
			return null;
		}

		return customerList.get(id);
	}

	public void saveAll(){

		try (ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream("customer.dat"))) {
			oo.writeObject(customerList);
		} catch (IOException e) {
			System.out.println(texts.getString("error.save.file"));
		}

	}

	@SuppressWarnings("unchecked")
	public void openAll(){

		File file = new File("customer.dat");
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
				customerList = (TreeMap<Integer, Customer>) ois.readObject();
			} catch (Exception e) {
				System.out.println(texts.getString("error.load.file"));
			}
		}
	}

	public void showAll(){

		System.out.println("-------------------");
		System.out.println(texts.getString("title.print.customers"));
		System.out.println("-------------------");
		
		for (Customer customer : customerList.values()) {
			customer.imprimir();
		    System.out.println("-------------------");
		}
	}
}
