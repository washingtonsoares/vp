package br.usp.vp.view.tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;

import br.usp.vp.model.tree.AbstractVertex;
import br.usp.vp.model.tree.InteractionsTree;

import com.mxgraph.model.mxCell;

public class InteractionsTreePanel extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private static final int fontSize = 12;
	private static final int gap = 15;

	private static final int X_PAD = 50;
	private static final int Y_PAD = 0;
	
	private TreeComponent component;
	private JLabel titleLabel;

	private JLayeredPane layeredPane;

	private JPanel vertexThumbnail;
	private JPanel edgeThumbnail;

	private boolean stillOnVertex = false;
	
	public InteractionsTreePanel(InteractionsTree tree) {

		super();

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(new EmptyBorder(gap, gap, gap, gap));

		// Title Label
		titleLabel = new JLabel("Interactions Tree");
		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font(null, Font.BOLD, fontSize));

		// Tree Component
		component = new TreeComponent(tree);

		// Thumb nails
		vertexThumbnail = createThumbnail();
		edgeThumbnail = createThumbnail();

		// Compose Layered Panel
		layeredPane = new JLayeredPane();
		component.getGraphControl().addMouseMotionListener(this); 
		this.addMouseMotionListener(this); 

		JViewport temp = component.getViewport();
		temp.setBounds(10, 20, 700, 300);
		layeredPane.add(temp, new Integer(0));

		layeredPane.add(vertexThumbnail, new Integer(1));
		layeredPane.add(edgeThumbnail, new Integer(1));

		this.add(layeredPane);
	}

	public TreeComponent getComponent() {
		return component;
	}

	private JPanel createThumbnail() {

		JPanel thumb = new JPanel();
		thumb.setLayout(new GridLayout(1,1));
		thumb.setVisible(false);
		thumb.setBounds(15, 15, Thumbnail.WIDTH, Thumbnail.HEIGHT);

		return thumb;
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

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {

		Object cell = component.getCellAt(e.getX(), e.getY());

		boolean notOverVertex = true;
		boolean notOverEdge = true;
		
		if (cell != null) {

			// if is cell
			if (cell instanceof mxCell) {

				if (((mxCell) cell).isVertex()) {
					
					notOverVertex = false;

					if (!stillOnVertex) {

						stillOnVertex = true;

						int x = (int) ((mxCell) cell).getGeometry().getX();

						Thumbnail vertexThumb = ((AbstractVertex) cell).getThumbnail();

						vertexThumbnail.removeAll();
						vertexThumbnail.add(vertexThumb);
						vertexThumbnail.setLocation(x + X_PAD, Y_PAD);
						vertexThumbnail.setVisible(true);
						vertexThumbnail.revalidate();
					}
				}

				if (((mxCell) cell).isEdge()) {

					notOverEdge = false;
					// TODO: do something
				}
			} 
		} 
		if (notOverVertex) {
			
			vertexThumbnail.setVisible(false);
			stillOnVertex = false;
		}
		if (notOverEdge) {
			
			edgeThumbnail.setVisible(false);
		}
	}
}