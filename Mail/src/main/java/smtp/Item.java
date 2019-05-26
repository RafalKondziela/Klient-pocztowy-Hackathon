package smtp;

import java.io.IOException;
import javax.mail.Message;
import javax.mail.MessagingException;

public class Item {

	public String content;
	public String subject;
	public String from;
	public int id;
	
	public Item(Message m) throws IOException, MessagingException {
		this.content = m.getContent().toString();
		this.subject = m.getSubject();
		this.from = m.getFrom().toString();
		
	}
	
	public String toString() {
		
		
		
		return (this.from +  " " + this.subject);
		
	}
	
	
}
