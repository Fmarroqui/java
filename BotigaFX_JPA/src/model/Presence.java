package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Locale.Category;

@Entity
@Table(name="presence")
public class Presence implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	public static  Locale localitzacioFormat = Locale.getDefault(Category.FORMAT);
	public static DateTimeFormatter dateTimeFormater = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idWorker;
	@Column(name="date")
	private LocalDate date;
	@Column(name="hourEntry")
	private LocalTime hourEntry;
	@Column(name="hourExit")
	private LocalTime hourExit;
	
	public Presence() {
		this.idWorker = 0;
		this.date = null;
		this.hourEntry = null;
		this.hourExit = null;
	}
	
	public Presence(Integer idWorker, LocalDate date, LocalTime hourEntry ) {
		this.idWorker = idWorker;
		this.setDate(date);
		this.setHourEntry(hourEntry);
	}
	
	public Presence(Integer idWorker, LocalDate date, LocalTime hourEntry, LocalTime hourExit ) {
		this.idWorker = idWorker;
		this.setDate(date);
		this.setHourEntry(hourEntry);
		this.setHourExit(hourExit);
	}
	

	public Integer getIdWorker() {
		return idWorker;
	}

	public void setIdWorker(Integer idWorker) {
		this.idWorker = idWorker;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getHourEntry() {
		return hourEntry;
	}

	public void setHourEntry(LocalTime hourEntry) {
		this.hourEntry = hourEntry;
	}

	public LocalTime getHourExit() {
		return hourExit;
	}

	public void setHourExit(LocalTime hourExit) {
		this.hourExit = hourExit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void print() {
		
		System.out.println(
				texts.getString("print.idWorker") + this.idWorker + "\n" +
				texts.getString("print.date") + this.date + "\n" +
				texts.getString("print.hourEntry") + this.hourEntry + "\n" +
				texts.getString("print.hourExit") + this.hourExit+ "\n" );
	
	}
}
