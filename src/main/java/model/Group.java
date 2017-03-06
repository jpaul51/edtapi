package model;

public enum Group {

	STUDENT("student"),
	ADMIN("admin"),
	TEACHER("teacher");
	
	private final String groupName;
	
	Group(String groupName)
	{
		this.groupName=groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	
	
	
}
