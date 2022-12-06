package fr.jacquelinedugame.tournoimc;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin{
	private State gState;
	private ChronoLancement chrono;
	private TimerRespawnDragon timerRespawnDragon;
	private ScoreboardTournoi SCTournoi = null;
	private boolean joinBloque;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		getCommand("startspeedrun").setExecutor(new CommandStartSpeedRun(this));
		getCommand("createteam").setExecutor(new CommandCreateTeam(this));
		getCommand("jointeam").setExecutor(new CommandJoinTeam(this));
		getCommand("leaveteam").setExecutor(new CommandLeaveTeam(this));
		getCommand("finishspeedrun").setExecutor(new CommandFinishSpeedRun(this));
		getCommand("sendcoords").setExecutor(new CommandSendCoords(this));
		getCommand("resetspeedrun").setExecutor(new CommandResetSpeedRun(this));
		getCommand("fautfairequoi").setExecutor(new CommandFautFaireQuoi(this));
		getCommand("stopjoin").setExecutor(new CommandStopJoin(this));
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		this.chrono = new ChronoLancement(this);
		this.gState = State.WAITING;
		this.joinBloque = false;
	}
	
	public void startGame() {
		if(this.SCTournoi == null) { //si le scoreboard n'a pas été initialisé
			this.SCTournoi = new ScoreboardTournoi(this);
		}
		
		for (Player p : this.getPlayers()) {
			if (!SCTournoi.isInTeam(p) && !(p.isOp())) { //On verifie que tout les joueurs sont dans une team
				String nomNewTeam = p.getName();
				SCTournoi.addTeam("~"+nomNewTeam);
				SCTournoi.addPlayerToTeam(p, "~"+nomNewTeam);
			}
			if(p.getScoreboard() != SCTournoi.getScoreboard()) { //si les joueurs ne sont pas avec le bon scoreboard
				p.setScoreboard(SCTournoi.getScoreboard());
				p.sendMessage("[TournoiNDI] scoreboard actualisé!");
			}
			p.setFoodLevel(20);
			p.setHealth(20.0);
			this.joinBloque = true;
		}
		this.chrono.runTaskTimer(this, 0, 20);
		gState = State.STARTED;
	}
	
	public void resetGame() {
		this.getScoreboard().resetScoreboard();
		this.getConfig().set("classement.guardian", new ArrayList<>());
		this.getConfig().set("classement.wither", new ArrayList<>());
		this.getConfig().set("classement.enderdragon", new ArrayList<>());
		this.saveConfig();
		this.gState = State.WAITING;
	}
	
	public void sendMessageToEveryone(String msg) {
		for (Player p : this.getPlayers()) {
			p.sendMessage(msg);
		}
	}
	
	public boolean blocageJoin() {
		this.joinBloque = !this.joinBloque;
		return this.joinBloque;
	}
	
	public void playSoundToEveryone(Sound sound) {
		for (Player p : this.getPlayers()) {
			p.playSound(p.getLocation(), sound, 100, 100);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendTitleAndSoundToEveryone(String title, String subtitle, Sound sound) {
		for (Player p : this.getPlayers()) {
			p.sendTitle(title, subtitle);
			p.playSound(p.getLocation(), sound, 100, 100);
		}
	}
	
	public void sendMessageAndSoundToEveryone(String msg, Sound sound) {
		for (Player p : this.getPlayers()) {
			p.sendMessage(msg);
			p.playSound(p.getLocation(), sound, 100, 100);
		}
	}
	
	public State getState() {
		return this.gState;
	}
	
	public boolean getJoinBloque() {
		return this.joinBloque;
	}
	
	public World getWorld() {
		return Bukkit.getWorld("world");
	}
	public List<Player> getPlayers() {
		return new ArrayList<>(this.getServer().getOnlinePlayers());
	}
	public ScoreboardTournoi getScoreboard() {
		if (this.SCTournoi == null) {
			this.SCTournoi = new ScoreboardTournoi(this);
		}
		return this.SCTournoi;
	}
	public void guardianKilled(Player killer) {
		Team teamKiller = SCTournoi.getTeam(killer);
		if (SCTournoi.getTeamScore(teamKiller) == 0) {
			SCTournoi.setTeamScore(teamKiller, 1);
			SCTournoi.setTeamColor(teamKiller, ChatColor.DARK_AQUA);
			teamKiller.setPrefix("§3"+teamKiller.getName()+"§r");
			this.sendMessageAndSoundToEveryone("§3 "+teamKiller.getName()+" ont tué un grand guardien !", Sound.ENTITY_ELDER_GUARDIAN_CURSE);
			
			List<String> classementGuardian = this.getConfig().getStringList("classement.guardian"); //on sauvegarde dans le config pour le classement
			classementGuardian.add(teamKiller.getName());
			this.getConfig().set("classement.guardian", classementGuardian);
			this.saveConfig();
		}
	}
	
	public void witherKilled(Player killer) {
		Team teamKiller = SCTournoi.getTeam(killer);
		if (SCTournoi.getTeamScore(teamKiller) == 1) {
			SCTournoi.setTeamScore(teamKiller, 2);
			SCTournoi.setTeamColor(teamKiller, ChatColor.DARK_GRAY);
			teamKiller.setPrefix("§8"+teamKiller.getName()+"§r");
			this.sendMessageAndSoundToEveryone("§8 "+teamKiller.getName()+" ont tué un WITHER !", Sound.ENTITY_WITHER_SPAWN);
			
			List<String> classementWither = this.getConfig().getStringList("classement.wither"); //on sauvegarde dans le config pour le classement
			classementWither.add(teamKiller.getName());
			this.getConfig().set("classement.wither", classementWither);
			
			List<String> classementGuardian = this.getConfig().getStringList("classement.guardian"); //on supprime l'equipe de son ancien classement
			classementGuardian.remove(teamKiller.getName());
			this.getConfig().set("classement.guardian", classementGuardian);
			
			this.saveConfig();
		}		
	}
	@SuppressWarnings("deprecation")
	public void enderDragonKilled(Player killer) {
		Team teamKiller = SCTournoi.getTeam(killer);
		if (SCTournoi.getTeamScore(teamKiller) == 2) {
			SCTournoi.setTeamScore(teamKiller, 3);
			SCTournoi.setTeamColor(teamKiller, ChatColor.DARK_PURPLE);
			SCTournoi.setSpecialPrefix(teamKiller);
			this.sendTitleAndSoundToEveryone("§4 "+teamKiller.getName(),"§d ont tué l'ender Dragon !", Sound.ENTITY_ENDER_DRAGON_DEATH);
			
			List<String> classementEnderDragon = this.getConfig().getStringList("classement.enderdragon"); //on sauvegarde dans le config pour le classement
			classementEnderDragon.add(teamKiller.getName());
			this.getConfig().set("classement.enderdragon", classementEnderDragon);
			
			List<String> classementWither = this.getConfig().getStringList("classement.wither"); //on supprime l'equipe de son ancien classement
			classementWither.remove(teamKiller.getName());
			this.getConfig().set("classement.wither", classementWither);
			
			this.saveConfig();
			
			for (OfflinePlayer p : teamKiller.getPlayers()) { //On met en spectate les membres de l'équipe gagnantes
				Player unPlayer = (Player) p;
				unPlayer.setGameMode(GameMode.SPECTATOR);
			}
		}
	}
	
	public void startRespawnEnderDragon() {
		this.timerRespawnDragon = new TimerRespawnDragon(this);
		this.timerRespawnDragon.runTaskTimer(this, 0, 20);
	}
	
	public void respawnEnderDragon() {
		World worldEnder = Bukkit.getWorld("world_the_end");
		Location locationPortal = worldEnder.getEnderDragonBattle().getEndPortalLocation();
		worldEnder.spawnEntity(new Location(locationPortal.getWorld(), locationPortal.getX()-2.5, locationPortal.getY()+1, locationPortal.getZ()+0.5), EntityType.ENDER_CRYSTAL);
		worldEnder.spawnEntity(new Location(locationPortal.getWorld(), locationPortal.getX()+3.5, locationPortal.getY()+1, locationPortal.getZ()+0.5), EntityType.ENDER_CRYSTAL);
		worldEnder.spawnEntity(new Location(locationPortal.getWorld(), locationPortal.getX()+0.5, locationPortal.getY()+1, locationPortal.getZ()-2.5), EntityType.ENDER_CRYSTAL);
		worldEnder.spawnEntity(new Location(locationPortal.getWorld(), locationPortal.getX()+0.5, locationPortal.getY()+1, locationPortal.getZ()+3.5), EntityType.ENDER_CRYSTAL);
		DragonBattle db = worldEnder.getEnderDragonBattle();
		db.initiateRespawn();
	}
	
	public void finish() {
		this.gState = State.FINISH;
		this.sendTitleAndSoundToEveryone("§7Game over", "§7Le tournoi est terminé !", Sound.BLOCK_BELL_USE);
		this.playSoundToEveryone(Sound.MUSIC_CREDITS);
		List<String> classementFinal = this.classementFinal();
		for(Player p: this.getPlayers()) {
			p.sendMessage("§4----------------------------------------");
			p.sendMessage("§4----------§eVoici le classement final§4----------");
			p.sendMessage("§4----------------------------------------");
			int cpt = 1;
			for (String team : classementFinal) {
				p.sendMessage("§e"+String.valueOf(cpt)+". "+team);
				cpt+=1;
			}
		}
	}
	
	private List<String> classementFinal(){
		List<String> classementFinal = new ArrayList<>();
		List<String> cClassementGuardian = this.getConfig().getStringList("classement.guardian");
		List<String> cClassementWither = this.getConfig().getStringList("classement.wither");
		List<String> cClassementEnderDragon = this.getConfig().getStringList("classement.enderdragon");
		
		classementFinal.addAll(cClassementEnderDragon);		
		classementFinal.addAll(cClassementWither);
		classementFinal.addAll(cClassementGuardian);
		
		return classementFinal;
	}
}
