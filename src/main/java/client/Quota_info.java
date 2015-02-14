package client;

public class Quota_info {
	private String datastores;
	public String getDatastores() {
		return datastores;
	}
	public void setDatastores(String datastores) {
		this.datastores = datastores;
	}
	public String getShared() {
		return shared;
	}
	public void setShared(String shared) {
		this.shared = shared;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
	public String getNormal() {
		return normal;
	}
	public void setNormal(String normal) {
		this.normal = normal;
	}
	private String shared;
	private String quota;
	private String normal;
}
