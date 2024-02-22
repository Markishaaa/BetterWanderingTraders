package markisha.headDbApi;

import com.google.gson.JsonObject;

public class HeadData {

	private String name;
	private String uuid;
	private JsonObject valueDecoded;

	public HeadData(String name, String uuid, JsonObject valueDecoded) {
		super();
		this.name = name;
		this.uuid = uuid;
		this.valueDecoded = valueDecoded;
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

	public JsonObject getValueDecoded() {
		return valueDecoded;
	}

	public void setValueDecoded(JsonObject valueDecoded) {
		this.valueDecoded = valueDecoded;
	}

	@Override
	public String toString() {
		return name;
	}

}
