package br.usp.icmc.vicg.vp.model.tree;

import java.util.ArrayList;


public class InteractionsTree {

	private Graph graph;

	private ArrayList<AbstractVertex> vertices;
	private AbstractVertex currentVertex;

	public InteractionsTree() {

		vertices = new ArrayList<AbstractVertex>();
		
		createGraph();
	}
	
	private void createGraph() {

		graph = new Graph();
	}
	
	public Graph getGraph() {
		return graph;
	}

	public AbstractVertex getVertexAt(Integer index) {
		
		return vertices.get(index);
	}

	public AbstractVertex getCurrentVertex() {
		return currentVertex;
	}
	
	public void addNewVertex(AbstractVertex newVertex) {

		graph.getModel().beginUpdate();
		try
		{
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

	public void setCurrentVertex(AbstractVertex vertex, boolean update) {
		
		if (currentVertex == null) {
			
			this.currentVertex = vertex;
		} 
		else {
					
			graph.setCellStyle(DPVertex.DEFAULT_STYLE, new Object[]{currentVertex});
			this.currentVertex = vertex;
		}
		
		if (update) {
			
			graph.setCellStyle(DPVertex.CURRENT_STYLE, new Object[]{vertex});
		}
	}
}
