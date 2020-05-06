package enums;

import help.PrivateConstReader;

public enum Token {
    API_KEY("C:\\projects\\tokens\\toornament_key.txt"),
    CLIENT_ID("C:\\projects\\tokens\\client_id.txt"),
    CLIENT_SECRET("C:\\projects\\tokens\\client_secret.txt"),
    BOT_TOKEN("C:\\projects\\tokens\\bot_token.txt");

    private final String token;

    Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return PrivateConstReader.getConstFromFile(token);
    }
}

