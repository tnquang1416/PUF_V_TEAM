package entities;
/**
 * Handling name and type for downloaded file
 * @author Mr.D
 *
 */
public class FileName {
	private String name;
	private String type;
	
	public FileName(String link) throws Exception {
		name = "";
		type = "";
		String tempList[] = link.split("/");
		String temp = tempList[tempList.length-1];
		
		if (!temp.contains("."))
			throw new Exception("It is not a file");
		
		String[] rsList = temp.split("\\.");
		
		setType(rsList[rsList.length-1]);		
		for (int i=0; i< rsList.length-1; i++)
			setName(getName() + rsList[i]);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getFullName()
	{
		//System.out.println("--> " + getName() + "." + getType());
		return getName() + "." + getType();
	}
}
