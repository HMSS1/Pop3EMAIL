import java.util.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.scene.control.cell.*;

import java.io.IOException;

public class SucessLogin implements MyViews{
	Scene scene;
    Controller controller;
	public SucessLogin(Controller controller){
		
                this.controller = controller;

                LinkedList<Email> emails_view = new LinkedList<>();
     
                LinkedList<String> emails_pop = controller.list();
                String[] arrLine;
                for (String line : emails_pop){
                        arrLine = line.split(" ");
                        emails_view.add(new Email(arrLine[0],null, null,
                                null, null));
                }


                TableView table = new TableView<>();
                TableColumn colunaNro  = new TableColumn<>("Nro");
                TableColumn colunaDate  = new TableColumn<>("Date");
                TableColumn colunaFrom    = new TableColumn<>("From");
                TableColumn colunaName  = new TableColumn<>("Name");

                colunaNro.setCellValueFactory(new PropertyValueFactory<>("nro"));
                colunaDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                colunaFrom.setCellValueFactory(new PropertyValueFactory<>("from"));
                colunaName.setCellValueFactory(new PropertyValueFactory<>("name"));

                table.setRowFactory(tv -> {
                    TableRow<Email> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                            Email email = (Email)table.getItems().get(table.getItems().indexOf(row.getItem()));
                            Map<String,LinkedList<String>> response = controller.retr(Integer.parseInt(email.getNro()));
                            email.setName(response.get("Name").getFirst());
                            email.setFrom(response.get("From").getFirst());
                            email.setDate(response.get("Date").getFirst());
                            email.setMsg(response.get("Msg"));
                            new GetMail(email);
                            table.refresh();
                        }
                    });
                    return row ;
                });


                table.setItems(FXCollections.observableArrayList(emails_view));
                table.getColumns().addAll(colunaNro, colunaName, colunaFrom, colunaDate);
                
                Button button =  new Button ("STAT");
                Button button2 =  new Button ("DELETE");
                Button button3 =  new Button ("RSET");
                Button button4 =  new Button ("LOGOUT");

                button.setOnAction(e->{
                    controller.stat();
                });

                button2.setOnAction(e->{
                    ObservableList<Email> selected, all;
                    all = table.getItems();
                    selected = table.getSelectionModel().getSelectedItems();

                    for (Email sel : selected) {
                        controller.dele(Integer.parseInt(sel.getNro()));
                        all.remove(all.indexOf(sel));
                    }

                });

                button3.setOnAction(e->{
                    controller.rset();
                });
                
                button4.setOnAction(e->{
                    controller.quit();
                    ((Stage)scene.getWindow()).close();
                    try{
                        new Main().start(new Stage());
                    }catch(Exception oss){}
                });

                HBox hb = new HBox(20);
                hb.setAlignment(Pos.CENTER);
                hb.getChildren().addAll(button,button2,button3,button4);

                BorderPane root = new BorderPane();
                root.setTop(hb);
                root.setCenter(table);
                scene = new Scene(root, 1200, 200);    


	}

	public Scene getScene(){
		return scene;
	}

	public void update(){
		
	}


}