package markisha.headDbApi;

public class HeadData {

	private String name;
	private String uuid;

	public HeadData() {
		super();
	}

	public HeadData(String name, String uuid) {
		super();
		this.name = name;
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return name;
	}

	
	
}
