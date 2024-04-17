package databaseProject;

import javafx.beans.property.*;

public class InformationSales {
	 private SimpleStringProperty sDate;
	    private IntegerProperty id;
	    private SimpleIntegerProperty customerId;
	


	    public InformationSales(int id, int customerId, String sDate) {
	        this.id = new SimpleIntegerProperty(id);
	        this.customerId = new SimpleIntegerProperty(customerId);
	        this.sDate = new SimpleStringProperty(sDate);
	    }
	   

	public void setId(int id) {
        this.id.set(id);
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public void setSDate(String sDate) {
        this.sDate.set(sDate);
    }

    public SimpleStringProperty getsDate() {
		return sDate;
	}
	public void setsDate(SimpleStringProperty sDate) {
		this.sDate = sDate;
	}
	public IntegerProperty getId() {
		return id;
	}
	public void setId(IntegerProperty id) {
		this.id = id;
	}
	public SimpleIntegerProperty getCustomerId() {
		return customerId;
	}
	public void setCustomerId(SimpleIntegerProperty customerId) {
		this.customerId = customerId;
	}
	
    



    public void setsDate(String sDate) {
        this.sDate.set(sDate);
    }

    public SimpleStringProperty sDateProperty() {
        return sDate;
    }
   
  
}
