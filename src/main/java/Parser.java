import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

        try {
            parser.setUp();
            boolean running = true;
            while (running)
            {
                System.out.println("\n📊 Choose sorting option:");
                System.out.println("1. Sort by Name");
                System.out.println("2. Sort by Rating");
                System.out.println("3. Sort by Price");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                switch (choice)
                {
                    case 1:
                        System.out.println("\n\uD83D\uDD24Games sorted by Name:");
                        parser.sortByName().forEach(System.out::println);
                        break;
                    case 2:
                        System.out.println("\n⭐ Games sorted by Rating:");
                        parser.sortByRating().forEach(System.out::println);
                        break;
                    case 3:
                        System.out.println("\n\uD83D\uDCB5Games sorted by Price:");
                        parser.sortByPrice().forEach(System.out::println);
                        break;
                    case 0:
                        System.out.println("👋 bye bye...");
                        running = false;
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Try again.");
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
