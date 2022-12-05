package fr.jacquelinedugame.tournoindi2022;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandResetSpeedRun implements CommandExecutor {
	private Main main;
	public CommandResetSpeedRun(Main main) {
		this.main = main;
	}
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		try {
			main.resetGame();
			arg0.sendMessage("[Tournoi NDI] réinitialisation OK !");
			return true;
		} catch (Exception e) {
			arg0.sendMessage("[Tournoi NDI] Erreur lors du reload de game!");
			return false;
		}
	}

}
