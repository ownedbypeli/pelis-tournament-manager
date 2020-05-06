package help;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestsHandler {
    static String key = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\toornament_key.txt");
    static String clientId = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\client_id.txt");
    static String clientSecret = PrivateConstReader.getConstFromFile("C:\\projects\\tokens\\client_secret.txt");

    public static JSONObject doPostRequest(String url, HashMap<String, String> headers, String jsonParams){
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            for (String key : headers.keySet()) {
                request.addHeader(key, headers.get(key));
            }
            request.addHeader("X-Api-Key",key);
            request.setHeader("Content-type", "application/json");
            request.setHeader("Accept", "application/json");
            request.setEntity(new StringEntity(jsonParams));
            HttpResponse response = client.execute(request);
            JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject doGetRequest(String url, HashMap<String, String> headers) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            for (String key : headers.keySet()) {
                request.addHeader(key, headers.get(key));
            }
            HttpResponse response = client.execute(request);
            return new JSONObject(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPostApiTokenRequest(String scope) {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("grant_type", "client_credentials");
            jsonParams.put("client_id", clientId);
            jsonParams.put("client_secret", clientSecret);
             jsonParams.put("scope",scope);
            JSONObject jsonObject = doPostRequest( "https://api.toornament.com/oauth/v2/token", new HashMap<>(), jsonParams.toString());
            return jsonObject.getString("access_token");
    }
}

