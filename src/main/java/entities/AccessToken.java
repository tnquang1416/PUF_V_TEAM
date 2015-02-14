package entities;

/**
 * store access token info
 * @author TRAN Nhat Quang
 *
 */
public class AccessToken {
	private String access_token;
	private String token_type;
	private String uid;
	
	public AccessToken(String input)
	{
		String[] mediums = input.split("\"");
		this.setAccess_token("");
		this.setToken_type("");
		this.setUid("");
		
		if (mediums.length == 13)
		{
			this.setAccess_token(mediums[3]);
			this.setToken_type(mediums[7]);
			this.setUid(mediums[11]);
		}
		else if (mediums.length == 15)
		{
			this.setAccess_token(mediums[3]);
			this.setToken_type(mediums[7]);
			this.setUid(mediums[13]);
		}
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	private void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getUid() {
		return uid;
	}

	private void setUid(String uid) {
		this.uid = uid;
	}
}
