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
/**
 * Clase per gestionar la persistència de les dades de les persones
 * @author manuel
 *
 */
public class PersonesDAO {

	private TreeMap<Integer, Persona> persones = new TreeMap<Integer,Persona>();
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);

	public boolean save(Persona persona){
		System.out.println(persona.getId());
		persones.put(persona.getId(), persona);
		return true;
	}

	public boolean delete(Integer id){

		if (persones.containsKey(id)){
			persones.remove(id);
			return true;
		}

		return false;
	}

	public Persona find(Integer id){

		if (id == null || id == 0){
			return null;
		}

		return persones.get(id);
	}

	public void saveAll(){

		try (ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream("personas.dat"))) {
			oo.writeObject(persones);
		} catch (IOException e) {
			System.out.println(texts.getString("error.save.file"));
		}

	}

	@SuppressWarnings("unchecked")
	public void openAll(){

		File file = new File("personas.dat");
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
				persones = (TreeMap<Integer, Persona>) ois.readObject();
			} catch (Exception e) {
				System.out.println(texts.getString("error.load.file"));
			}
		}
	}

	public void showAll(){

		System.out.println("-------------------");
		System.out.println(texts.getString("title.print.persons"));
		System.out.println("-------------------");
		
		for (Persona persona : persones.values()) {
		    persona.imprimir();
		    System.out.println("-------------------");
		}
	}
}

