package markisha.headDbApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import markisha.constants.Categories;

public class ApiManager {

	private static final String HEADDB_API_URL = "https://headdb.org/api/category/";
	private static final String MCHEADS_API_URL = "https://minecraft-heads.com/scripts/api.php?cat=";

	private static List<HeadData> blockList = new ArrayList<>();
	private static List<HeadData> electronicsList = new ArrayList<>();
	private static List<HeadData> letterList = new ArrayList<>();
	private static List<HeadData> foodList = new ArrayList<>();
	private static List<HeadData> plantList = new ArrayList<>();
	private static List<HeadData> decorationList = new ArrayList<>();

	static {
		fetchData(HEADDB_API_URL, Categories.BLOCKS, blockList);
		fetchData(HEADDB_API_URL, Categories.ELECTRONICS, electronicsList);
		fetchData(HEADDB_API_URL, Categories.LETTERS, letterList);
		fetchData(HEADDB_API_URL, Categories.FOOD, foodList);
		fetchData(MCHEADS_API_URL, Categories.PLANTS, plantList);
		fetchData(MCHEADS_API_URL, Categories.DECORATION, decorationList);
	}

	public static void fetchData(String apiUrl, String category, List<HeadData> headList) {
	    try {
	        URL url = new URL(apiUrl + category);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");

	        int responseCode = connection.getResponseCode();
	        if (responseCode != HttpURLConnection.HTTP_OK) {
	            System.out.println("Failed to fetch data. HTTP response code: " + responseCode);
	            return;
	        }

	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder response = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            response.append(line);
	        }
	        reader.close();

	        Gson gson = new Gson();
	        JsonElement jsonElement = gson.fromJson(response.toString(), JsonElement.class);

	        if (jsonElement.isJsonObject()) {
	            parseJsonObject(jsonElement.getAsJsonObject(), headList);
	        } else if (jsonElement.isJsonArray()) {
	            parseJsonArray(jsonElement.getAsJsonArray(), headList);
	        } else {
	            System.out.println("Unexpected JSON format.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// object format: { "uuid1": {...}, "uuid2": {...} }
	private static void parseJsonObject(JsonObject jsonObject, List<HeadData> headList) {
	    if (headList == null) {
	        headList = new ArrayList<>();
	    }

	    for (String key : jsonObject.keySet()) {
	        JsonElement jsonItem = jsonObject.get(key);

	        String name = jsonItem.getAsJsonObject().getAsJsonPrimitive("name").getAsString();
	        String uuid = jsonItem.getAsJsonObject().getAsJsonPrimitive("uuid").getAsString();

	        JsonObject jsonValueDecoded = jsonItem.getAsJsonObject().getAsJsonObject("valueDecoded");
	        String textureUrl = getTextureUrlFromJsonObject(jsonValueDecoded);

	        HeadData headData = new HeadData(name, uuid, textureUrl);
	        headList.add(headData);
	    }
	}
	
	// array format: [ {...}, {...} ]
	private static void parseJsonArray(JsonArray jsonArray, List<HeadData> headList) {
	    if (headList == null) {
	        headList = new ArrayList<>();
	    }

	    for (JsonElement element : jsonArray) {
	        JsonObject jsonItem = element.getAsJsonObject();

	        String name = jsonItem.getAsJsonPrimitive("name").getAsString();
	        String uuid = jsonItem.getAsJsonPrimitive("uuid").getAsString();

	        String valueEncoded = jsonItem.getAsJsonObject().getAsJsonPrimitive("value").getAsString();
	        String textureUrl = decodeTextureUrl(valueEncoded);

	        HeadData headData = new HeadData(name, uuid, textureUrl);
	        headList.add(headData);
	    }
	}
	
	private static String decodeTextureUrl(String encodedValue) {
		try {
			
			byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
			String decodedString = new String(decodedBytes);
			
			JsonObject decodedJson = JsonParser.parseString(decodedString).getAsJsonObject();
			
			return getTextureUrlFromJsonObject(decodedJson);
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getTextureUrlFromJsonObject(JsonObject object) {
		return object.getAsJsonObject("textures")
				.getAsJsonObject("SKIN")
				.getAsJsonPrimitive("url")
				.getAsString();
	}

	public static List<HeadData> getBlockList() {
		return blockList;
	}

	public static List<HeadData> getElectronicsList() {
		return electronicsList;
	}

	public static List<HeadData> getLetterList() {
		return letterList;
	}

	public static List<HeadData> getFoodList() {
		return foodList;
	}
	
	public static List<HeadData> getPlantList() {
		return plantList;
	}
	
	public static List<HeadData> getDecorationList() {
		return decorationList;
	}

}
