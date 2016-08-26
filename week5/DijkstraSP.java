package week5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Computes the shortest path from a source vertex to every other vertex in a
 * positive edge weighted graph. Uses Dijkstra's Algorithm with a min-heap.
 * Running Time: O(E * log V)
 *
 * @author Eric
 *
 */

public class DijkstraSP {
	private MinHeap<Vertex> pq;

	/**
	 * Finds the shortest paths from a source
	 */
	public void findShortestPath(Graph G, int source) {
		int n = G.getNumOfVertices();
		Vertex sourceVertex = G.getVertex(source);
		sourceVertex.setCost(0.0); // set source cost to 0

		// Add all of the vertices to the heap
		Vertex[] vertexArr = G.getVertices().toArray(new Vertex[n]);
		this.pq = new MinHeap<Vertex>(vertexArr);

		while (!this.pq.isEmpty()) {
			// grab the vertex with the least cost
			Vertex v = this.pq.poll();
			// evaluate each edge
			for (Edge e : v.getEdges()) {
				evaluateEdge(e);
			}
		}
	}

	/**
	 * Evaluate an edge to determine if a shorter path to the destination vertex
	 * form the source vertex exists. If there is a shorter path, update the
	 * cost of the destination vertex to the new cost.
	 *
	 * @param e
	 *
	 */
	private void evaluateEdge(Edge e) {
		Vertex edgeSource = e.getSource();
		Vertex edgeDest = e.getDestination();
		double curDist = edgeDest.getCost();
		double newDist = edgeSource.getCost() + e.getWeight();
		// Update if there is a shorter path to the vertex
		if (newDist < curDist) {
			edgeDest.setCost(newDist);
			this.pq.decreaseKey(edgeDest);
		}
	}

	private static List<List<String>> readInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("src/week5/input.txt"));
		List<List<String>> input = new ArrayList<List<String>>();
		String[] edges;
		// Read the input
		while (scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if (!(nextLine.equals(""))) {
				edges = nextLine.split("\\s+");
				if (edges.length > 0) {
					input.add(new ArrayList<String>(Arrays.asList(edges)));
				}
			}
		}
		scanner.close();
		return input;
	}

	private static List<Vertex> createVertices(List<List<String>> input) {
		List<Vertex> vertices = new ArrayList<Vertex>();
		// Create the vertices and initialize their cost to be infinity
		for (int i = 0; i <= input.size(); i++) {
			vertices.add(new Vertex(i, Double.POSITIVE_INFINITY));
		}
		// Loop through each edge in the input and create an Edge
		for (int i = 0; i < input.size(); i++) {
			Vertex source = vertices.get(i + 1);
			for (int j = 1; j < input.get(i).size(); j++) {
				List<String> strEdge = Arrays.asList(input.get(i).get(j).split(","));
				Vertex dest = vertices.get(Integer.valueOf(strEdge.get(0)));
				double cost = Double.valueOf(strEdge.get(1));
				Edge edge = new Edge(source, dest, cost);
				source.addEdge(edge);
			}
		}
		return vertices;
	}

	public static void main(String[] args) throws FileNotFoundException {
		DijkstraSP sp = new DijkstraSP();

		List<List<String>> input = readInput();
		List<Vertex> vertices = createVertices(input);
		Graph G = new Graph(vertices);
		sp.findShortestPath(G, 1);
		List<Vertex> path = G.getVertices();

		for (Vertex v : path) {
			System.out.println(v);
		}

	}

}
