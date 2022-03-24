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

public class RegisterPresenceDAO implements Persistable<Presence> {
	
	TreeMap<Integer, Presence> daoRegisterList=new TreeMap<>();
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	public void add(Presence obj) {
		this.daoRegisterList.put(obj.getIdWorker(),obj);
	}
	
	public boolean exists(Integer id) {
		return this.daoRegisterList.containsKey(id); 
	}
	public Presence search(Integer id) {
		return this.daoRegisterList.get(id);
	}
		
	public boolean delete(Integer id) {
		this.daoRegisterList.remove(id);
		return this.exists(id);
	}

	public void showAll() {
		System.out.println("-------------------");
		System.out.println(texts.getString("title.print.presences"));
		System.out.println("-------------------");
		
		for (Presence presence : daoRegisterList.values()) {
			presence.print();
			
		    System.out.println("-------------------");
		}
	}

	
	
	public void save() {
			
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("presence.dat"))) {
			oos.writeObject(daoRegisterList);
	        oos.close();
		} catch (IOException e) {
			System.out.println(texts.getString("error.save.file"));
		}
        
	}
	

	@SuppressWarnings("unchecked")
	public void load() {
		File file = new File("presence.dat");
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
				daoRegisterList = (TreeMap<Integer, Presence>) ois.readObject();
			} catch (Exception e) {
				System.out.println(texts.getString("error.load.file"));
			}
		}
	}
}
