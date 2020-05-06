package Listeners;

import commands.GetTournamentCommand;
import commands.RegisterCommand;
import help.StringHelper;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import enums.Command;
import org.json.JSONException;

import java.util.ArrayList;

public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (checkIfBot(event.getAuthor())) return;
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        Guild guild = null;
        if (!message.isFromType(ChannelType.PRIVATE))guild = event.getGuild();
        User user = event.getAuthor();
        try {
            String content = message.getContentRaw();
            if (StringHelper.checkIfCommand(content)) {
                String command = StringHelper.getCommandName(content);
                ArrayList<String> props = StringHelper.getCommandProps(content);
                if (command.equals(Command.GET_TOURNAMENT.getCommand())) {
                     if (props.size() == 1) {
                       GetTournamentCommand gtc = new GetTournamentCommand(props.get(0), guild);
                        gtc.getTournament();
                        channel.sendMessage("Pong!").queue();
                    } else {
                         //Throw exception props length
                    }
                } else if (command.equals(Command.Register_Participant.getCommand())) {
                    if (guild == null && props.size() == 3) {

                            RegisterCommand rc = new RegisterCommand(user, props);
                            rc.sendRegistrationFormToUser();

                    } else  if (props.size() == 0){
                        RegisterCommand rc = new RegisterCommand(user);
                        if (!channel.getName().equals("Tournament-Registration")) {

                                rc.sendRegistrationFormToUser();

                        } else {
                            //Throw exception Wrong Channel
                        }
                    }else {
                        //Throw exception props length
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfBot(User writer) {
        return writer.isBot();
    }


}
