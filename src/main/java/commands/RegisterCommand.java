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

        this.registrations = new Registrations(toornamentClient,tournamentId);

    }

    public void sendRegistrationFormToUser(){
        user.openPrivateChannel().queue(chan ->{
            chan.sendMessage("Registration for "+ tournamentNameWithId+" \n write: !register <Your LoL summoner name> <Name of your toornament account> <The email of your toornament account> \n example: !register fakert1 faker faker@gmx.com").queue();
        });
    }

    public void registerUserToTournament(){
        this.toornamentClient.authorize();
        registrations = new Registrations(toornamentClient,tournamentId);
        RegistrationQuery.RegistrationQueryBuilder registrationQuery = RegistrationQuery.builder();
                registrationQuery.name(toornamentName)
                .email(toornamentMail)
                .tournament_id(tournamentId)
                .type(RegistrationType.PLAYER)
                .customField("beschwoerername",summonerName)
                .customField("discordname",user.getId());
        Registration registrationResponse =registrations.register(registrationQuery.build());
        System.out.println(registrationResponse.toString());
        user.openPrivateChannel().queue(chan ->{
            chan.sendMessage("Your registration has been sent").queue();
        });

    }

}
