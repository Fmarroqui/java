package model;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.Locale.Category;

public final class Pack extends Product{
	
	private static final long serialVersionUID = 1L;
	private TreeSet<Product> products;
	private double discountRate;
	
	static Locale localitzacioDisplay = Locale.getDefault(Category.DISPLAY);
	static ResourceBundle texts = ResourceBundle.getBundle("vista.Texts", localitzacioDisplay);
	
	public static  Locale localitzacioFormat = Locale.getDefault(Category.FORMAT);
	public static NumberFormat numberFormatter = NumberFormat.getNumberInstance(localitzacioFormat);
	public static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localitzacioFormat);
	public static DateTimeFormatter dateTimeFormater = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
	
	public Pack() {
		this.products = null;
		this.discountRate = 0;
	}


	public Pack(Integer id,String name, double price, Integer stock, LocalDate catalogStartDate,LocalDate catalogEndDate, TreeSet<Product> products, double discountRate) {
		super(id,name, price, stock, catalogStartDate,catalogEndDate);
		this.products = products;
		this.discountRate = discountRate;
	}
	
	public Pack(Integer id,String name, double price, Integer stock, LocalDate catalogStartDate,LocalDate catalogEndDate, double discountRate) {
		super(id,name, price, stock, catalogStartDate,catalogEndDate);
		this.discountRate = discountRate;
	}

	public TreeSet<Product> getProducts() {
		return products;
	}

	public void setProducts(TreeSet<Product> products) {
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

	@Override
	public void print() {
		super.print();
		System.out.println(texts.getString("print.pack")  +"\n");
		this.productsPack();
		System.out.println(texts.getString("print.discount") + this.discountRate +"\n");
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
}
