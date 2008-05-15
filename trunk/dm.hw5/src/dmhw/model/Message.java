package dmhw.model;

import java.util.Date;

public class Message {
	private int id = -1;
	private String title = "title";
	private String type = "type";
	private Integer rank = new Integer(1);
	private Date startTime = new Date();
	private Date endTime = new Date();
	private String body = "some text";
	
	private String author = "stam";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setStartTime(Integer stime) {
		startTime = new Date();
	}
	
	public void setEndTime(Integer etime) {
		endTime = new Date(etime.longValue());
	}
	
}
