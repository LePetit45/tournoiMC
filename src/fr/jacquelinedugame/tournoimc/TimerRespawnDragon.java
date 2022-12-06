package fr.jacquelinedugame.tournoimc;

import org.bukkit.scheduler.BukkitRunnable;

public class TimerRespawnDragon extends BukkitRunnable{
	private Main main;
	private int cpt;
	
	public TimerRespawnDragon(Main main) {
		this.main = main;
		this.cpt = 80;
	}
	@Override
	public void run() {
		if (cpt == 80) {
			main.sendMessageToEveryone("§7Le dragon va réapparaître dans " + String.valueOf(cpt) + " secondes !");
		}
		else if(cpt == 60) {
			main.sendMessageToEveryone("§7Le dragon va réapparaître dans " + String.valueOf(cpt) + " secondes !");
		}
		else if(cpt == 30) {
			main.sendMessageToEveryone("§7Le dragon va réapparaître dans " + String.valueOf(cpt) + " secondes !");
		}
		else if(cpt ==10) {
			main.sendMessageToEveryone("§7Le dragon va réapparaître dans " + String.valueOf(cpt) + " secondes !");
		}
		else if (cpt <= 5) {
			main.sendMessageToEveryone("§7Le dragon va réapparaître dans " + String.valueOf(cpt) + " secondes !");
		}
		cpt-=1;
		if(cpt == 0) {
			main.respawnEnderDragon();
			this.cancel();
		}
	}

}
