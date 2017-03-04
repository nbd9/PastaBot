package Commands;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dean.jraw.RedditClient;
import net.dean.jraw.fluent.FluentRedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import org.apache.commons.lang3.StringUtils;
import utils.Reddit;

import java.util.Random;

public class PastaCommand implements CommandExecutor {
    @Command(aliases = {"pasta", "copypasta"}, description = "Gets a random copypasta from /r/copypasta.", usage = "pasta")
    public static String onPastaCommand(String[] args) throws OAuthException {
        RedditClient redditClient = Reddit.redditClient;
        if (!redditClient.isAuthenticated()) {
            Reddit.authenticateReddit();
        }

        FluentRedditClient fluent = new FluentRedditClient(redditClient);
        Random random = new Random();
        Listing<Submission> copypasta = fluent.subreddit("copypasta").fetch();
        System.out.println("Serving some pasta.");
        return StringUtils.abbreviate(copypasta.get(random.nextInt(copypasta.size())).getSelftext(), 2000);
    }
}
