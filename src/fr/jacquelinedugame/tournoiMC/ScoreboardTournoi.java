package fr.jacquelinedugame.tournoindi2022;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardTournoi{
	private Scoreboard scoreboard;
	private Objective lesEquipes;
	private Main main;
	private FileConfiguration config;
	private int maxTeamPlayers;
	
	public ScoreboardTournoi(Main main) {
		this.main = main;
		this.config = this.main.getConfig();
		this.maxTeamPlayers = this.config.getInt("effectifEquipe");
		this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		this.lesEquipes = scoreboard.getObjective("teams");
		if (lesEquipes == null) {
			this.prepareScoreBoard();
		}
		
	}
	
	public void addTeam(String nomTeam) {
		Team team = scoreboard.registerNewTeam(nomTeam);
		team.setPrefix(nomTeam);
		team.setDisplayName(nomTeam);
		Score score = lesEquipes.getScore(team.getDisplayName());
		score.setScore(0);
	}
	
	@SuppressWarnings("deprecation")
	public boolean addPlayerToTeam(Player player, String nomTeam) {
		Team laTeam = this.getTeam(nomTeam);
		if(!(laTeam.getPlayers().size() >= maxTeamPlayers)) {
		    laTeam.addPlayer(player);
		    return true;
		}
		else {
			return false;
		}
	}
	
	public int getTeamScore(Team team) {
		Score score = lesEquipes.getScore(team.getDisplayName());
		return score.getScore();
	}
	
	public void setTeamScore(Team team, int newScore) {
		Score score = lesEquipes.getScore(team.getDisplayName());
		score.setScore(newScore);
	}
	
	@SuppressWarnings("deprecation")
	public boolean leavePlayerTeam(Player player) {
		if (this.isInTeam(player)) {
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
			Team team = scoreboard.getPlayerTeam(offlinePlayer);
			team.removePlayer(offlinePlayer);
			if(team.getPlayers().size() == 0) {
				scoreboard.resetScores(team.getDisplayName());;
				team.unregister();
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setSpecialPrefix(Team team) {
		team.setPrefix("§4§kABC§r§5"+team.getName()+"§4§kABC§r");
	}
	
	public Team getTeam(String nomTeam) {
		return scoreboard.getTeam(nomTeam);
	}
	
	@SuppressWarnings("deprecation")
	public Team getTeam(Player player) {
		return scoreboard.getPlayerTeam(player);
	}
	
	public Set<Team> getTeams(){
		return scoreboard.getTeams();
	}
	
	@SuppressWarnings("deprecation")
	public boolean isInTeam(Player player) {
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
		for (Team team : scoreboard.getTeams()) { //On vérifie que le joueur n'est pas dans une autre team
			if (team.getPlayers().contains(offlinePlayer)) {
				return true;
			}
		}
		return false;
	}
	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}

	public void setTeamColor(Team team, ChatColor color) {
		team.setColor(color);
		if(color == ChatColor.DARK_PURPLE) {
			team.setDisplayName("§5"+team.getName()+"§r");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void prepareScoreBoard() {
		this.lesEquipes = scoreboard.registerNewObjective("teams", "dummy");
		this.lesEquipes.setDisplayName("§7--------§fLes équipes§7--------");
		this.lesEquipes.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	public void resetScoreboard() {
		for (Team t : this.scoreboard.getTeams()) {
			this.scoreboard.resetScores(t.getDisplayName());
			t.unregister();
		}
	}
}