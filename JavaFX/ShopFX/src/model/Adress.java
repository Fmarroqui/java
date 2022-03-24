package model;
public class Adress { 
	
	private String houseNumber;
	private String streetName;
	private String postalCode;
	private String city;
	private String province;
	private String country;
	
	public Adress(String houseNumber, String streetName, String postalCode, String city, String province, String country) {
		this.houseNumber=houseNumber;
		this.streetName=streetName;
		this.postalCode=postalCode;
		this.city=city;
		this.province=province;
		this.country=country;
	}
	
	public String toString() {
		return " Adress [houseNumber=" + this.houseNumber + ", streetName=" + this.streetName + ", postalCode=" + this.postalCode
				+ ", city=" + this.city + ", province=" + this.province + ", country=" + this.country + "] ";
	}
}
