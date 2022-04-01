package utils;

import model.Edge;
import model.StopLocation;

import java.util.HashMap;
import java.util.Map;

public class GraphMaker {

    private final Map<String, StopLocation> locations;

    public GraphMaker() {
        locations = new HashMap<>();
    }

    public Map<String, StopLocation> getLocations() {
        return locations;
    }

    public void addNode(String stop_id, double stop_lat, double stop_long, int direction_id, String name) {
        locations.putIfAbsent(stop_id, new StopLocation(stop_id, stop_lat, stop_long, direction_id, name));
    }

    public void addEdge(String stop_id1, String stop_id2) {
        StopLocation stop1 = locations.get(stop_id1);
        StopLocation stop2 = locations.get(stop_id2);

        if (stop1 == null || stop2 == null) {
            return;
        }

        Double distance = get_distance(stop1.getLat(), stop1.getLng(), stop2.getLat(), stop2.getLng());

        if (!(stop1.getName().equals(stop2.getName())) && (distance.compareTo(0.005) < 0)) {
            if(isSubway(stop1.getName()) && isSubway(stop2.getName())){ //Si son subtes tienen menos peso
                if(distance.compareTo(0.0005)<0) //la distancia debe ser aun menor para que sea una combinacion REAL
                    stop1.getEdges().add(new Edge(stop2,distance *0.9));
            }else{
                stop1.getEdges().add(new Edge(stop2, distance*10000)); //la distancia se multiplica si son de diferentes colectivos
            }
        }

        if ((stop1.getName().equals(stop2.getName()) && stop1.getDir_id() == stop2.getDir_id())) {
            if(isSubway(stop1.getName()) && isSubway(stop2.getName())){ //Si son subtes de la misma linea tienen menos peso aun
                stop1.getEdges().add(new Edge(stop2,distance *0.8));
            }else {
                stop1.getEdges().add(new Edge(stop2, distance));
            }
        }
    }


    private boolean isSubway(String linea){
        return (linea.equals("A") || linea.equals("B") || linea.equals("C") || linea.equals("D") || linea.equals("E") || linea.equals("H") );
    }

    public static Double get_distance(double lat1, double lng1, double lat2, double lng2) {
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lng1 - lng2, 2));
    }

}
