package databaseProject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InfoMainStore {
	private IntegerProperty codeNumber;
	private IntegerProperty quantity;

	public InfoMainStore(IntegerProperty codeNumber, IntegerProperty quantity) {
		this.codeNumber = codeNumber;
		this.quantity = quantity;
	}

	public InfoMainStore(int codeNumber, int quantity) {
		this.codeNumber = new SimpleIntegerProperty(codeNumber);
		this.quantity = new SimpleIntegerProperty(quantity);
	}

	public IntegerProperty getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(IntegerProperty codeNumber) {
		this.codeNumber = codeNumber;
	}



	public IntegerProperty getQuantity() {
		return quantity;
	}

	public void setQuantity(IntegerProperty quantity) {
		this.quantity = quantity;
	}

	public void setCodeNumber(int codeNumber) {
		this.codeNumber.set(codeNumber);
	}

	public IntegerProperty codeNumberProperty() {
		return codeNumber;
	}



	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
	}

	public IntegerProperty quantityProperty() {
		return quantity;
	}
}
