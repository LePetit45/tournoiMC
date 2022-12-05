package fr.jacquelinedugame.tournoindi2022;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class CommandSendCoords implements CommandExecutor {
	private Main main;
	
	public CommandSendCoords(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			if (main.getState() != State.WAITING) {
				Player player = (Player) sender;
				Team teamPlayer = main.getScoreboard().getTeam(player);
				Location locationPlayer = player.getLocation();
				for (OfflinePlayer p : teamPlayer.getPlayers()) {
					Player unPlayer = (Player) p;
					unPlayer.sendMessage("§6Coordonés de "+player.getName()+" :");
					unPlayer.sendMessage("§6Monde : "+locationPlayer.getWorld().getName()+", X: "+locationPlayer.getBlockX()+", Y: "+locationPlayer.getBlockY()+", Z: "+locationPlayer.getBlockZ());
				}
				return true;
			}
			return false;
		} else {
			sender.sendMessage("Seul un joueur peut utiliser cette commande !");
			return false;
		}
	}

}
