package dmhw.management;

import dmhw.User;


public class UserManager {

	public static boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean exists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void addUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public static User getUser(String username) {
		return new User(username, "", "type", new Integer(1));
	}

}
