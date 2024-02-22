package markisha.headDbApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ApiManager {

	private static final String API_URL = "https://headdb.org/api/category/";

	private static List<HeadData> blockList = new ArrayList<>();
	private static List<HeadData> electronicsList = new ArrayList<>();
	private static List<HeadData> letterList = new ArrayList<>();
	private static List<HeadData> foodList = new ArrayList<>();
	
	static {
	    fetchData("blocks", blockList);
	    fetchData("electronics", electronicsList);
	    fetchData("letters", letterList);
	    fetchData("food", foodList);
	}

	public static void fetchData(String category, List<HeadData> headList) {
		try {
			URL url = new URL(API_URL + category);
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
			JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

			if (headList == null) {
				headList = new ArrayList<>();
			}

			for (String key : jsonObject.keySet()) {
				JsonElement jsonItem = jsonObject.get(key);

				String name = jsonItem.getAsJsonObject().getAsJsonPrimitive("name").getAsString();
				String uuid = jsonItem.getAsJsonObject().getAsJsonPrimitive("uuid").getAsString();
				JsonObject valueDecoded = jsonItem.getAsJsonObject().getAsJsonObject("valueDecoded");

				HeadData headData = new HeadData(name, uuid, valueDecoded);

				headList.add(headData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static void main(String[] args) {
		System.out.println(ApiManager.getBlockList().size());
		System.out.println(ApiManager.getElectronicsList().size());
		System.out.println(ApiManager.getLetterList().size());
		System.out.println(ApiManager.getFoodList().size());
	}

}
