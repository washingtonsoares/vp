package br.usp.icmc.vicg.vp.model.projection;

import matrix.AbstractMatrix;
import projection.model.ProjectionModel;
import projection.model.ProjectionModel.InstanceType;
import visualizationbasics.color.ColorScaleFactory.ColorScaleType;

public class DualProjections  {

	private ProjectionModel itemsModel;
	private ProjectionModel dimensionsModel;

	public DualProjections(AbstractMatrix itemsProj, AbstractMatrix dimsProj) {

		itemsModel = createProjectionModel(itemsProj);
		dimensionsModel = createProjectionModel(dimsProj);
	}

	public ProjectionModel getItemsModel() {
		return itemsModel;
	}

	public ProjectionModel getDimensionsModel() {
		return dimensionsModel;
	}

	private ProjectionModel createProjectionModel(AbstractMatrix projection) {

		ProjectionModel projModel = new ProjectionModel();
		projModel.addProjection(projection, InstanceType.CIRCLED_INSTANCE, 5);
		projModel.changeColorScaleType(ColorScaleType.ORANGE_TO_BLUESKY);

		return projModel;
	}
}
