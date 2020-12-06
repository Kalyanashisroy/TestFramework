package Scripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestCaseReadData {
	static String XLTestCaseArray[]=new String[100];
	int i=0;
	Row row;
	Cell cell;
	@SuppressWarnings("deprecation")
	public String[] XLTestCaseReadData(String FilePath,String sheetname) throws IOException {
		InputStream ExcelTCFileToRead=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(ExcelTCFileToRead);
		XSSFSheet sheet=wb.getSheet(sheetname);
		Iterator<Row> rows=sheet.rowIterator();
		row=rows.next();
		while(rows.hasNext()) {
			row=rows.next();
			Iterator<Cell> cells=row.cellIterator();
			cell=null;
			cell=row.getCell(0);
			while(cells.hasNext()) {
				cell=cells.next();
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(cell.getStringCellValue().isEmpty()!=true) {
					XLTestCaseArray[i]=cell.getStringCellValue();
					i=i+1;
				}
			}
		}
		wb.close();
		return XLTestCaseArray;
		
	}
}
