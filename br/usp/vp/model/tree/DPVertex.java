package br.usp.vp.model.tree;

import br.usp.vp.model.projection.dual.DualProjections;
import br.usp.vp.view.projection.dual.DualProjectionsPanel;
import br.usp.vp.view.tree.Thumbnail;

public class DPVertex extends AbstractVertex {
	
	private static final long serialVersionUID = -419912908147663072L;
	
	private DualProjections dualProjections;
	private DualProjectionsPanel dualPanel;
	
	public DPVertex(Integer id, DualProjections dualProjections,
			DualProjectionsPanel dualPanel) {
		
		super(id);
		
		this.dualPanel = dualPanel;
		this.dualProjections = dualProjections;
	}

	public DualProjections getDualProjections() {
		return dualProjections;
	}
	
	public void createThumbnail() {
		
		thumbnail = new Thumbnail(dualPanel);
	}
}
