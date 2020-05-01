import help.PrivateConstReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Application {


    public static void main(String[] args) throws Exception {
        final String  BOT_TOKEN = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\bot_token.txt");
        JDA jda = JDABuilder.createDefault(BOT_TOKEN).addEventListeners(new BotListener()).build();

    }



}
