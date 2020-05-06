package commands;

import help.PrivateConstReader;
import help.RequestsHandler;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import objects.Tournament;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;

public class GetTournamentCommand {
    String tournamentId;
    Guild guild;
    String key = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\toornament_key.txt");
    String token;
    String clientId = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\client_id.txt");
    String clientSecret = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\client_secret.txt");
    Tournament tournament;


    public GetTournamentCommand(String tournamentId, Guild guild) {
        this.tournamentId = tournamentId;
        this.guild = guild;
    }

    public void getTournament() throws IOException {

        postApiToken();
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

    public void getTournamentData() throws IOException {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("X-Api-Key",key);
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

    public void postApiToken() throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-Api-Key",key);
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("scope", "organizer:view");
        JSONObject jsonObject = RequestsHandler.doPostRequest( "https://api.toornament.com/oauth/v2/token", headers, params);
        token = jsonObject.getString("access_token");
    }

}
