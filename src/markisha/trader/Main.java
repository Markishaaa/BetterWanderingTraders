package markisha.trader;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import markisha.events.CustomWanderingTrader;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new CustomWanderingTrader(), this);
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[BetterWanderingTraders]: Plugin enabled!");
	}

	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[BetterWanderingTraders]: Plugin disabled!");
	}

}
