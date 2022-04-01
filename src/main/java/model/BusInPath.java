package model;

public class BusInPath {
  private final String name;
  private final double fromLat;
  private final double fromLng;
  private double toLat;
  private double toLng;

  //hago un constructor solo con la parada. El from van a ser las coordenadas de esa parada, el to se actualiza al finalizar el recorrido
  public BusInPath(StopLocation stop) {
    this.name = stop.getName();
    this.fromLat = stop.getLat();
    this.fromLng = stop.getLng();
  }

  public BusInPath(String name, double fromLat, double fromLng, StopLocation toLoc) {
    this.name = name;
    this.fromLat = fromLat;
    this.fromLng = fromLng;
    this.toLat = toLoc.getLat();
    this.toLng = toLoc.getLng();
  }

  public double getToLat() {
    return toLat;
  }

  public double getToLng() {
    return toLng;
  }

  public double getFromLat() {
    return fromLat;
  }

  public double getFromLng() {
    return fromLng;
  }

  public String getName() {
    return name;
  }

}
