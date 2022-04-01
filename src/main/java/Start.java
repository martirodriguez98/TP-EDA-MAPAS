import model.PlaceLocation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import utils.GraphMaker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import static spark.Spark.*;
import static utils.Json.json;

public class Start {

  public static void main(String[] args) throws IOException {

    String fileName= "/paradas-de-colectivo.csv";
    InputStream is = Start.class.getResourceAsStream(fileName);


    Reader in = new InputStreamReader(is);
    Iterable<CSVRecord> records = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .parse(in);

    GraphMaker graph=new GraphMaker();
    for (CSVRecord record : records) {
      String stop_id = record.get("stop_id");
      String route_short_name = record.get("route_short_name");
      int dir_id = Integer.parseInt(record.get("direction_id"));
      double stop_lat = Double.parseDouble(record.get("stop_lat"));
      double stop_lon = Double.parseDouble(record.get("stop_lon"));

      graph.addNode(stop_id,stop_lat,stop_lon,dir_id,route_short_name);
    }

    in.close();

    String fileSubtes = "/estaciones-de-subte.csv";
    InputStream isS = Start.class.getResourceAsStream(fileSubtes);

    Reader inS = new InputStreamReader(isS);
    Iterable<CSVRecord> subtes = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(inS);
    //sabemos que podemos usar el stop_id de los subtes porque van del 1 al 90 y los colectivos arrancan en 200
    for(CSVRecord subte : subtes){
      String stop_id = subte.get("id");
      String route_short_name = subte.get("linea");
      int dir_id = 1; //no hay direcciones, la seteamos siempre en 1
      double stop_lat = Double.parseDouble(subte.get("lat"));
      double stop_lon = Double.parseDouble(subte.get("long"));
      graph.addNode(stop_id,stop_lat,stop_lon,dir_id,route_short_name);
    }
    inS.close();

    //Conectamos el grafo

    for(String stop1 : graph.getLocations().keySet()) {
      for (String stop2 : graph.getLocations().keySet()) {
        graph.addEdge(stop1, stop2);
      }
    }

    String fileName2= "/espacios-culturales.csv";
    InputStream is2 = Start.class.getResourceAsStream(fileName2);


    Reader in2 = new InputStreamReader(is2);
    Iterable<CSVRecord> records2 = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .parse(in2);

    List<PlaceLocation> placeLocations=new LinkedList<>();
    for (CSVRecord record : records2) {
      String establecimiento = record.get("establecimiento");
      String funcion_principal = record.get("funcion_principal");
      double lat = Double.parseDouble(record.get("latitud"));
      double lon = Double.parseDouble(record.get("longitud"));
      PlaceLocation placeLocation=new PlaceLocation(establecimiento,funcion_principal,lat,lon);
      placeLocations.add(placeLocation);
    }

    in2.close();

    Controller controller = new Controller(graph,placeLocations);
    cors();
    after((req, res) -> res.type("application/json"));
    get("/path", (req, res) -> {
      double fromLat = Double.parseDouble(req.queryParams("fromLat"));
      double fromLng = Double.parseDouble(req.queryParams("fromLng"));
      double toLat = Double.parseDouble(req.queryParams("toLat"));
      double toLng = Double.parseDouble(req.queryParams("toLng"));
      return controller.findPath(fromLat, fromLng, toLat, toLng);
    }, json());
    get("/place", (req, res) -> {
      String searchTerm= req.queryParams("searchTerm");
      return controller.findPlaces(searchTerm);
    }, json());
  }

  public static void cors() {
    before((req, res) -> {
      res.header("Access-Control-Allow-Methods", "*");
      res.header("Access-Control-Allow-Origin", "*");
      res.header("Access-Control-Allow-Headers", "*");
      res.header("Access-Control-Allow-Credentials", "true");
    });
    options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }
      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }
      return "OK";
    });
  }
}
