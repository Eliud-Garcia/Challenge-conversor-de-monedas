import com.google.gson.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ApiConnection {

    public Double get_conversion_rate(String base, String target){
        var URL = "https://v6.exchangerate-api.com/v6/876cdf14b2f903359475c725/pair/" + base + "/" + target;
        //cliente http
        HttpClient client = HttpClient.newHttpClient();

        Double ans = 0.0;

        try {
            //solicitud http
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .build();
            //http response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonElement element = JsonParser.parseString(response.body());
            JsonObject objectRoot = element.getAsJsonObject();

            var result = objectRoot.get("result").getAsString();
            //System.out.println(objectRoot.toString());
            if(result.equals("error")){
                System.out.println("============================");
                System.out.println("Se ha presentado un error");
                System.out.println(objectRoot.get("error-type"));
                System.out.println("============================");
            }else{
                ans = objectRoot.get("conversion_rate").getAsDouble();
            }

        }catch (Exception e){
            System.out.println("Ha ocurrido en error" + e.getMessage());
        }
        return ans;
    }

    public List<String> get_supported_codes(){
        var URL = "https://v6.exchangerate-api.com/v6/876cdf14b2f903359475c725/codes";
        List<String> list = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonElement element = JsonParser.parseString(response.body());
            JsonObject objectRoot = element.getAsJsonObject();
            JsonArray array = objectRoot.get("supported_codes").getAsJsonArray();
            for(var i: array){
                list.add(i.toString());
            }
        }catch (Exception e){
            System.out.println("Error al traer todas la monedas soportadas");
            System.out.println(e.getMessage());
        }

        return list;
    }
}
