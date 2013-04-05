package br.usp.vp.model.tree;

import java.util.ArrayList;


public class InteractionsTree {

	private Graph graph;

	private ArrayList<Vertex> vertices;
	private Vertex currentVertex;

	public InteractionsTree() {

		vertices = new ArrayList<Vertex>();
		
		createGraph();
	}
	
	private void createGraph() {

		graph = new Graph();
	}
	
	public Graph getGraph() {
		return graph;
	}

	public Vertex getVertexAt(Integer index) {
		
		return vertices.get(index);
	}

	public void addNewVertex(Object thumbnail) {

		graph.getModel().beginUpdate();
		try
		{
			Vertex newVertex = new Vertex(graph.getNumVertices() + 1);
			graph.addVertex(newVertex);
			vertices.add(newVertex);

			if (currentVertex != null) {
				
				graph.addEdge(currentVertex, newVertex);
			}
			setCurrentVertex(newVertex, false);
		}
		finally
		{
			graph.getModel().endUpdate();
		}
	}

	public void setCurrentVertex(Vertex vertex, boolean update) {
		
		if (currentVertex == null) {
			
			this.currentVertex = vertex;
		} 
		else {
					
			graph.setCellStyle(Vertex.DEFAULT_STYLE, new Object[]{currentVertex});
			this.currentVertex = vertex;
		}
		
		if (update) {
			
			graph.setCellStyle(Vertex.CURRENT_STYLE, new Object[]{vertex});
		}
	}
}
