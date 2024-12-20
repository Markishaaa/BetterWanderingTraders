package markisha.items;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

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
		categoryMap.put(Categories.PLANTS, ApiManager.getPlantList());
		categoryMap.put(Categories.DECORATION, ApiManager.getDecorationList());
	}

	public List<ItemStack> getHeads(int amountPerTrade) {
		List<ItemStack> heads = new ArrayList<>();

		heads.addAll(createHeadsOfCategory(categoryMap.get(Categories.BLOCKS), 10, ChatColor.DARK_AQUA, amountPerTrade));
		heads.addAll(createHeadsOfCategory(categoryMap.get(Categories.ELECTRONICS), 5, ChatColor.RED, amountPerTrade));
		heads.addAll(createHeadsOfCategory(categoryMap.get(Categories.LETTERS), 5, ChatColor.GRAY, amountPerTrade));
		heads.addAll(createHeadsOfCategory(categoryMap.get(Categories.FOOD), 15, ChatColor.YELLOW, amountPerTrade));
		heads.addAll(createHeadsOfCategory(categoryMap.get(Categories.PLANTS), 15, ChatColor.GREEN, amountPerTrade));
		heads.addAll(createHeadsOfCategory(categoryMap.get(Categories.DECORATION), 15, ChatColor.DARK_PURPLE, amountPerTrade));

		return heads;
	}

	private List<ItemStack> createHeadsOfCategory(List<HeadData> headList, int amount, ChatColor textColor, int amountPerTrade) {
		if (headList == null || headList.isEmpty()) {
			return null;
		}

		List<ItemStack> skullList = new ArrayList<>();
		Set<Integer> uniqueRandomNumbers = generateUniqueRandomNumbers(amount, headList.size());

		for (Integer i : uniqueRandomNumbers) {
			skullList.add(makeHead(headList.get(i), textColor, amountPerTrade));
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

	private ItemStack makeHead(HeadData headData, ChatColor textColor, int amountPerTrade) {
		String randomProfileName = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
		PlayerProfile profile = Bukkit.createPlayerProfile(UUID.fromString(headData.getUuid()), randomProfileName);
		ItemStack head = new ItemStack(Material.PLAYER_HEAD, amountPerTrade);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		PlayerTextures textures = profile.getTextures();

		try {
			URL url = new URL(headData.getTextureUrl());
			textures.setSkin(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		profile.setTextures(textures);
		meta.setOwnerProfile(profile);
		meta.setDisplayName(textColor + headData.getName());
		head.setItemMeta(meta);
		return head;
	}
	
}
