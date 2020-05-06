package commands;

import com.toornament.ToornamentClient;
import com.toornament.model.enums.Scope;
import enums.Token;
import help.PrivateConstReader;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RegisterCommand {

    User user;
    String toornamentName;
    String toornamentMail;
    String summonerName;

    String tournamentId;
    ToornamentClient toornamentClient;


    public RegisterCommand(User user) {
        this.user = user;
    }

    public RegisterCommand(User user, ArrayList<String> props) {
        this.user = user;
        this.summonerName = props.get(0);
        this.toornamentName = props.get(1);
        this.toornamentMail = props.get(2);
        HashSet<Scope> scopes = new HashSet<>();
        scopes.add(Scope.ORGANIZER_REGISTRATION);
        this.toornamentClient = new ToornamentClient(Token.API_KEY.getToken(), Token.CLIENT_ID.getToken(),Token.CLIENT_SECRET.getToken(), scopes);
    }

    public void sendRegistrationFormToUser(){
        user.openPrivateChannel().queue(chan ->{
            chan.sendMessage("!register <summonerName> <toornamentName> <toornamentEmail>").queue();
        });
    }

    public void registerUserToTournament(){

    }

}
