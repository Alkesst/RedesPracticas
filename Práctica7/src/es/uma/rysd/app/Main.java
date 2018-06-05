package es.uma.rysd.app;


import java.util.List;
import java.util.Scanner;

import es.uma.rysd.entities.Tweet;

public class Main {	
	private final static String proxy = "proxy.lcc.uma.es";
	private final static String proxy_port = "3128";

	private static String imprimirTwitaso(Tweet tweetaso){
        return tweetaso.text + "\n\tBy: @" + tweetaso.user.screen_name + ". RT: " + tweetaso.retweet_count
                + " FAV: " + tweetaso.favorite_count + ". Created at: " + tweetaso.created_at + " " +
                tweetaso.user.location;
    }


    public static void main(String[] args) {
	
    	// Descomente las siguiente líneas si lo está probando en el laboratorio y accede a Internet usando el proxy
    	// System.setProperty("https.proxyHost",proxy); 
    	// System.setProperty("https.proxyPort",proxy_port);
    	
        TwitterClient t = new TwitterClient();
        Scanner sc = new Scanner(System.in);
        String repetir = null;

        if(t.hasToken()) System.out.println("Conectado a Twitter");
        else {
        	System.out.println("Error en la conexión");
        	System.exit(-1);
        }
        do {
        	System.out.println("Termino a buscar: ");        	
        	String text = sc.nextLine();
        	Tweet[] ts = t.search(text, true);
        	Integer i = 0;
        	for(Tweet t1 : ts){
        		i++;
        		System.out.println(i.toString()+" : "+ imprimirTwitaso(t1));
        	}

        	String [] lista = new String[3];
        	for (i = 1; i <= 3; i++){
        		System.out.println("Usuario "+i+" a buscar: ");
        		lista[i-1] = sc.nextLine();
        	}
        	List<Tweet[]> listOfTweets;
        	listOfTweets = t.getTweetsUser(lista);
        	i = 0;
			for(Tweet[] ahidentro : listOfTweets) {
				for(Tweet canelo : ahidentro) {
					i++;
					System.out.println(i.toString()+" : "+imprimirTwitaso(canelo));
				}
			}
        	System.out.println("\nDesea repetir el proceso (s/n)?");
        	repetir = sc.nextLine();
        }while(repetir.equals("s"));
        
        System.out.println("Finalizando ...");
        sc.close();
    }
}
