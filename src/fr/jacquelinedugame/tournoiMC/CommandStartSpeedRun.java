package fr.jacquelinedugame.tournoindi2022;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandStartSpeedRun implements CommandExecutor {
	private Main main;
	
	public CommandStartSpeedRun(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (main.getState().equals(State.WAITING)){
			main.startGame();
			return true;
		}
		else {
			return false;
		}
	}

}
