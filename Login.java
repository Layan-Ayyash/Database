package databaseProject;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {
	
@FXML
	private TextField name ;
@FXML
	private PasswordField password ;
@FXML
	private Button login ;
	
	
	public void logIn()throws IOException {
		
		if(name.getText().equals("q")&&password.getText().equals("9")) {
			//FXMLLoader fx =new FXMLLoader(Main.class.getResource("f2"));
			
			//Main main =new Main();
			Main.page2("f2.fxml");
			
		}
		else System.out.println("error");
		
	}

}
