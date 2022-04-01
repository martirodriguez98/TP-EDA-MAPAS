package model;

public class PlaceLocation implements Comparable<PlaceLocation>{

  private double lat;
  private double lng;
  private final String name;
  private final String funcion_ppal;
  private Double similarity;

  public PlaceLocation(String name,String funcion_ppal,double lat,double lng) {
    this.name = name;
    this.funcion_ppal=funcion_ppal;
    this.lat = lat;
    this.lng = lng;
  }

  public void setSimilarity(Double similarity){
    this.similarity=similarity;
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

  public String getFuncion_ppal(){
    return funcion_ppal;
  }

  public Double getSimilarity(){
    return similarity;
  }

  @Override
  public int compareTo(PlaceLocation o) {
    return o.getSimilarity().compareTo(this.getSimilarity());
  }
}
