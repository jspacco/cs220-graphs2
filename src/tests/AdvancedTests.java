package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Map;

import org.junit.jupiter.api.Test;

import graphs.Graph;
import graphs.Node;
import graphs.NodeVisitor;

class AdvancedTests {

	@Test
	void testBFS() {
		Graph g = Graph.createWeightedUndirectedGraphFromFile(new File("data/dijkstra1.txt"));
		g.breadthFirstSearch("A", new NodeVisitor() {
			@Override
			public void visit(Node node) {
				System.out.println(node.getName());
			}
		});
	}
	
	@Test
	void testDFS() {
		Graph g = Graph.createWeightedUndirectedGraphFromFile(new File("data/dijkstra1.txt"));
		g.depthFirstSearch("A", new NodeVisitor() {
			@Override
			public void visit(Node node) {
				System.out.println(node.getName());
			}
		});
	}
	
	@Test
	void testDijkstra() {
		Graph g = Graph.createWeightedUndirectedGraphFromFile(new File("data/dijkstra1.txt"));
		Map<Node, Integer> results = g.dijkstra("A");
		System.out.println(results);
	}
	
	@Test
	void testPrimJarnik() {
		Graph g = Graph.createWeightedUndirectedGraphFromFile(new File("data/dijkstra1.txt"));
		Graph result = g.primJarnik();
		
		String s = Graph.toUndirectedWeightedDotFile(result, "alpha");
		System.out.println(s);
	}

}
