package databaseProject;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	private Scene scene ;
	static Stage stg ;
	@Override
	public void start(Stage stage) throws Exception {
		stg =stage ;
		FXMLLoader fxmlloader = new FXMLLoader(Main.class.getResource("f1.fxml"));
		scene =new Scene( fxmlloader.load());
		stg.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stg.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
	public static void page2(String r2) throws IOException {
		
		FXMLLoader fxmlloader2 = new FXMLLoader(Main.class.getResource(r2));
		Scene scene2 =new Scene( fxmlloader2.load());
		scene2.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
		stg.setScene(scene2);
	//	stg.setScene(scene);
	//	stg.show();
	}

}
