package controller;

import utils.GraphMaker;

class NewGraph {

  public static GraphMaker Graph() {

    GraphMaker graph = new GraphMaker();
    graph.addNode("A", 58.401, 34.601, 1, "Nodo A");
    graph.addNode("B", 58.402, 34.602, 1, "Nodo B");
    graph.addNode("C", 58.403, 34.603, 1, "Nodo C");
    graph.addNode("D", 58.404, 34.602, 1, "Nodo D");
    graph.addNode("E", 58.405, 34.603, 1, "Nodo E");
    graph.addNode("F", 58.406, 34.601, 1, "Nodo F");
    graph.addNode("G", 58.407, 34.604, 1, "Nodo G");

    graph.addEdge("A", "B");
    graph.addEdge("B", "A");
    graph.addEdge("B", "C");
    graph.addEdge("C", "B");
    graph.addEdge("A", "C");
    graph.addEdge("C", "A");
    graph.addEdge("A", "D");
    graph.addEdge("D", "A");
    graph.addEdge("C", "D");
    graph.addEdge("D", "C");
    graph.addEdge("D", "E");
    graph.addEdge("E", "D");
    graph.addEdge("E", "G");
    graph.addEdge("G", "E");
    graph.addEdge("G", "F");
    graph.addEdge("F", "G");

    return graph;
  }

}