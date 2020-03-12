import java.io.IOException;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;


public class Main extends Application implements MyViews{
	Stage window;
	Controller controller;
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception{
		controller = new Controller();
		window = primaryStage;

		Label passwordIncorrect = new Label("");

		TextField serverField  = new TextField();
		serverField.setFont(new Font(20));
		serverField.setPromptText("Server");
		HBox server = new HBox(10);
		server.getChildren().addAll( serverField);
		server.setAlignment(Pos.CENTER);

		TextField portField  = new TextField();
		portField.setFont(new Font(20));
		portField.setPromptText("Port");
		HBox port = new HBox(10);
		port.getChildren().addAll( portField);
		port.setAlignment(Pos.CENTER);
	

		TextField userField  = new TextField();
		userField.setFont(new Font(20));
		userField.setPromptText("Your username");
		HBox user = new HBox(10);
		user.getChildren().addAll( userField);
		user.setAlignment(Pos.CENTER);


		PasswordField passwordField = new PasswordField();
		passwordField.setFont(new Font(18));
		passwordField.setPromptText("Your password");
		HBox password = new HBox(10);
		password.getChildren().addAll( passwordField);
		password.setAlignment(Pos.CENTER);

		Button button = new Button("Enter");
		button.setOnAction(e->{
			controller.connect(serverField.getText(), portField.getText());
			controller.setObserver(this);
			controller.login(userField.getText(), passwordField.getText());
			
			passwordIncorrect.setText("Login Failed");
			passwordIncorrect.setTextFill(Color.rgb(200, 40, 50));
			passwordIncorrect.setFont(new Font(20));
		});
		button.setFont(new Font(15));

		Button button2 = new Button("Quit");
		button2.setOnAction(e->{
			controller.quit();
			System.exit(0);
		});

		VBox layout = new VBox(30);
		layout.getChildren().addAll(server, port, user, password, button, button2, passwordIncorrect);
		layout.setAlignment(Pos.CENTER);


		window.setScene(new Scene(layout, 400,600));
		window.show();

	}

	public void update(){
		window.setScene(new SucessLogin(controller).getScene());

	}
}