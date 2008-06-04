package dmhw.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dmhw.model.DB.MessagesTable;
import dmhw.model.DB.UsersTable;

public class MessageManager {
	private final static DB db = DB.getInstance();

	public static ArrayList<Message> getByUser(User user) throws SQLException {
		return searchMessages(null, user.getRank(), new Date().getTime(), user.getType()); 
	}

	private static ArrayList<Message> getMessages(String query) throws SQLException {
		ArrayList<Message> m = new ArrayList<Message>();
		ResultSet rs = null;
		try {
			rs = db.executeQuery(query);
			
			while (rs.next())
				m.add(makeMessage(rs));
		}
		finally { db.close(rs); }
		return m;
	}

	private static Message makeMessage(ResultSet rs) throws SQLException {
		Message m = new Message();
		m.setId(rs.getInt(MessagesTable.MsgId));
		m.setAuthor(rs.getString(UsersTable.Username));
		m.setTitle(rs.getString(MessagesTable.Title));
		m.setType(rs.getString(MessagesTable.Type));
		m.setRank(rs.getInt(MessagesTable.Rank));
		m.setStartTime(new Date(rs.getLong(MessagesTable.STime)));
		m.setEndTime(new Date(rs.getLong(MessagesTable.ETime)));
		m.setBody(rs.getString(MessagesTable.Body));
		return m;
	}
	
	private static void listMessages() throws SQLException {
		ResultSet rs = null;
		try {
			long now = new Date().getTime();
			System.out.println("now: " + now);
			String q = "SELECT "+MessagesTable.TableName+".*,"+UsersTable.Username+" FROM "
					+ MessagesTable.TableName + " JOIN " + UsersTable.TableName
					+ " ON " + MessagesTable.TableName+"."+MessagesTable.UserId + "="+UsersTable.TableName+"."+UsersTable.UserId;
			
			rs = db.executeQuery(q);
			
			while (rs.next()) {
				Message m = makeMessage(rs); 
				System.out.println(String.format("title: %s, user: %s, type: %s, rank: %d, stime: "+m.getStartTime().getTime()+", etime: "+m.getEndTime().getTime()+", body:\n%s", 
						m.getTitle(), m.getAuthor(),
						m.getType(), m.getRank(), m.getBody()));
			}
		}
		finally { db.close(rs); }
	}

	public static void addMessage(Message msg) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = db.prepareStatement(
					"INSERT INTO "+MessagesTable.TableName+"("
					+ MessagesTable.Title + ","
					+ MessagesTable.Type + ","
					+ MessagesTable.Rank + ","
					+ MessagesTable.STime + ","
					+ MessagesTable.ETime + ","
					+ MessagesTable.Body + ","
					+ MessagesTable.UserId
					+ ")"+" VALUES (?,?,?,?,?,?,?)");
			pstmt.setString(1, msg.getTitle());
			pstmt.setString(2, msg.getType());
			pstmt.setInt(3, msg.getRank());
			pstmt.setLong(4, msg.getStartTime().getTime());
			pstmt.setLong(5, msg.getEndTime().getTime());
			pstmt.setString(6, msg.getBody());
			pstmt.setInt(7, UserManager.getUserId(msg.getAuthor()));
			db.execute(pstmt);
			
			listMessages();
		}
		finally { db.close(pstmt); }
	}

	public static Message getMessage(int msgid) throws SQLException {
		ResultSet rs = null;
		try {
			String q = "SELECT "+MessagesTable.TableName+".*,"+UsersTable.Username+" FROM "
			+ MessagesTable.TableName + " JOIN " + UsersTable.TableName
			+ " ON " + MessagesTable.TableName+"."+MessagesTable.UserId + "="+UsersTable.TableName+"."+UsersTable.UserId
			+ " WHERE "
			+ MessagesTable.MsgId + "=" + msgid;
			rs = db.executeQuery(q);
				
			if (rs.next())
				return makeMessage(rs);
		}
		finally { db.close(rs); }
		return null;
	}

	public static void deleteMessage(int msgid) throws SQLException {
		String q = "DELETE FROM "+MessagesTable.TableName
				+ " WHERE " + MessagesTable.MsgId + "=" + msgid;
		db.poseUpdate(q);
	}

	public static ArrayList<Message> searchMessages(String[] keywords, int rank, long time) throws SQLException {
		return searchMessages(keywords, rank, time, null);
	}
	
	public static ArrayList<Message> searchMessages(String[] keywords, int rank, long time, String type) throws SQLException {
		//listMessages();
		
		String q = "SELECT "+MessagesTable.TableName+".*,"+UsersTable.Username+" FROM "
			+ MessagesTable.TableName + " JOIN " + UsersTable.TableName
			+ " ON " + MessagesTable.TableName+"."+MessagesTable.UserId + "="+UsersTable.TableName+"."+UsersTable.UserId;
		
		ArrayList<String> w = new ArrayList<String>();
		if (!Utils.isNullOrEmpty(type)) {
			w.add(MessagesTable.TableName+"."+MessagesTable.Type + "='"+type+"'");
		}
		if (rank > 0) {
			w.add(MessagesTable.TableName+"."+MessagesTable.Rank + "<="+rank);
		}
		if (time > -1) {
			w.add(MessagesTable.TableName+"."+MessagesTable.STime + "<="+time);
			w.add(MessagesTable.TableName+"."+MessagesTable.ETime + ">="+time);			
		}
		if (keywords != null) {
			for (String word : keywords) {
				if (!Utils.isNullOrEmpty(word)) {
					w.add("("+MessagesTable.TableName+"."+MessagesTable.Title + " LIKE '%" + word + "%')OR("+MessagesTable.TableName+"."+MessagesTable.Body + " LIKE '%" + word + "%')");
				}
			}
		}
		if (w.size() > 0) {
			String o = "";
			for (String ws : w) {
				if (o.length() > 0) {
					o += " AND ";
				}
				o += "("+ws+")";
			}
			q += " WHERE " + o;
		}
		
		return getMessages(q);
	}

}
