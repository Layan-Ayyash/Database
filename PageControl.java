/*package databaseProject;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PageControl {
@FXML
Pane p1 ;
@FXML
Pane p2;
@FXML 
Pane pMainStore ;

@FXML
Button btImport;
@FXML
Pane pImport;
@FXML
Pane pPurchases;

public void MainPage() throws IOException {
	System.out.println("Hello");
	Main.page2("f2.fxml");
}
public void saless() throws IOException {
	FXMLLoader fxmlloader2 = new FXMLLoader(Main.class.getResource("Saless.fxml"));
	Scene scene2 =new Scene(fxmlloader2.load());
	Main.stg.setScene(scene2);
}
public void PurchasesPage() throws IOException {
	FXMLLoader fxmlloader2 = new FXMLLoader(Main.class.getResource("Purchases.fxml"));
	Scene scene2 =new Scene(fxmlloader2.load());
	Main.stg.setScene(scene2);
}
public void EmployeesPage() throws IOException {
	FXMLLoader fxmlloader2 = new FXMLLoader(Main.class.getResource("Employees.fxml"));
	Scene scene2 =new Scene(fxmlloader2.load());
	Main.stg.setScene(scene2);
}
public void ItemPage() throws IOException {
	FXMLLoader fxmlloader2 = new FXMLLoader(Main.class.getResource("Item.fxml"));
	Scene scene2 =new Scene(fxmlloader2.load());
	Main.stg.setScene(scene2);
}

public void purchases() {
	System.out.println("Hello");
	Purchases p =new Purchases();
	pPurchases.getChildren().add(p);
	//Scene scene =new Scene(p,1000,600);
	//Main.stg.setScene(scene);
}
public void importTruck() {
	System.out.println("Hello");
	ImportTruck p =new ImportTruck();
	pImport.getChildren().add(p);

}
public void address() {
	System.out.println("Hello");
	MainAddress m =new MainAddress();
	p2.getChildren().add(m);
}
public void aluminum() {
	//Aluminum a =new Aluminum();
	Scene scene =new Scene(a,1000,600);
	Main.stg.setScene(scene);
}
public void orderdetails() {
	OrderDetails a =new OrderDetails();
	Scene scene =new Scene(a,1000,600);
	Main.stg.setScene(scene);
}
public void employee() {
	Employee a =new Employee();
	Scene scene =new Scene(a,1000,600);
	Main.stg.setScene(scene);
}
public void customer() {
	MainCustomer a =new MainCustomer();
	p1.getChildren().add(a);
}
public void salesOrder() {
	MainnSales_order a =new MainnSales_order();
	Scene scene =new Scene(a,1000,600);
	Main.stg.setScene(scene);
}
public void mainstore() {
	System.out.println("KK");
	MainStore a =new MainStore();
	pMainStore.getChildren().add(a);
	
}
public void order() {
	OrderDetails a =new OrderDetails();
	Scene scene =new Scene(a,1000,600);
	Main.stg.setScene(scene);
}


}*/
