package smtp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AppMail {

	static ObservableList<Item> lista = FXCollections.observableArrayList();
	public static void main(String[] args) throws AddressException, MessagingException, FileNotFoundException, IOException {

		
		char key;
		System.out.println("Co chcesz zrobić?");
		Scanner sc = new Scanner(System.in);
		key = sc.nextLine().charAt(0);
		while(key!='q') {
			switch(key) {
			case 'o':
			//	readMail("pop3.wp.pl", "pop3", "zajecia2222@wp.pl", "haslozajecia1");
				break;
			case 'r':
				recieveMails("pop3.wp.pl", "pop3", "zajecia2222@wp.pl", "haslozajecia1");
				break;
			case 's':
				String reciever, title, text;
				System.out.println("Do kogo chcesz napisać? ");
				reciever = sc.nextLine();
				System.out.println("Podaj tytuł: ");
				title = sc.nextLine();
				System.out.println("Podaj treść: ");
				text = sc.nextLine();
				sendMail(reciever, title, text);
				break;
			case 'h':
				System.out.println("------------------------- ");
				System.out.println("h - help ");
				System.out.println("s - send ");
				System.out.println("r - recieve mails ");
				System.out.println("o - open mail ");
				System.out.println("q - quit");
				System.out.println("------------------------- ");
				break;
			default:
				System.out.println("Podano złą komende. Wpisz 'h' aby wyświetlić listę komend ");
				break;
			}
			System.out.println("Co chcesz zrobić?");
			key = sc.nextLine().charAt(0);
		}
	}
	
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	
	public static void sendMail(String reciever, String title, String text) throws AddressException, MessagingException, FileNotFoundException, IOException {
		 
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.load(new FileInputStream("C:\\Users\\MediaMarkt\\eclipse-workspace\\Mail\\src\\main\\resources\\resources.resources"));
	

		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
	
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.setFrom(new InternetAddress("zajecia2222@wp.pl"));
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
		generateMailMessage.setSubject(title);
		
		generateMailMessage.setContent(text, "text/html");
		System.out.println("Mail Session has been created successfully..");
 
		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtps");
 
		transport.connect("smtp.wp.pl", "zajecia2222@wp.pl", "haslozajecia1");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
	
	public static ObservableList<Item> recieveMails(String host, String storeType, String user,
		      String password) 
		   {
			//ObservableList<String> lista = FXCollections.observableArrayList();
		      try {

		      //create properties field
		      Properties properties = new Properties();
		      properties.load(new FileInputStream("C:\\Users\\MediaMarkt\\eclipse-workspace\\Mail\\src\\main\\resources\\resources.resources"));
		      Session emailSession = Session.getDefaultInstance(properties);
		  
		      //create the POP3 store object and connect with the pop server
		      Store store = emailSession.getStore("pop3s");

		      store.connect(host, user, password);

		      //create the folder object and open it
		      Folder emailFolder = store.getFolder("INBOX");
		      emailFolder.open(Folder.READ_ONLY);

		      // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		      System.out.println("messages.length---" + messages.length);

		      for (int i = 0, n = messages.length; i < n; i++) {
		         Message message = messages[i];
		         Item nmessage = new Item(message);
		         nmessage.id = i;
		         
		         /*
		         System.out.println("---------------------------------");
		         System.out.println("Email Number " + (i + 1));
		         System.out.println("Subject: " + message.getSubject());
		         System.out.println("From: " + message.getFrom()[0]);
		         */
		         lista.add(nmessage);
		         
		        

		      }

		      //close the store and folder objects
		      emailFolder.close(false);
		      store.close();

		      } catch (NoSuchProviderException e) {
		         e.printStackTrace();
		      } catch (MessagingException e) {
		         e.printStackTrace();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
			return lista;
			
		   }
	public static String readMail(String host, String storeType, String user, String password, int id) {
		//Scanner in = new Scanner(System.in);
		try {
		      //create properties field
		      Properties properties = new Properties();

		      properties.load(new FileInputStream("C:\\Users\\MediaMarkt\\eclipse-workspace\\Mail\\src\\main\\resources\\resources.resources"));
		      Session emailSession = Session.getDefaultInstance(properties);
		  
		      //create the POP3 store object and connect with the pop server
		      Store store = emailSession.getStore("pop3s");

		      store.connect(host, user, password);

		      //create the folder object and open it
		      Folder emailFolder = store.getFolder("INBOX");
		      emailFolder.open(Folder.READ_ONLY);

		      // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		      System.out.println("Subject: " + messages[id-1].getSubject());
		      System.out.println("Text: " + messages[id-1].getContent().toString());
		
		      return messages[id-1].getContent().toString();
		}
		 catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	 
	      }
		return null;
	}


}
