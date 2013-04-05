package br.usp.vp.model.tree;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

public class Vertex extends mxCell {
	
	private static final long serialVersionUID = 1L;
	
	private static final int SIZE = 30;
	
	public static final String STYLE_TEMPLATE = 
			"defaultVertex;" + 
			"shape=ellipse;" +
			"perimeter=ellipsePerimeter;" +
			"strokeColor=#000000;";
	
	public static final String DEFAULT_STYLE = 
			 STYLE_TEMPLATE + "fillColor=#24AAC3";
	
	public static final String CURRENT_STYLE = 
			STYLE_TEMPLATE + "fillColor=#E69400";
	
	public Vertex(Integer id) {
		
		super();
		
		mxGeometry geometry = new mxGeometry(0, 0, SIZE, SIZE);
		geometry.setRelative(false);
		
		Object value = id;
		
		this.setValue(value);
		this.setGeometry(geometry);
		this.setStyle(CURRENT_STYLE);
		this.setVertex(true);
		this.setConnectable(true);
	}
}
