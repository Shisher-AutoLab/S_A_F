package com.qa.FW.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataReader {

	private static FileInputStream fis;
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static XSSFRow row;

	public static void loadfile(String sheetname) {
		// FileInputStream file = null;
		File src = new File("./TestData/CP Test data.xlsx");
		try {
			FileInputStream file = new FileInputStream(src);
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(sheetname);
			workbook.close();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static Map<String, Map<String, String>> getdatamap(String sheetname) {
		// if (sheet == null) {
		loadfile(sheetname);
		// }

		Map<String, Map<String, String>> supermap = new HashMap<String, Map<String, String>>();
		Map<String, String> mymap = new HashMap<String, String>();

		int rowcount = sheet.getLastRowNum();
		// int coulumncount = sheet.getRow(0).getLastCellNum();
		for (int i = 1; i < rowcount + 1; i++) {
			row = sheet.getRow(i);
			String Keycell = row.getCell(0).getStringCellValue();
			int coulumncount = row.getLastCellNum();
			for (int j = 1; j < coulumncount; j++) {
				String value = row.getCell(j).getStringCellValue();
				mymap.put(Keycell, value);
			}
			supermap.put("Masterdata", mymap);
		}
		return supermap;
	}

	public static String getvalue(String key, String sheetname) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> data = getdatamap(sheetname).get("Masterdata");
		String mydata = data.get(key);
		return mydata;
	}

}
