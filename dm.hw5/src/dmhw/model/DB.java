package dmhw.model;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;

import snaq.db.ConnectionPool;
import snaq.db.ConnectionPoolManager;

public class DB {
	private static DB db = null;

	private long timeout = 10000;  // 10 seconds timeout
	private static ConnectionPool pool;
	
	public static void init(ServletContext servletContext) {
		if (db == null)
			try {
				db = new DB(servletContext);
				//db.recreate();
			}
			catch (Exception e) {
				print(e);
			}
	}
	
	public static DB getInstance() {
		return db;
	}
	
	private DB(ServletContext servletContext) throws IOException {
		String dbConfig = servletContext.getRealPath("/") + "/WEB-INF/db.properties";
		pool = ConnectionPoolManager.getInstance(new File(dbConfig)).getPool("local");
		pool.init(10);
	}
	
	public void constructTables() {
//		poseUpdate(
//				"CREATE TABLE " + TypesTable.TableName + " ("
//				+ TypesTable.TypeId + " INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
//				+ TypesTable.TypeName + " VARCHAR(15) NOT NULL"
//				+ ") "
//				+ " UNIQUE ( " + TypesTable.TypeName + " ) "
//				);
		try {
		poseUpdate(
				"CREATE TABLE " + UsersTable.TableName + " ("
				+ UsersTable.UserId + " INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ UsersTable.Username + " VARCHAR(255) NOT NULL, "
//				+ UsersTable.TypeId + " INT NOT NULL, "
				+ UsersTable.Type + " VARCHAR(15) NOT NULL, "
				+ UsersTable.Rank + " INT NOT NULL, "
				+ UsersTable.Password + " VARCHAR(255) NOT NULL, "
				+ UsersTable.Guest + " BOOLEAN NOT NULL "
				+ ")"
//				+ " UNIQUE ( " + UsersTable.Username + " ) "
////				+ " FOREIGN KEY ( " + UsersTable.TypeId + " ) "
////				+ " REFERENCES "+TypesTable.TableName+" ( " + TypesTable.TypeId + " ) "
				);
		}
		catch (SQLException e) { print(e); }
		try {
		poseUpdate(
				"CREATE TABLE " + MessagesTable.TableName + " ("
				+ MessagesTable.MsgId + " INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ MessagesTable.Title + " VARCHAR(255) NOT NULL, "
				+ MessagesTable.UserId + " INT NOT NULL, "
//				+ MessagesTable.TypeId + " INT NOT NULL, "
				+ MessagesTable.Type + " VARCHAR(15) NOT NULL, "
				+ MessagesTable.Rank + " INT NOT NULL, "
				+ MessagesTable.STime + " LONG NOT NULL, "
				+ MessagesTable.ETime + " LONG NOT NULL, "
				+ MessagesTable.Body + " VARCHAR(2047) NOT NULL"
				+ ")"
////				+ " FOREIGN KEY ( " + MessagesTable.TypeId + " ) "
////				+ " REFERENCES "+TypesTable.TableName+" ( " + TypesTable.TypeId + " ) "
//				+ " FOREIGN KEY ( " + MessagesTable.UserId + " ) "
//				+ " REFERENCES "+UsersTable.TableName+" ( " + UsersTable.UsrId + " ) "
				);
		}
		catch (SQLException e) { print(e); }
	}
	
	public void deleteTables() {
		try { poseUpdate("DROP TABLE "+MessagesTable.TableName); }
		catch (SQLException e) { print(e); }
		try { poseUpdate("DROP TABLE "+UsersTable.TableName); }
		catch (SQLException e) { print(e); }
//		poseUpdate("DROP TABLE "+TypesTable.TableName);
	}

	/**
	 * Clears the content of the cache (the tables are NOT deleted).
	 * @throws SQLException 
	 */
	public void clearTables() throws SQLException {
		try { poseUpdate("DELETE FROM "+MessagesTable.TableName); }
		catch (SQLException e) { print(e); }
		try { poseUpdate("DELETE FROM "+UsersTable.TableName); }
		catch (SQLException e) { print(e); }
//		poseUpdate("DELETE FROM "+TypesTable.TableName);
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
		
		public static final String UserId = "UserId";
		public static final String Username = "Username";
//		public static final String TypeId = "TypeId";
		public static final String Type = "Type";
		public static final String Rank = "Rank";
		public static final String Password = "Password";
		public static final String Guest = "Guest";
	}

//	public class TypesTable {
//		public static final String TableName = "Types"; 
//		
//		public static final String TypeId = "TypeId";
//		public static final String TypeName = "TypeName";
//	}
	
	public void poseUpdate(String query) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		}
		finally {
			close(stmt);
			close(con);
		}
	}
	
	private Connection getConnection() throws SQLException {
		return pool.getConnection(timeout);
	}

	public ResultSet executeQuery(String query) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		con = getConnection();
		stmt = con.createStatement();
		return stmt.executeQuery(query);
	}

	public PreparedStatement prepareStatement(String s) throws SQLException {
		Connection con = null;
		try {
			con = getConnection();
			return con.prepareStatement(s);
		}
		catch (SQLException e) {
			close(con);
			throw e;
		}
	}

	public void execute(PreparedStatement pstmt) {
		try { pstmt.execute(); }
		catch (Exception e) {}
		finally { close(pstmt); }
	}

	public void close(ResultSet o) {
		Statement st = null;
		try { o.close(); }
		catch (Exception e) {}
		finally { close(st); }
	}

	public void close(Statement o) {
		Connection con = null;
		try { con=o.getConnection(); o.close(); }
		catch (Exception e) {}
		finally { close(con); }
	}

	public void close(Connection o) {
		try { o.close(); }
		catch (Exception e) {}
	}

	public void recreate() {
		db.deleteTables();
		db.constructTables();
	}
}