package dmhw.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dmhw.model.DB.UsersTable;

public class UserManager {
	private final static DB db = DB.getInstance();
	
	public static boolean login(String username, String password) {
		User u = getUser(username);
		if (u != null && u.getPassword().equals(password))
			return true;
		return false;
	}

	public static boolean exists(String username) {
		User u = getUser(username);
		return u != null;
	}

	public static int getUserId(String username) {
		User user = getUser(username);
		if (user != null)
			return user.getId();
		return -1;
	}

	public static void addUser(User user) {
		if (user == null)
			return;
		PreparedStatement pstmt = null;
		try {
			pstmt = db.prepareStatement(
					"INSERT INTO "+UsersTable.TableName+"("
					+ UsersTable.Username + ","
					+ UsersTable.Type + ","
					+ UsersTable.Rank + ","
					+ UsersTable.Password + ","
					+ UsersTable.Guest
					+ ")"+" VALUES (?,?,?,?,?)");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getType());
			pstmt.setInt(3, user.getRank());
			pstmt.setString(4, user.getPassword());
			pstmt.setBoolean(5, user.isGuest());
			db.execute(pstmt);
			
			listUsers();
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { db.close(pstmt); }
	}

	private static void listUsers() {
		ResultSet rs = null;
		try {
			rs = db.executeQuery("SELECT * FROM "+UsersTable.TableName);
			while (rs.next()) {
				User u = makeUser(rs);
				System.out.println(String.format("user: %s, type %s, rank %d, password %s, guest %b", u.getUsername(), u.getType(), u.getRank(), u.getPassword(), u.isGuest()));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { db.close(rs); }
	}

	public static User getUser(String username) {
		ResultSet rs = null;
		try {
			rs = db.executeQuery(String.format(
					"SELECT * FROM "+UsersTable.TableName+" WHERE %s='%s'",
					UsersTable.Username, username
					));
	
			if (!rs.next())
				return null;
		
			return makeUser(rs);
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { db.close(rs); }
		return null;
	}

	private static User makeUser(ResultSet rs) throws SQLException {
		return new User(rs.getInt(UsersTable.UserId),
				rs.getString(UsersTable.Username),
				rs.getString(UsersTable.Password),
				rs.getString(UsersTable.Type),
				rs.getInt(UsersTable.Rank),
				rs.getBoolean(UsersTable.Guest));
	}
}
