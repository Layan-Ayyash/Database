package databaseProject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainAddress extends BorderPane {
    private Connection con;
    private ObservableList<InformationAddress> data = FXCollections.observableArrayList();
    private TableView<InformationAddress> tableView;
    private TextField textField1, textField2, textField3;

    
    public MainAddress() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false",
                    "root", "QY932003");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM address");

            tableView = createTableView();
            tableView.setItems(data);

            while (rs.next()) {
                InformationAddress info = new InformationAddress(rs.getString("Phone"), rs.getString("City"), rs.getString("Street"));
                data.add(info);
            }
            rs.close();
            stm.close();
            con.close();

           // BorderPane root = new BorderPane();


            

//            File file = new File("C:\\Users\\Lenovo\\Downloads\\side-view-hands-holding-smartphone.jpg");
//            Image image = new Image(file.toURI().toString());
//            ImageView resizedImageView = new ImageView(image);
//            resizedImageView.setFitWidth(200);
//            resizedImageView.setFitHeight(300);
//            resizedImageView.setPreserveRatio(true);
//            HBox imageBox = new HBox(resizedImageView);
//            imageBox.setAlignment(Pos.CENTER);
//            gridPane.addRow(3, imageBox);
//
//            gridPane.setStyle("-fx-background-color: #FFCCCC;"); // Light Red color

          
            Button addButton = new Button("Add");
            addButton.setOnAction(ev->{
            	Add();
            });
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e->{
            	deleteRecord();
            });
            Button updateButton = new Button("Update");
            updateButton.setOnAction(ev->{
            	handleUpdateButton();
            });

            HBox buttonBox = new HBox(20, addButton, deleteButton, updateButton);
            buttonBox.setAlignment(Pos.CENTER);

         //   hbox.getChildren().addAll(gridPane, tableView);
            setCenter(tableView);
            
            setBottom(buttonBox);
         //   VBox vbox1 = new VBox(30, hbox, buttonBox);
           // vbox1.setStyle("-fx-background-color: #FFCCCC;"); // Light Red color




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TableView<InformationAddress> createTableView() {
        TableView<InformationAddress> tableView = new TableView<>();

        TableColumn<InformationAddress, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().getPhoneAddress());
        TableColumn<InformationAddress, String> cityColumn = new TableColumn<>("City");
        cityColumn.setCellValueFactory(cellData -> cellData.getValue().getCity());

        TableColumn<InformationAddress, String> streetColumn = new TableColumn<>("Street");
        streetColumn.setCellValueFactory(cellData -> cellData.getValue().getStreet());

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(phoneColumn, cityColumn, streetColumn);
        tableView.setEditable(false);
        tableView.setPrefSize(400, 400);

        tableView.setStyle("-fx-background-color: #ECECEC;");

        return tableView;
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-control-inner-background: lavender; -fx-text-fill: black;");

        textField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                textField.setStyle("-fx-control-inner-background: lavender; -fx-text-fill: black;");
            }
        });

        return textField;
    }

    private void Add() {
        try {
            Stage addDialog = new Stage();
            addDialog.initModality(Modality.APPLICATION_MODAL);
            addDialog.setTitle("Add Record");

            VBox addDialogVBox = new VBox(20);
            addDialogVBox.setAlignment(Pos.CENTER);
            addDialogVBox.setPadding(new Insets(20));

            Label label1 = new Label("Phone:");
            Label label2 = new Label("City:");
            Label label3 = new Label("Street");

            label1.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            label2.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            label3.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

            TextField t = new TextField();
            TextField t1 = new TextField();
            TextField t2 = new TextField();

            t1.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
            t2.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
            t.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");

            Button addButtonInDialog = new Button("Add");
            addButtonInDialog.setStyle("-fx-background-color: lightseagreen; -fx-text-fill: white; -fx-font-size: 16px;");
            Label notFoundLabel = new Label("");
            notFoundLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14;");

            addButtonInDialog.setOnAction(event -> {
                try {
                    String phone = t.getText();
                    String city = t1.getText();
                    String street = t2.getText();

                    if (!codeExistsInAddress(phone)) {
                        String insertQuery = String.format(
                                "INSERT INTO address (Phone, City, Street) VALUES (%s, '%s', '%s')",
                                phone, city, street);

                        System.out.println("Executing query: " + insertQuery);
                        executeStatement(insertQuery);

                        InformationAddress newRecord = new InformationAddress(phone, city, street);
                        data.add(newRecord);
                        tableView.setItems(data);
                        System.out.println("Record added successfully!");

                        addDialog.close();
                    } else {
                        notFoundLabel.setText("Phone already exists.");

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Phone Exists");
                        alert.setHeaderText("Duplicate Phone");
                        alert.setContentText("The specified phone already exists. Please choose a different phone.");
                        alert.showAndWait();
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                } catch (SQLException e) {
					e.printStackTrace();
				}
            });

            addDialogVBox.getChildren().addAll(label1, t, label2, t1, label3, t2, addButtonInDialog, notFoundLabel);
            Scene addDialogScene = new Scene(addDialogVBox, 350, 350);
            addDialog.setScene(addDialogScene);

            addDialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean codeExistsInAddress(String phone) {
        String query = String.format("SELECT * FROM address WHERE Phone = %s", phone);

        try (ResultSet resultSet = executeQuery(query)) {
            return resultSet.next(); // Returns true if a row is found, indicating the phone exists
        } catch (SQLException e) {
            // Replace the exception handling with a print statement
            System.out.println("Error checking phone existence in address: " + e.getMessage());
            return false;  // Assuming false in case of an error
        }
    }
    private ResultSet executeQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false", "root", "QY932003");
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }


    private void handleUpdateButton() {
        try {
            InformationAddress selectedInfo = tableView.getSelectionModel().getSelectedItem();

            if (selectedInfo != null) {
                Stage updateDialog = new Stage();
                updateDialog.initModality(Modality.APPLICATION_MODAL);
                updateDialog.setTitle("Update Record");

                VBox updateDialogVBox = new VBox(20);
                updateDialogVBox.setAlignment(Pos.CENTER);
                updateDialogVBox.setPadding(new Insets(20));

                Label label1 = new Label(" Phone");
                Label label2 = new Label(" City");
                Label label3 = new Label("Street");

                label1.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
                label2.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
                label3.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

                TextField field1 = new TextField();
                TextField field2 = new TextField();
                TextField field3 = new TextField();

                field1.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
                field2.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
                field3.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");

                Button updateButtonInDialog = new Button("Update");
                updateButtonInDialog.setStyle("-fx-background-color: lightseagreen; -fx-text-fill: white; -fx-font-size: 16px;");

                updateButtonInDialog.setOnAction(event -> {
                    try {
                        String updatedId = field1.getText();
                        String city = field2.getText();
                        String street = field3.getText();

                        // Use the updatedId in the UPDATE query
                        String updateQuery = String.format(
                                "UPDATE address SET phone = %s, city = '%s', street = '%s' WHERE phone = %s",
                                updatedId, city, street, selectedInfo.getPhoneAddress());

                        System.out.println("Executing query: " + updateQuery);
                        executeStatement(updateQuery);

                        selectedInfo.setPhoneAddress(updatedId);
                        selectedInfo.setCity(city);
                        selectedInfo.setStreet(street);

                        tableView.refresh();

                        updateDialog.close();
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    } catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                });

                updateDialogVBox.getChildren().addAll(label1, field1, label2, field2, label3, field3, updateButtonInDialog);

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

    private void deleteRecord() {
        InformationAddress selectedRecord = tableView.getSelectionModel().getSelectedItem();

        if (selectedRecord != null) {
            Stage deleteDialog = new Stage();
            deleteDialog.initModality(Modality.APPLICATION_MODAL);
            deleteDialog.setTitle("Delete Record");

            VBox deleteDialogVBox = new VBox(20);
            deleteDialogVBox.setAlignment(Pos.CENTER);
            deleteDialogVBox.setPadding(new Insets(20));

            Label confirmationLabel = new Label("Are you sure you want to delete this record?");
            confirmationLabel.setStyle("-fx-font-weight: bold; -fx-font-family: 'Arial Black'; -fx-font-size: 16px;");

            Button deleteButtonInDialog = new Button("Delete");
            deleteButtonInDialog.setStyle("-fx-background-color: lightcoral; -fx-font-size: 16px; -fx-pref-width: 100px;");

            deleteButtonInDialog.setOnAction(event -> {
                try {
                	String deleteQuery = String.format("DELETE FROM address WHERE phone = %s", selectedRecord.getPhoneAddress());
                    executeStatement(deleteQuery);

                    data.remove(selectedRecord);
                    tableView.refresh();

                    System.out.println("Record deleted successfully!");

                    deleteDialog.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            deleteDialogVBox.getChildren().addAll(confirmationLabel, deleteButtonInDialog);

            Scene deleteDialogScene = new Scene(deleteDialogVBox, 450, 200);
            deleteDialog.setScene(deleteDialogScene);

            deleteDialog.showAndWait();
        }
    }

    private void executeStatement(String query) throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false", "root", "QY932003");
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();  // Print the exception details
            System.out.println("SQL statement is not executed!");
        }
    }


    
}
