package databaseProject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class InfoOrderDetails {
    private IntegerProperty aluminumId;
    private IntegerProperty orderId;
    private IntegerProperty quantity;
    private DoubleProperty totalPrice;
    private DoubleProperty totalWeight;

    public InfoOrderDetails(int aluminumId, int orderId, int quantity, double totalPrice, double totalWeight) {
        this.aluminumId = new SimpleIntegerProperty(aluminumId);
        this.orderId = new SimpleIntegerProperty(orderId);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.totalWeight = new SimpleDoubleProperty(totalWeight);
    }

    public int getAluminumId() {
        return aluminumId.get();
    }

    public void setAluminumId(int aluminumId) {
        this.aluminumId.set(aluminumId);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public double getTotalWeight() {
        return totalWeight.get();
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight.set(totalWeight);
    }

	public void setAluminumId(IntegerProperty aluminumId) {
		this.aluminumId = aluminumId;
	}

	public void setOrderId(IntegerProperty orderId) {
		this.orderId = orderId;
	}

	public void setQuantity(IntegerProperty quantity) {
		this.quantity = quantity;
	}

	public void setTotalPrice(DoubleProperty totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setTotalWeight(DoubleProperty totalWeight) {
		this.totalWeight = totalWeight;
	}

    public IntegerProperty getaluminumId() {
        return aluminumId;
    }
    public IntegerProperty getorderId() {
        return orderId;
    } 
    public IntegerProperty getquantity() {
        return  quantity;
    }
    public DoubleProperty gettotalPrice() {
        return  totalPrice;
    } public DoubleProperty gettotalWeight() {
        return  totalWeight;
    }
    
}
