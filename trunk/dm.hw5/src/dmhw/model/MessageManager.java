package dmhw.model;

import java.util.ArrayList;

public class MessageManager {

	public static ArrayList<Message> getByUser(User user) {
		ArrayList<Message> m = new ArrayList<Message>();
		m.add(new Message());
		m.add(new Message());
		m.add(new Message());
		return m; 
	}

}
