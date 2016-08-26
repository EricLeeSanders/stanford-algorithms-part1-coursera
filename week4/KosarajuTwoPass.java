package week4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

import week1.MergeSort;

/**
 * Computes the number of strongly connected components (SCC) in a directed
 * graph using Kosarju's algorithm. Running Time: O(V + E)
 *
 * @author Eric
 *
 */

public class KosarajuTwoPass {
	/**
	 * Represents a graph with a list of vertices, a list of adjacent
	 * relationships and the reverse of the adjacent relationship.
	 *
	 * @author Eric
	 *
	 */
	class Graph {
		List<Integer> vertices = new ArrayList<Integer>();
		List<List<Integer>> adj = new ArrayList<List<Integer>>();
		List<List<Integer>> adjReverse = new ArrayList<List<Integer>>();

		public Graph(int numOfVertices) {
			this.vertices.add(0); // Add a blank, dummy vertex in position 0.
			this.adj.add(new ArrayList<Integer>());
			this.adjReverse.add(new ArrayList<Integer>());
			for (int i = 1; i <= numOfVertices; i++) {
				this.vertices.add(i);
				this.adj.add(new ArrayList<Integer>());
				this.adjReverse.add(new ArrayList<Integer>());
			}
		}

		public void addEdge(int v, int w) {
			this.adj.get(v).add(w);
			this.adjReverse.get(w).add(v);
		}

	}

	/**
	 * Uses Kosaraju's algorithm to compute the number of SCCs. Returns an int
	 * array. The value in each array position is the number of vertices for the
	 * given leader node in its SCC. If the value is 0, then that vertex is not
	 * a leader node. If it is > 0 then that vertex is a leader node and the
	 * value is the number of vertices in its SCC.
	 *
	 * @param G
	 * @return int []
	 */
	public int[] kosarajuSCC(Graph G) {
		Deque<Integer> stack = new ArrayDeque<Integer>(G.vertices.size());
		boolean[] visited = new boolean[G.vertices.size()];

		// Perform a DFS to get the vertices in post-order
		for (int i = 1; i < G.vertices.size(); i++) {
			int v = G.vertices.get(i);
			if (!visited[v]) {
				dfs(G, v, visited, stack);
			}
		}

		// Reset the visited array
		for (int i = 1; i < visited.length; i++) {
			visited[i] = false;
		}

		int[] sccCount = new int[G.vertices.size()];

		// Pass through each vertex
		while (!stack.isEmpty()) {
			int leader = stack.pop();
			if (!visited[leader]) {
				// If it is unvisited then it is a leader
				int v = leader; // Just for clarity
				scc_dfs(G, v, visited, leader, sccCount);
			}
		}
		return sccCount;
	}

	/**
	 * Calculates the number of vertices in each SCC
	 *
	 * @param G - Graph
	 * @param v - The vertex that is being evaluated
	 * @param visited - Array to know if a vertex has been visited
	 * @param leader - The vertex that is the leader of the SCC
	 * @param sccCount - The number of vertices in each SCC
	 */
	private void scc_dfs(Graph G, int v, boolean[] visited, int leader, int[] sccCount) {
		visited[v] = true;
		sccCount[leader]++; // Add 1 to show that there is 1 more vertex in this
							// SCC.
		// Iterate through the edges reversed
		for (Integer adjV : G.adjReverse.get(v)) {
			if (!visited[adjV]) {
				scc_dfs(G, adjV, visited, leader, sccCount);
			}
		}
	}

	/**
	 * Performances a DFS on a Graph G given a Vertex v.
	 *
	 * @param G
	 * @param v
	 * @param visited
	 * @param stack
	 */
	private void dfs(Graph G, int v, boolean[] visited, Deque<Integer> stack) {

		visited[v] = true;
		for (Integer adjV : G.adj.get(v)) {
			if (!visited[adjV]) {
				dfs(G, adjV, visited, stack);
			}
		}
		stack.push(v);
	}

	public static void main(String[] args) throws FileNotFoundException {
		KosarajuTwoPass kosarajuTwoPass = new KosarajuTwoPass();
		KosarajuTwoPass.Graph G = kosarajuTwoPass.new Graph(875714);

		Scanner scanner = new Scanner(new File("src/week4/input.txt"));
		int v, w;
		while (scanner.hasNext()) {
			v = scanner.nextInt();
			w = scanner.nextInt();
			G.addEdge(v, w);
		}
		scanner.close();
		int[] sccCount = kosarajuTwoPass.kosarajuSCC(G);

		// Finds the 5 largest SCCs
		// Need to convert to Integer array
		Integer[] sccCountObject = new Integer[sccCount.length];
		for (int i = 0; i < sccCount.length; i++) {
			sccCountObject[i] = Integer.valueOf(sccCount[i]);
		}
		MergeSort<Integer> ms = new MergeSort<Integer>();
		ms.sort(sccCountObject);

		System.out.print("5 largest SCCs: ");
		for (int i = sccCountObject.length - 1; i >= sccCountObject.length - 5; i--) {
			System.out.print(sccCountObject[i] + ",");
		}

	}

}
