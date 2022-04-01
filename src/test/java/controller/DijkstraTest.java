package controller;

import model.PathInfo;
import org.junit.jupiter.api.Test;
import utils.GraphMaker;
import utils.PathFinder;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DijkstraTest {

 @Test
 public void dijkstra(){
    GraphMaker graph = NewGraph.Graph();
    PathFinder finder = new PathFinder(graph);
    PathInfo path1 = finder.dijkstra(58.4,34.601,58.404,34.604);

    assertEquals(path1.getTotalWeight(),0.0010000000000047748);
    assertNotEquals(path1.getTotalWeight(),800);


    PathInfo path2 = finder.dijkstra(58.4,34.601,58.407,34.604);

    assertEquals(path2.getTotalWeight(),0.0022360679775009335);
    assertNotEquals(path2.getTotalWeight(),0.0022);


    PathInfo path3 = finder.dijkstra(58.4006,34.60199,58.404,34.604);

    assertEquals(path3.getTotalWeight(),0.0010677546534705938);
    assertNotEquals(path3.getTotalWeight(),0.00108);


    PathInfo path4 = finder.dijkstra(58.4099,34.602,58.402,34.605);

    assertEquals(path4.getTotalWeight(),0.0035227829907663053);
    assertNotEquals(path4.getTotalWeight(),0.0035227828907663053);

  }
}
