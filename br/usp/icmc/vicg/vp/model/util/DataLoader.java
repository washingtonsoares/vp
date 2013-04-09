package br.usp.icmc.vicg.vp.model.util;

import java.io.IOException;

import matrix.AbstractMatrix;
import matrix.MatrixFactory;
import br.usp.icmc.vicg.vp.app.DataSet;
import br.usp.icmc.vicg.vp.model.data.DataMatrix;

public class DataLoader {

	public static AbstractMatrix loadData(DataSet dataset) throws IOException {
		
		DataMatrix data = new DataMatrix(dataset);
		data.load();
		return data;
	}
	
	public static AbstractMatrix loadData(String filename) throws Exception {
		
		return MatrixFactory.getInstance(filename);
	}
}
