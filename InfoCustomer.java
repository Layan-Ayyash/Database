package databaseProject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InfoCustomer {
    private IntegerProperty idcustomer;
    private SimpleStringProperty phonecustomer;
    private SimpleStringProperty name;

    public InfoCustomer(int i, String j, String string) {
        this.idcustomer =new SimpleIntegerProperty(i);
        this.phonecustomer = new SimpleStringProperty(j);
        this.name = new SimpleStringProperty(string);
    }

    public IntegerProperty getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(IntegerProperty idcustomer) {
        this.idcustomer = idcustomer;
    }

    public SimpleStringProperty getPhonecustomer() {
        return phonecustomer;
    }

    public void setPhonecustomer(SimpleStringProperty phonecustomer) {
        this.phonecustomer = phonecustomer;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public void setName(SimpleStringProperty name) {
        this.name = name;
    }
  
    public void setphonecustomer(String phonecustomer) {
        this.phonecustomer.set(phonecustomer);
    }

    public void setCustomerId(int updatedCustomerId) {
        this.idcustomer.set(updatedCustomerId);
    }
    public void setname(String name) {
        this.name.set(name);
    }

    public InfoCustomer(String phone, String name) {
        this.phonecustomer = new SimpleStringProperty(phone);
        this.name = new SimpleStringProperty(name);
    }
    public String getName1() {
        return name.get();
    }

	public void setIdcustomer(int updatedId) {
		// TODO Auto-generated method stub
		
	}
}

