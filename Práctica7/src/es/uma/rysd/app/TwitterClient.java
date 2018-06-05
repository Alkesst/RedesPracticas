package es.uma.rysd.app;

import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import com.google.gson.Gson;

import es.uma.rysd.entities.TokenResponse;
import es.uma.rysd.entities.SearchResponse;
import es.uma.rysd.entities.Tweet;

public class TwitterClient {
    private String bearer_token = null;
    
    // TODO: Completar los atributos con sus datos 
    private final String consumer_key = "";
    private final String consumer_secret = "";
    private final String app_name = "";
    
    private final String url_auth = "https://api.twitter.com/oauth2/token";
    private final String url_search = "https://api.twitter.com/1.1/search/tweets.json?result_type=popular&q=";
    private final String url_user = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";

    private String getKey(){
        String key = consumer_key + ":" + consumer_secret;
        return new String(Base64.getEncoder().encode(key.getBytes()));
    }
    
    public TwitterClient(){
        super();
        // TODO: Crear una conexión a la url indicada en url_auth

        // TODO: Añadir las cabeceras User-Agent, Content-Type, Authorization y Accept (ver enunciado)
        
        // TODO: Indicar el método POST y que el mensaje lleva datos
        
        // TODO: Obtener el flujo de salida (OutputStream) y escriba (write) el mensaje adecuado. Debe cerrar el flujo tras escribir
        
        // TODO: Obtener el código de respuesta y comprobar que es correcto
        
        // TODO: Obtener el flujo de entrada (InputStream) y deserializar su contenido en un objeto de tipo BearerToken usando Gson
        Gson parser = new Gson();
        InputStream in = null; // TODO: indicar el de la conexión
        TokenResponse b = parser.fromJson(new InputStreamReader(in), TokenResponse.class);
        
        // TODO: Almacenar el token en el atributo bearer_token
    }

    public boolean hasToken(){
        return this.bearer_token != null;
    }
    
    public Tweet[] search(String text){
        // URL: url_search + texto ajustado (ver enunciado)
        // Método: GET
        // Cabeceras: User-Agent, Accept y Authorization (ver enunciado)
        // Clase para deserializar: SearchResponse
        Tweet[] t = null;
        return t;
    }

    public Tweet[] getTweetsUser(String [] users){
        // URL: url_user + user ajustado (ver enunciado) (x3)
        // Método: GET
        // Cabeceras: User-Agent, Accept y Authorization (ver enunciado)
        // Clase para deserializar: Tweet []
        Tweet[] t = null;
        return t;
    }
}
