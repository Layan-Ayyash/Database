package databaseProject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ImportTruck extends BorderPane{
	private Connection con;
    private ArrayList<InfoImportTruck> data;
    private ArrayList<InfoImportTruck> data1;

    private TableView<InfoImportTruck> tableView;
    private TextField numberTextField,aluminumTextField,dateTextField, weightTextField,quantityTextField;
    public ImportTruck() {
       // BorderPane root = new BorderPane();

      //  Scene scene = new Scene(root, 1000, 600);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?verifyServerCertificate=false",
                    "root", "شيسب");
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM ImportTruck");

            data = new ArrayList<>();
            data1 = new ArrayList<>();
            tableView = new TableView<>();

            TableColumn<InfoImportTruck, Number> numberColumn = new TableColumn<>("Number");
            numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());

            TableColumn<InfoImportTruck, Number> aluminumColumn = new TableColumn<>("AluminumId");
            aluminumColumn.setCellValueFactory(cellData -> cellData.getValue().aluminumIdProperty());
            
            TableColumn<InfoImportTruck, Number> weightColumn = new TableColumn<>("Weight");
            weightColumn.setCellValueFactory(cellData -> cellData.getValue().weightProperty());
            
            TableColumn<InfoImportTruck, String> dateColumn = new TableColumn<>("Date");
            dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        
            
            TableColumn<InfoImportTruck, Number> quantityColumn = new TableColumn<>("Quantity");
            quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
//            tableView.getColumns().addAll(numberColumn, aluminumColumn,dateColumn,weightColumn,quantityColumn);
            tableView.getColumns().addAll(numberColumn, aluminumColumn,weightColumn,dateColumn,quantityColumn);
            ObservableList<InfoImportTruck> observableData = FXCollections.observableList(data);

            tableView.setItems(observableData);

            setCenter(tableView);

            setupControls(this);
            
           // stage.setScene(scene);
            //stage.show();

            while (rs.next()) {
            	InfoImportTruck info = new InfoImportTruck(rs.getInt("numberI"),rs.getInt("aluminumId"), rs.getDouble("weight"),rs.getString("idate"),rs.getInt("quantity"));
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
        
       // numberTextField,aluminumTextField,dateTextField, weightTextField,quantityTextField
        numberTextField = new TextField();
        numberTextField.setPromptText("Number");
        
        aluminumTextField = new TextField();
        aluminumTextField.setPromptText("aluminumId");
        
        weightTextField = new TextField();
        weightTextField.setPromptText("weight");
        
        dateTextField = new TextField();
        dateTextField.setPromptText("date");
        
        quantityTextField = new TextField();
        quantityTextField.setPromptText("quantity");
        

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
        	try {
        		
				e(0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e ->

        {
			try {
				deleteRecord();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
        		);

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> Refresh());

        Button clearAllButton = new Button("Clear All");
        clearAllButton.setOnAction(e -> clearAllFields());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
        	try {
				e(1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        
      

        inputBox.getChildren().addAll(addButton,deleteButton, refreshButton, clearAllButton, updateButton);

        root.setBottom(inputBox);
    }

    private void addRecord(int number,int AluminumId,double weight,String date,int quantity) throws SQLException {
        try {
            
                

                String insertQuery = String.format(
                        "INSERT INTO ImportTruck (numberI,aluminumId,weight,idate, quantity) VALUES (%d,%d,%f,%s,%d)",
                        number, AluminumId,weight,date,quantity);
                executeStatement(insertQuery);

                InfoImportTruck info = new InfoImportTruck(number, AluminumId,weight,date,quantity);
                data.add(info);

                ObservableList<InfoImportTruck> updatedData = FXCollections.observableList(data);
                tableView.setItems(updatedData);
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    private void deleteRecord() throws IOException {
    	InfoImportTruck selectedRecord = tableView.getSelectionModel().getSelectedItem();

        if (selectedRecord != null) {
            try {
                String deleteQuery = "DELETE FROM ImportTruck WHERE numberI = " + selectedRecord.getNumber();
                executeStatement(deleteQuery);

                data.remove(selectedRecord);
                tableView.refresh();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
        	e(2);
        }
    }
    private void deleteRecord(int x) {

        
            try {
                String deleteQuery = "DELETE FROM ImportTruck WHERE numberI = " + x;
                executeStatement(deleteQuery);

                data.remove(x);
                tableView.refresh();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        
    }
   
    private void updateSelectedRow(double weight,String date,int quantity) {

    	InfoImportTruck selectedRecord = tableView.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            try {
                int number = selectedRecord.getNumber();

                String updateQuery = String.format(
                        "UPDATE ImportTruck SET weight = '%f',idate = '%s',quantity = '%d', WHERE number = %d",  weight,date,quantity,number);
                executeStatement(updateQuery);

                selectedRecord.setWeight(weight);
                selectedRecord.setDate(date);
                selectedRecord.setQuantity(quantity);
                

                ObservableList<InfoImportTruck> updatedData = FXCollections.observableList(data);

                tableView.setItems(updatedData);
                tableView.refresh();
                

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void updateRecordInTableView(InfoImportTruck updatedInfo) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            data.set(selectedIndex, updatedInfo);

            tableView.refresh();
        }
    }
    private void updateRecordInDatabase(InfoImportTruck updatedInfo,int number,double weight,String date,int quantity) {
        try {
        	String updateQuery = String.format(
                    "UPDATE ImportTruck SET weight = '%f',idate = '%s',quantity = '%d', WHERE number = %d",
                    weight,date,quantity,number);
            executeStatement(updateQuery);

            updateRecordInTableView(updatedInfo);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void clearAllFields() {

        
        data.clear();

        tableView.refresh();
        
    }
    private void executeStatement(String SQL) throws SQLException {
        try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/company?verifyServerCertificate=false", "root", "شيب");
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate(SQL);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL statement is not executed!");
        }
    }
    
    private void e(int n) throws IOException {
    		System.out.println("KK");
    		FXMLLoader fx =new FXMLLoader(getClass().getResource("import.fxml"));
    		
        	Stage stage =new Stage();
        	Scene scene =new Scene(fx.load());
        	scene.getStylesheets().add(getClass().getResource("importAdd.css").toExternalForm());
        	stage.setScene(scene);
        	stage.show() ;
        	int number ,id ,quantity ;
        	double weight ;
        	String date ;
			Label lbl =(Label)fx.getNamespace().get("lbl");
			TextField tfN =(TextField)fx.getNamespace().get("tfN");
			TextField tfA =(TextField)fx.getNamespace().get("tfA");
			TextField tfW =(TextField)fx.getNamespace().get("tfW");
			TextField tfQ =(TextField)fx.getNamespace().get("tfQ");
			ComboBox<String> cmbD =(ComboBox)fx.getNamespace().get("cmbD");
			for(int i=1;i<=31;i++)
				cmbD.getItems().add(i+"");
			cmbD.getSelectionModel().select(0);
			ComboBox<String> cmbM =(ComboBox)fx.getNamespace().get("cmbM");
			for(int i=1;i<=12;i++)
				cmbM.getItems().add(i+"");
			cmbM.getSelectionModel().select(0);
			ComboBox<String> cmbY =(ComboBox)fx.getNamespace().get("cmbY");
			for(int i=2000;i<=2025;i++)
				cmbY.getItems().add(i+"");
			cmbY.getSelectionModel().select(0);
			Button btAdd =(Button)fx.getNamespace().get("btAdd");
			if(n==1) {
				btAdd.setText("Update");
				tfN.setVisible(false);
			}
			btAdd.setOnAction(ev->{
				try {
				if(tfN.getText()!=null && tfA.getText()!=null&&tfW.getText()!=null
    					&&tfQ.getText()!=null) {
    				int id0 =Integer.parseInt(tfA.getText());
    				double weight0 =Double.parseDouble(tfW.getText());
    				String date0 =cmbY.getSelectionModel().getSelectedItem()+"-"+cmbM.getSelectionModel().getSelectedItem()
    						+"-"+cmbD.getSelectionModel().getSelectedItem();
    				int quantity0 =Integer.parseInt(tfQ.getText());
    				if(n==0)
						try {
		    				int number0 =Integer.parseInt(tfN.getText());
							addRecord(number0,id0,weight0,date0,quantity0);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					else if(n==1) {
	    			//	int number0 =Integer.parseInt(tfN.getText());

    					updateSelectedRow(weight0,date0,quantity0);
					}
					else if(n==2) {
						btAdd.setText("Delete");
						tfW.setVisible(false);
						tfQ.setVisible(false);
						tfA.setVisible(false);
						cmbD.setVisible(false);
						cmbY.setVisible(false);
						cmbM.setVisible(false);
						deleteRecord(Integer.parseInt(tfN.getText()));


					}
    			}
    			else {
    				lbl.setText("Empty field");
    			}
				stage.close();
				}catch(Exception e) {
    				lbl.setText("Enter a vaild value for aluminum ID");
				}
			});
    	}
    
    
}
