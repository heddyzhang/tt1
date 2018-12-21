package jp.co.toshiba.traces.entity.k;

public class User {

	public User() {
	}

	public User(String userId) {
		this.userId = userId;
	}

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
