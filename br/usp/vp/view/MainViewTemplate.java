package br.usp.vp.view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainViewTemplate extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final int width = 800;
	private static final int topPanelHeight= 50;
	private static final int middlePanelHeight= 500;
	private static final int bottomPanelHeight= 200;
	private static final int minSize = 100;
	private static final int maxSize = Short.MAX_VALUE;

	protected JPanel mainPanel;
	
	protected JPanel topPanel;
	protected JPanel middlePanel;
	protected JPanel bottomPanel;
	
	public MainViewTemplate() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.setLayout(new GridLayout(1,1));
		
		initComponents();
		
		this.add(mainPanel);
		
		pack();
	}
	
	private void initComponents() {

		mainPanel = new JPanel();
		
		topPanel = new JPanel();
		middlePanel = new JPanel();
		bottomPanel = new JPanel();
		
		topPanel.setPreferredSize(new Dimension(width, topPanelHeight));
		topPanel.setSize(new Dimension(width, topPanelHeight));
		topPanel.setMinimumSize(new Dimension(minSize, topPanelHeight));
		topPanel.setMaximumSize(new Dimension(maxSize, topPanelHeight));
		topPanel.setLayout(new GridLayout(1,1));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		middlePanel.setPreferredSize(new Dimension(width, middlePanelHeight));
		middlePanel.setSize(new Dimension(width, middlePanelHeight));
		middlePanel.setMinimumSize(new Dimension(minSize, minSize));
		middlePanel.setMaximumSize(new Dimension(maxSize, maxSize));
		middlePanel.setLayout(new GridLayout(1,1));
		middlePanel.setBorder(BorderFactory.createEtchedBorder());
		
		bottomPanel.setPreferredSize(new Dimension(width, bottomPanelHeight));
		bottomPanel.setSize(new Dimension(width, bottomPanelHeight));
		bottomPanel.setMinimumSize(new Dimension(minSize, minSize));
		bottomPanel.setMaximumSize(new Dimension(maxSize, maxSize));
		bottomPanel.setLayout(new GridLayout(1,1));
		bottomPanel.setBorder(BorderFactory.createEtchedBorder());
		
		GroupLayout layout = new GroupLayout(mainPanel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		GroupLayout.ParallelGroup hGroup = layout.createParallelGroup();
		hGroup.addComponent(topPanel).
		addComponent(middlePanel).
		addComponent(bottomPanel);
		layout.setHorizontalGroup(hGroup);
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addComponent(topPanel).
		addComponent(middlePanel).
		addComponent(bottomPanel);
		layout.setVerticalGroup(vGroup);
		
		mainPanel.setLayout(layout);
	}
}
