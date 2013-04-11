/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import matrix.AbstractMatrix;
import matrix.AbstractVector;
import projection.util.ProjectionConstants;
import visualizationbasics.affinetransformation.TransformationMatrix2D;
import visualizationbasics.color.ColorScaleFactory.ColorScaleType;
import visualizationbasics.color.ColorTable;
import visualizationbasics.model.AbstractModel;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class ProjectionModel extends AbstractModel {

	public enum InstanceType {

		POINT_INSTANCE("Point Instance"),
		LABELED_INSTANCE("Labeled Instance"),
		CIRCLED_INSTANCE("Circled Instance");

		private InstanceType(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
		private final String name;
	};

	protected TransformationMatrix2D mat;
	protected ArrayList<Scalar> scalars;
	protected float alpha;
	protected ColorTable colortable;
	protected Scalar selscalar;

	public ProjectionModel() {

		this.scalars = new ArrayList<Scalar>();
		this.alpha = 1.0f;
		this.colortable = new ColorTable();
		this.selscalar = null;
		this.mat = TransformationMatrix2D.newInstance();
	}

	public void addProjection(AbstractMatrix projection, InstanceType instanceType,
			Integer instanceSize) {

		Scalar cdata = this.addScalar(ProjectionConstants.CDATA);

		//        Why dots and dColor ???
				//        Scalar dots = this.addScalar(ProjectionConstants.DOTS);
		//        Scalar dColor = this.addScalar(ProjectionConstants.DYNAMIC_COLOR_SCALAR);

		int nrows = projection.getRowCount();

		// For each row of the projection
		for (int i = 0; i < nrows; i++) {

			AbstractVector row = projection.getRow(i);
			ProjectionInstance pi = null;

			switch (instanceType) {
			case POINT_INSTANCE:
				pi = new ProjectionInstance(row.getId(),
						row.getValue(0), row.getValue(1));
				break;
			case CIRCLED_INSTANCE:
				if (instanceSize != null) {

					pi = new CircledProjectionInstance(projection.getLabel(i),
							row.getId(), row.getValue(0), row.getValue(1), instanceSize);
				}
				else {

					pi = new CircledProjectionInstance(projection.getLabel(i),
							row.getId(), row.getValue(0), row.getValue(1));
				}
				break;
			case LABELED_INSTANCE:
				if (instanceSize != null) {

					pi = new LabeledProjectionInstance(projection.getLabel(i),
							row.getId(), row.getValue(0), row.getValue(1), instanceSize);
				}
				else {

					pi = new LabeledProjectionInstance(projection.getLabel(i),
							row.getId(), row.getValue(0), row.getValue(1));
				}
				break;
			}

			this.addInstance(pi);

			pi.setScalarValue(cdata, row.getKlass());

			//            Why dots and dColor ???
					//            pi.setScalarValue(dots, 0.0f);
			//            pi.setScalarValue(dColor, 0.0f);
		}
	}

	public void addProjection(AbstractMatrix projection, InstanceType instanceType) {

		addProjection(projection, instanceType, null);
	}

	public void draw(BufferedImage image, boolean highquality) {
		
		if (image != null) {
			//first draw the non-selected instances
			for (int i = 0; i < instances.size(); i++) {
				ProjectionInstance pi = (ProjectionInstance) instances.get(i);

				if (!pi.isSelected()) {
					pi.draw(image, highquality);
				}
			}

			//then the selected instances
			for (int i = 0; i < instances.size(); i++) {
				ProjectionInstance pi = (ProjectionInstance) instances.get(i);

				if (pi.isSelected()) {
					pi.draw(image, highquality);
				}
			}
		}
	}

	public ProjectionInstance getInstanceByPosition(Point point) {
		for (int i = 0; i < instances.size(); i++) {
			ProjectionInstance pi = (ProjectionInstance) instances.get(i);

			if (pi.isInside(point)) {
				return pi;
			}
		}

		return null;
	}

	public ArrayList<ProjectionInstance> getInstancesByPosition(Rectangle rect) {
		ArrayList<ProjectionInstance> selected = new ArrayList<ProjectionInstance>();

		for (int i = 0; i < instances.size(); i++) {
			ProjectionInstance pi = (ProjectionInstance) instances.get(i);

			if (pi.isInside(rect)) {
				selected.add(pi);
			}
		}

		return selected;
	}

	public ArrayList<ProjectionInstance> getInstancesByPosition(Polygon polygon) {
		ArrayList<ProjectionInstance> selected = new ArrayList<ProjectionInstance>();

		for (int i = 0; i < instances.size(); i++) {
			ProjectionInstance pi = (ProjectionInstance) instances.get(i);

			if (pi.isInside(polygon)) {
				selected.add(pi);
			}
		}

		return selected;
	}

	public Scalar addScalar(String name) {
		Scalar scalar = new Scalar(name);

		if (!scalars.contains(scalar)) {
			scalars.add(scalar);
			return scalar;
		} else {
			return scalars.get(scalars.indexOf(scalar));
		}
	}

	public ArrayList<Scalar> getScalars() {
		return scalars;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		setChanged();
	}

	public void changeColorScaleType(ColorScaleType scaletype) {
		colortable.setColorScaleType(scaletype);
		setSelectedScalar(selscalar);
		setChanged();
	}

	public ColorTable getColorTable() {
		return colortable;
	}

	public Scalar getSelectedScalar() {
		return selscalar;
	}

	public void setSelectedScalar(Scalar scalar) {
		if (scalars.contains(scalar)) {
			selscalar = scalar;

			//change the color of each instance
			for (int i = 0; i < instances.size(); i++) {
				ProjectionInstance pi = (ProjectionInstance) instances.get(i);

				if (scalar.getMin() >= 0.0f && scalar.getMax() <= 1.0f) {
					pi.setColor(colortable.getColor(pi.getScalarValue(scalar)));
				} else {
					pi.setColor(colortable.getColor(pi.getNormalizedScalarValue(scalar)));
				}
			}
		} else {
			selscalar = null;

			//change the color of each instance
			for (int i = 0; i < instances.size(); i++) {
				ProjectionInstance pi = (ProjectionInstance) instances.get(i);
				pi.setColor(Color.BLACK);
			}
		}

		setChanged();
	}

	public void setViewport(Rectangle viewport) {

		//getting the projection bounding box
		float maxx = Float.NEGATIVE_INFINITY;
		float minx = Float.POSITIVE_INFINITY;
		float maxy = Float.NEGATIVE_INFINITY;
		float miny = Float.POSITIVE_INFINITY;

		for (int i = 0; i < instances.size(); i++) {
			ProjectionInstance pi = (ProjectionInstance) instances.get(i);

			if (maxx < pi.getX()) {
				maxx = pi.getX();
			}

			if (minx > pi.getX()) {
				minx = pi.getX();
			}

			if (maxy < pi.getY()) {
				maxy = pi.getY();
			}

			if (miny > pi.getY()) {
				miny = pi.getY();
			}
		}

		//getting the transformation matrix to map the projection to
		//the viewport
		mat.loadIdentity();
		mat.scale(1, -1);
		mat.translate(-minx, maxy);
		
		// Why not scale both?
		mat.scale(viewport.width / (maxx - minx), viewport.height / (maxy - miny));
//		if (viewport.width > viewport.height) {
//			mat.scale(viewport.height / (maxx - minx), viewport.height / (maxy - miny));
//		} else {
//			mat.scale(viewport.width / (maxx - minx), viewport.width / (maxy - miny));
//		}
		
		mat.translate(viewport.x, viewport.y);

		setChanged();
	}

	public TransformationMatrix2D getViewportMatrix() {
		return mat;
	}
}
