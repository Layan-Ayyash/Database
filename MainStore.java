package databaseProject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
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

public class MainStore extends BorderPane {
    private Connection con;
    private ObservableList<InfoMainStore> data = FXCollections.observableArrayList();
    private TextField totalPriceField = new TextField();
    TableView<InfoMainStore> tableView = createTableView();
    TextField textField1 = createTextField();
    TextField textField2 = createTextField();
    TextField textField3 = createTextField();

    

    
    public MainStore() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false",
                    "root", "asdf");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM mainstore");

            while (rs.next()) {
                InfoMainStore info = new InfoMainStore(rs.getInt("codeNumber"),
                        rs.getInt("quantity"));
                data.add(info);
            }
            rs.close();
            stm.close();
            con.close();

            tableView.setItems(data);
            BorderPane root = new BorderPane();
            HBox hbox = new HBox(30);
            hbox.setPadding(new Insets(30));

            ImageView imageView = new ImageView(
                    new Image(new File("C:\\Users\\Lenovo\\Downloads\\market_13374634.png").toURI().toString()));
            imageView.setFitWidth(450);
            imageView.setPreserveRatio(true);

            GridPane gridPane = new GridPane();

            gridPane.addRow(0);

            gridPane.add(tableView, 0, 1);

            gridPane.setStyle("-fx-background-color: lightblue;");


            Button tableViewButton = new Button("Table View");

            Button addButton = new Button("Add");

            Button deleteButton = new Button("Delete");

            Button updateButton = new Button("Update");

            Button refreshButton = new Button("Refresh");

            tableViewButton.setOnAction(event -> {
                if (tableView.isVisible()) {
                    tableView.setVisible(false);
                } else {
                    tableView.setVisible(true);
                }
            });

            addButton.setOnAction(e -> {
                try {
                    Add();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });

            deleteButton.setOnAction(e -> {
                Delete();
            });

            updateButton.setOnAction(e -> {
                Update();
            });

            refreshButton.setOnAction(e -> {
                try {
                    tableView.getItems().clear();

                    con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false", "root", "asdf");
                    Statement s = con.createStatement();
                    ResultSet r = s.executeQuery("SELECT * FROM mainstore");

                    while (r.next()) {
                        InfoMainStore info = new InfoMainStore(r.getInt("codeNumber"),
                                r.getInt("quantity"));
                        data.add(info);
                    }

                    s.close();
                    r.close();
                    con.close();

                    tableView.setItems(data);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            HBox buttonBox = new HBox(30, addButton, deleteButton, updateButton, refreshButton,
                    tableViewButton);
            buttonBox.setAlignment(Pos.CENTER);

            hbox.getChildren().addAll(gridPane, imageView);

            VBox vbox1 = new VBox(50, hbox, buttonBox);
            vbox1.setStyle("-fx-background-color: lightblue;");

            
            setCenter(tableView);
            setBottom(buttonBox);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private TableView<InfoMainStore> createTableView() {
        TableView<InfoMainStore> tableView = new TableView<>();
        tableView.setVisible(false);

        tableView.setPrefSize(200, 600);

        TableColumn<InfoMainStore, Integer> codeNumberColumn = new TableColumn<>("Code Number");
        codeNumberColumn.setCellValueFactory(cellData -> cellData.getValue().codeNumberProperty().asObject());
        codeNumberColumn.setStyle("-fx-background-color: #6495ED; -fx-text-fill: white;");
        codeNumberColumn.setReorderable(false);

        TableColumn<InfoMainStore, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        quantityColumn.setStyle("-fx-background-color: #6495ED; -fx-text-fill: white;");
        quantityColumn.setReorderable(false);

        tableView.getColumns().addAll(codeNumberColumn, quantityColumn);
        tableView.setEditable(false);
        tableView.setStyle("-fx-background-color: #FFFFE0;");
        tableView.setSortPolicy(tv -> false);
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setPercentWidth(100);

        GridPane gridPane = new GridPane();
        gridPane.getColumnConstraints().add(colConstraints);
        gridPane.add(tableView, 0, 0);
        applyColumnStyle(codeNumberColumn);
        applyColumnStyle(quantityColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox tableContainer = new VBox(tableView);
        tableContainer.setStyle("-fx-background-color: #ECECEC;");
        return tableView;
    }

    private void applyColumnStyle(TableColumn<?, ?> column) {
        column.setStyle("-fx-alignment: CENTER-RIGHT; -fx-font-size: 14; -fx-text-fill: #333333;");
    }

    private void clearTextFields() {
        textField1.clear();
        textField2.clear();
        textField3.clear();
    }

  


    private TextField createTextField() {
        TextField textField = new TextField();
        textField.setStyle(
                "-fx-control-inner-background: lavender; " + "-fx-text-fill: black; " + "-fx-font-size: 20px; " +
                        "-fx-pref-height: 100px;");

        return textField;
    }

    private void Delete() {
        InfoMainStore selectedRecord = tableView.getSelectionModel().getSelectedItem();

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
                    String deleteQuery = "DELETE FROM mainstore WHERE codeNumber = " + selectedRecord.getCodeNumber().intValue();
                    int affectedRows = executeStatement(deleteQuery);

                    if (affectedRows > 0) {
                        // Deletion successful
                        data.remove(selectedRecord);
                        tableView.refresh();
                        deleteDialog.close();
                    } else {
                        // Deletion failed
                        Label deletionErrorLabel = new Label("Deletion failed. The record may not exist or an error occurred.");
                        deletionErrorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14;");
                        deleteDialogVBox.getChildren().add(deletionErrorLabel);
                    }
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

    private void Add() throws SQLException {
        Stage addDialog = new Stage();
        addDialog.initModality(Modality.APPLICATION_MODAL);
        addDialog.setTitle("Add Record");

        VBox addDialogVBox = new VBox(20);
        addDialogVBox.setAlignment(Pos.CENTER);
        addDialogVBox.setPadding(new Insets(20));

        Label codeNumberLabel = new Label("Code Number:");
        Label quantityLabel = new Label("Quantity:");
        codeNumberLabel.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        quantityLabel.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        TextField codeNumberField = new TextField();
        TextField quantityField = new TextField();
        codeNumberField.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
        quantityField.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");

        Button addButtonInDialog = new Button("Add");
        addButtonInDialog.setStyle("-fx-background-color: lightseagreen; -fx-text-fill: white; -fx-font-size: 16px;");
        Label notFoundLabel = new Label("");
        notFoundLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14;");

        addButtonInDialog.setOnAction(event -> {
            try {
                int codeNumber = Integer.parseInt(codeNumberField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                if (codeExistsInAluminum(codeNumber)) {
                    String updateQuery = String.format(
                            "UPDATE mainstore SET quantity = quantity + %d WHERE codeNumber = %d",
                            quantity, codeNumber);
                    System.out.println("Executing query: " + updateQuery);
                    executeStatement(updateQuery);
                } else {
                    notFoundLabel.setText("Code not found in aluminum. Adding to  aluminum");

                    
                    addDialogVBox.getChildren().addAll(notFoundLabel);

                    addDialog.close();
                }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
			}
        });

        addDialogVBox.getChildren().addAll(codeNumberLabel, codeNumberField, quantityLabel, quantityField, addButtonInDialog, notFoundLabel);
        Scene addDialogScene = new Scene(addDialogVBox, 350, 350);
        addDialog.setScene(addDialogScene);

        addDialog.showAndWait();
    }

    private boolean codeExistsInAluminum(int codeNumber) {
        String query = String.format("SELECT * FROM aluminum WHERE codeNumber = %d", codeNumber);

        try (ResultSet resultSet = executeQuery(query)) {
            return resultSet.next(); 
        } catch (SQLException e) {
            System.out.println("Error checking code existence in aluminum: " + e.getMessage());
            return false;  
        }
    }


    private void Update() {
        InfoMainStore selectedRecord = tableView.getSelectionModel().getSelectedItem();

        if (selectedRecord != null) {
            Stage updateDialog = new Stage();
            updateDialog.initModality(Modality.APPLICATION_MODAL);
            updateDialog.setTitle("Update Record");

            VBox updateDialogVBox = new VBox(20);
            updateDialogVBox.setAlignment(Pos.CENTER);
            updateDialogVBox.setPadding(new Insets(20));

            Label codeNumberLabel = new Label("Code Number:");
            Label quantityLabel = new Label("Quantity:");
            codeNumberLabel.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            quantityLabel.setStyle("-fx-background-color: lightcoral; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            TextField codeNumberField = new TextField();
            TextField quantityField = new TextField();
            codeNumberField.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
            quantityField.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: #333333; -fx-font-size: 16px;");
            Button updateButtonInDialog = new Button("Update");
            updateButtonInDialog.setStyle("-fx-background-color: lightcoral; -fx-font-size: 16px; -fx-pref-width: 100px;");

            updateButtonInDialog.setOnAction(event -> {
                try {
                    int codeNumber = Integer.parseInt(codeNumberField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());

                    String updateQuery = String.format(
                            "UPDATE mainstore SET quantity = %d WHERE codeNumber = %d",
                            quantity, codeNumber);

                    System.out.println("Executing query: " + updateQuery);
                    executeStatement(updateQuery);

                    selectedRecord.setQuantity(quantity);

                    tableView.refresh();
                    clearTextFields();

                    updateDialog.close();
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                }
            });

            updateDialogVBox.getChildren().addAll(codeNumberLabel, codeNumberField, quantityLabel, quantityField, updateButtonInDialog);

            Scene updateDialogScene = new Scene(updateDialogVBox, 350, 350);
            updateDialog.setScene(updateDialogScene);

            updateDialog.showAndWait();
        }
    }


private int executeStatement(String query) throws SQLException {
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false", "root", "asdf");
         Statement stmt = con.createStatement()) {
        stmt.executeUpdate(query);
    } catch (SQLException e) {
        e.printStackTrace();  
        System.out.println("SQL statement is not executed!");
        throw e;
    }
	return 0;
}

private ResultSet executeQuery(String query) throws SQLException {
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?verifyServerCertificate=false", "root", "QY932003");
    Statement statement = connection.createStatement();
    return statement.executeQuery(query);
}

}

