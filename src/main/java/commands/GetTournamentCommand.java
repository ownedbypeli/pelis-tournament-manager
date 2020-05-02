package commands;

import help.PrivateConstReader;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import objects.Tournament;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetTournamentCommand {
    String tournamentId;
    Guild guild;
    String key = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\toornament_key.txt");
    String token;
    String clientId = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\client_id.txt");
    String clientSecret = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\client_secret.txt");
    CloseableHttpClient httpClient = HttpClients.createDefault();
    Tournament tournament;


    public GetTournamentCommand(String tournamentId, Guild guild) {
        this.tournamentId = tournamentId;
        this.guild = guild;
    }

    public void start() throws IOException {

        getApiToken();
        getTournamentData();
        String categoryName = tournament.getName() + " #" + tournament.getId();
        ChannelAction channelAction = guild.createCategory(categoryName);
        channelAction.queue(


        );

//        Category category = guild.getCategoriesByName("categoryName",true).get(0);
//        category.createTextChannel("Tournament Information").queue();

    }

    public void getTournamentData() throws IOException {
        try {
            HttpGet request = new HttpGet("https://api.toornament.com/organizer/v2/tournaments/" + tournamentId);
            // add request headers
            request.addHeader("X-Api-Key", key);
            request.addHeader("Authorization", token);
            CloseableHttpResponse response = httpClient.execute(request);
            JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
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
                    jsonObject.getJSONArray("platforms").getString(0),
                    jsonObject.getString("organization")
            );

        } finally {
            httpClient.close();
        }

    }

    public void getApiToken() throws IOException {
        HttpPost request = new HttpPost("https://api.toornament.com/oauth/v2/token");
        request.addHeader("X-Api-Key", key);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "client_credentials"));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("scope", "organizer:view"));
        request.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = httpClient.execute(request);
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
        token = jsonObject.getString("access_token");
    }

}
