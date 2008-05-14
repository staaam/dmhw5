package dmhw.servlet;

import java.util.ArrayList;

import dmhw.Message;
import dmhw.User;

public class MessageManager {

	public static ArrayList<Message> getByUser(User user) {
		ArrayList<Message> m = new ArrayList<Message>();
		m.add(new Message());
		m.add(new Message());
		m.add(new Message());
		return m; 
	}

}
