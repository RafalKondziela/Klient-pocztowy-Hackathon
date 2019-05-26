package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import smtp.AppMail;
import smtp.Item;

public class SMTPGUI {
	public int id;
	private ListView<Item> lista2; 
	
	public SMTPGUI(Stage primaryStage) {

		final GridPane root = new GridPane();
		try {

			Scene scene = new Scene(root, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Klient Poczty SMPT");

			// Tworzenie buttonów

			Button SendMail = new Button("Send");
			SendMail.setOnMouseClicked(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent event) {
					Parent root2 = getForm();

					Scene scene2 = new Scene(root2, 700, 700);
					Stage stage = new Stage();
					stage.setTitle("Send Message");
					stage.setScene(scene2);
					stage.show();
				}

				private Parent getForm() {

					GridPane root = new GridPane();

					// Pole podaj adres
					final TextField mail = new TextField();
					mail.setEditable(true);
					mail.setPrefSize(500, 50);
					root.add(mail, 0, 0);

					// Pole Podaj Tytół
					final TextField title = new TextField();
					title.setEditable(true);
					title.setPrefSize(500, 50);
					root.add(title, 0, 1);

					// Pole Podaj Treść
					final TextField message = new TextField();
					message.setEditable(true);
					message.setPrefSize(500, 500);
					root.add(message, 0, 2);

					// Button Send

					Button Send = new Button("Send");
					root.add(Send, 0, 3);
					Send.setOnMouseClicked(new EventHandler<MouseEvent>() {

						public void handle(MouseEvent event) {
							String tmail = mail.getText().toString();
							String tmessage = message.getText();
							String ttitle = title.getText().toString();
							try {
								AppMail.sendMail(tmail, ttitle, tmessage);
							} catch (AddressException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
						}

					});
					return root;
				}
			});

			Button ChceckMail = new Button("Check");
			ChceckMail.setOnMouseClicked(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent event) {
					ObservableList<Item> lista = AppMail.recieveMails("pop3.wp.pl", "pop3", "zajecia2222@wp.pl",
							"haslozajecia1");
					lista2 = new ListView<Item>();
					lista2.setItems(lista);
					root.add(lista2, 0, 0);
					lista2.setOnMouseClicked(new EventHandler<MouseEvent>() {
							
						public void handle(MouseEvent event) {
							Parent root3 = getForm2();

						id = (lista2.getSelectionModel().getSelectedIndex());
						
							Scene scene3 = new Scene(root3, 500, 400);
							Stage stage = new Stage();
							stage.setTitle("Read Message");
							stage.setScene(scene3);
							stage.show();
						}

						private Parent getForm2() {
							GridPane root = new GridPane();
							TextField msg = new TextField();
							msg.setPrefSize(500, 400);
							msg.setEditable(false);
							msg.setText(AppMail.readMail("pop3.wp.pl", "pop3", "zajecia2222@wp.pl", "haslozajecia1",(id + 1)));

							root.add(msg, 0, 0);
							return root;
						}

					});

				}
			});

			// Umieszczanie na GridPanie
			root.add(SendMail, 1, 0);
			root.add(ChceckMail, 2, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
