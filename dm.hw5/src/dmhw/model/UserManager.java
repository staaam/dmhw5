package dmhw.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dmhw.model.DB.UsersTable;

public class UserManager {
	
	public static boolean login(String username, String password) {
		try {
			ResultSet rs = DB.getInstance().executeQuery(String.format(
						"SELECT 1 FROM "+UsersTable.TableName+" WHERE %s='%s' AND %s='%s'",
						UsersTable.Username, username,
						UsersTable.Password, password
						));
			
			if (rs.next())
				return true;
			
		} catch (SQLException e) {
			return false;
		}
		return false;
	}

	public static boolean exists(String username) {
		try {
			ResultSet rs = DB.getInstance().executeQuery(String.format(
						"SELECT 1 FROM "+UsersTable.TableName+" WHERE %s='%s'",
						UsersTable.Username, username
						));
			
			if (rs.next())
				return true;
		} catch (SQLException e) {
			return false;
		}
		return false;
	}

	public static void addUser(User user) {
		try {
			PreparedStatement pstmt = DB.getInstance().prepareStatement(
					"INSERT INTO "+UsersTable.TableName+"("
					+ UsersTable.Username + ","
					+ UsersTable.Type + ","
					+ UsersTable.Rank + ","
					+ UsersTable.Password
					+ ")"+" VALUES (?,?,?,?)");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getType());
			pstmt.setInt(3, user.getRank());
			pstmt.setString(4, user.getPassword());
			DB.getInstance().execute(pstmt);
			
			listUsers();
		} catch (SQLException e) {
		}
	}

	private static void listUsers() {
		try {
			ResultSet rs = DB.getInstance().executeQuery("SELECT * FROM "+UsersTable.TableName);
			while (rs.next()) {
				User u = getUser(rs);
				System.out.println(String.format("user: %s, type %s, rank %d, password %s", u.getUsername(), u.getType(), u.getRank(), u.getPassword()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static User getUser(String username) {
		try {
			ResultSet rs = DB.getInstance().executeQuery(String.format(
					"SELECT * FROM "+UsersTable.TableName+" WHERE %s='%s'",
					UsersTable.Username, username
					));
	
			if (rs.next())
				return null;
		
			return getUser(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static User getUser(ResultSet rs) throws SQLException {
		return new User(rs.getString(UsersTable.Username),
				rs.getString(UsersTable.Password),
				rs.getString(UsersTable.Type),
				rs.getInt(UsersTable.Rank));
	}

}
