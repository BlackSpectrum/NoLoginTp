package nologintp;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NoLoginTp extends JavaPlugin implements Listener{
	
	@Override
	public void onDisable() {	
		
	}

	@Override
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		 pm.registerEvents( this,  this);
	}
	
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onLogout(PlayerQuitEvent event)
	{
		event.getPlayer().setMetadata("logOutPos", new FixedMetadataValue(this, event.getPlayer().getLocation()));			
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onLogin(PlayerJoinEvent event)
	{
		if(event.getPlayer().hasMetadata("logOutPos"))
		{
			Location loc = null;
			for(MetadataValue meta : event.getPlayer().getMetadata("logOutPos"))
				if(meta.getOwningPlugin().equals(this))
					loc = (Location) meta.value();
			
			if(!loc.equals(event.getPlayer().getLocation()))
			{
				event.getPlayer().teleport(loc);
				this.getServer().getLogger().info(event.getPlayer().getName() + " logout and login locations didnt match. He got teleported.");
			}
			event.getPlayer().removeMetadata("logOutPos", this);
			
			
		}
		
	}
	

}
