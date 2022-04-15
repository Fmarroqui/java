package model;
import java.time.LocalDate;

public class Customer extends Persona {
	
	private static final long serialVersionUID = -8878748818869622074L;

	public Customer(Integer id,String dni, String name, String lastName, LocalDate dateOfBirth, String mail,
			String phone) {
		super(id, dni, name, lastName, dateOfBirth, mail, phone);
	}
	
	
}
