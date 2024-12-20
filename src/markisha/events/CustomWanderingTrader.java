package markisha.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import markisha.items.CustomHead;

public class CustomWanderingTrader implements Listener {

	private CustomHead ch;

	public CustomWanderingTrader() {
		ch = new CustomHead();
	}

	@EventHandler
	public void onTraderSpawn(CreatureSpawnEvent event) {
		if (event.getEntityType() == EntityType.WANDERING_TRADER) {
			WanderingTrader trader = (WanderingTrader) event.getEntity();
			setupCustomTrades(trader);
		}
	}

	private void setupCustomTrades(WanderingTrader trader) {
		List<ItemStack> heads = ch.getHeads(4);
		List<MerchantRecipe> trades = new ArrayList<>();

		ItemStack price = new ItemStack(Material.EMERALD, 2);

		for (ItemStack item : heads) {
			MerchantRecipe customTrade = new MerchantRecipe(item, 15);
			customTrade.addIngredient(price);

			trades.add(customTrade);
		}

		trader.setRecipes(trades);
	}

}
