package com.qa.api.lms.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	
	public static Workbook book;
	public static Sheet sheet;
	
	public static String TESTDATA_FILE_PATH = "./src/main/java/com/qa/api/lms/testdata/LMSData.xlsx";


	
	public static Object[][] getTestData(String sheetName) {

		FileInputStream file = null;

		try {
			file = new FileInputStream(TESTDATA_FILE_PATH);
			book = WorkbookFactory.create(file);
			sheet = book.getSheet(sheetName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// for loop:
		Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				if((k==0) && (sheetName .equals("LMSData")) ||(k==0) &&  (sheetName .equals("LMSGetAPIData")) || (k==0) &&  (sheetName .equals("LMSGetAPIUnavailableData")) ) {
					
						double cellValue =	sheet.getRow(i + 1).getCell(k).getNumericCellValue();
						int cellValueAfterCasting = (int) cellValue;
						data[i][k] = cellValueAfterCasting;
					
				} else if((k==3) && (sheetName .equals("LMSData"))) {
					data[i][k] = sheet.getRow(i + 1).getCell(k).getBooleanCellValue();
				}
				else {
					data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
					}
			}
		}

		return data;

	}



}
