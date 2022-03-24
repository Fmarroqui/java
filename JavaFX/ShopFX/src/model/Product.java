package model;
import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale.Category;

public class Product implements Comparable<Product>, Serializable{
	

	private static final long serialVersionUID = 1L;
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	public static  Locale localitzacioFormat = Locale.getDefault(Category.FORMAT);
	public static NumberFormat numberFormatter = NumberFormat.getNumberInstance(localitzacioFormat);
	public static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localitzacioFormat);
	public static DateTimeFormatter dateTimeFormater = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
	
	
	private Integer id;
	private String name;
	private double price;
	private int stock;
	private LocalDate catalogStartDate;
	private LocalDate catalogEndDate;
	
	public Product() {
		this.id = 0;
		this.name = "";
		this.price = 0;
		this.stock = 0;
		this.catalogStartDate = null;
		this.catalogEndDate = null;
	}
	
	public Product(Integer id,String name, double price, int stock, LocalDate catalogStartDate, LocalDate catalogEndDate) {
		this.setId(id);
		this.setName(name);
		this.setPrice(price);
		this.setStock(stock);
		this.setCatalogStartDate(catalogStartDate);
		this.setCatalogEndDate(catalogEndDate);
	}
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public LocalDate getCatalogStartDate() {
		return catalogStartDate;
	}

	public void setCatalogStartDate(LocalDate catalogStartDate) {
		this.catalogStartDate = catalogStartDate;
	}
	
	public LocalDate getCatalogEndDate() {
		return catalogEndDate;
	}

	public void setCatalogEndDate(LocalDate catalogEndDate) {
		this.catalogEndDate = catalogEndDate;
	}

	public void print() {
		Object catalogStartDay = this.catalogStartDate != null?dateTimeFormater.format(this.catalogStartDate):this.catalogStartDate;
		Object catalogEndDay = this.catalogEndDate != null?dateTimeFormater.format(this.catalogEndDate):this.catalogEndDate;
		
		System.out.println(
				texts.getString("print.id") + this.id + "\n" +
				texts.getString("print.name") + this.name + "\n" +
				texts.getString("print.price") + currencyFormatter.format(this.price) + "\n" +
				texts.getString("print.stock") + numberFormatter.format(this.stock) + "\n" +
				texts.getString("print.catalogStartDay") + catalogStartDay + "\n" +
				texts.getString("print.catalogEndDay") + catalogEndDay + "\n" ); 
	
	}
	
	
	public int compareTo(Product obj){

		if(this.id == obj.getId()) {
			return 0;
		}
		
		return 1;
	}
	
}
