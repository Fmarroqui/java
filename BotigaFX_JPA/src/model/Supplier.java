package model;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale.Category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="supplier")
public class Supplier{
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="dni")
	private String dni;
	@Column(name="name")
	private String name;
	@Column(name="lastName")
	private String lastName;
	@Column(name="dateOfBirth")
	private LocalDate dateOfBirth;
	@Column(name="email")
	private String email;
	@Column(name="phone")
	private String phone;

	public Supplier() {
		this.id = 0;
		this.dni = "";
		this.name = "";
		this.lastName = "";
		this.dateOfBirth = null;
		this.email = "";
		this.phone = "";
	}
	
	public Supplier(Integer id,String dni, String name, String lastName, LocalDate dateOfBirth, String email, String phone) {
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
