package client;

public class DropboxUser {
	private String uid;
	private String display_name;
	private String referral_link;
	private String country;
	private String locale;
	private String is_paired;
	private String email_verified;
	public String getEmail_verified() {
		return email_verified;
	}

	public void setEmail_verified(String email_verified) {
		this.email_verified = email_verified;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Quota_info getQuota_info() {
		return quota_info;
	}

	public void setQuota_info(Quota_info quota_info) {
		this.quota_info = quota_info;
	}

	public Name_details getName_details() {
		return name_details;
	}

	public void setName_details(Name_details name_details) {
		this.name_details = name_details;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String team;
	private Quota_info quota_info;
	private Name_details name_details;
	private String email;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getReferral_link() {
		return referral_link;
	}

	public void setReferral_link(String referral_link) {
		this.referral_link = referral_link;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getIs_paired() {
		return is_paired;
	}

	public void setIs_paired(String is_paired) {
		this.is_paired = is_paired;
	}

	@Override
	public String toString() {
		return "Number Id: " + this.getUid() + "Display_name: "
				+ this.getDisplay_name();
	}

}
