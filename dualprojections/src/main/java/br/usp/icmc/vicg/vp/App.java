package br.usp.icmc.vicg.vp;

import javax.swing.JFrame;

import matrix.AbstractMatrix;
import projection.model.ProjectionModel;
import projection.model.ProjectionModel.InstanceType;
import projection.technique.Projection;
import projection.technique.mds.ClassicalMDSProjection;
import projection.view.ProjectionFrameComp;
import br.usp.icmc.vicg.vp.controller.ControllerHandle;
import br.usp.icmc.vicg.vp.model.data.DataLoader;
import br.usp.icmc.vicg.vp.model.data.DataSet;
import br.usp.icmc.vicg.vp.model.data.DataSets;
import br.usp.icmc.vicg.vp.view.MainView;
import distance.DistanceMatrix;
import distance.dissimilarity.Euclidean;

public class App {

	public static void main(String[] args) throws Exception {

		MainView view = new MainView();
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.pack();
		view.setVisible(true);

		ControllerHandle.getInstance().initView(
				view.getTopPanel(),
				view.getMiddlePanel(), 
				view.getBottomPanel());
		
		AbstractMatrix dataMatrix = null;
		if (args.length > 0) {
		
			dataMatrix = DataLoader.loadData(new DataSet(args[0],
					Integer.parseInt(args[1])));
		}
		else {
			
			dataMatrix = DataLoader.loadData(DataSets.iris);
		}
		
		ControllerHandle.getInstance().attachData(dataMatrix, false);
	}

	public static void main2(String[] args) throws Exception {

		AbstractMatrix dataMatrix = DataLoader.loadData(DataSets.iris);

		// Create Distance Matrices
		DistanceMatrix elemDmat = new DistanceMatrix(dataMatrix, new Euclidean());

		// Project data
		Projection projTech = new ClassicalMDSProjection();
		AbstractMatrix itemsProj = projTech.project(elemDmat);

		ProjectionModel model = new ProjectionModel();
		model.addProjection(itemsProj, InstanceType.CIRCLED_INSTANCE);

		ProjectionFrameComp as = new ProjectionFrameComp();
		as.execute(new ProjectionFrameComp.Input(model));
	}
}
