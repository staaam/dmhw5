package dmhw.servlet;

import java.util.LinkedList;

import dmhw.User;

public class MessageManager {

	public static LinkedList<Message> getByUser(User user) {
		LinkedList<Message> m = new LinkedList<Message>();
		m.add(new Message());
		m.add(new Message());
		m.add(new Message());
		return m; 
	}

}
