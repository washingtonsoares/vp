package br.usp.vp.view.tree;

import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import br.usp.vp.controller.Controller;
import br.usp.vp.model.tree.InteractionsTree;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxRectangle;

public class TreeComponent extends mxGraphComponent implements ComponentListener,
	MouseListener{

	private static final long serialVersionUID = -768829761119011630L;

	private mxHierarchicalLayout layout;

	public TreeComponent(InteractionsTree tree) {

		super(tree.getGraph());

		this.layout = new mxHierarchicalLayout(getGraph(),SwingConstants.WEST);

		this.getViewport().setOpaque(true);
		this.setEnabled(false);
		this.setBorder(null);

		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.getGraphControl().addComponentListener(this);
		this.getGraphControl().addMouseListener(this);
	}

	public void resizeComponent() {

		mxRectangle actualBounds = getGraph().getGraphBounds();
		Rectangle visibleBounds = getViewport().getVisibleRect();

		float widthRatio = (float) (visibleBounds.getWidth() / 
				actualBounds.getWidth());

		float heigthRatio = (float) (visibleBounds.getHeight() / 
				actualBounds.getHeight());

		if (widthRatio > 1 && heigthRatio > 1) {

			zoomActual();
		}
		else {

			float factor = Math.min(widthRatio, heigthRatio);
			factor = factor * 0.95f;
			zoom(factor);
		}
	}

	public void layoutGraph() {

		getGraph().getModel().beginUpdate();
		try {
			layout.execute(getGraph().getDefaultParent());
		} finally {

			getGraph().getModel().endUpdate();
			resizeComponent();
		}
	}

	public void morphingLayoutGraph() {

		getGraph().getModel().beginUpdate();
		try {
			layout.execute(getGraph().getDefaultParent());
		} finally {
			mxMorphing morph = new mxMorphing(this);

			morph.addListener(mxEvent.DONE, new mxIEventListener() {

				@Override
				public void invoke(Object arg0, mxEventObject arg1) {

					resizeComponent();
				}
			});
			morph.startAnimation();
			getGraph().getModel().endUpdate();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		Object cell = getCellAt(e.getX(), e.getY());

		if (cell != null)
		{
			if (cell instanceof mxCell) {

				if (((mxCell) cell).isVertex()) {

					Integer value = (Integer) getGraph().getModel().getValue(cell);
					Controller.changeContextTo(value);
				}
			}
		}
	}
	
	@Override
	public void componentResized(ComponentEvent e) {

		System.out.println(getSize());
		layoutGraph();
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
