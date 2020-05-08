package commands;

import com.toornament.ToornamentClient;
import com.toornament.concepts.Registrations;
import com.toornament.model.Custom.Custom;
import com.toornament.model.Custom.CustomFields;
import com.toornament.model.Registration;
import com.toornament.model.enums.CustomFieldTargetType;
import com.toornament.model.enums.RegistrationType;
import com.toornament.model.enums.Scope;
import com.toornament.model.request.RegistrationQuery;
import enums.Token;
import help.PrivateConstReader;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.User;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RegisterCommand {

    User user;
    String toornamentName;
    String toornamentMail;
    String summonerName;
    String tournamentId;
    String tournamentNameWithId;
    ToornamentClient toornamentClient;
    Registrations registrations;



    public RegisterCommand(User user, String tournamentNameWithId) {
        this.user = user;
        this.tournamentNameWithId = tournamentNameWithId;
    }

    public RegisterCommand(User user, ArrayList<String> props,String tournamentId) {
        this.user = user;
        this.tournamentId = tournamentId;
        this.summonerName = props.get(0);
        this.toornamentName = props.get(1);
        this.toornamentMail = props.get(2);
        HashSet<Scope> scopes = new HashSet<>();
        scopes.add(Scope.ORGANIZER_REGISTRATION);
        this.toornamentClient = new ToornamentClient(Token.API_KEY.getToken(), Token.CLIENT_ID.getToken(),Token.CLIENT_SECRET.getToken(), scopes);
        this.toornamentClient.authorize();
        this.registrations = new Registrations(toornamentClient,tournamentId);

    }

    public void sendRegistrationFormToUser(){
        user.openPrivateChannel().queue(chan ->{
            chan.sendMessage("Registration for "+ tournamentNameWithId+" \n!register <summonerName> <toornamentName> <toornamentEmail>").queue();
        });
    }

    public void registerUserToTournament(){
        registrations = new Registrations(toornamentClient,tournamentId);
        RegistrationQuery registrationQuery = RegistrationQuery
                .builder()
                .name(toornamentName)
                .email(toornamentMail)
                .tournament_id(tournamentId)
                .type(RegistrationType.PLAYER)
                .customField("beschwoerername",summonerName)
                .customField("discordname",user.getId())
                .build();
        Registration registrationResponse =registrations.register(registrationQuery);
        registrationResponse.toString();

    }

}
