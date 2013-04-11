package br.usp.icmc.vicg.vp.model.data;

public class DataSet {

	private String filename;
	private Integer labelIndex; 
	private Integer classIndex;
	private Integer[] ignoreIndices;
	
	public DataSet(String filename, Integer labelIndex, Integer classIndex,
			Integer[] ignoreIndices) {
		
		this.filename = filename;
		this.labelIndex = labelIndex;
		this.classIndex = classIndex;
		this.ignoreIndices = ignoreIndices;
	}
	
	public DataSet(String filename, Integer ignored) {
		
		this.filename = filename;
		this.labelIndex = null;
		this.classIndex = null;
		this.ignoreIndices = new Integer[]{ignored};
	}

	public String getFilename() {
		return filename;
	}

	public Integer getLabelIndex() {
		return labelIndex;
	}

	public Integer getClassIndex() {
		return classIndex;
	}

	public Integer[] getIgnoreIndices() {
		return ignoreIndices;
	}
}
