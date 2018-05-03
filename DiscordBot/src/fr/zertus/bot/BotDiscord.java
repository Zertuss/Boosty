package fr.zertus.bot;

import java.util.ArrayList;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

public class BotDiscord {

	public static JDA jda;
	private static boolean stop = false;
	public static ArrayList<String> admin = new ArrayList<>();
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			jda = new JDABuilder(AccountType.BOT).setToken("MjUzOTU5OTQyNDgwODU1MDUw.Dcc2-w.pBdgoHoeRQVhJ0RekivzhSkJeto").buildAsync();
			System.out.println("Bot Discord > Connect");
			jda.addEventListener(new BotListener());
			System.out.println("Bot Discord > Event Detector ON");
			jda.getPresence().setStatus(OnlineStatus.ONLINE);
			jda.getPresence().setGame(Game.playing("=help for help"));
			System.out.println("Bot Discord > All status OK");
			BotDiscord.admin.add("181450653703471104");
			System.out.println("Bot Discord > List of admins OK");
			while (!stop) {
	            Scanner scanner = new Scanner(System.in);
	            String cmd = scanner.next();
	            if (cmd.equalsIgnoreCase("stop")) {
	                jda.shutdown();
	                stop = true;
	            }
	        }
		} catch (LoginException e) {
			System.out.println("Bot Discord > Error on Connect");
		}
	}
	
}
