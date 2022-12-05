package fr.jacquelinedugame.tournoindi2022;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCreateTeam implements CommandExecutor {
	private Main main;
	private Player player;
	private ScoreboardTournoi SCTournoi;
	
	public CommandCreateTeam(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		else {
			sender.sendMessage("§6 Seul un joueur peut créer une équipe !");
			return false;
		}
		
		if (SCTournoi == null) {
			SCTournoi = main.getScoreboard();
		}
		
		if (args.length <= 0) { //On vérifie qu'un nom d'équipe à été donnée
			player.sendMessage("§6 Veuillez entrer un nom d'équipe ! ");
			return false;
		}
		
		if (args.length > 1) { //On vérifie qu'un nom d'équipe à été donnée
			player.sendMessage("§6 Veuillez entrer un nom d'équipe sans espaces ! ");
			return false;
		}
		
		if(args[0].length() > 16) {
			player.sendMessage("§6 Le nom de l'équipe est trop long ! (max 16 caractères)");
			return false;
		}
		
		if(SCTournoi.isInTeam(player)) { //On vérifie si le joueur est dajà dans une équipe
			player.sendMessage("§6 Vous êtes déjà dans une équipe ! Faites /leaveteam pour la quitter");
			return false;
		}
		
		try {
			SCTournoi.addTeam(args[0]);
			SCTournoi.addPlayerToTeam(player, args[0]);
			player.sendMessage("§6 Votre équipe §4 " + args[0] + " §6 à bien été crée et vous y avez été ajouté !");
			return true;
		} catch (Exception e) {
			player.sendMessage("§6 Une erreur s'est produite pendant la création de l'équipe. Une équipe doit déjà s'appeler par le nom donnée.");
			e.printStackTrace();
			return false;
		}
		
		
	}

}
