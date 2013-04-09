package br.usp.icmc.vicg.vp.model.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.usp.icmc.vicg.vp.app.DataSet;

import matrix.AbstractVector;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;

public class DataMatrix extends DenseMatrix {

	private static final String separator = ",";

	private String filename;

	private Integer labelIndex;
	private Integer classIndex;
	private String classLabel;
	private Set<Integer> ignoreIndices;

	public DataMatrix(DataSet dataset) {

		this.rows = new ArrayList<AbstractVector>();
		this.attributes = new ArrayList<String>();

		this.filename = dataset.getFilename();
		this.labelIndex = dataset.getLabelIndex();
		this.classIndex = dataset.getClassIndex();
		
		Integer[] ignored = dataset.getIgnoreIndices();
		this.ignoreIndices = new HashSet<Integer>();
		if (ignored != null) {

			this.ignoreIndices.addAll(Arrays.asList(ignored));
		}
	}

	public String getClassLabel() {
		return classLabel;
	}

	@Override
	public void load(String filename) throws IOException {

		load();
	}

	private boolean isIndexValid(Integer index) {

		if (ignoreIndices.contains(index)) {

			return false;
		}
		return true;
	}

	public void load() throws IOException {

		BufferedReader in = null;

		try {

			in = new BufferedReader(new FileReader(filename));

			// Get first line
			String[] colNames = in.readLine().split(separator);

			// Set valid indices 
			ArrayList<Integer> validIndices = new ArrayList<>();
			for (int i = 0; i < colNames.length; i++) {

				if (isIndexValid(i)) {

					validIndices.add(i);
				}
			}

			// set number of columns 
			int nrdims = validIndices.size();

			// Read columns names
			for (Integer vi : validIndices) {

				this.attributes.add(colNames[vi].trim());
			}
			
			if (classIndex != null) {

				classLabel = colNames[classIndex].trim();
			}

			String line;
			
			// Read vectors
			while ((line = in.readLine()) != null && line.trim().length() > 0) {

				String[] values = line.split(separator);

				// Create vector
				float[] vector = new float[nrdims];

				// Fill vector
				int index = 0;
				for (Integer vi : validIndices) {

					vector[index++] = Float.parseFloat(values[vi]);
				}

				// Get id
				Integer id = this.getRowCount();
				
				// Get label
				String label; 
				if (labelIndex != null) {
					
					label = values[labelIndex];
				}
				else {
					
					label = id.toString();
				}
				
				// Get klass
				float klass;
				if (classIndex != null) {
					
					klass = Float.parseFloat(values[classIndex]);
				}
				else {
					
					klass = 0f;
				}

				// Add vector
				DenseVector newVector = new DenseVector(vector); 
				newVector.setId(id);
				newVector.setKlass(klass);
				
				this.addRow(newVector, label);
			}

		} catch (FileNotFoundException e) {
			throw new IOException("File " + filename + " does not exist!");
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					Logger.getLogger(DataMatrix.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
}
