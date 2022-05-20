package tests;
import java.io.File;

import graphs.Graph;
import graphs.Node;

public class Main {

	public static void main2() {
		Graph g = new Graph();
		Node n1 = g.getOrCreate("A");
		Node n2 = g.getOrCreate("A");
		// true or false?
		// true because of caching! There is only one node named "A" in the graph
		System.out.println(n1 == n2);

	}
	
	public static void main(String[] args) {
		Graph g = Graph.createWeightedUndirectedGraphFromFile(new File("data/dijkstra1.txt"));
		
		String s = Graph.toUndirectedWeightedDotFile(g, "alpha");
		System.out.println(s);
	}

}
