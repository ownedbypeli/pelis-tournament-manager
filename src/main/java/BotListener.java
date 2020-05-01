import commands.GetTournamentCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (checkIfBot(event.getAuthor())) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        Guild guild = event.getGuild();
        if (checkIfCommand(content)) {
            String command = getCommandName(content);
            ArrayList<String> props = getCommandProps(content);
            if (command.equals("!getTournament")) {
                if (props.size() == 1) {
                    MessageChannel channel = event.getChannel();
                    channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
                    GetTournamentCommand gtc = new GetTournamentCommand(props.get(0),guild);
                    gtc.start();
                }else{
                    //Throw exception
                }
            }

        }
    }

    public boolean checkIfCommand(String text) {
        return text.startsWith("!");
    }

    public String getCommandName(String text) {
        String[] words = text.split(" ");

        return words[0];
    }

    public boolean checkIfBot(User writer) {
        return writer.isBot();
    }

    public ArrayList<String> getCommandProps(String text) {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(text.split(" ")));
        words.remove(0);
        return words;
    }


}
