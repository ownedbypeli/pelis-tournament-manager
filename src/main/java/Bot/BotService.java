package Bot;


import com.toornament.ToornamentClient;
import com.toornament.concepts.Matches;
import com.toornament.concepts.Tournaments;
import com.toornament.model.MatchDetails;
import com.toornament.model.Tournament;
import com.toornament.model.TournamentDetails;
import com.toornament.model.enums.Scope;
import com.toornament.model.request.MatchQuery;
import enums.Token;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class BotService {

    private final String TOURNAMENT_ID_MARK = "#:";
    List<Category> tournamentCategories;
    private  JDA jda;
    private  ToornamentClient toornamentClient;
    private List<TournamentDetails> serverTournaments = new ArrayList<>();
    private List<MatchDetails> matchDetails;

    public BotService(JDA jda) {
        this.jda = jda;
        List<Category> categories = jda.getCategories();
        this.tournamentCategories = categories.stream()
                .filter(c -> c.getName().contains(TOURNAMENT_ID_MARK))
                .collect(Collectors.toList());
        HashSet<Scope> scopes = new HashSet<>();
        scopes.add(Scope.ORGANIZER_RESULT);
        this.toornamentClient = new ToornamentClient(Token.API_KEY.getToken(), Token.CLIENT_ID.getToken(), Token.CLIENT_SECRET.getToken(), scopes);
        this.toornamentClient.authorize();
        Tournaments tournaments = toornamentClient.tournaments();
        for (Category cat: tournamentCategories) {
            String tournamentID = cat.getName().substring(cat.getName().indexOf("#:") + 2);
            TournamentDetails tournament = tournaments.getTournament(tournamentID);
            serverTournaments.add(tournament);
        }
    }

    public void startGetMatchesService() {

        for (TournamentDetails tournament:serverTournaments) {
            Matches matches = new Matches(toornamentClient,tournament);
            MatchQuery matchQuery = MatchQuery.builder().build();

            matchDetails =  matches.getMatches(matchQuery,"matches=0-99");
          matchDetails.forEach(x ->{
              System.out.println(x.toString());
          });
        }


    }
}
