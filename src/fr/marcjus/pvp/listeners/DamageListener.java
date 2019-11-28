package fr.marcjus.pvp.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.marcjus.pvp.Gstate;
import fr.marcjus.pvp.Main;

public class DamageListener implements Listener {
	
	private Main main;
	
	public DamageListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		
		Entity ent = e.getEntity();
		
		if(ent instanceof Player){
			if(main.isState(Gstate.STARTING)){
				e.setCancelled(true);
			}
			Player victim = (Player) ent;
			if(victim.getHealth() <= e.getDamage()){
				e.setDamage(0);
				main.eliminate(victim);
			}
		}
		
	}
	
	@EventHandler
	public void onPvp(EntityDamageByEntityEvent e){
		
		Entity ent = e.getEntity();
		
		if(ent instanceof Player){
			
			if(main.isState(Gstate.STARTING)){
				e.setCancelled(true);
			}
			
			Player victim = (Player) ent;
			Entity damager = e.getDamager();
			Player killer = victim;
			
			if(victim.getHealth() <= e.getDamage()){
				if(damager instanceof Player)killer = (Player) damager;
				
			    if(damager instanceof Snowball){
					
					Snowball ball = (Snowball) damager;
					if(ball.getShooter() instanceof Player){
						killer = (Player) ball.getShooter();
					}
					
				}
			    killer.sendMessage("§eTu viens de tuer §2"+victim.getName());
			    victim.sendMessage("§eTu a été tué par §c"+killer.getName());
				e.setDamage(0);
				main.eliminate(victim);
			}
		}
		
	}

}
