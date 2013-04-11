package br.usp.icmc.vicg.vp.model.tree;

import br.usp.icmc.vicg.vp.model.projection.DualProjections;
import br.usp.icmc.vicg.vp.view.projection.dual.DualProjectionsPanel;
import br.usp.icmc.vicg.vp.view.tree.Thumbnail;

public class ContextVertex extends AbstractVertex {
	
	private static final long serialVersionUID = -419912908147663072L;
	
	private DualProjections dualProjections;
	private DualProjectionsPanel dualPanel;
	
	public ContextVertex(Integer id, DualProjections dualProjections,
			DualProjectionsPanel dualPanel) {
		
		super(id);
		
		this.dualProjections = dualProjections;
		this.dualPanel = dualPanel;
	}

	public DualProjections getDualProjections() {
		return dualProjections;
	}
	
	public DualProjectionsPanel getDualPanel() {
		return dualPanel;
	}

	@Override
	public void createThumbnail() {
		
		if (thumbnail == null) {
			
			thumbnail = new Thumbnail(dualPanel);
		}
	}
}
