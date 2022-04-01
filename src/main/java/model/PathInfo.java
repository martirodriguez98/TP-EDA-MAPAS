package model;

import java.util.LinkedList;

public class PathInfo implements Comparable<PathInfo>{

    private final Double totalWeight;
    private LinkedList<BusInPath> allCombinations;
    private final StopLocation stop;
    private boolean hasPath;

    public PathInfo(StopLocation stop, Double weight) {
        this.stop = stop;
        this.allCombinations = new LinkedList<>();
        allCombinations.add(new BusInPath(stop));
        this.totalWeight = weight;
        this.hasPath = false;
    }

    public void setHasPath(boolean hasPath){
        this.hasPath=hasPath;
    }

    public boolean hasPath(){
        return hasPath;
    }
    
    public Double getTotalWeight() {
        return totalWeight;
    }

    public LinkedList<BusInPath> getAllCombinations() {
        return allCombinations;
    }

    public void setPreviousPath(LinkedList<BusInPath> previousPath){
        this.allCombinations = new LinkedList<>(previousPath);
    }

    public void addNewCombination(StopLocation stop){
        this.allCombinations.addLast(new BusInPath(stop));
    }

    public void setEnd(StopLocation stop) {
        BusInPath last = allCombinations.removeLast(); //lo remuevo para luego ingresarlo con el end cambiado
        allCombinations.addLast(new BusInPath(stop.getName(),last.getFromLat(),last.getFromLng(),stop));
    }

    @Override
    public int compareTo(PathInfo o) {
        return  this.totalWeight.compareTo(o.totalWeight);
    }

    public StopLocation getStop() {
        return stop;
    }

    public String getLastStopName(){
        return allCombinations.getLast().getName();
    }

}
