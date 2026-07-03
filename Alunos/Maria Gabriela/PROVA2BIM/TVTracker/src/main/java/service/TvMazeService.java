package service;

import model.Serie;
import org.json.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class TvMazeService {

public List<Serie> buscar(String nome)
throws Exception{

List<Serie> lista =
        new ArrayList<>();

URL url =
new URL(
"https://api.tvmaze.com/search/shows?q="
+nome);

HttpURLConnection con =
(HttpURLConnection)
url.openConnection();

BufferedReader br =
new BufferedReader(
new InputStreamReader(
con.getInputStream()));

String resposta="";
String linha;

while((linha=br.readLine())!=null){
    resposta+=linha;
}

JSONArray array =
new JSONArray(resposta);

for(int i=0;i<array.length();i++){

JSONObject show =
array.getJSONObject(i)
.getJSONObject("show");

Serie s =
new Serie(

show.getString("name"),
show.optString("language"),
show.getJSONArray("genres")
.toString(),
show.getJSONObject("rating")
.optDouble("average",0),
show.optString("status"),
show.optString("premiered"),
show.optString("ended"),
show.isNull("network")
?
""
:
show.getJSONObject("network")
.getString("name")
);

lista.add(s);

}

return lista;

}
}