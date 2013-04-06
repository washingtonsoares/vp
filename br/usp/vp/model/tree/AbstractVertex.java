package br.usp.vp.model.tree;

import br.usp.vp.view.tree.Thumbnail;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

public abstract class AbstractVertex extends mxCell {

	private static final long serialVersionUID = -5008917739710606556L;

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
	
	protected Thumbnail thumbnail;
	
	public AbstractVertex(Integer id) {
		
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
	
	public Thumbnail getThumbnail() {
		
		if (thumbnail == null) {
			
			initThumbnail();
		}
		return thumbnail;
	}
	
	public abstract void initThumbnail();

	@Override
	public String toString() {
		return "AbstractVertex [id=" + id + ", value=" + value + "]";
	}
}
