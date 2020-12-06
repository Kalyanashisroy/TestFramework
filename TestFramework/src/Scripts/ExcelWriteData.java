package Scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriteData {
	Cell targetcell=null;
	@SuppressWarnings("deprecation")
	public void writeExcel(String FilePath,int sheetNo,String dataToWrite,int rowNo,int cellNo) throws IOException {
		InputStream ExcelFileToRead=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(ExcelFileToRead);
		XSSFSheet sheet=wb.getSheetAt(sheetNo);
		targetcell=sheet.getRow(rowNo).getCell(cellNo);
		targetcell.setCellType(Cell.CELL_TYPE_STRING);
		targetcell.setCellValue(dataToWrite);
		ExcelFileToRead.close();
		FileOutputStream outpurStream=new FileOutputStream(FilePath);
		wb.write(outpurStream);
		wb.close();
	}
}
