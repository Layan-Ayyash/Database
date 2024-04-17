package databaseProject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InformationAddress {
    private SimpleStringProperty phoneAddress;
    private SimpleStringProperty street;
    private SimpleStringProperty city;


    public SimpleStringProperty getCity() {
		return city;
	}

	public void setCity(String city) {
        this.city.set(city);
	}

	public void setPhoneAddress(SimpleStringProperty phoneAddress) {
		this.phoneAddress = phoneAddress;
	}

	public void setStreet(SimpleStringProperty street) {
		this.street = street;
	}

	public InformationAddress(String phoneAddress, String city, String street) {
        this.phoneAddress = new SimpleStringProperty(phoneAddress);
        this.city = new SimpleStringProperty(city);

        this.street = new SimpleStringProperty(street);
    }

    public SimpleStringProperty getPhoneAddress() {
        return phoneAddress;
    }

    public void setPhoneAddress(String phoneAddress) {
        this.phoneAddress.set(phoneAddress);
    }

    public SimpleStringProperty getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }
}
