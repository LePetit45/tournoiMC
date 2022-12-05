package fr.jacquelinedugame.tournoindi2022;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
public class ChronoLancement extends BukkitRunnable{
	private Main main;
	private int cpt;
	public ChronoLancement(Main main) {
		this.main = main;		
		cpt = 6;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		cpt-=1;
		if (cpt > 0) {
			for (Player player : main.getPlayers()) {
				player.sendTitle("La partie commence dans", String.valueOf(cpt));
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 100);
			}
		}
		else {
			for (Player player : main.getPlayers()) {
				player.sendTitle("Le speedrun commence !!", "Bonne chance !");
				player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 100, 100);
				if (!(player.isOp())){
					player.setGameMode(GameMode.SURVIVAL);
				}
			}
			main.getWorld().getWorldBorder().setSize(10000000000.0);
			this.cancel();
		}
	}
	
}