package commands;

import help.PrivateConstReader;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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


    public GetTournamentCommand(String tournamentId, Guild guild) {
        this.tournamentId = tournamentId;
        this.guild = guild;
    }

    public void start() {

        try {
            getApiToken();
            getTournamentData();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //guild.createCategory("Turnier").queue();

    }

    public void getTournamentData() throws IOException {
        try {
            HttpGet request = new HttpGet("https://api.toornament.com/organizer/v2/tournaments/" + tournamentId);
            // add request headers
            request.addHeader("X-Api-Key", key);
            request.addHeader("Authorization", token);
            CloseableHttpResponse response = httpClient.execute(request);
            try {

                // Get HttpResponse Status
 /*               System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
*/
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    //System.out.println(result);
                }

            } finally {
                response.close();
            }
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
    HttpEntity entity = response.getEntity();
    String result = EntityUtils.toString(entity);
    JSONObject jsonObject = new JSONObject(result);
    token = jsonObject.getString("access_token");
}

}
