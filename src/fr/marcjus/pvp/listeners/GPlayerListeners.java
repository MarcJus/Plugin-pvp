package fr.marcjus.pvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.marcjus.pvp.Gstate;
import fr.marcjus.pvp.Main;
import fr.marcjus.pvp.task.GAutoStartTask;

public class GPlayerListeners implements Listener {
	
	private Main main;

	public GPlayerListeners(Main main) {
		
		this.main = main;
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player player = e.getPlayer();
		Location spawn = new Location(Bukkit.getWorld("world"), 0, 5, 0);
		player.teleport(spawn);
		player.setFoodLevel(20);
		player.setHealth(20);
		player.getInventory().clear();
		
		if(main.isState(Gstate.FINISH) && Bukkit.getOnlinePlayers().size() == 1){
			main.setState(Gstate.WAITING);
		}
		
		if(!main.isState(Gstate.WAITING) && Bukkit.getOnlinePlayers().size() > 1){
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("§cLe jeu a déja démarré");
			e.setJoinMessage(null);
			return;
		}
		
		if(!main.getPlayers().contains(player))main.getPlayers().add(player);
			
		player.setGameMode(GameMode.ADVENTURE);
		e.setJoinMessage("§eSalut a toi §2"+player.getName()+"§e qui vient pvp!");
		
		if(main.isState(Gstate.WAITING) && main.getPlayers().size() == 2){
			GAutoStartTask start = new GAutoStartTask(main);
			start.runTaskTimer(main, 0, 20);
			main.setState(Gstate.STARTING);
			
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		Player player = e.getPlayer();
		if(main.getPlayers().contains(player)){
			main.getPlayers().remove(player);
		}
		
		e.setQuitMessage(player.getName()+" §e a quitté la partie");
		main.checkWin();
		
	}

}
