package model;
import java.time.LocalDate;

public class Supplier extends Persona{

	private static final long serialVersionUID = -1036597087145494444L;

	public Supplier(Integer id,String dni, String name, String lastName, LocalDate dateOfBirth, String mail,
			String phone) {
		super(id, dni, name, lastName, dateOfBirth, mail, phone);
	}
}
