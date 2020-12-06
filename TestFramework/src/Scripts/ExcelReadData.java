package Scripts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadData {
	static String DriverPath,URL,XLLoginID,XLClientName;
	static String BrowserData[]=new String[20];
	static String EnvironmentData[]=new String[30];
	static String ScenarioName[]=new String[30];
	static String ScenarioFlag[]=new String[30];
	static String XLAddClientOptionData[]=new String[50];
	static String XLEditClientOptionData[]=new String[100];
	static String XLSendMailData[]=new String[100];
	ExcelWriteData WriteDrv=new ExcelWriteData();
	int i=0;
	Row row;
	Cell cell;

	public String ChoiceBrowser(String FilePath,String sheetname) throws IOException {
		InputStream ExcelFileToRead=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(ExcelFileToRead);
		XSSFSheet sheet=wb.getSheet(sheetname);
		Iterator<Row> rows=sheet.rowIterator();
		while(rows.hasNext()) 
		{
			row=rows.next();
			Iterator<Cell> cells=row.cellIterator();
			while(cells.hasNext())
			{
				cell=cells.next();
				BrowserData[i]=cell.getStringCellValue();
				if(BrowserData[i].contentEquals("Y"))
				{
					cell=cells.next();
					BrowserData[i]=cell.getStringCellValue();
					DriverPath=BrowserData[i];
				}
			}
			wb.close();
		}
		if(DriverPath==null) {
			return sheetname;
		}
		else {
			return DriverPath;
		}
	}
	public String Environment(String FilePath,String sheetname) throws IOException {
		InputStream ExcelFileToRead=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(ExcelFileToRead);
		XSSFSheet sheet=wb.getSheet(sheetname);
		Iterator<Row> rows=sheet.rowIterator();
		while(rows.hasNext()) 
		{
			row=rows.next();
			Iterator<Cell> cells=row.cellIterator();
			while(cells.hasNext()) {
				cell=cells.next();
				EnvironmentData[i]=cell.getStringCellValue();
				if(EnvironmentData[i].contentEquals("Y")) {
					WriteDrv.writeExcel(FilePath, 1, "N", cell.getRowIndex(), cell.getColumnIndex());
					cell=cells.next();
					EnvironmentData[i]=cell.getStringCellValue();
					URL=EnvironmentData[i];
					cell=cells.next();
					XLLoginID=cell.getStringCellValue();
					cell=cells.next();
					XLClientName=cell.getStringCellValue();
					return URL;
				}
			}
			wb.close();
		}
		if(URL==null) {
			return sheetname;
		}
		else {
			return URL;
		}
	}
	public String LoginIDSelect() {
		return XLLoginID;
	}
	public String ClientSelect() {
		return XLClientName;
	}
	@SuppressWarnings({ "deprecation", "static-access" })
	public String[] ScenarioSelection(String FilePath,String sheetname) throws IOException {
		InputStream ExcelFileToRead=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(ExcelFileToRead);
		XSSFSheet sheet=wb.getSheet(sheetname);
		Iterator<Row> rows=sheet.rowIterator();
		while(rows.hasNext()) {
			row=rows.next();
			Iterator<Cell> cells=row.cellIterator();
			while(cells.hasNext()) {
				cell=cells.next();
				cell.setCellType(cell.CELL_TYPE_STRING);
				ScenarioFlag[i]=cell.getStringCellValue();
				if(ScenarioFlag[i].contentEquals("Y")) {
					cell=cells.next();
					cell.setCellType(cell.CELL_TYPE_STRING);
					ScenarioName[i]=cell.getStringCellValue();
					i=i+1;
				}
			}
		}
		wb.close();
		return ScenarioName;

	}
	@SuppressWarnings({ "deprecation", "static-access" })
	public String[] XLAddClientOption(String FilePath,String sheetname) throws IOException {
		InputStream ExcelFileToRead=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(ExcelFileToRead);
		XSSFSheet sheet=wb.getSheet(sheetname);
		Iterator<Row> rows=sheet.rowIterator();
		while(rows.hasNext()) {
			row=rows.next();
			Iterator<Cell> cells=row.cellIterator();
			while(cells.hasNext()) {
				cell=cells.next();
				cell.setCellType(cell.CELL_TYPE_STRING);
				i=i+1;
				XLAddClientOptionData[i]=cell.getStringCellValue();
			}
		}
		wb.close();
		return XLAddClientOptionData;
	}
	@SuppressWarnings({ "deprecation", "static-access" })
	public String[] XLEditClientOption(String FilePath,String sheetname) throws IOException {
		InputStream ExcelFileToRead=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(ExcelFileToRead);
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
				cell.setCellType(cell.CELL_TYPE_STRING);
				if(cell.getStringCellValue().isEmpty()!=true) {
					XLEditClientOptionData[i]=cell.getStringCellValue();
					i=i+1;
				}
			}
		}
		wb.close();
		return XLEditClientOptionData;
	}
	@SuppressWarnings("deprecation")
	public String[] XLSendMailData(String FilePath,String sheetname) throws IOException {
		InputStream ExcelFileToRead=new FileInputStream(FilePath);
		XSSFWorkbook wb=new XSSFWorkbook(ExcelFileToRead);
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
					XLSendMailData[i]=cell.getStringCellValue();
					i=i+1;
				}
			}
		}
		wb.close();
		return XLSendMailData;
	}
}
