package commands;

import help.PrivateConstReader;
import help.RequestsHandler;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterCommand {

    User user;
    String toornamentName;
    String toornamentMail;
    String summonerName;
    String token;
    String tournamentId;
    String key = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\toornament_key.txt");


    public RegisterCommand(User user) {
        this.user = user;
    }

    public RegisterCommand(User user, ArrayList<String> props) {
        this.user = user;
        this.summonerName = props.get(0);
        this.toornamentName = props.get(1);
        this.toornamentMail = props.get(2);
    }

    public void sendRegistrationFormToUser(){
        user.openPrivateChannel().queue(chan ->{
            chan.sendMessage("!register <summonerName> <toornamentName> <toornamentEmail>").queue();
        });
    }

    public void registerUserToTournament(){
        token = RequestsHandler.doPostApiTokenRequest("organizer:registration");
        HashMap<String,String> headers = new HashMap<>();
        headers.put("X-Api-Key",key);
        headers.put("Authorization",token);
        HashMap<String,String> params = new HashMap<>();

        //JSONObject jsonObject = RequestsHandler.doPostRequest("https://api.toornament.com/organizer/v2/tournaments/"+tournamentId+"/registrations",headers,params);
    }

    public User getUser() {
        return user;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public String getToornamenAtName() {
        return toornamentName;
    }

    public String getToornamentMail() {
        return toornamentMail;
    }
}
