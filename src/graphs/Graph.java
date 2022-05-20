package graphs;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Graph {
	private Map<String, Node> nodes;
	
	public Graph() {
		nodes = new HashMap<>();
	}
	
	// static factories
	public static Graph createWeightedUndirectedGraphFromFile(File file) {
		try {
			Graph g = new Graph();

			// read undirected graph from the file
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner scan2 = new Scanner(line);
				String name1 = scan2.next();
				String name2 = scan2.next();
				int weight = scan2.nextInt();
				Node n1 = g.getOrCreate(name1);
				Node n2 = g.getOrCreate(name2);
				n1.addUndirectedEdge(n2, weight);
				scan2.close();
			}
			scan.close();
			return g;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Graph createWeightedDirectedGraphFromFile(File file) {
		try {
			Graph g = new Graph();

			// read undirected graph from the file
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				Scanner scan2 = new Scanner(line);
				String name1 = scan2.next();
				String name2 = scan2.next();
				int weight = scan2.nextInt();
				Node n1 = g.getOrCreate(name1);
				Node n2 = g.getOrCreate(name2);
				n1.addDirectedEdge(n2, weight);
				scan2.close();
			}
			scan.close();
			return g;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Graph createUnweightedUndirectedGraphFromFile(File file) {
		try {
			Graph g = new Graph();

			// read undirected graph from the file
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				String name1 = scan.next();
				String name2 = scan.next();
				Node n1 = g.getOrCreate(name1);
				Node n2 = g.getOrCreate(name2);
				n1.addUndirectedEdge(n2, 1);
			}
			scan.close();
			return g;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Graph createUnweightedDirectedGraphFromFile(File file) {
		try {
			Graph g = new Graph();

			// read undirected graph from the file
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				String name1 = scan.next();
				String name2 = scan.next();
				Node n1 = g.getOrCreate(name1);
				Node n2 = g.getOrCreate(name2);
				n1.addDirectedEdge(n2, 1);
			}
			scan.close();
			return g;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String toDirectedWeightedDotFile(Graph g, String graphname)
    {
        StringBuilder buf = new StringBuilder();
        Set<String> set=new HashSet<String>();
        buf.append("digraph "+graphname+" {\n");
        for (Node src : g.getAllNodes()) {
            for (Node dst : src.getNeighbors()) {
                String srcStr=src.getName();
                String dstStr=dst.getName();
                String key=srcStr+" "+dstStr;
                if (srcStr.compareTo(dstStr)>0){
                    key=dstStr+" "+srcStr;
                }
                if (set.contains(key)) {
                    continue;
                }
                set.add(key);
                int cost=src.getWeight(dst);
                buf.append(src.getName()+" -> "+dst.getName()+" [label="+cost+"];\n");
            }
        }
        buf.append("}\n");
        return buf.toString();
    }
	
	public static String toUndirectedWeightedDotFile(Graph g, String graphname)
    {
        StringBuilder buf = new StringBuilder();
        Set<String> set=new HashSet<String>();
        buf.append("graph "+graphname+" {\n");
        for (Node src : g.getAllNodes()) {
            for (Node dst : src.getNeighbors()) {
                String srcStr=src.getName();
                String dstStr=dst.getName();
                String key=srcStr+" "+dstStr;
                if (srcStr.compareTo(dstStr)>0){
                    key=dstStr+" "+srcStr;
                }
                if (set.contains(key)) {
                    continue;
                }
                set.add(key);
                int cost=src.getWeight(dst);
                buf.append(src.getName()+" -- "+dst.getName()+" [label="+cost+"];\n");
            }
        }
        buf.append("}\n");
        return buf.toString();
    }
    
	
	public Node getOrCreate(String name) {
		// version of the singleton pattern
		// we only want ONE node with a given name
		// caching - reusing existing things instead of creating new ones
		if (!nodes.containsKey(name)) nodes.put(name, new Node(name));
		return nodes.get(name);
	}
	
	public Collection<Node> getAllNodes() {
		return nodes.values();
	}
	
	public boolean containsNode(String name) {
		return nodes.containsKey(name);
	}
	
	public void breadthFirstSearch(String name, NodeVisitor v) {
		Set<Node> visited = new HashSet<>();
		Queue<Node> tovisit = new LinkedList<>();
		Node start = getOrCreate(name);
		tovisit.add(start);
		while (!tovisit.isEmpty() && visited.size() < getAllNodes().size()) {
			Node node = tovisit.poll();
			if (visited.contains(node)) continue;
			v.visit(node);
			visited.add(node);
			for (Node n : node.getNeighbors()) {
				if (!visited.contains(n)) {
					tovisit.add(n);
				}
			}
		}
	}
	
	public void depthFirstSearch(String name, NodeVisitor v) {
		Set<Node> visited = new HashSet<>();
		Stack<Node> tovisit = new Stack<>();
		Node start = getOrCreate(name);
		tovisit.add(start);
		while (!tovisit.isEmpty() && visited.size() < getAllNodes().size()) {
			Node node = tovisit.pop();
			if (visited.contains(node)) continue;
			v.visit(node);
			visited.add(node);
			for (Node n : node.getNeighbors()) {
				if (!visited.contains(n)) {
					tovisit.push(n);
				}
			}
		}
	}
	
	static class Path implements Comparable<Path> {
		Node dst;
		int cost;
		Path(Node dst, int cost) {
			this.dst = dst;
			this.cost = cost;
		}
		@Override
		public int compareTo(Path other) {
			return this.cost - other.cost;
		}
	}
	
	public Map<Node, Integer> dijkstra(String startName) {
		Map<Node, Integer> results = new HashMap<>();
		
		PriorityQueue<Path> q = new PriorityQueue<>();
		Node start = getOrCreate(startName);
		q.add(new Path(start, 0));
		
		while (!q.isEmpty()) {
			Path p = q.poll();
			Node node = p.dst;
			int cost = p.cost;
			if (results.containsKey(node)) continue;
			results.put(node, cost);
			for (Node n : node.getNeighbors()) {
				if (!results.containsKey(n)) {
					q.add(new Path(n, cost + node.getWeight(n)));
				}
			}
		}
		// TODO: check for disconnected graph
		// we know it's disconnected when:
		// results.size() < getAllNodes().size()
		// how to represent infinity?
		// Some options: -1, null, Integer.MAX_VALUE
		// 
		return results;
	}
	
	private static class Edge implements Comparable<Edge> {
		
		String src;
		String dst;
		int weight;
		
		public Edge(String src, String dst, int weight) {
			this.src = src;
			this.dst = dst;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge other) {
			// TODO Auto-generated method stub
			return this.weight - other.weight;
		}
		
		public String toString() {
			return String.format("%s - %s (%d)", src, dst, weight);
		}
	}
	
	static void putEdgesintoPQ(Node node, PriorityQueue<Edge> q, Graph g) {
		for (Node n : node.getNeighbors()) {
			if (!g.containsNode(n.getName())) {
				Edge e = new Edge(node.getName(), n.getName(), node.getWeight(n));
				q.add(e);
			}
		}
	}
	
	public String toString() {
		return getAllNodes().toString();
	}
	
	public Graph primJarnik() {
		Graph result = new Graph();
		PriorityQueue<Edge> q = new PriorityQueue<>();
		
		// put one node's edges into the PQ
		Node start = getAllNodes().iterator().next();
		putEdgesintoPQ(start, q, result);
		
		while (!q.isEmpty() && result.getAllNodes().size() < getAllNodes().size()) {
			Edge e = q.poll();
			if (result.containsNode(e.src) && result.containsNode(e.dst)) continue;
			// n1 and n2 are nodes in the RESULT GRAPH
			Node n1 = result.getOrCreate(e.src);
			Node n2 = result.getOrCreate(e.dst);
			n1.addUndirectedEdge(n2, e.weight);
			
			// originalN2 is in the ORIGINAL GRAPH, or "this"
			Node originalN2 = getOrCreate(e.dst);
			putEdgesintoPQ(originalN2, q, result);
		}
		
		if (result.getAllNodes().size() < getAllNodes().size()) {
			// TODO: make a new exception type that looks better or cleaner or nicer or at least has a good name
			
			throw new RuntimeException("Disconnected graph");
		}
		return result;
	}
	
}
