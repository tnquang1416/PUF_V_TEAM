package entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "token")
public class DropboxToken {
	private String access_token;
	private String token_type;
	private String uid;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String toString()
	{
		return access_token;
	}
}
