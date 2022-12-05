package fr.jacquelinedugame.tournoindi2022;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class CommandFautFaireQuoi implements CommandExecutor {
	private Main main;
	public CommandFautFaireQuoi(Main main) {
		this.main = main;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			Team teamP = main.getScoreboard().getTeam(p);
			switch (main.getScoreboard().getTeamScore(teamP)) {
				case 0 : p.sendMessage("[Tournoi NDI] : §3Votre équipe doit tuer un grand guardien !"); return true;
				case 1 : p.sendMessage("[Tournoi NDI] : §8Votre équipe doit tuer un wither !"); return true;
				case 2 : p.sendMessage("[Tournoi NDI] : §dVotre équipe doit tuer l'ender dragon !"); return true;
				case 3 : p.sendMessage("[Tournoi NDI] : §eVous avez fini le tournoi, bravo !"); return true; 
				default : p.sendMessage("[Tournoi NDI] : §4il y a une erreur, demandez à un administrateur."); return false;
			}
			
		}
		else {
			sender.sendMessage("[Tournoi NDI] seul un joueur peut utiliser cette commande !");
			return false;
		}
	}

}
