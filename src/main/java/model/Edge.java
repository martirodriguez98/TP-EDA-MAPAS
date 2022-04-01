package model;

import java.util.Objects;

public class Edge{

    private final StopLocation target;
    private final double distance;

    public Edge(StopLocation target, double distance) {
        this.target = target;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public StopLocation getTarget(){
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.distance, distance) == 0 &&
                Objects.equals(target, edge.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, distance);
    }
}