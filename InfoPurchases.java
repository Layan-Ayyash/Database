package databaseProject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InfoPurchases {
	 private IntegerProperty num;
	    private DoubleProperty price;
	    

	    public InfoPurchases(int num, double price) {
	        this.num = new SimpleIntegerProperty(num);
	        this.price = new SimpleDoubleProperty(price);
	    }

	    // Getters and setters for the properties

	    public IntegerProperty NumProperty() {
	        return num;
	    }

	    public int getNum() {
	        return num.get();
	    }

	    public void setNum(int num) {
	        this.num.set(num);
	    }

	    public DoubleProperty priceProperty() {
	        return price;
	    }

	    public Double getPrice() {
	        return price.get();
	    }

	    public void setPrice(double price) {
	        this.price.set(price);
	    }

}
