package databaseProject;

//import java.awt.Label;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Purchases extends BorderPane{
	private Connection con;
    private ArrayList<InfoPurchases> data;
    private ArrayList<InfoPurchases> data1;

    private TableView<InfoPurchases> tableView;
    private TextField numTextField, priceTextField;
    public Purchases() {
       // BorderPane root = new BorderPane();

      //  Scene scene = new Scene(root, 1000, 600);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?verifyServerCertificate=false",
                    "root", "sadf");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM purchases");

            data = new ArrayList<>();
            data1 = new ArrayList<>();
            tableView = new TableView<>();

            TableColumn<InfoPurchases, Number> idColumn = new TableColumn<>("num");
            idColumn.setCellValueFactory(cellData -> cellData.getValue().NumProperty());

            TableColumn<InfoPurchases, Number> priceColumn = new TableColumn<>("price");
            priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());


            tableView.getColumns().addAll(idColumn, priceColumn);

            ObservableList<InfoPurchases> observableData = FXCollections.observableList(data);

            tableView.setItems(observableData);

            setCenter(tableView);

            setupControls(this);
            
           // stage.setScene(scene);
            //stage.show();

            while (rs.next()) {
                InfoPurchases info = new InfoPurchases(rs.getInt("num"), rs.getDouble("price"));
                data.add(info);
                data1.add(info);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
//    public static void main(String[] args) {
//		launch(args);
//	}
    private void Refresh() {
        data.clear();
        data.addAll(data1);
        tableView.refresh();
    }

    private void setupControls(BorderPane root) {
        HBox inputBox = new HBox(10);
        inputBox.setStyle("-fx-padding: 10;");

        priceTextField = new TextField();
        priceTextField.setPromptText("Price");
        priceTextField.setPrefWidth(50); // Set preferred width
        priceTextField.setPrefHeight(20);
        
        numTextField = new TextField();
        numTextField.setPromptText("num");
        numTextField.setPrefWidth(50); // Set preferred width
        numTextField.setPrefHeight(20);
        

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            try {
                addRecord();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e ->
        
        deleteRecord()
        		);

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> Refresh());

        Button clearAllButton = new Button("Clear All");
        clearAllButton.setOnAction(e -> clearAllFields());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateSelectedRow());
        
        Button sum = new Button("Sum");
        sum.setOnAction(e->{
        	double sum0 =0;
        	for(int i=0;i<data.size();i++)
        		sum0+=data.get(0).getPrice();
        	Text l =new Text();
        	l.setText("sum prices = "+sum0+"");
        	StackPane p =new StackPane();
        	p.getChildren().add(l);
        	Scene sc =new Scene(p);
        	Stage stg =new Stage();
        	stg.setScene(sc);
        	stg.show();
        });
        

        inputBox.getChildren().addAll(priceTextField, numTextField, addButton,
                deleteButton, refreshButton, clearAllButton, updateButton,sum);

        root.setBottom(inputBox);
    }

    private void addRecord() throws SQLException {
        try {
            if (numTextField != null && priceTextField != null ) {
                int num = Integer.parseInt(numTextField.getText());
                double price = Double.parseDouble(priceTextField.getText());
                

                String insertQuery = String.format(
                        "INSERT INTO Purchases (num, price) VALUES (%d, %f)",
                        num, price);
                executeStatement(insertQuery);

                InfoPurchases info = new InfoPurchases(num, price);
                data.add(info);

                ObservableList<InfoPurchases> updatedData = FXCollections.observableList(data);
                tableView.setItems(updatedData);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    private void deleteRecord() {
    	InfoPurchases selectedRecord = tableView.getSelectionModel().getSelectedItem();

        if (selectedRecord != null) {
            try {
                String deleteQuery = "DELETE FROM purchases WHERE num = " + selectedRecord.getNum();
                executeStatement(deleteQuery);

                data.remove(selectedRecord);
                tableView.refresh();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void updateSelectedRow() {

    	InfoPurchases selectedRecord = tableView.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            try {
                int num = selectedRecord.getNum();
                Double price = Double.parseDouble(priceTextField.getText());

                String updateQuery = String.format(
                        "UPDATE purchases SET price = '%f' WHERE num = %d", price,
                        num);
                executeStatement(updateQuery);

                selectedRecord.setPrice(price);
                

                ObservableList<InfoPurchases> updatedData = FXCollections.observableList(data);

                tableView.setItems(updatedData);
                tableView.refresh();
                numTextField.setText("");
                priceTextField.setText("");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void updateRecordInTableView(InfoPurchases updatedInfo) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            data.set(selectedIndex, updatedInfo);

            tableView.refresh();
        }
    }
    private void updateRecordInDatabase(InfoPurchases updatedInfo) {
        try {
            String updateQuery = String.format(
                    "UPDATE purchases SET price = '%f' WHERE num = %d",
                    updatedInfo.getPrice(), updatedInfo.getNum());
            executeStatement(updateQuery);

            updateRecordInTableView(updatedInfo);
            numTextField.setText("");
            priceTextField.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void clearAllFields() {

        priceTextField.clear();
        numTextField.clear();
        data.clear();

        tableView.refresh();
        numTextField.setText("");
        priceTextField.setText("");
    }
    private void executeStatement(String SQL) throws SQLException {
        try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/company?verifyServerCertificate=false", "root", "asdf");
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(SQL);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL statement is not executed!");
        }
    }
    

}

