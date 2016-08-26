package week5;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a vertex with a cost to reach from a source vertex
 * 
 * @author Eric
 *
 */
public class Vertex implements Comparable<Vertex> {
	private final int name;
	private final List<Edge> edges = new ArrayList<Edge>();
	private double cost; // cost to reach this vertex from a source

	public Vertex(int name) {
		this(name, Double.POSITIVE_INFINITY);

	}

	public Vertex(int name, double cost) {
		this.name = name;
		this.cost = cost;
	}

	public int getName() {
		return this.name;
	}

	public double getCost() {
		return this.cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void addEdge(Edge e) {
		this.edges.add(e);
	}

	public List<Edge> getEdges() {
		return this.edges;
	}

	@Override
	public String toString() {
		return this.name + ": " + this.cost;
	}

	@Override
	public int compareTo(Vertex v) {
		if (this.getCost() == v.getCost()) {
			return 0;
		}
		if (this.getCost() < v.getCost()) {
			return -1;
		}
		return 1;
	}

}
