package markisha.items;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import markisha.constants.Categories;
import markisha.headDbApi.ApiManager;
import markisha.headDbApi.HeadData;

public class CustomHead {

	private Map<String, List<HeadData>> categoryMap;

	public CustomHead() {
		categoryMap = new HashMap<>();
		categoryMap.put(Categories.BLOCKS, ApiManager.getBlockList());
		categoryMap.put(Categories.ELECTRONICS, ApiManager.getElectronicsList());
		categoryMap.put(Categories.LETTERS, ApiManager.getLetterList());
		categoryMap.put(Categories.FOOD, ApiManager.getFoodList());
	}

	public List<ItemStack> getHeads() {
		List<ItemStack> heads = new ArrayList<>();

		heads.addAll(createHeadsOfCategory(categoryMap.get(Categories.BLOCKS), 10, ChatColor.YELLOW));
		heads.addAll(createHeadsOfCategory(categoryMap.get(Categories.ELECTRONICS), 5, ChatColor.RED));
		heads.addAll(createHeadsOfCategory(categoryMap.get(Categories.LETTERS), 5, ChatColor.GRAY));
		heads.addAll(createHeadsOfCategory(categoryMap.get(Categories.FOOD), 15, ChatColor.GREEN));

		return heads;
	}

	private List<ItemStack> createHeadsOfCategory(List<HeadData> headList, int amount, ChatColor textColor) {
		if (headList == null || headList.isEmpty()) {
			return null;
		}

		List<ItemStack> skullList = new ArrayList<>();
		Set<Integer> uniqueRandomNumbers = generateUniqueRandomNumbers(amount, headList.size());

		for (Integer i : uniqueRandomNumbers) {
			skullList.add(setHead(headList.get(i), textColor));
		}

		return skullList;
	}

	private Set<Integer> generateUniqueRandomNumbers(int amount, int i) {
		Set<Integer> uniqueRandomNumbers = new HashSet<>();

		Random random = new Random();

		while (uniqueRandomNumbers.size() < amount) {
			int randomNumber = random.nextInt(i);
			uniqueRandomNumbers.add(randomNumber);
		}

		return uniqueRandomNumbers;
	}

	private ItemStack setHead(HeadData headData, ChatColor textColor) {
		ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
		ItemMeta itemMeta = playerHead.getItemMeta();

		GameProfile profile = new GameProfile(UUID.fromString(headData.getUuid()), null);

		byte[] encodedData = Base64.getEncoder().encode(headData.getValueDecoded().toString().getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;

		try {
			profileField = itemMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(itemMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}

		itemMeta.setDisplayName(textColor + headData.getName());
		

		playerHead.setItemMeta(itemMeta);

		return playerHead;
	}

}
