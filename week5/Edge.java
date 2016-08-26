package week5;

/**
 * Represents a weighted edge with a source and destination
 * 
 * @author Eric
 *
 */
public class Edge {
	private final Vertex source;
	private final Vertex destination;
	private final double weight;

	public Edge(Vertex source, Vertex destination, double weight) {
		if (weight < 0) {
			throw new IndexOutOfBoundsException("Weights cannot be negative");
		}
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public Vertex getSource() {
		return this.source;
	}

	public Vertex getDestination() {
		return this.destination;
	}

	public double getWeight() {
		return this.weight;
	}

	@Override
	public String toString() {
		return this.source.getName() + " - " + this.destination.getName() + " : " + this.weight;
	}
}
