package commands;

import help.RequestsHandler;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import objects.Tournament;
import org.json.JSONObject;

import java.util.EnumSet;
import java.util.HashMap;

public class GetTournamentCommand {
    String tournamentId;
    Guild guild;
    String token;
    Tournament tournament;

    public GetTournamentCommand(String tournamentId, Guild guild) {
        this.tournamentId = tournamentId;
        this.guild = guild;
    }

    public void getTournament()  {
        token = RequestsHandler.doPostApiTokenRequest("organizer:view");
        getTournamentData();
        String categoryName = tournament.getFullName() + " #:" + tournament.getId();
        guild.createCategory(categoryName).queue((cat) -> {
            cat.createTextChannel("Tournament-Information")
                    .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.MESSAGE_WRITE)).setTopic("Here you gonna find all information about this upcoming tournament")
                    .queue(textChan -> {
                        textChan.sendMessage(tournament.toString()).queue();
                    });
            cat.createTextChannel("Tournament-Feed").addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.MESSAGE_WRITE)).queue();
            cat.createTextChannel("Tournament-Registration").queue();
            cat.createTextChannel("Tournament-Chat").queue();
        });

    }

    public void getTournamentData() {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization",token);
        JSONObject jsonObject = RequestsHandler.doGetRequest("https://api.toornament.com/organizer/v2/tournaments/" + tournamentId,headers);
            tournament = new Tournament(
                    jsonObject.getString("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("full_name"),
                    jsonObject.getString("scheduled_date_start"),
                    jsonObject.getString("timezone"),
                    String.valueOf(jsonObject.getInt("size")),
                    jsonObject.getString("registration_closing_datetime"),
                    jsonObject.getString("description"),
                    jsonObject.getString("rules"),
                    jsonObject.getString("prize"),
                    jsonObject.getString("check_in_participant_start_datetime"),
                    jsonObject.getString("check_in_participant_end_datetime"),
                    jsonObject.getJSONArray("platforms").getString(0)
            );

    }

}
