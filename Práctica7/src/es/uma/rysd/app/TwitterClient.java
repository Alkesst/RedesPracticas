package es.uma.rysd.app;

import javax.net.ssl.HttpsURLConnection;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;

import es.uma.rysd.entities.TokenResponse;
import es.uma.rysd.entities.SearchResponse;
import es.uma.rysd.entities.Tweet;

public class TwitterClient {
    private String bearer_token = null;
    
    // TODO: Completar los atributos con sus datos 
    private final String consumer_key = "eDPVzuRrCGLzv9DSCNsR4ayXv";
    private final String consumer_secret = "Mv9k0Iv6UJAmDhWo2MX6HDBqjQ5l4x4h4ofFokwdCAdooqoRTA";
    private final String app_name = "RSDAppAlpesst98";

    private final String url_auth = "https://api.twitter.com/oauth2/token";
    private final String url_search = "https://api.twitter.com/1.1/search/tweets.json?result_type=popular&q=";
    private final String url_user = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";
    private final String url_searchNormalNoPopularPorqueSomosSocialistas = "https://api.twitter.com/1.1/search/tweets.json?q=";

    private String getKey(){
        String key = consumer_key + ":" + consumer_secret;
        return new String(Base64.getEncoder().encode(key.getBytes()));
    }
    
    public TwitterClient(){
        super();
        try {
            // TODO: Crear una conexión a la url indicada en url_auth
            URL urlAuth = new URL(url_auth);
            HttpsURLConnection connectionAuth = (HttpsURLConnection) urlAuth.openConnection();
            // TODO: Añadir las cabeceras User-Agent, Content-Type, Authorization y Accept (ver enunciado)
            connectionAuth.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
            connectionAuth.setRequestProperty("User-Agent", app_name);
            connectionAuth.setRequestProperty("Accept", "application/json");
            connectionAuth.setRequestProperty("Authorization", "Basic " + getKey());
            // TODO: Indicar el método POST y que el mensaje lleva datos
            connectionAuth.setRequestMethod("POST");
            // TODO: Obtener el flujo de salida (OutputStream) y escriba (write) el mensaje adecuado. Debe cerrar el flujo tras escribir
            connectionAuth.setDoOutput(true);
            OutputStream os = connectionAuth.getOutputStream();
            os.write("grant_type=client_credentials".getBytes());
            os.flush();
            // TODO: Obtener el código de respuesta y comprobar que es correcto
            if(Integer.toString(connectionAuth.getResponseCode()).charAt(0) != '2'){
                System.err.println("Something went wrong: " + connectionAuth.getResponseMessage());
                System.exit(-1);
            }
            InputStream in = connectionAuth.getInputStream();
            // TODO: Obtener el flujo de entrada (InputStream) y deserializar su contenido en un objeto de tipo BearerToken usando Gson
            Gson parser = new Gson();
            TokenResponse b = parser.fromJson(new InputStreamReader(in), TokenResponse.class);
            // TODO: Almacenar el token en el atributo bearer_token
            bearer_token = b.access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasToken(){
        return this.bearer_token != null;
    }
    
    public Tweet[] search(String text, boolean popular){
        // URL: url_search + texto ajustado (ver enunciado)
        // Método: GET
        // Cabeceras: User-Agent, Accept y Authorization (ver enunciado)
        // Clase para deserializar: SearchResponse
        Tweet[] t = null;
        try {
            URL request;
            if(popular) {
                request = new URL(url_search + URLEncoder.encode(text, "UTF-8"));
            } else {
                request = new  URL(url_searchNormalNoPopularPorqueSomosSocialistas + URLEncoder.encode(text, "UTF-8"));
            }
            HttpsURLConnection requestConnection = (HttpsURLConnection) request.openConnection();
            requestConnection.setRequestProperty("Accept", "application/json");
            requestConnection.setRequestProperty("User-Agent", app_name);
            requestConnection.setRequestProperty("Authorization", "Bearer " + bearer_token);
            requestConnection.setRequestMethod("GET");
            if(Integer.toString(requestConnection.getResponseCode()).charAt(0) != '2'){
                System.err.println("Something went wrong: " + requestConnection.getResponseMessage());
                System.exit(-1);
            }
            InputStream in = requestConnection.getInputStream();
            Gson parser = new Gson();
            SearchResponse b = parser.fromJson(new InputStreamReader(in), SearchResponse.class);
            t = b.statuses;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public List<Tweet[]> getTweetsUser(String [] users){
        // URL: url_user + user ajustado (ver enunciado) (x3)
        // Método: GET
        // Cabeceras: User-Agent, Accept y Authorization (ver enunciado)
        // Clase para deserializar: Tweet []
        Tweet[] tweets = null;
        List<Tweet[]> listaDeTweets = new ArrayList<>();
        try {
            for (String user : users) {
                URL request = new URL(url_user + URLEncoder.encode(user, "UTF-8"));
                HttpsURLConnection requestConnection = (HttpsURLConnection) request.openConnection();
                requestConnection.setRequestProperty("Accept", "application/json");
                requestConnection.setRequestProperty("User-Agent", app_name);
                requestConnection.setRequestProperty("Authorization", "Bearer " + bearer_token);
                requestConnection.setRequestMethod("GET");
                if (Integer.toString(requestConnection.getResponseCode()).charAt(0) != '2') {
                    System.err.println("Something went wrong: " + requestConnection.getResponseMessage());
                    System.exit(-1);
                }
                InputStream in = requestConnection.getInputStream();
                Gson parser = new Gson();
                Tweet[] b = parser.fromJson(new InputStreamReader(in), Tweet[].class);
                listaDeTweets.add(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaDeTweets;
    }

    public static void main(String[] args) {
        TwitterClient tc = new TwitterClient();
        Tweet[] tw = tc.search("Pedro Sánchez", true);
        System.out.println(tw[0].user.location);
    }
}
