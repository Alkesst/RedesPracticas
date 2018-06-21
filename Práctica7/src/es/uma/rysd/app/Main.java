package es.uma.rysd.app;

import java.util.List;
import java.util.Scanner;

import es.uma.rysd.entities.Tweet;

public class Main {
    // private final static String proxy = "proxy.lcc.uma.es"; // Puesto que el trabajo ha sido realizado en nuestros pcs
    // private final static String proxy_port = "3128";        // No hemos usado el proxy

    private static String imprimirTweet(Tweet tweet) {                                                                  // Función para imprimir Tweets
        return tweet.text + "\n\tBy: @" + tweet.user.screen_name + ". RT: " + tweet.retweet_count
                + " FAV: " + tweet.favorite_count + ". Created at: " + tweet.created_at + " " +
                tweet.user.location;
    }

    public static void main(String[] args) {
        // Descomente las siguiente líneas si lo está probando en el laboratorio y accede a Internet usando el proxy
        // System.setProperty("https.proxyHost",proxy);
        // System.setProperty("https.proxyPort",proxy_port);

        TwitterClient twitterClient = new TwitterClient();
        Scanner sc = new Scanner(System.in);
        String repeat;

        if (twitterClient.hasToken()) {
            System.out.println("Connecting to Twitter");                                                                // Se conecta a Twitter
        } else {
            System.out.println("Connection error");
            System.exit(-1);                                                                                         // En caso de error, cierra la aplicación
        }

        do {
            System.out.println("Term to search: ");                                                                     // Término a buscar
            String text = sc.nextLine();                                                                                // Lo introducimos mediante terminal
            Tweet[] tweets = twitterClient.search(text, true);                                                  // Guardamos los últimos tweets con ese t en un array de tweets
            Integer counter = 0;

            for (Tweet t1 : tweets) {
                counter++;
                System.out.println(counter.toString() + " : " + imprimirTweet(t1));                                     // Imprime todos los tweets recogidos
            }

            String[] list = new String[3];
            for (counter = 1; counter <= 3; counter++) {
                System.out.println("User " + counter + " to search: ");
                list[counter - 1] = sc.nextLine();                                                                      // Insertamos por terminal los 3 usuarios que vamos a buscar los últimos Tweets
            }

            List<Tweet[]> listOfTweets;
            listOfTweets = twitterClient.getTweetsUser(list);
            counter = 0;

            for (Tweet[] tweets1 : listOfTweets) {
                for (Tweet tweet : tweets1) {
                    counter++;
                    System.out.println(counter.toString() + " : " + imprimirTweet(tweet));                              // Imprimimos los Tweets de los 3 usuarios
                }
                System.out.print("\n");
            }
            System.out.println("\nDo you want to repeat the process (y/n)?");
            repeat = sc.nextLine();
        } while (repeat.toLowerCase().equals("y"));

        System.out.println("Finalizing ...");
        sc.close();
    }
}
