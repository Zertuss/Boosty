package fr.zertus.bot;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class BotListener implements EventListener{
	
	@Override
	public void onEvent(Event event) {
		System.out.println(event.getClass().getSimpleName());
		if(event instanceof MessageReceivedEvent) {
			onMessageReceived((MessageReceivedEvent) event);
		}
	}

	private void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().equals(event.getJDA().getSelfUser())) return;
		Message message = event.getMessage();
		String msg = message.getContentRaw();
		if(msg.startsWith("=")) {
			message.delete().queue();
			msg = msg.replace("=", "");
			if(msg.equals("help")) {
				EmbedBuilder builder = new EmbedBuilder();
				builder.setTitle("__**Liste des commandes**__");
				builder.setColor(Color.GREEN);
				builder.addField("=infos", "Obtenir les informations sur le serveur", false);
				builder.addField("=addadmin", "Ajouter un admin", false);
				builder.addField("=deladmin", "Retirer un admin", false);
				builder.addField("=delmsg", "Retire les 10 derniers messages", false);
				
				event.getAuthor().openPrivateChannel().complete().sendMessage(builder.build()).complete();
			} else if (msg.equals("=delmsg")) {
				if(BotDiscord.admin.contains(event.getAuthor().getId())) {
					event.getAuthor().openPrivateChannel().complete().sendMessage("Maintenance en cours... Retour bientôt... Ou pas").queue();
				}else {
					event.getAuthor().openPrivateChannel().complete().sendMessage("Vous n'avez pas **la permission** de faire cette **commande** !").complete();
				}
			} else if (msg.equals("infos")) {
				EmbedBuilder builder = new EmbedBuilder();
				builder.setAuthor(event.getGuild().getName(), null, event.getGuild().getIconUrl());
				builder.setTitle("Infos sur "+event.getGuild().getName());
				builder.addBlankField(false);
				builder.addField("Nombres de membres:", ""+event.getGuild().getMembers().size(), false);
				builder.addBlankField(false);
				builder.setFooter(event.getGuild().getCreationTime().toString(), null);
				builder.setColor(Color.RED);
				event.getTextChannel().sendMessage(builder.build()).queue();
			} else if(msg.startsWith("addadmin")) {
				if(BotDiscord.admin.contains(event.getAuthor().getId())) {
					msg = msg.replace("addadmin", "");
					msg = msg.replace(" ", "");
					if(!BotDiscord.admin.contains(msg)) {
						BotDiscord.admin.add(msg);
						event.getTextChannel().sendMessage(event.getGuild().getMemberById(msg).getAsMention()+" vient d'être **ajouté** à la liste des **modos** du __BOT__ !").queue();
					}else {
						event.getAuthor().openPrivateChannel().complete().sendMessage("Cette personne est déjà présente dans la liste des __**modos**__ !").queue();
					}
				}else {
					event.getAuthor().openPrivateChannel().complete().sendMessage("Vous n'avez pas **la permission** de faire cette **commande** !").complete();
				}
			}else if(msg.startsWith("deladmin")) {
				if(BotDiscord.admin.contains(event.getAuthor().getId())) {
					msg = msg.replace("deladmin", "");
					msg = msg.replace(" ", "");
					if(BotDiscord.admin.contains(msg)) {
						BotDiscord.admin.remove(msg);
						event.getTextChannel().sendMessage(event.getGuild().getMemberById(msg).getAsMention()+" vient d'être **enlevé** de la liste des **modos** du __BOT__ !").queue();
					}else {
						event.getAuthor().openPrivateChannel().complete().sendMessage("Cette personne n'est pas dans la liste des __**modos**__ !").queue();
					}
				}else {
					event.getAuthor().openPrivateChannel().complete().sendMessage("Vous n'avez pas **la permission** de faire cette **commande** !").complete();
				}
			}else {
				event.getAuthor().openPrivateChannel().complete().sendMessage("Cette commande est **inconnue**. Tapez **=help** pour plus d'infos.").complete();
			}
		}
	}

}
