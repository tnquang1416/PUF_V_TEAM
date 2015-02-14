package client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Content {
	private String rev;
	public String getRev() {
		return rev;
	}
	public void setRev(String rev) {
		this.rev = rev;
	}
	public String getThumb_exists() {
		return thumb_exists;
	}
	public void setThumb_exists(String thumb_exists) {
		this.thumb_exists = thumb_exists;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIs_dir() {
		return is_dir;
	}
	public void setIs_dir(String is_dir) {
		this.is_dir = is_dir;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getRead_only() {
		return read_only;
	}
	public void setRead_only(String read_only) {
		this.read_only = read_only;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getBytes() {
		return bytes;
	}
	public void setBytes(String bytes) {
		this.bytes = bytes;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public Shared_folder getShared_folder() {
		return shared_folder;
	}
	public void setShared_folder(Shared_folder shared_folder) {
		this.shared_folder = shared_folder;
	}
	private String thumb_exists;
	private String path;
	private String is_dir;
	private String icon;
	private String read_only;
	private String modifier;
	private String bytes;
	private String modified;
	private String size;
	private String root;
	private int revision;
	private Shared_folder shared_folder;
	private String mime_type;
	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}
	public String getMime_type() {
		// TODO Auto-generated method stub
		return mime_type;
	}
}
