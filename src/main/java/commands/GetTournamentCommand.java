package commands;

import com.toornament.ToornamentClient;
import com.toornament.concepts.Tournaments;
import com.toornament.model.TournamentDetails;
import com.toornament.model.enums.Scope;
import enums.Token;
import help.PrivateConstReader;
import help.StringHelper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;

import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.HashSet;

public class GetTournamentCommand {

    String tournamentId;
    Guild guild;
    ToornamentClient toornamentClient;
    Tournaments tournaments;
    TournamentDetails tournament;
    String infoChannelData;
    String dataChannelData;

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
        prepareInfoData();
        String categoryName = tournament.getFullName() + "     #:" + tournament.getId();
        guild.createCategory(categoryName).queue((cat) -> {
//            cat.createTextChannel("Tournament-Data")
//                    .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.MESSAGE_READ))
//                    .queue();

            cat.createTextChannel("Tournament-Information")
                    .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.MESSAGE_WRITE)).setTopic("Here you gonna find all information about this upcoming tournament")
                    .queue(textChan -> {
                        textChan.sendMessage(this.infoChannelData).queue();
                    });

//            cat.createTextChannel("Tournament-Feed")
//                    .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.MESSAGE_WRITE))
//                    .queue();
            cat.createTextChannel("Tournament-Registration")
                    .setTopic("Here can you type !register to get a registration dm")
                    .queue(textChan -> {
                        textChan.sendMessage("Write !register and check your dms after").queue();
                    });
//
//            cat.createTextChannel("Tournament-Chat").queue();
        });

    }

    public void prepareInfoData() {
        this.infoChannelData =
                StringHelper.getTextBoldUnderlined("Date:") + StringHelper.getBreak(1) + this.tournament.getScheduledDateStart().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + StringHelper.getBreak(1) +
                StringHelper.getTextBoldUnderlined("Amount Players:") + StringHelper.getBreak(1) + this.tournament.getSize() + StringHelper.getBreak(1) +
                StringHelper.getTextBoldUnderlined("Rules:") + StringHelper.getBreak(1) + this.tournament.getRules() + StringHelper.getBreak(2) +
                StringHelper.getTextBoldUnderlined("Prices:" + StringHelper.getBreak(1)) + this.tournament.getPrize() + StringHelper.getBreak(2) +
                StringHelper.getTextBoldUnderlined("Description:" + StringHelper.getBreak(1))
                + this.tournament.getDescription().replace("Registration takes place via Discord. All further information can be found there.","");
    }

}
