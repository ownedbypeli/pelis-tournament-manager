package Listeners;

import commands.GetTournamentCommand;
import commands.RegisterCommand;
import enums.Command;
import help.StringHelper;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (checkIfBot(event.getAuthor())) return;
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        Guild guild = null;
        if (!message.isFromType(ChannelType.PRIVATE)) guild = event.getGuild();
        User user = event.getAuthor();
        String content = message.getContentRaw();
        if (!StringHelper.checkIfCommand(content)) return;
        ArrayList<String> props = StringHelper.getCommandProps(content);
        Command command = Command.getCommandFromString(StringHelper.getCommandName(content));
        switch (Objects.requireNonNull(command)) {
            case GET_TOURNAMENT:
                if (props.size() == 1) {
                    GetTournamentCommand gtc = new GetTournamentCommand(props.get(0), guild);
                    gtc.getTournament();
                    channel.sendMessage("Pong!").queue();
                } else {
                    //Throw exception props length
                }
                break;
            case Register_Participant:
                if (guild == null && props.size() == 3) {
                    PrivateChannel dmChannel = event.getPrivateChannel();
                    MessageHistory messages = dmChannel.getHistoryBefore(message.getId(), 1).complete();
                    String lastMessage = messages.getRetrievedHistory().get(0).getContentRaw();
                    String tournamentId = StringHelper.getFirstWord(lastMessage.substring(lastMessage.indexOf("#:") + 2));
                    RegisterCommand rc = new RegisterCommand(user, props, tournamentId);
                    rc.registerUserToTournament();

                } else if (props.size() == 0) {
                    if (!channel.getName().equals("Tournament-Registration")) {//Need To be fixed
                        String tournamentNameWithId = event.getTextChannel().getParent().getName();
                        RegisterCommand rc = new RegisterCommand(user, tournamentNameWithId);
                        rc.sendRegistrationFormToUser();
                        message.delete().queue();
                    } else {
                        //Throw exception Wrong Channel
                    }
                } else {
                    //Throw exception props length
                }
                break;
            default:
                //Throw exception command doesnt exist
                break;

        }

    }


    public boolean checkIfBot(User writer) {
        return writer.isBot();
    }


}
