package markisha.trader;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		loadConfig();
		
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[BetterWanderingTraders]: Plugin enabled!");
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[BetterWanderingTraders]: Plugin disabled!");
	}
	
	private void loadConfig() {
        getConfig().options().copyDefaults(false);
        saveConfig();
    }
	
}
