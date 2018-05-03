package fr.zertus.bot.music;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class MusicCommand {
	
	public static MusicManager manager = new MusicManager();

	public static void play(Guild guild, TextChannel textChannel, User user, String command) {
		if(guild == null) return;
		if(!guild.getAudioManager().isConnected() && !guild.getAudioManager().isAttemptingToConnect()){
			VoiceChannel voiceChannel = guild.getMember(user).getVoiceState().getChannel();
			if(voiceChannel == null){
				textChannel.sendMessage("**[BoostySound]** Vous devez être connecté à un salon vocal.").queue();
				return;
			}
			guild.getAudioManager().openAudioConnection(voiceChannel);
		}
		
		manager.loadTrack(textChannel, command.replaceFirst("play ", ""));
	}
	
	public static void skip(Guild guild, TextChannel textChannel) {
		if(!guild.getAudioManager().isConnected() && !guild.getAudioManager().isAttemptingToConnect()){
			textChannel.sendMessage("**[BoostySound]** Le player n'as pas de piste en cours.").queue();
			return;
		}
		
		manager.getPlayer(guild).skipTrack();
		textChannel.sendMessage("**[BoostySound]** La lecture est passé à la piste suivante.").queue();
	}
	
	public static void clear(TextChannel textChannel) {
		MusicPlayer player = manager.getPlayer(textChannel.getGuild());
		if(player.getListener().getTracks().isEmpty()){
			textChannel.sendMessage("**[BoostySound]** Il n'y a pas de piste dans la liste d'attente.").queue();
			return;
		}
		player.getListener().getTracks().clear();
		textChannel.sendMessage("**[BoostySound]** La liste d'attente à été vidé.").queue();
	}
	
}
