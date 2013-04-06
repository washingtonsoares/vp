package br.usp.vp.model.util;

public class Misc {

	public static double[][] convertFloatsToDoubles(float[][] input) {
        if (input == null) {
            return null; // Or throw an exception - your choice
        }
        double[][] output = new double[input.length][];
        for (int i = 0; i < input.length; i++) {
            output[i] = new double[input[i].length];
            for (int j = 0; j < input[i].length; j++) {
                output[i][j] = input[i][j];
            }
        }
        return output;
    }
	
	public static double[] convertFloatsToDoubles(float[] input) {
		
        if (input == null) {
            return null; // Or throw an exception - your choice
        }
        double[] output = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = input[i];
        }
        return output;
    }
}
