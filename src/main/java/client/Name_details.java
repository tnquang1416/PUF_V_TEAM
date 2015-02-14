package client;

public class Name_details {
	private String familiar_name;
	public String getFamiliar_name() {
		return familiar_name;
	}
	public void setFamiliar_name(String familiar_name) {
		this.familiar_name = familiar_name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getGiven_name() {
		return given_name;
	}
	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}
	private String surname;
	private String given_name;
}
