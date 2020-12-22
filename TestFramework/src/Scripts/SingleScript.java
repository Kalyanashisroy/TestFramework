package Scripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SingleScript {
	String LogMeIn, ClientSearch, BrowserChoice, URL, C1ientName, Selectedclient, ClientValue;
	WebDriver Driver;
	String Value;
	String webelementValues;
	ExtentReports extent;
	ExtentTest extentTest;
	String ImagePath;
	String pathfileName;
	String URL1Handle, URL2Handle;

	String Str1=new String(java.lang.System.getProperty( "user.dir")+"//TestData// TestData.xlsx");
	ExcelReadData ReadDrv=new ExcelReadData();
	ExcelWriteData WriteDrv=new ExcelWriteData ();
	FunctionLibrary WebElementobj=new FunctionLibrary();
	static String EnvironmentData []= new String [30];
	String SystemTimeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	String extentReportFile=System.getProperty("user. dir")+"//. .//TestReports//RegressionTestSuiteExecutionSummary.html";
	boolean firefoxHeadless=true;
	Row row;
	Cell cell;
	int i=0;
	/*Browser launch Definition*/
	public WebDriver BrowserLaunch ()throws IOException, InterruptedException
	{
		BrowserChoice= ReadDrv.ChoiceOfBrowser(Str1,"BrowserChoice");
		System.out.println(BrowserChoice);
		extent = new ExtentReports (extentReportFile, false);
		/*Browser Choice Definition*/
		switch (BrowserChoice) 
		{
		case "webdriver.ie.driver":
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+ "//Config/Driver/IEDriver.exe");
			DesiredCapabilities caps=DesiredCapabilities.internetExplorer( );
			Driver=new InternetExplorerDriver(caps);
			BrowserChoice= ReadDrv.ChoiceOfBrowser (Str1, "BrowserChoice") ;
			break;
		case "webdriver.chrome.driver":
			System.setProperty( "webdriver.chrome. driver", System.getProperty ("user.dir")+ "//Config/Driver/chromedriver.exe");
			ChromeOptions CRO=new ChromeOptions();
			CRO. addArguments ("disable-extensions");
			CRO.setExperimentalOption("useAutomationExtension", false) ;
			CRO.addArguments ("disable-infobars");
			//CRO.addArguments ("enable-automation");
			//CRO. addArguments ("--headless") ;
			// CRO.addArguments ("--no-sandbox");
			CRO.addArguments ("--start-maximized") ;
			CRO. addArguments ("--disable-dev-shm-usage" );
			//CRO.addArguments ("--disable-browser-side-navigation");
			//CRO.addArguments("--disable-gpu");
			Driver=new ChromeDriver(CRO);
			BrowserChoice= ReadDrv.ChoiceOfBrowser(Str1, "BrowserChoice");
			break;
		case "webdriver.firefox.marionette":
			System.setProperty ("webdriver.firefox.marionette", System.getProperty("user.dir")+"//Config/Driver/geckodriver.exe");
			FirefoxOptions o = new FirefoxOptions();
			o.setHeadless(firefoxHeadless);
			Driver = new FirefoxDriver();
			BrowserChoice=ReadDrv.ChoiceOfBrowser(Str1,"BrowserChoice");
			break;
		case "SourceData":
			System.out.println("Please select (Y/N) into browser column under TestData.xlsx- - -> "+BrowserChoice);
			break;
		}
		return Driver;
	}
	/*Test Environment selection Definition*/
	public String ChooseURL (String FilePath, String sheetname ) throws IOException
	{
		ExcelReadData ReadDrv=new ExcelReadData ();
		URL=ReadDrv.Environment(FilePath, sheetname);
		System.out.println(URL);
		extentTest.log(LogStatus.INFO, "Select Browser "+BrowserChoice+" & Launch URL") ;
		Value=BrowserChoice;
		org.junit.Assert.assertTrue(Value.contains(BrowserChoice));
		extentTest.log(LogStatus.PASS,"Browser "+ BrowserChoice+" is launched & URL opened");
		return URL;
	}
	/*Identified scenario from excel and driving execution of individual scripts*/
	public String SelectedScenarios(String FilePath, String sheetname) throws Exception
	{
		ExcelReadData ReadDrv=new ExcelReadData ();
		String SelectedScenario[]=new String [30];
		SelectedScenario= ReadDrv.ScenarioSelection(Str1, "TestScenario");
		String ScenarioName;
		int XLRowNotoWrite=1;
		int XLColNotoWrite=3;
		try{
			for (int i = 0; i< SelectedScenario.length; i++)
			{	
				ScenarioName = SelectedScenario[i];
				if(ScenarioName !=null){
					switch(ScenarioName)
					{
					case "BrowserChoice_Launch":
						System.out.println(ScenarioName);
						extentTest=extent.startTest("BrowserChoice_Launch URL_Login","Verify User is able to launch in selected browser");
						extentTest.log(LogStatus.INFO,"Select Browser "+BrowserChoice+" & Launch URL");
						URL=ChooseURL(Str1,"EnvironmentData");
						Thread.sleep(5000);
						String Currenturl1= Driver.getCurrentUrl();
						System.out.println(Currenturl1);
						Driver.get(URL);
						Thread.sleep(5000);
						/*extentTest.log(LogStatus.INFO, "Click Login Button");
					WebElement0bj.Buttonclick(Driver, By.id("ctl0e_phCenter_btnSubmitx"),extentTest);*/
						Set<String> windows= Driver.getWindowHandles( );
						URL1Handle = Driver.getWindowHandle() ;
						((JavascriptExecutor)Driver).executeScript("window.open();");
						Set<String> proxywindow = Driver.getWindowHandles();
						proxywindow.removeAll(windows);
						URL2Handle=((String)proxywindow.toArray()[0]);
						Driver.switchTo().window(URL2Handle);
						/*Code for multiwindow*/
						URL=ChooseURL (Str1, "EnvironmentData");
						Thread.sleep (5000) ;
						String Currenturl2= Driver.getCurrentUrl();
						System.out.println(Currenturl2);
						Driver. get(URL);
						//Driver.switchTo().window(URL1lHandle);
						Thread.sleep(5000);
						//Value=LogMeIn;
						//Assert.assertTrue(Value.contains (webelementValues));
						//Thread.sleep (3000);
						//WebElementobj.EnterTextBoxValue (Driver, By.id("txtUsername"), "sdey156", extentTest);
						//WebElementObj.EnterText BoxVa lue (Driver, By.id("txtPassword") , "Pwcwelcome2 ", €
						//Thread.sleep(3000);
						//WebElementobj. ButtonClick (Driver, By.xpath("//button [@type='submit']"),extentTest);
						Thread.sleep (5000);
						if(Driver.findElements(By.xpath ("//img [@alt='Fork me on GitHub']")).size ()> 0){
							extentTest.log(LogStatus.PASS, "User is able to launch");
							//EnterClient();
							WriteDrv.writeExcel(FilePath, 2, "Pass ", XLRowNotoWrite, XLColNotoWrite);
						}
						else{
							extentTest.log(LogStatus.FAIL,"User is NOT able to login");
							WriteDrv.writeExcel(FilePath, 2, "Fail",XLRowNotoWrite, XLColNotoWrite);
						}
						XLRowNotoWrite=XLRowNotoWrite+1;
						extent.endTest(extentTest);
						extent.flush();
						break;
					case "Validate Home Page":
						System.out.println(ScenarioName);
						//IS_AddClientOption objAddClientOption=new IS_AddClientOption();
						//objAddClientOption.AddClientOption(Driver,Str1,"AddClientOption",extent,extentTest);
						//extent.endTest(extentTest);
						//extent.flush();
						Driver.switchTo().window(URL1Handle);
						if (Driver.findElements(By.xpath("//img[@alt='Fork me on GitHub']")).size()>0){
							if (Driver.findElements(By.xpath("//input[@type='text']")).size()> 0) {
								if (Driver.findElements(By.xpath("//input[@type='password']")).size()>0){
									if(Driver.findElements(By.xpath("//button[@type='button']")).size()>0){
										WriteDrv.writeExcel(FilePath, 2, "Pass ", XLRowNotoWrite,XLColNotoWrite);
										ImagePath=WebElementobj.ImageCapture(Driver);
										pathfileName=FilenameUtils.getName(ImagePath);
										pathfileName="Screenshots/"+pathfileName;
										extentTest.log(LogStatus.INFO,"Step Snapshot: "+ extentTest.addScreenCapture(pathfileName));
									}
								}
							}	
						}else {
							extentTest.log(LogStatus.FAIL, "User is NOT able to login");
							WriteDrv.writeExcel(FilePath, 2, "Fail", XLRowNotoWrite,XLColNotoWrite);
						}
						XLRowNotoWrite=XLRowNotoWrite+1;
						extent.endTest(extentTest);
						extent.flush();
						break;
					case "Validate Home Page using proxy": 
						System. out.println(ScenarioName);
						// IS_AddC1ientOption objAddclientOption=new IS_AddClientOption();
						//objAddClientOption.AddClientOption (Driver, Str1, "AddclientOption",extent,extentTest);
						//	extent. endTest (extentTest);
						//extent.flush() ;
						Driver.switchTo().window(URL2Handle);
						if(Driver.findElements (By.xpath("//img [@alt='Fork me on GitHub ']")).size()> 0){
							if(Driver.findElements (By.xpath("//input [@type='text']")).size()> 0){
								if(Driver.findElements(By.xpath("//input[@type='password']")).size()>0) {
									if(Driver.findElements(By.xpath("//button[text()='SIGN UP']")).size()>0){
										WriteDrv.writeExcel(FilePath, 2, "Pass",XLRowNotoWrite, XLColNotoWrite);
										ImagePath=WebElementobj.ImageCapture(Driver);
										pathfileName=FilenameUtils.getName(ImagePath);
										pathfileName="ScreenShots/"+pathfileName;
										extentTest.log(LogStatus.INFO,"step Snapshot :" +extentTest.addScreenCapture(pathfileName));
									}
								}
							}
						}else{
							extentTest.log(LogStatus.FAIL,"User is NOT able to login") ;
							WriteDrv.writeExcel(FilePath, 2, "Fail",XLRowNotoWrite, XLColNotoWrite);
						}
						XLRowNotoWrite=XLRowNotoWrite+1 ;
						extent.endTest(extentTest) ;
						extent.flush();
						break;
					case "Compare Local with Proxy":
						System.out.println(ScenarioName);
						//IS_AddClientOption objAddClientOption=new IS_AddClientOption();
						//objAddClientOption.AddClientOption(Driver,Str1,"AddClientOption",extent,extentTest);
						//extent.endTest(extentTest);
						//extent.flush();
						Driver.switchTo().window(URL1Handle);
						int obj1=Driver.findElements(By.xpath("//input[@type='text']")).size();
						Driver.switchTo().window(URL2Handle);
						int obj2=Driver.findElements(By.xpath("//input[@type='text']")).size();

						if(obj1 == obj2){
							WriteDrv.writeExcel(FilePath,2,"Pass", XLRowNotoWrite, XLColNotoWrite);
							extentTest.log(LogStatus.INFO,"Comparison matched");
						}else {
							extentTest.log(LogStatus.FAIL,"User is NOT able to login");
							WriteDrv.writeExcel(FilePath,2,"Fai1",XLRowNotoWrite,XLColNotoWrite);
						}
						XLRowNotoWrite=XLRowNotoWrite+1;
						extent.endTest(extentTest);
						extent.flush();
						break;
					case "Sign Up button":
						WebElementobj.ButtonClick(Driver, By.xpath("//button[text()='SIGN UP']"),extentTest);
						if(Driver.findElements(By.xpath("//h2[text()='Welcome to sign up")).size()> 0){
							WriteDrv.writeExcel(FilePath,2,"Pass",XLRowNotoWrite, XLColNotoWrite);
							extentTest.log(LogStatus.PASS, "Clicked on Sign Up Button ");
						}else{
							extentTest.log(LogStatus.FAIL, "User is NOT able to sign up");
							WriteDrv.writeExcel(FilePath, 2, "Fail",XLRowNotoWrite, XLColNotoWrite);
						}
						extent.endTest (extentTest);
						extent.flush();
						break;
					default:
						//Driver.manage ().window().
						Driver.quit();
						break;
					}
				}else {
					InputStream ExcelFileToRead=new FileInputStream(FilePath);
					XSSFWorkbook wb =new XSSFWorkbook(ExcelFileToRead);
					XSSFSheet sheet=wb.getSheet("EnvironmentData");
					Iterator<Row> rows=sheet.rowIterator();
					while(rows.hasNext())
					{
						row=rows.next ();
						Iterator<Cell> cells = row.cellIterator();
						while(cells.hasNext())
						{
							cell=cells.next();
							EnvironmentData [i]=cell.getStringCellValue();
							if(EnvironmentData[i].contentEquals("N"))
							{
								WriteDrv.writeExcel(FilePath,1,"Y",cell.getRowIndex(),cell.getColumnIndex());
							}
						}
					}
					wb.close();
					Driver.quit();
					break;

				}
				extent.flush();
				//Driver.quit();
			}

		}catch(Exception e) {
			e.printStackTrace();
			extent.endTest(extentTest);
			extent.flush();
			Driver.quit ();
		}
		return extentReportFile;
	}
}
