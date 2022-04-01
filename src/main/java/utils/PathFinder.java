package utils;

import model.Edge;
import model.PathInfo;
import model.StopLocation;

import java.util.PriorityQueue;

public class PathFinder {

    private final GraphMaker graph;

    public PathFinder(GraphMaker graph){
        this.graph = graph;
    }

    public PathInfo dijkstra(double fromLat, double fromLng, double toLat, double toLng) {
        PriorityQueue<PathInfo> queue = new PriorityQueue<>();
        graph.getLocations().values().forEach(node -> {
            if (isNear(node, fromLat, fromLng)) {
                node.setWeight(graph.get_distance(fromLat, fromLng, node.getLat(), node.getLng()));
                queue.add(new PathInfo(node, node.getWeight()));  //tenemos que ver como agregar el colectivo donde estoy como primer camino
            } else {
                node.setWeight(Double.MAX_VALUE);
            }
            node.setVisited(false);
        });

        if (queue.isEmpty()) {
            return null; //no hay paradas cercanas
        }


        while (!queue.isEmpty()) {

            PathInfo node = queue.remove(); //el que camine menos va a ser el que tenga mas prioridad

            if (node.getStop().isVisited()) {
                continue;
            }

            node.getStop().setVisited(true);

            if (!isNear(node.getStop(), toLat, toLng)) {
                for (Edge edge : node.getStop().getEdges()) {
                    double targetNewCost = node.getTotalWeight() + edge.getDistance();
                    if (targetNewCost < edge.getTarget().getWeight()) {
                        edge.getTarget().setWeight(targetNewCost);
                        PathInfo newPath = new PathInfo(edge.getTarget(), targetNewCost);
                        queue.add(newPath);
                        if(!edge.getTarget().getName().equals(node.getLastStopName())){ //diferentes colectivos
                            newPath.setEnd(node.getStop()); //actualizo el final del recorrido previo
                            newPath.setPreviousPath(node.getAllCombinations()); //agrego el recorrido previo
                            newPath.addNewCombination(edge.getTarget()); //agrego el edge al final
                        }else { //mismo colectivo
                            newPath.setPreviousPath(node.getAllCombinations());
                            newPath.setEnd(edge.getTarget());
                        }
                    }
                }
            }
            else{//condicion de corte
                node.setEnd(node.getStop());
                node.setHasPath(true);
                return node;
            }
        }
        return null;
    }

    private boolean isNear(StopLocation pl, double lat, double lng) {
        Double dist = graph.get_distance(pl.getLat(),pl.getLng(),lat,lng);
        return dist.compareTo(0.006) < 0;
    }
}
