package br.usp.vp.view.tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

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

public class InteractionsTreePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int fontSize = 12;
	private static final int gap = 15;

	private InteractionsTree tree;

	private mxGraphComponent component;
	private mxHierarchicalLayout layout;
	private JLabel titleLabel;

	public InteractionsTreePanel() {

		this.setLayout(new GridLayout(1,1));
		this.setBorder(new EmptyBorder(gap, gap, gap, gap));

		titleLabel = new JLabel("Interactions Tree");
		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font(null, Font.BOLD, fontSize));
	}
	
	public void setTree(InteractionsTree tree) {

		this.tree = tree;
		
		createComponent();
		createLayout();

		this.add(component);
		
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent arg0) {

				layoutGraph();
			}}
		);
		setBackground(getBackground());
	}

	private void createLayout() {

		layout = new mxHierarchicalLayout(component.getGraph(),
				SwingConstants.WEST);
	}

	public void layoutGraph() {

		component.getGraph().getModel().beginUpdate();
		try {
			layout.execute(component.getGraph().getDefaultParent());
		} finally {

			component.getGraph().getModel().endUpdate();
			resizeComponent();
		}
	}

	public void morphingLayoutGraph() {

		component.getGraph().getModel().beginUpdate();
		try {
			layout.execute(component.getGraph().getDefaultParent());
		} finally {
			mxMorphing morph = new mxMorphing(component);

			morph.addListener(mxEvent.DONE, new mxIEventListener() {

				@Override
				public void invoke(Object arg0, mxEventObject arg1) {

					resizeComponent();
				}
			});
			morph.startAnimation();
			component.getGraph().getModel().endUpdate();
		}
	}

	private void createComponent() {

		component = new mxGraphComponent(tree.getGraph());
		
		component.getViewport().setOpaque(true);
		component.setEnabled(false);
		component.setBorder(null);

		component.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		component.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		component.getGraphControl().addMouseListener(new MouseAdapter()
		{

			public void mouseReleased(MouseEvent e)
			{
				Object cell = component.getCellAt(e.getX(), e.getY());

				if (cell != null)
				{
					if (cell instanceof mxCell) {

						if (((mxCell) cell).isVertex()) {

							Integer value = (Integer) 
									component.getGraph().getModel().getValue(cell);
							Controller.changeContextTo(value);
						}
					}
				}
			}
		});
	}

	private void resizeComponent() {

		mxRectangle actualBounds = component.getGraph().getGraphBounds();
		Rectangle visibleBounds = component.getViewport().getVisibleRect();

		float widthRatio = (float) (visibleBounds.getWidth() / 
				actualBounds.getWidth());

		float heigthRatio = (float) (visibleBounds.getHeight() / 
				actualBounds.getHeight());

		if (widthRatio > 1 && heigthRatio > 1) {

			component.zoomActual();
		}
		else {

			float factor = Math.min(widthRatio, heigthRatio);
			factor = factor * 0.95f;
			component.zoom(factor);
		}
	}

	@Override
	public void setBackground(Color color) {

		super.setBackground(color);

		if (component != null) {

			component.getViewport().setBackground(color);
		}
		if (this.titleLabel != null) {

			this.titleLabel.setBackground(color);
		}
	}
}
