import model.BusInPath;
import model.PathInfo;
import model.PlaceLocation;
import utils.GraphMaker;
import utils.PathFinder;
import utils.QGram;

import java.util.*;

public class Controller {

  private final GraphMaker graph;
  private final List<PlaceLocation> cultural_places;

  public Controller(GraphMaker graph, List<PlaceLocation> cp) {
    this.graph=graph;
    this.cultural_places=cp;
  }

  public List<BusInPath> findPath(double fromLat, double fromLng, double toLat, double toLng) {
    PathFinder finder = new PathFinder(graph);
    PathInfo bestPath=finder.dijkstra(fromLat,fromLng,toLat,toLng);
    if(bestPath.hasPath()){
      return bestPath.getAllCombinations();
    }
    return new LinkedList<>();
  }

  public List<PlaceLocation> findPlaces(String searchTerm) {
    searchTerm=searchTerm.toUpperCase();
    QGram qGram=new QGram(2);
    Set<PlaceLocation> places=new TreeSet<>();
    for(PlaceLocation place : cultural_places) {
      Double similarity_Name = qGram.similarity(place.getName(), searchTerm);
      Double similarity_fppal = qGram.similarity(place.getFuncion_ppal(), searchTerm);
      Double max = Math.max(similarity_Name, similarity_fppal);
      if (max.compareTo(0.3) >= 0) {
        place.setSimilarity(max);
        places.add(place);
      }
    }
    if(places.size()>10){
      List<PlaceLocation> placeLocations=new LinkedList<>();
      for(PlaceLocation place: places){
        placeLocations.add(place);
        if(placeLocations.size()==10){
          return placeLocations;
        }
      }
    }
    return new LinkedList<>(places);
  }
}
