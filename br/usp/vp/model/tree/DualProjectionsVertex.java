package br.usp.vp.model.tree;

import br.usp.vp.model.projection.dual.DualProjections;

public class DualProjectionsVertex extends AbstractVertex {
	
	private static final long serialVersionUID = -419912908147663072L;
	
	private DualProjections dualProjections;
	
	public DualProjectionsVertex(Integer id, DualProjections dualProjections) {
		
		super(id);
		
		this.dualProjections = dualProjections;
	}

	public DualProjections getDualProjections() {
		return dualProjections;
	}
}
