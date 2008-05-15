package dmhw.model;

public class User {
	private int id = -1;
	private String username;
	private String password;
	private String type;
	private Integer rank;
	
	public User(String username, String password, String type, Integer rank) {
		this(-1, username, password, type, rank);
	}

	public User(int id, String username, String password, String type, Integer rank) {
		super();
		this.id = id; 
		this.username = username;
		this.password = password;
		this.type = type;
		this.rank = rank;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
}
