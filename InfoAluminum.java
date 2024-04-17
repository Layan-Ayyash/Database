package databaseProject;

public class InfoAluminum {
	private int codeNumber;
	private double price;
	private String name;
	private String color;

	public InfoAluminum(int codeNumber, double price, String name, String color) {
		this.codeNumber = codeNumber;
		this.price = price;
		this.name = name;
		this.color = color;
	}

	public int getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(int codeNumber) {
		this.codeNumber = codeNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
