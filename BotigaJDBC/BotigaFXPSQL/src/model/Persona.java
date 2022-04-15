package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale.Category;

public class Persona implements Serializable {
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String dni;
	private String name;
	private String lastName;
	private LocalDate dateOfBirth;
	private String email;
	private String phone;

	public Persona() {
		this.id = 0;
		this.dni = "";
		this.name = "";
		this.lastName = "";
		this.dateOfBirth = null;
		this.email = "";
		this.phone = "";
	}
	
	public Persona(Integer id,String dni, String name, String lastName, LocalDate dateOfBirth, String email, String phone) {
		this.id = id;
		this.dni = dni;
		this.name = name;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phone = phone;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void imprimir(){
		System.out.println(texts.getString("print.id") + id);
		System.out.println(texts.getString("print.dni") + dni);
		System.out.println(texts.getString("print.name") + name);
		System.out.println(texts.getString("print.lastName") + lastName);
		System.out.println(texts.getString("print.dateOfBirth") + dateOfBirth);
		System.out.println(texts.getString("print.email") + email);
		System.out.println(texts.getString("print.phone") + phone);
	}
}
