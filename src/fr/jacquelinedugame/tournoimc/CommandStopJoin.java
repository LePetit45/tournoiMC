package fr.jacquelinedugame.tournoimc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandStopJoin implements CommandExecutor {
	private Main main;
	public CommandStopJoin(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (main.blocageJoin()) {
			sender.sendMessage("[Tournoi NDI] Le blocage des joueurs est activé !");
		}
		else {
			sender.sendMessage("[Tournoi NDI] Le blocage des joueurs est désactivé !");
		}
		return false;
	}

}
