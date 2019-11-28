package fr.marcjus.pvp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.marcjus.pvp.listeners.DamageListener;
import fr.marcjus.pvp.listeners.GPlayerListeners;

public class Main extends JavaPlugin {
	
	private Gstate state;
	private List<Location> spawns = new ArrayList<>();
	private List<Player> players = new ArrayList<>();
	
	@Override
	public void onEnable() {
		World world = Bukkit.getWorld("world");
		spawns.add(new Location(world, -5, 4, -3, -45f, 0f));
		spawns.add(new Location(world, 5, 4, 3, 135f, 0f));
		setState(Gstate.WAITING);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GPlayerListeners(this), this);
		pm.registerEvents(new DamageListener(this), this);
		
	}

	public boolean isState(Gstate state){
		return this.state == state;
	}

	public void setState(Gstate state) {
		this.state = state;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Location> getSpawns() {
		return spawns;
	}

	public void setLocations(List<Location> locations) {
		this.spawns = locations;
	}

	public void eliminate(Player victim) {
		
		if(players.contains(victim))players.remove(victim);
		victim.setGameMode(GameMode.SPECTATOR);
		checkWin();
		
	}

	public void checkWin() {
		
		if(players.size() == 1){
			Player winner = players.get(0);
			Bukkit.broadcastMessage("§2"+winner.getName()+ " §egagne la partie!");
			setState(Gstate.FINISH);
		}
		
	}

}
