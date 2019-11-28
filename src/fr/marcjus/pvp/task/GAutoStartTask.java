package fr.marcjus.pvp.task;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.marcjus.pvp.Gstate;
import fr.marcjus.pvp.Main;

public class GAutoStartTask extends BukkitRunnable{
	
	private Main main;
	
	public GAutoStartTask(Main main) {
		this.main = main;
	}
	
	int timer = 10;
	
	@Override
	public void run() {
		
		if(timer == 10 || timer == 5 || timer == 4 ||timer == 3 || timer == 2 || timer == 1){
			Bukkit.broadcastMessage("Il reste "+timer+"s");
		}
		if(timer == 0){
			Bukkit.broadcastMessage("Lancement du jeu!");
			main.setState(Gstate.PLAYING);
			
			for (int i = 0; i < main.getPlayers().size(); i++){
				Location spawn = main.getSpawns().get(i);
				Player player = main.getPlayers().get(i);
				player.teleport(spawn);
				player.getInventory().clear();
				player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
				ItemStack gun = new ItemStack(Material.DIAMOND_HOE);
				ItemMeta meta = gun.getItemMeta();
				meta.setDisplayName("§4Pistolet");
				meta.setLore(Arrays.asList("Appuyez pour tirer", "Ne marche pas sur en cliquant sur les blocs", "Tapez l'entité pour la faire reculer loin(très)"));
				meta.addEnchant(Enchantment.KNOCKBACK, 5, true);
				gun.setItemMeta(meta);
				player.getInventory().addItem(gun);
				player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
				player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
				player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_BOOTS));
			}
			
			cancel();
		}
		
		
		timer --;
		
	}

}
