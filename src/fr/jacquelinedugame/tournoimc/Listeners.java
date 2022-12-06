package fr.jacquelinedugame.tournoimc;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener {
	private Main main;
	private Random random;
	private boolean neverInNether;
	private List<Player> playersInEnder;
	public Listeners(Main main) {
		this.main = main;
		this.random = new Random();
		this.playersInEnder = new ArrayList<>();
		this.neverInNether = true;
	}
	@EventHandler
	public void estDansLenderOuNether(PlayerPortalEvent event) {
		if(main.getState().equals(State.STARTED)) {
			Player lePlayer = event.getPlayer();
			if (event.getCause().equals(TeleportCause.END_PORTAL) && !(lePlayer.isOp()) && !(playersInEnder.contains(lePlayer))){
				main.sendTitleAndSoundToEveryone("§5" + lePlayer.getName(), " est dans l'ender !", Sound.ENTITY_ENDER_DRAGON_GROWL);
				playersInEnder.add(lePlayer);
			}
			else if (event.getCause().equals(TeleportCause.NETHER_PORTAL) && !(lePlayer.isOp()) && neverInNether){
				main.sendTitleAndSoundToEveryone("§c" + lePlayer.getName(), " est dans le nether !", Sound.ENTITY_BLAZE_DEATH);
				neverInNether = false;
			}
		}
	}
	
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event) {		
		Player lePlayer = event.getPlayer();
		if (main.getState().equals(State.WAITING) && !(lePlayer.isOp())) {
			lePlayer.setGameMode(GameMode.ADVENTURE);
		}
		else if(main.getJoinBloque() && !(main.getScoreboard().isInTeam(lePlayer)) && !(lePlayer.isOp())) {
			lePlayer.kickPlayer("Le tournoi à déjà commencé !");
		}
	}
	
	@EventHandler
	public void entityKilled(EntityDeathEvent event) {
		if(main.getState().equals(State.STARTED)) {
			LivingEntity victim = event.getEntity();
			if (victim.getType().equals(EntityType.ENDER_DRAGON)) {
				main.startRespawnEnderDragon();
			}
			if (victim.getType().equals(EntityType.WITHER_SKELETON)) { //on modifie la probabilité de drop des têtes de wither
				List<ItemStack> drops = event.getDrops();
				for (ItemStack item : drops) { //On cherche l'itemstack des têtes de wither pour le supprimer
					if (item.getType().equals(Material.WITHER_SKELETON_SKULL)) {
						return; //Le jeu à déjà donné une tête, donc on ne fait rien
					}
				}
				if (random.nextInt(101) <= main.getConfig().getInt("tauxDropTeteWither")) { //on utilise nos probabilités pour drop ou non la tête
					drops.add(new ItemStack(Material.WITHER_SKELETON_SKULL));					
				}
			}
			
			else if (victim.getType().equals(EntityType.ELDER_GUARDIAN)){
				if (victim.getKiller() instanceof Player) {
					Player killer = (Player) victim.getKiller();
					main.guardianKilled(killer);
				}
			}
			else if (victim.getType().equals(EntityType.WITHER)) {
				if (victim.getKiller() instanceof Player) {
					Player killer = (Player) victim.getKiller();
					main.witherKilled(killer);
				}
			}
			else if (victim.getType().equals(EntityType.ENDER_DRAGON)) {
				if (victim.getKiller() instanceof Player) {
					Player killer = (Player) victim.getKiller();
					main.enderDragonKilled(killer);
				}
			}
		}
	}
}
