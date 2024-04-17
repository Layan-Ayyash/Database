package databaseProject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainnSales_order extends Pane {
    private Connection con;
    private ObservableList<InformationSales> data = FXCollections.observableArrayList();
    private TextField totalPriceField = new TextField();
    private TableView<InformationSales> tableView;


    
    public MainnSales_order() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false",
                    "root", "asdf");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM sales_order");

            tableView = createTableView();

            while (rs.next()) {
                InformationSales info = new InformationSales(rs.getInt("ID"), rs.getInt("CustomerID"), rs.getString("SDate"));
                data.add(info);
            }
            rs.close();
            stm.close();
            con.close();
            tableView.setItems(data);

            BorderPane root = new BorderPane();
            HBox hbox = new HBox(30);
            hbox.setPadding(new Insets(30));

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            VBox vbox = new VBox(10); // Use VBox for vertical alignment

            gridPane.addRow(0, vbox);

            File file = new File("C:\\Users\\Lenovo\\Downloads\\growth_1655140.png");
            Image image = new Image(file.toURI().toString());
            ImageView resizedImageView = new ImageView(image);
            resizedImageView.setFitWidth(200);
            resizedImageView.setFitHeight(300);
            resizedImageView.setPreserveRatio(true);
            HBox imageBox = new HBox(resizedImageView);
            imageBox.setAlignment(Pos.CENTER);
            gridPane.addRow(3, imageBox);

            gridPane.setStyle("-fx-background-color: #ADD8E6;"); // Light Blue color

            Button mainPageButton = new Button("Main Page");
            mainPageButton.setStyle("-fx-background-color: #FFCCCC; -fx-text-fill: black; -fx-font-size: 14;");

            Button addButton = new Button("Add");
            addButton.setStyle("-fx-background-color: #FFCCCC; -fx-text-fill: black; -fx-font-size: 14;");

            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #FFCCCC; -fx-text-fill: black; -fx-font-size: 14;");

            Button updateButton = new Button("Update");
            updateButton.setStyle("-fx-background-color: #FFCCCC; -fx-text-fill: black; -fx-font-size: 14;");

            mainPageButton.setPrefSize(100, 100);
            addButton.setPrefSize(100, 100);
            deleteButton.setPrefSize(100, 100);
            updateButton.setPrefSize(100, 100);

            addButton.setOnAction(e -> handleAddButton());

            deleteButton.setOnAction(e -> {
                deleteRecord();
            });

            updateButton.setOnAction(e -> handleUpdateButton());

            HBox buttonBox = new HBox(20, mainPageButton, addButton, deleteButton, updateButton);
            buttonBox.setAlignment(Pos.CENTER);

            hbox.getChildren().addAll(gridPane, tableView);

            VBox vbox1 = new VBox(30, hbox, buttonBox);
            vbox1.setStyle("-fx-background-color: #ADD8E6;"); // Light Blue color
            getChildren().add(vbox1);
           // Scene scene = new Scene(vbox1, 800, 600);

           // primaryStage.setScene(scene);
           // primaryStage.setResizable(false);

          //  primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TableView<InformationSales> createTableView() {
        TableView<InformationSales> tableView = new TableView<>();

        TableColumn<InformationSales, Number> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId());

        TableColumn<InformationSales, Number> customerIdColumn = new TableColumn<>("Customer ID");
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerId());

        TableColumn<InformationSales, String> sDateColumn = new TableColumn<>("SDate");
        sDateColumn.setCellValueFactory(cellData -> cellData.getValue().sDateProperty());

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(idColumn, customerIdColumn, sDateColumn);
        tableView.setEditable(false);
        tableView.setPrefSize(500, 500);

        tableView.setStyle("-fx-background-color: white;");

        return tableView;
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-control-inner-background: white; -fx-text-fill: white;");

        textField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                textField.setStyle("-fx-control-inner-background: white; -fx-text-fill: black;");
            }
        });

        return textField;
    }

    private void handleAddButton() {
        try {
            Stage addDialog = new Stage();
            addDialog.initModality(Modality.APPLICATION_MODAL);
            addDialog.setTitle("Add Record");

            VBox addDialogVBox = new VBox(20);
            addDialogVBox.setAlignment(Pos.CENTER);
            addDialogVBox.setPadding(new Insets(20));

            Label label = new Label("ID");
            Label label2 = new Label("Customer Id");
            Label label4 = new Label("Date");

            label.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold;");
            label4.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold;");

            label2.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold;");
            TextField field = new TextField();
            TextField field1 = new TextField();
            TextField field11 = new TextField();

            field.setStyle("-fx-control-inner-background: white; -fx-text-fill: black; -fx-font-size: 16px;");
            field1.setStyle("-fx-control-inner-background: white; -fx-text-fill: black; -fx-font-size: 16px;");

            Button addButtonInDialog = new Button("Add");
            addButtonInDialog.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16px;");

            addButtonInDialog.setOnAction(event -> {
                try {
                    int f = Integer.parseInt(field.getText());
                    int f1 = Integer.parseInt(field1.getText());
                    String f2 = field11.getText();

                    // Create a new record locally
                    InformationSales newRecord = new InformationSales(f, f1, f2);

                    // Add the new record to the ObservableList
                    data.add(newRecord);
                    tableView.setItems(data);

                    // Insert the new record into the database
                    insertSalesRecordToDatabase(f, f1, f2);

                    // Close the dialog
                    addDialog.close();
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            });

            addDialogVBox.getChildren().addAll(label, field, label2, field1, label4, field11, addButtonInDialog);

            Scene addDialogScene = new Scene(addDialogVBox, 350, 350);
            addDialog.setScene(addDialogScene);

            addDialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertSalesRecordToDatabase(int id, int customerId, String date) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/YourDatabaseName?verifyServerCertificate=false",
                    "root", "1882017");

            String insertQuery = String.format("INSERT INTO sales (ID, CustomerId, Date) VALUES (%d, %d, '%s')", id, customerId, date);
            executeStatement1(insertQuery);

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeStatement1(String query) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/YourDatabaseName?verifyServerCertificate=false",
                "root", "1882017");
        Statement stm = con.createStatement();
        stm.executeUpdate(query);
        stm.close();
        con.close();
    }

    private void handleUpdateButton() {
        try {
            InformationSales selectedInfo = tableView.getSelectionModel().getSelectedItem();

            if (selectedInfo != null) {
                Stage updateDialog = new Stage();
                updateDialog.initModality(Modality.APPLICATION_MODAL);
                updateDialog.setTitle("Update Record");

                VBox updateDialogVBox = new VBox(20);
                updateDialogVBox.setAlignment(Pos.CENTER);
                updateDialogVBox.setPadding(new Insets(20));

                Label label = new Label("ID");
                Label label2 = new Label("Customer Id");
                Label label4 = new Label("Date");

                label.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold;");
                label2.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold;");
                label4.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold;");
                TextField field = new TextField();
                TextField field1 = new TextField();
                TextField field11 = new TextField();

                field.setStyle("-fx-control-inner-background: white; -fx-text-fill: black; -fx-font-size: 16px;");
                field1.setStyle("-fx-control-inner-background: white; -fx-text-fill: black; -fx-font-size: 16px;");

                Button updateButtonInDialog = new Button("Update");
                updateButtonInDialog.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16px;");

                updateButtonInDialog.setOnAction(event -> {
                    try {
                        int updatedId = Integer.parseInt(field.getText());
                        int updatedCustomerId = Integer.parseInt(field1.getText());
                        String updatedSDate = field11.getText();

                        // Update the selected record locally
                        selectedInfo.setId(updatedId);
                        selectedInfo.setCustomerId(updatedCustomerId);
                        selectedInfo.setsDate(updatedSDate);

                        // Update the record in the database
                        updateSalesRecordInDatabase(updatedId, updatedCustomerId, updatedSDate);

                        // Refresh the table view
                        tableView.refresh();

                        // Close the dialog
                        updateDialog.close();
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                });

                updateDialogVBox.getChildren().addAll(label, field, label2, field1, label4, field11, updateButtonInDialog);

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

    private void updateSalesRecordInDatabase(int id, int customerId, String date) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/YourDatabaseName?verifyServerCertificate=false",
                    "root", "1882017");

            String updateQuery = String.format(
                    "UPDATE sales SET CustomerId = %d, Date = '%s' WHERE ID = %d",
                    customerId, date, id);

            executeStatement1(updateQuery);

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteRecord() {
        InformationSales selectedRecord = tableView.getSelectionModel().getSelectedItem();

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
            deleteButtonInDialog.setStyle("-fx-background-color: white; -fx-font-size: 16px; -fx-pref-width: 100px;");

            deleteButtonInDialog.setOnAction(event -> {
                try {
                    String deleteQuery = "DELETE FROM sales_order WHERE ID = " + selectedRecord.getId();
                    executeStatement1(deleteQuery);

                    data.remove(selectedRecord);
                    tableView.refresh();

                    deleteDialog.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            deleteDialogVBox.getChildren().addAll(confirmationLabel, deleteButtonInDialog);

            Scene deleteDialogScene = new Scene(deleteDialogVBox, 450, 200);
            deleteDialog.setScene(deleteDialogScene);

            deleteDialog.showAndWait();
        }
    }

}
