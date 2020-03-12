import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import java.io.*;

public class GetMail{

	public GetMail(Email email){
		Stage mail = new Stage();

		BorderPane layout = new BorderPane();
		HBox downloads = new HBox(10);
		layout.setBottom(downloads);
		Label label = new Label("");
		layout.setCenter(label);

		for(String string  : email.getMsg()){
			final String[] line = string.split("\n");
			

			if(line.length <= 1) continue;

			if(!line[1].equals("base64")){
				StringBuffer bf = new StringBuffer();
				for (int i = 1; i < line.length ; i++) {
					bf.append(line[i] + "\n");
				}
				label.setText(bf.toString());
			}else{
				Button button = new Button("Download");
				button.setOnAction(e->{
					Stage window = new Stage();
					VBox layout2 = new VBox(10);
					layout2.setAlignment(Pos.CENTER);
					TextField text = new TextField();
					Button button2 =  new Button("OK");
					button2.setOnAction(e2->{
						window.close();
					});
					layout2.getChildren().addAll(text,button2);
					window.setScene(new Scene(layout2, 200, 200));
					window.showAndWait();
					try{
						new DecoderBase64(line[0],line[2]).download(text.getText());
					}catch(Exception ioe){}
				});

				downloads.getChildren().add(button);

			}

		}
	


		Scene scene = new Scene(layout, 400,400);
		mail.setScene(scene);
		mail.show(); 
	}
}