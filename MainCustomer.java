package databaseProject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class MainCustomer extends BorderPane {
    private Connection con;
    private TextField totalPriceField = new TextField();
    private ObservableList<InfoCustomer> data1 = FXCollections.observableArrayList();
    TableView<InfoCustomer> customerTableView = createTableView();
   
    

    public MainCustomer() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false",
                    "root", "asdf");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM customer");

            TableView<InfoCustomer> tableView = createTableView();
            tableView.setItems(data1);
            tableView.setPrefSize(400, 400);
            while (rs.next()) {
                InfoCustomer info = new InfoCustomer(rs.getInt("id"), rs.getString("phone"), rs.getString("cname"));
                data1.add(info);
            }
            rs.close();
            stm.close();
            con.close();
            tableView.setItems(data1);

            BorderPane root = new BorderPane();
            HBox hbox = new HBox(30);
            hbox.setPadding(new Insets(10));

            GridPane gridPane = new GridPane();
            gridPane.setHgap(20);
            gridPane.setVgap(10);


            VBox vbox = new VBox(10);
            vbox.getChildren().add(gridPane);

            File file = new File("C:\\Users\\Lenovo\\Downloads\\diversity_4807598.png");
            Image image = new Image(file.toURI().toString());
            ImageView resizedImageView = new ImageView(image);
            resizedImageView.setFitWidth(200);
            resizedImageView.setFitHeight(300);
            resizedImageView.setPreserveRatio(true);
            HBox imageBox = new HBox(resizedImageView);
            imageBox.setAlignment(Pos.CENTER);
            gridPane.addRow(3, imageBox);

            gridPane.setStyle("-fx-background-color: lightblue;");


            Button addButton = new Button("Add");

            Button deleteButton = new Button("Delete");

            Button updateButton = new Button("Update");
           
            addButton.setOnAction(e -> {
				try {
					Add();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});

            deleteButton.setOnAction(e ->handleDeleteButton(tableView) );

            updateButton.setOnAction(e ->handleUpdateButton());

            HBox buttonBox = new HBox(20, addButton, deleteButton, updateButton);
            buttonBox.setAlignment(Pos.CENTER);

            hbox.getChildren().addAll(gridPane, tableView);

           // VBox vbox1 = new VBox(30, hbox, buttonBox);
          //  vbox1.setStyle("-fx-background-color: lightblue;");
            setCenter(tableView);
            setBottom(buttonBox);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TableView<InfoCustomer> createTableView() {
        TableView<InfoCustomer> tableView = new TableView<>();

        TableColumn<InfoCustomer, Number> idcustomer = new TableColumn<>("idcustomer");
        idcustomer.setCellValueFactory(cellData -> cellData.getValue().getIdcustomer());

        TableColumn<InfoCustomer, String> phonecustomer = new TableColumn<>("phonecustomer");
        phonecustomer.setCellValueFactory(cellData -> cellData.getValue().getPhonecustomer());

        TableColumn<InfoCustomer, String> name = new TableColumn<>("name");
        name.setCellValueFactory(cellData -> cellData.getValue().getName());

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(idcustomer, phonecustomer, name);
        tableView.setEditable(false);
        tableView.setPrefSize(500, 500);

        tableView.setStyle("-fx-background-color: #ECECEC;");

        return tableView;
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-control-inner-background: lavender; -fx-text-fill: white;");

        textField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            textField.setStyle("-fx-control-inner-background: lavender; -fx-text-fill: black;");
        });

        return textField;
    }
    private void Add() throws SQLException {
        try {
            Stage addDialog = new Stage();
            addDialog.initModality(Modality.APPLICATION_MODAL);
            addDialog.setTitle("Add Record");

            VBox addDialogVBox = new VBox(20);
            addDialogVBox.setAlignment(Pos.CENTER);
            addDialogVBox.setPadding(new Insets(20));

            Label labelId = new Label("ID:");
            Label labelPhone = new Label("Phone:");
            Label labelName = new Label("Name:");

            labelId.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            labelPhone.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            labelName.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

            TextField idField = new TextField();
            TextField phoneField = new TextField();
            TextField nameField = new TextField();

            idField.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
            phoneField.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
            nameField.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");

            Button addButtonInDialog = new Button("Add");
            addButtonInDialog.setStyle("-fx-background-color: lightseagreen; -fx-text-fill: white; -fx-font-size: 16px;");

            addButtonInDialog.setOnAction(event -> {
            	 try {
            	        int id = Integer.parseInt(idField.getText());
            	        String phone = phoneField.getText();
            	        String name = nameField.getText();

            	        boolean codeExists = codeExistsInAddress(phone);
            	        if (codeExists) {
            	            String insertQuery = String.format(
            	                    "INSERT INTO customer VALUES (%d, %s, %s)",
            	                    id, phone, name);

            	            executeStatement(insertQuery);

            	            InfoCustomer newRecord = new InfoCustomer(id, phone, name);
            	            data1.add(newRecord);
            	            customerTableView.setItems(data1);

            	            System.out.println("Record added successfully!");

            	            addDialog.close();
            	        } else {
            	            Alert alert = new Alert(Alert.AlertType.WARNING);
            	            alert.setTitle("Duplicate Phone");
            	            alert.setHeaderText("Phone already exists");
            	            alert.setContentText("The specified phone already exists in the address table. Please choose a different phone.");
            	            alert.showAndWait();
            	        
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            });

            addDialogVBox.getChildren().addAll(labelId, idField, labelPhone, phoneField, labelName, nameField, addButtonInDialog);
            Scene addDialogScene = new Scene(addDialogVBox, 350, 350);
            addDialog.setScene(addDialogScene);

            addDialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean codeExistsInAddress(String phone) {
        String query = String.format("SELECT * FROM address WHERE phone = %s", phone);

        try (ResultSet resultSet = executeQuery(query)) {
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error checking phone existence in address: " + e.getMessage());
            return false;
        }
    }

    private void handleUpdateButton() {
        try {
            InfoCustomer selectedInfo = customerTableView.getSelectionModel().getSelectedItem();

            if (selectedInfo != null) {
                Stage updateDialog = new Stage();
                updateDialog.initModality(Modality.APPLICATION_MODAL);
                updateDialog.setTitle("Update Record");

                VBox updateDialogVBox = new VBox(20);
                updateDialogVBox.setAlignment(Pos.CENTER);
                updateDialogVBox.setPadding(new Insets(20));

                Label labelId = new Label("ID:");
                Label labelPhone = new Label("Phone:");
                Label labelName = new Label("Name:");

                labelId.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
                labelPhone.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
                labelName.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

                TextField fieldId = new TextField();
                TextField fieldPhone = new TextField();
                TextField fieldName = new TextField();

                fieldId.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
                fieldPhone.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
                fieldName.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");

                Button updateButtonInDialog = new Button("Update");
                updateButtonInDialog.setStyle("-fx-background-color: lightseagreen; -fx-text-fill: white; -fx-font-size: 16px;");

                updateButtonInDialog.setOnAction(event -> {
                    try {
                        int updatedId = Integer.parseInt(fieldId.getText());
                        String updatedPhone = fieldPhone.getText();
                        String updatedName = fieldName.getText();

                        // Use the updatedId in the UPDATE query
                        String updateQuery = String.format(
                                "UPDATE customer SET id = %d, phone = %s, cname = '%s' WHERE phone = %s",
                                updatedId, updatedPhone, updatedName, selectedInfo.getPhonecustomer());

                        System.out.println("Executing query: " + updateQuery);
                        executeStatement(updateQuery);

                        selectedInfo.setCustomerId(updatedId);
                        selectedInfo.setphonecustomer(updatedPhone);
                        selectedInfo.setname(updatedName);

                        customerTableView.refresh();

                        updateDialog.close();
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                updateDialogVBox.getChildren().addAll(labelId, fieldId, labelPhone, fieldPhone, labelName, fieldName, updateButtonInDialog);

                Scene updateDialogScene = new Scene(updateDialogVBox, 350, 350);
                updateDialog.setScene(updateDialogScene);

                updateDialog.showAndWait();
            } else {
                System.out.println("Please select a row to update.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean codeExistsIncustomer(String phone) {
        String query = String.format("SELECT * FROM customer WHERE Phone = %s", phone);

        try (ResultSet resultSet = executeQuery(query)) {
            return resultSet.next(); 
        } catch (SQLException e) {
            System.out.println("Error checking phone existence in address: " + e.getMessage());
            return false;  
        }
    }
 
    private ResultSet executeQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false", "root", "QY932003");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
    private void handleDeleteButton(TableView<InfoCustomer> tableView) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            InfoCustomer selectedRecord = tableView.getItems().get(selectedIndex);

            if (deleteRecordFromDatabase(selectedRecord)) {
                tableView.getItems().remove(selectedIndex);
                System.out.println("Record deleted successfully!");
            } else {
                System.out.println("Error deleting record from the database.");
            }
        } else {
            System.out.println("Please select a row to delete.");
        }
    }

    private boolean deleteRecordFromDatabase(InfoCustomer selectedRecord) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false", "root", "asdf");
            Statement statement = connection.createStatement();

            String deleteQuery = String.format("DELETE FROM customer WHERE phone = %s", selectedRecord.getPhonecustomer().get());

            System.out.println("Executing query: " + deleteQuery);

            int rowsAffected = statement.executeUpdate(deleteQuery);

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int executeStatement(String query) throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false", "root", "adsf");
             Statement stmt = con.createStatement()) {

            System.out.println("Executing query: " + query);

            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL statement is not executed!");
            throw e;
        }
        return 0;
    }

private boolean codeExistsInCustomer(String phone) {
    String query = String.format("SELECT * FROM customer WHERE phone = %s", phone);

    try (ResultSet resultSet = executeQuery(query)) {
        return resultSet.next();
    } catch (SQLException e) {
        System.out.println("Error checking phone existence in customer: " + e.getMessage());
        return false;
    }
}

}
