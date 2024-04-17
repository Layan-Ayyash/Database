package databaseProject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InfoImportTruck {
	 private IntegerProperty numberI;
	 private IntegerProperty aluminumId;
	 private StringProperty idate ;
	 private DoubleProperty weight;
	 private IntegerProperty quantity;
	    

	    public InfoImportTruck(int numberI,int aluminumId,double weight,String idate,int quantity){
	        this.numberI = new SimpleIntegerProperty(numberI);
	        this.aluminumId = new SimpleIntegerProperty(aluminumId);
	        this.idate = new SimpleStringProperty(idate);
	        this.weight = new SimpleDoubleProperty(weight);
	        this.quantity = new SimpleIntegerProperty(quantity);

	    }

	    // Getters and setters for the properties

	    public IntegerProperty numberProperty() {
	        return numberI;
	    }

	    public int getNumber() {
	        return numberI.get();
	    }
	    
	    public void setNumber(int numberI) {
	        this.numberI.set(numberI);
	    }
	    public IntegerProperty aluminumIdProperty() {
	        return aluminumId;
	    }

	    public int getAluminumId() {
	        return aluminumId.get();
	    }
	    
	    public void setAluminumId(int aluminumId) {
	        this.aluminumId.set(aluminumId);
	    }
	    public StringProperty dateProperty() {
	        return idate;
	    }

	    public String getDate() {
	        return idate.get();
	    }
	    
	    public void setDate(String idate) {
	        this.idate.set(idate);
	    }

	    public DoubleProperty weightProperty() {
	        return weight;
	    }

	    public Double getWeight() {
	        return weight.get();
	    }

	    public void setWeight(double weight) {
	        this.weight.set(weight);
	    }
	    public IntegerProperty quantityProperty() {
	        return quantity;
	    }

	    public Integer getQuantity() {
	        return quantity.get();
	    }

	    public void setQuantity(int quantity) {
	        this.quantity.set(quantity);
	    }

}
