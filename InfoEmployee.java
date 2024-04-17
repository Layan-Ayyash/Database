package databaseProject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InfoEmployee {
	private int id;
	private StringProperty ename;
	private int age;
	private double salary;
	private StringProperty address;



	public InfoEmployee(int id, String ename, int age, double salary, String address) {
		super();
		this.id = id;
		this.ename = new SimpleStringProperty(ename);
		this.age = age;
		this.salary = salary;
		this.address = new SimpleStringProperty(address);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public StringProperty getEname() {
		return ename;
	}

	public void setEname(StringProperty ename) {
		this.ename = ename;
	}

	public StringProperty getAddress() {
		return address;
	}

	public void setAddress(StringProperty address) {
		this.address = address;
	}
	

}
