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

	private long timeout = 5000;  // 2 second timeout
	private static ConnectionPool pool;
	
	public static void init(ServletContext servletContext) {
		if (db == null)
			try {
				db = new DB(servletContext);
				try {
					db.deleteTables();
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
	
	private DB(ServletContext servletContext) throws IOException {
		String dbConfig = servletContext.getRealPath("/") + "/WEB-INF/db.properties";
		pool = ConnectionPoolManager.getInstance(new File(dbConfig)).getPool("local");
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
				+ UsersTable.UserId + " INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
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
	}

//	public class TypesTable {
//		public static final String TableName = "Types"; 
//		
//		public static final String TypeId = "TypeId";
//		public static final String TypeName = "TypeName";
//	}
	
	public void poseUpdate(String query) {
		Connection con = null;
		Statement stmt = null;
		try {
			con = pool.getConnection(timeout);
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			print(e);
		}
		finally {
			close(stmt);
			close(con);
		}
	}
	
	public ResultSet executeQuery(String query) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = pool.getConnection(timeout);
			stmt = con.createStatement();
			return stmt.executeQuery(query);
		}
		catch (SQLException e) {
			print(e);
			throw e;
		}
		finally {
			close(stmt);
			close(con);
		}
	}

	public PreparedStatement prepareStatement(String s) {
		Connection con = null;
		try {
			con = pool.getConnection(timeout);
			return con.prepareStatement(s);
		}
		catch (SQLException e) {
			print(e);
			close(con);
			return null;
		}
	}

	public void execute(PreparedStatement pstmt) {
		try { pstmt.execute(); }
		catch (Exception e) { print(e); }
		finally { close(pstmt); }
	}

	public void close(ResultSet o) {
		try { o.close(); }
		catch (Exception e) { print(e); }
		try { close(o.getStatement()); }
		catch (Exception e) { print(e); }
	}

	public void close(Statement o) {
		try { o.close(); }
		catch (Exception e) { print(e); }
		try { close(o.getConnection()); }
		catch (Exception e) { print(e); }
	}

	public void close(Connection o) {
		try { o.close(); }
		catch (Exception e) { print(e); }
	}
}