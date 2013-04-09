package br.usp.icmc.vicg.vp.app;

public class DataSets {

	public static String pexFolder = "D:\\Dropbox\\work\\datasets\\PEX\\";
	public static String micFolder = "D:\\Dropbox\\work\\datasets\\mic\\";
	public static String uciFolder = "D:\\Dropbox\\work\\datasets\\uci\\";
	
	public static String idh = pexFolder + "idh-2006.data";
	public static String tumor = pexFolder + "primary-tumor.data";
	public static String diabetes = pexFolder + "diabetes.data";
	public static String messages4 = pexFolder + "messages4.data";

	public static String who = micFolder + "WHO.csv";
	public static String Spellman = micFolder + "Spellman.csv";
	public static String microNo = micFolder + "MicrobiomeNoMetadata.csv";
	public static String microWith = micFolder + "MicrobiomeWithMetadata.csv";
	
	public static DataSet iris = new DataSet(
			uciFolder + "Iris.csv", null, 4, new Integer[]{4});
	
	public static DataSet wine = new DataSet(
			uciFolder + "wine.csv", null, 13, new Integer[]{13});
	
	public static DataSet mlb = new DataSet(
			micFolder + "MLB2008.csv", 0, 2, new Integer[]{0,1,2});
}
