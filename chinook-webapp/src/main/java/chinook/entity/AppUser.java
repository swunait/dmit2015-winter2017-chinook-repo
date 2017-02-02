package chinook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppUser {

	@Id
	@Column(nullable=false, unique=true, length=64)
	private String loginName;
	
	@Column(nullable=false, length=64)
	private String password;

	public AppUser() {
		super();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
