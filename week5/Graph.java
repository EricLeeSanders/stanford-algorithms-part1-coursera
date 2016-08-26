package week5;

import java.util.ArrayList;
import java.util.List;

/**
 * A graph with vertices
 * 
 * @author Eric
 *
 */
public class Graph {
	private final List<Vertex> vertices;

	public Graph() {
		this.vertices = new ArrayList<Vertex>();
	}

	public Graph(List<Vertex> vertices) {
		this.vertices = vertices;
	}

	public void addVertex(Vertex v) {
		this.vertices.add(v);
	}

	public Vertex getVertex(int v) {
		return this.vertices.get(v);
	}

	public List<Vertex> getVertices() {
		return this.vertices;
	}

	public int getNumOfVertices() {
		return this.vertices.size();
	}
}
