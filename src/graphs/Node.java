package graphs;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Node {
	private String name;
	private Map<Node, Integer> besties = new HashMap<>();
	
	public Node(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	public void addUndirectedEdge(Node other, int weight) {
		//besties.put(other, weight);
		//other.besties.put(this, weight);
		this.addDirectedEdge(other, weight);
		other.addDirectedEdge(this, weight);
	}
	
	public void addDirectedEdge(Node other, int weight) {
		besties.put(other, weight);
	}
	
	// other useful methods
	
	public boolean containsEdge(Node other) {
		return besties.containsKey(other);
	}
	
	public int getWeight(Node other) {
		// what should this do if no such edge exists?
		return besties.get(other);
	}
	
	public Collection<Node> getNeighbors() {
		// defensive collection!
		return new HashSet<Node>(besties.keySet());
	}

	public String getName() {
		return name;
	}
}
