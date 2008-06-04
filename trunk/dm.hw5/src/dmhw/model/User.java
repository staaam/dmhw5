package dmhw.model;

public class User {
	public static final String GUEST_NAME = "guest";
	public static final User GUEST = new User(GUEST_NAME, GUEST_NAME, "", 1, true);
	
	private int id = -1;
	private String username;
	private String password;
	private String type;
	private Integer rank;
	private boolean isGuest;
	
	public User(String username, String password, String type, Integer rank, boolean isGuest) {
		this(-1, username, password, type, rank, isGuest);
	}

	public User(int id, String username, String password, String type, Integer rank, boolean isGuest) {
		super();
		this.id = id; 
		this.username = username;
		this.password = password;
		this.type = type;
		this.rank = rank;
		this.isGuest = isGuest;
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

	public boolean isGuest() {
		return isGuest;
	}
	
}
