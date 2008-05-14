package dmhw.model;

public class User {
	private String username;
	private String password;
	private String type;
	private Integer rank;
	
	public User(String username, String password, String type, Integer rank) {
		super();
		this.username = username;
		this.password = password;
		this.type = type;
		this.rank = rank;
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
