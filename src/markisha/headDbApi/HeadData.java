package markisha.headDbApi;

public class HeadData {

	private String name;
	private String uuid;
	private String textureUrl;

	public HeadData(String name, String uuid, String textureUrl) {
		super();
		this.name = name;
		this.uuid = uuid;
		this.textureUrl = textureUrl;
	}

	public String getName() {
		return name;
	}

	public String getUuid() {
		return uuid;
	}

	public String getTextureUrl() {
		return textureUrl;
	}

}
