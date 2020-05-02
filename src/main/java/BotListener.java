import commands.GetTournamentCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONException;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        try {
            if (checkIfBot(event.getAuthor())) return;
            String content = message.getContentRaw();
            if (checkIfCommand(content)) {
                String command = getCommandName(content);
                ArrayList<String> props = getCommandProps(content);
                if (command.equals("!getTournament")) {
                    if (props.size() == 1) {
                        GetTournamentCommand gtc = new GetTournamentCommand(props.get(0),guild);
                        gtc.start();
                        channel.sendMessage("Pong!").queue();
                    }else{
                        //Throw exception
                    }
                }

            }
        } catch (JSONException e) {
            channel.sendMessage(e.toString()).queue();
        }catch (IOException e){
            e.printStackTrace();
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
