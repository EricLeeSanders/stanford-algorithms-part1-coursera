package week3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
/**
 * Computes the minimum number of cuts for a graph using Karger's Algorithm.
 * Running time: lower bounded by Omega(n^2 * m).
 * Probability of failure: 1/n
 * @author Eric
 *
 */
public class KargerMinCut {
	/**
	 * Computes the minimum number of cuts
	 * @param vertices
	 * @return int - estimated number of cuts
	 */
	public int minNumOfCuts(Map<Integer, List<Integer>> vertices) {
		while (vertices.size() > 2) {
			mergeRandomVertices(vertices);
		}
		// Return the number of adjacent vertices for a vertex.
		// The vertex we choose doesn't really matter.
		Integer value = vertices.keySet().iterator().next();
		List<Integer> values = vertices.get(value);
		return values.size();
	}

	/**
	 * Merges two vertices randomly. Unions the edges of the merged vertices.
	 * 
	 * @param vertices
	 */
	private void mergeRandomVertices(Map<Integer, List<Integer>> vertices) {
		Random random = new Random();

		// get a random vertex
		Object[] keys = vertices.keySet().toArray();
		// 0 : length-1
		int randVertex = (int) keys[random.nextInt(keys.length)]; 

		// get a random adjacent vertex
		// 0 : number of edges - 1
		int randAdjVertexPos = random.nextInt(vertices.get(randVertex).size()); 
		int randAdjVertex = vertices.get(randVertex).get(randAdjVertexPos);

		// All adjacent vertices for the old vertex need to be changed to the
		// new merged vertex
		int mergedVertex = randVertex; // for clarity
		int oldVertex = randAdjVertex;
		for (int v : vertices.get(oldVertex)) {
			vertices.get(v).remove((Integer) oldVertex);
			vertices.get(v).add(mergedVertex);
			vertices.get(mergedVertex).add(v);
		}
		// Remove the old vertex from the map
		vertices.remove(oldVertex);
		// remove any self loops
		removeSelfReferences(vertices, mergedVertex);

	}

	/**
	 * Removes self references
	 * 
	 * @param vertices
	 * @param mergedVertex
	 */
	private void removeSelfReferences(Map<Integer, List<Integer>> vertices, Integer mergedVertex) {
		List<Integer> mergedVertexList = vertices.get(mergedVertex);
		for (Iterator<Integer> iterator = mergedVertexList.iterator(); iterator.hasNext();) {
			Integer vertex = iterator.next();
			if (vertex.equals(mergedVertex)) {
				iterator.remove();
			}
		}
	}

	/**
	 * Creates a list of vertices from input where the first integer is the
	 * vertex and the remaining integers are adjacent vertices.
	 * 
	 * @param input
	 *            - list of vertices and their edges
	 * @return Map<Integer, List<Integer>>
	 */
	private static Map<Integer, List<Integer>> createVertices(List<List<String>> input) {
		final Map<Integer, List<Integer>> vertices = new HashMap<Integer, List<Integer>>();
		for (int i = 1; i <= input.size(); i++) {
			List<Integer> edges = new ArrayList<Integer>();
			for (int j = 1; j < input.get(i - 1).size(); j++) {
				Integer edge = Integer.valueOf(input.get(i - 1).get(j));
				edges.add(edge);
			}
			vertices.put(i, edges);
		}

		return vertices;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("src/week3/input.txt"));
		List<List<String>> input = new ArrayList<List<String>>();
		while (scanner.hasNextLine()) {
			String[] vertices = scanner.nextLine().split("\\s+");
			if (vertices.length > 1) {
				input.add(new ArrayList<String>(Arrays.asList(vertices)));
			}
		}
		scanner.close();
		KargerMinCut kmc = new KargerMinCut();
		int size = input.size();
		int smallestCut = Integer.MAX_VALUE;
		double loopCount = Math.pow(size, 2) * Math.log(size);
		for (int i = 0; i <= loopCount; i++) {
			
			if ((i % 100) == 0) {
				System.out.println((i + 1) + " out of " + loopCount);
			}
			
			Map<Integer, List<Integer>> vertices = createVertices(input);
			int cutSize = kmc.minNumOfCuts(vertices);
			if (cutSize < smallestCut) {
				smallestCut = cutSize;
			}
		}
		System.out.println("Smallest count: " + smallestCut);
	}

}
