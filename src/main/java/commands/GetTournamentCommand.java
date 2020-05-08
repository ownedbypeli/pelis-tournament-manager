package commands;

import com.toornament.ToornamentClient;
import com.toornament.concepts.Tournaments;
import com.toornament.model.TournamentDetails;
import com.toornament.model.enums.Scope;
import enums.Token;
import help.PrivateConstReader;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;

import java.util.EnumSet;
import java.util.HashSet;

public class GetTournamentCommand {

    String tournamentId;
    Guild guild;
    ToornamentClient toornamentClient;
    Tournaments tournaments;
    TournamentDetails tournament;

    public GetTournamentCommand(String tournamentId, Guild guild) {
        this.tournamentId = tournamentId;
        this.guild = guild;
        HashSet<Scope> scopes = new HashSet<>();
        scopes.add(Scope.ORGANIZER_VIEW);
        this.toornamentClient = new ToornamentClient(Token.API_KEY.getToken(), Token.CLIENT_ID.getToken(),Token.CLIENT_SECRET.getToken(), scopes);
        toornamentClient.authorize();
        this.tournaments = toornamentClient.tournaments();
    }

    public void getTournament() {
        this.tournament = tournaments.getTournament(tournamentId);
        String categoryName = tournament.getFullName() + "     #:" + tournament.getId();
        guild.createCategory(categoryName).queue((cat) -> {
            cat.createTextChannel("Tournament-Information")
                    .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.MESSAGE_WRITE)).setTopic("Here you gonna find all information about this upcoming tournament")
                    .queue(textChan -> {
                        textChan.sendMessage("Hier sollten Turnierinfos angezeigt werden.").queue();
                    });
            cat.createTextChannel("Tournament-Feed").addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.MESSAGE_WRITE)).queue();
            cat.createTextChannel("Tournament-Registration").queue();
            cat.createTextChannel("Tournament-Chat").queue();
        });

    }

}
