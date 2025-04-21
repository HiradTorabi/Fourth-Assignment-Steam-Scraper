import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser
{
    static List<Game> games = new ArrayList<>();

    public List<Game> sortByName()
    {
        List<Game> sortedByName = new ArrayList<>(games);
        sortedByName.sort(Comparator.comparing(Game::getName));
        return  sortedByName;
    }

    public List<Game> sortByRating()
    {
        List<Game> sortedByRating = new ArrayList<>(games);
        sortedByRating.sort(Comparator.comparing(Game::getRating).reversed());
        return sortedByRating;
    }

    public List<Game> sortByPrice(){
        List<Game> sortedByPrice = new ArrayList<>(games);
        sortedByPrice.sort(Comparator.comparing(Game::getPrice).reversed());
        return sortedByPrice;
    }

    public void setUp() throws IOException
    {
        File input = new File("src/Resources/Video_Games.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements gameElements = doc.select("div.col-md-4.game");

        for (Element gameElement : gameElements)
        {
            String name = gameElement.select("h3.game-name").text();

            String ratingStr = gameElement.select("span.game-rating").text(); // "4.8/5"
            double rating = Double.parseDouble(ratingStr.split("/")[0]);

            String priceStr = gameElement.select("span.game-price").text(); // "91 €"
            int price = Integer.parseInt(priceStr.replace("€", "").trim());

            Game game = new Game(name, rating, price);
            games.add(game);
        }
    }

    public static void main(String[] args)
    {
        //you can test your code here before you run the unit tests
        Parser parser = new Parser();
        try
        {
            parser.setUp();

            System.out.println("Games sorted by name:");
            parser.sortByName().forEach(System.out::println);

            System.out.println("\nGames sorted by rating:");
            parser.sortByRating().forEach(System.out::println);

            System.out.println("\nGames sorted by price:");
            parser.sortByPrice().forEach(System.out::println);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
