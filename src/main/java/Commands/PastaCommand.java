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
import utils.Keys;

import java.util.Random;

public class PastaCommand implements CommandExecutor {
    private static RedditClient redditClient;
    private static Credentials credentials = Credentials.script("DiscordPastaBot", Keys.RedditPass, Keys.RedditID, Keys.RedditSecret);

    public static void main(String[] args) throws OAuthException {
        redditClient = new RedditClient(UserAgent.of("desktop", "com.nbdeg.pastabot", "v0.1", "DiscordPastaBot"));

        if (!redditClient.isAuthenticated()) {
            OAuthData authData = redditClient.getOAuthHelper().easyAuth(credentials);
            redditClient.authenticate(authData);
        }
    }

    @Command(aliases = {"pasta", "copypasta"}, description = "Gets a random copypasta from /r/copypasta.", usage = "pasta")
    public static String onPastaCommand(String[] args) throws OAuthException {
        if (!redditClient.isAuthenticated()) {
            OAuthData authData = redditClient.getOAuthHelper().easyAuth(credentials);
            redditClient.authenticate(authData);
        }

        FluentRedditClient fluent = new FluentRedditClient(redditClient);
        System.out.println("Preparing Pasta");
        Random random = new Random();
        Listing<Submission> copypasta = fluent.subreddit("copypasta").fetch();
        return StringUtils.abbreviate(copypasta.get(random.nextInt(copypasta.size())).getSelftext(), 2000);
    }
}
