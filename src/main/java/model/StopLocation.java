package model;

import java.util.HashSet;
import java.util.Set;

public class StopLocation {

    private final String stop_id;
    private final double lat;
    private final double lng;
    private final int dir_id;
    private final String name;
    private final Set<Edge> edges;
    private boolean visited = false;
    private double weight = Double.MAX_VALUE;

    public StopLocation(String stop_id, double lat, double lng, int dir_id, String name) {
        this.stop_id = stop_id;
        this.lat = lat;
        this.lng = lng;
        this.dir_id = dir_id;
        this.name = name;
        edges = new HashSet<>();
    }

    public String getStop_id(){
        return stop_id;
    }

    public int getDir_id(){
        return dir_id;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
