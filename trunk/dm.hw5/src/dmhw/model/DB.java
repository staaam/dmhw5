package dmhw.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContext;

public class DB {
	private static DB db = null;

	private Connection con = null;
	private Statement stmt = null;
	
	private String user;
	private String password;
	private String url;
	
	public static void init(ServletContext servletContext) {
		if (db == null)
			try {
				db = new DB(servletContext.getRealPath("/") + "/WEB-INF");
				try {
					db.constructTables();
				}
				catch (Exception e) {
				}
			}
			catch (Exception e) {
				print(e);
			}
	}
	
	public static DB getInstance() {
		return db;
	}
	
	private DB(String webinf) throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		readConfiguration(webinf + "/db.properties");

		con = DriverManager.getConnection(url, user, password);
		stmt = con.createStatement();
	}
	
	private void readConfiguration(String filename) throws IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(filename));
		user = props.getProperty("DBServer.user");
		password = props.getProperty("DBServer.password");
		url = "jdbc:mysql://" + props.getProperty("DBServer.host") + ":"
				+ props.getProperty("DBServer.port") + "/"
				+ props.getProperty("DBServer.database");
	}
	
	public void constructTables() {
//		poseUpdate(
//				"CREATE TABLE " + TypesTable.TableName + " ("
//				+ TypesTable.TypeId + " INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
//				+ TypesTable.TypeName + " VARCHAR(15) NOT NULL"
//				+ ") "
//				+ " UNIQUE ( " + TypesTable.TypeName + " ) "
//				);
		poseUpdate(
				"CREATE TABLE " + UsersTable.TableName + " ("
				+ UsersTable.UsrId + " INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ UsersTable.Username + " VARCHAR(255) NOT NULL, "
//				+ UsersTable.TypeId + " INT NOT NULL, "
				+ UsersTable.Type + " VARCHAR(15) NOT NULL, "
				+ UsersTable.Rank + " INT NOT NULL, "
				+ UsersTable.Password + " VARCHAR(255) NOT NULL"
				+ ")"
//				+ " UNIQUE ( " + UsersTable.Username + " ) "
////				+ " FOREIGN KEY ( " + UsersTable.TypeId + " ) "
////				+ " REFERENCES "+TypesTable.TableName+" ( " + TypesTable.TypeId + " ) "
				);
		poseUpdate(
				"CREATE TABLE " + MessagesTable.TableName + " ("
				+ MessagesTable.MsgId + " INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ MessagesTable.Title + " VARCHAR(255) NOT NULL, "
				+ MessagesTable.UserId + " INT NOT NULL, "
//				+ MessagesTable.TypeId + " INT NOT NULL, "
				+ MessagesTable.Type + " VARCHAR(15) NOT NULL, "
				+ MessagesTable.Rank + " INT NOT NULL, "
				+ MessagesTable.STime + " DATE NOT NULL, "
				+ MessagesTable.ETime + " DATE NOT NULL, "
				+ MessagesTable.Body + " VARCHAR(2047) NOT NULL"
				+ ")"
////				+ " FOREIGN KEY ( " + MessagesTable.TypeId + " ) "
////				+ " REFERENCES "+TypesTable.TableName+" ( " + TypesTable.TypeId + " ) "
//				+ " FOREIGN KEY ( " + MessagesTable.UserId + " ) "
//				+ " REFERENCES "+UsersTable.TableName+" ( " + UsersTable.UsrId + " ) "
				);
	}
	
	public void deleteTables() {
		poseUpdate("DROP TABLE "+MessagesTable.TableName);
		poseUpdate("DROP TABLE "+UsersTable.TableName);
//		poseUpdate("DROP TABLE "+TypesTable.TableName);
	}

	/**
	 * Clears the content of the cache (the tables are NOT deleted).
	 */
	public void clearTables() {
		poseUpdate("DELETE FROM "+MessagesTable.TableName);
		poseUpdate("DELETE FROM "+UsersTable.TableName);
//		poseUpdate("DELETE FROM "+TypesTable.TableName);
	}
	
	public void poseUpdate(String query) {
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			print(e);
		}
	}
	
	private static void print(Exception e) {
		System.err.println("Exception message: "+e.getMessage());
		System.err.println("The stack trace:");
		e.printStackTrace();
	}
	
	public class MessagesTable {
		public static final String TableName = "Messages"; 
		
		public static final String MsgId = "MsgId";
		public static final String Title = "Title";
//		public static final String TypeId = "TypeId";
		public static final String Type = "Type";
		public static final String Rank = "Rank";
		public static final String STime = "STime";
		public static final String ETime = "ETime";
		public static final String Body = "Body";
		public static final String UserId = "UserId";
	}

	public class UsersTable {
		public static final String TableName = "Users"; 
		
		public static final String UsrId = "MsgId";
		public static final String Username = "Username";
//		public static final String TypeId = "TypeId";
		public static final String Type = "Type";
		public static final String Rank = "Rank";
		public static final String Password = "Password";
	}

//	public class TypesTable {
//		public static final String TableName = "Types"; 
//		
//		public static final String TypeId = "TypeId";
//		public static final String TypeName = "TypeName";
//	}
	
	public ResultSet executeQuery(String query) throws SQLException {
		try {
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			print(e);
			throw e;
		}
	}

	public PreparedStatement prepareStatement(String s) throws SQLException {
		try {
			return con.prepareStatement(s);
		} catch (SQLException e) {
			print(e);
			return null;
		}
	}

	public void execute(PreparedStatement pstmt) {
		try {
			pstmt.execute();
		} catch (Exception e) {
			print(e);
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					print(e);
				}
		}
	}
}
