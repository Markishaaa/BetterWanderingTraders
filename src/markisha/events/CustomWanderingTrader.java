package markisha.events;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public class CustomWanderingTrader implements Listener {

	@EventHandler
	public void onTraderSpawn(EntitySpawnEvent event) {
		if (event.getEntityType() == EntityType.WANDERING_TRADER) {
			WanderingTrader trader = (WanderingTrader) event.getEntity();
			setupCustomTrades(trader);
		}
	}

	private void setupCustomTrades(WanderingTrader trader) {
		MerchantRecipe customTrade = new MerchantRecipe(new ItemStack(Material.ACACIA_BOAT), 1);

		// Add custom trades
		trader.getRecipes().add(customTrade);
	}

}
