package fr.jacquelinedugame.tournoindi2022;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandJoinTeam implements CommandExecutor {
	private Main main;
	private Player player;
	private ScoreboardTournoi SCTournoi;
	
	public CommandJoinTeam(Main main) {
		this.main = main;
		
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) { //On vérifie que c'est un player qui execute la commande
			player = (Player) sender;
		}
		else {
			sender.sendMessage("§6 Seul un joueur peut rejoindre une équipe !");
			return false;
		}
		if (SCTournoi == null) {
			SCTournoi = main.getScoreboard();
		}
		
		if(SCTournoi.isInTeam(player)) {
			player.sendMessage("§6 Vous êtes déjà dans une équipe ! Faites /leaveteam pour la quitter");
			return false;
		}
		
		if (args.length <= 0) { //On vérifie qu'un nom d'équipe à été donnée
			player.sendMessage("§6 Veuillez entrer un nom d'équipe ! ");
			return false;
		}
		
		if (SCTournoi.getTeam(args[0]) == null) { //On vérifie que la team existe
			player.sendMessage("§6 L'équipe §4 " + args[0] + " §6 n'existe pas ! Utilisez /createteam <nom de team> pour la créer");
			return false;
		}
		
		if (SCTournoi.addPlayerToTeam(player, args[0])){ //On ajoute le joueur à la team et on l'informe si il est ajouté ou non
			player.sendMessage("§6 Vous avez bien été ajouté à l'équipe §4 " + args[0]);
			return true;
		}
		else {
			player.sendMessage("§6 Vous êtes déjà dans une équipe ou le nombre maximum de membres dans l'équipe §4 "+args[0]+" §6 à déjà été atteint !");
			return false;
		}
		
	}

}
