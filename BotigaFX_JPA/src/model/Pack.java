package model;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.util.Locale.Category;
@Entity
@Table(name="pack")
public final class Pack{
	
private static final long serialVersionUID = 1L;
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	public static  Locale localitzacioFormat = Locale.getDefault(Category.FORMAT);
	public static NumberFormat numberFormatter = NumberFormat.getNumberInstance(localitzacioFormat);
	public static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localitzacioFormat);
	public static DateTimeFormatter dateTimeFormater = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="price")
	private double price;
	@Column(name="stock")
	private int stock;
	@Column(name="catalogStartDate")
	private LocalDate catalogStartDate;
	@Column(name="catalogEndDate")
	private LocalDate catalogEndDate;
	@ManyToMany(cascade = {
			CascadeType.ALL
			
    })
    @JoinTable(name = "productos_pack",
            joinColumns = @JoinColumn(name = "id_pack"),
            inverseJoinColumns = @JoinColumn(name = "id_producto")
    )
    private List<Product> products = new ArrayList<>();
	
	@Column(name="discountRate")
	private double discountRate;
	
	public Pack() {
		this.id = 0;
		this.name = "";
		this.price = 0;
		this.stock = 0;
		this.catalogStartDate = null;
		this.catalogEndDate = null;
		this.products = null;
		this.discountRate = 0;
	}

	public Pack(Integer id,String name, double price, Integer stock, LocalDate catalogStartDate,LocalDate catalogEndDate, List<Product> products, double discountRate) {
		this.setId(id);
		this.setName(name);
		this.setPrice(price);
		this.setStock(stock);
		this.setCatalogStartDate(catalogStartDate);
		this.setCatalogEndDate(catalogEndDate);
		this.products = products;
		this.discountRate = discountRate;
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


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
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


	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}


	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(float discountRate) {
		this.discountRate = discountRate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void productsPack() {
		if(this.products == null) {
			return;
		}
		System.out.println(texts.getString("print.products"));
		for (Product product : this.products) {
			product.print();
			
		    System.out.println("****************\n");
		}
		
	}
	

	public String getPack() {
		return "Pack [products=" + this.products + ", discountRate=" + this.discountRate + "]";
	}
	
	public boolean equals(String pack ) {
		return this.getPack().equals(pack);
		
	}
	
	public String getProductsIds() {
		StringBuilder ids = new StringBuilder();
		 Iterator<Product> product = products.iterator();
		while (product.hasNext()) {
			ids.append(product.next().getId()+",");
		}
		
		return ids.toString();
            
	}
	
	public void addProduct(Product product) {
        products.add(product);
        product.getPacks().add(this);
    }
    public void removeProduct(Product product) {
    	products.remove(product);
    	product.getPacks().remove(this);
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
				texts.getString("print.catalogEndDay") + catalogEndDay + "\n"); 
		
		System.out.println(texts.getString("print.pack")  +"\n");
		this.productsPack();
		System.out.println(texts.getString("print.discount") + this.discountRate +"\n");
	
	}
}
