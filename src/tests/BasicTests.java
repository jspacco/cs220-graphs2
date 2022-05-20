package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import graphs.Graph;
import graphs.Node;

class BasicTests {

	@Test
	void testBasic() {
		Node n1 = new Node("A");
		Node n2 = new Node("B");
		Node n3 = new Node("C");
		
		n1.addUndirectedEdge(n2, 1);
		n2.addDirectedEdge(n3, 5);
		
		// TODO: put in assertions
		assertTrue(n1.containsEdge(n2));
		assertTrue(n2.containsEdge(n1));
		assertTrue(n2.containsEdge(n3));
		assertFalse(n3.containsEdge(n2));
		assertFalse(n1.containsEdge(n3));
	}
	
	
	@Test
	void testFile1() {
		Graph g = Graph.createWeightedUndirectedGraphFromFile(new File("data/graph1.txt"));
		Node a = g.getOrCreate("A");
		Node b = g.getOrCreate("B");
		Node c = g.getOrCreate("C");
		System.out.println(g.getAllNodes());
		System.out.println(a.getNeighbors());
		assertEquals(3, a.getWeight(b));
		assertEquals(3, b.getWeight(a));
		assertEquals(4, b.getWeight(c));
		assertEquals(4, c.getWeight(b));
		assertFalse(a.containsEdge(c));
		
	}
}
