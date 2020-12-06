package Scripts;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.EmptyFileException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class FunctionLibrary {
	String ImagePath;
	String SystemTimeStamp;
	File TargetScreenshotName;
	String pathfileName;

	public String EnterTextBoxValue(WebDriver driver,By locator,String Value,ExtentTest extentTest) throws Exception{
		try {
			WebElement TextBox=driver.findElement(locator);
			TextBox.sendKeys(Value);
			Thread.sleep(2000);
			ImagePath=ImageCapture(driver);
			pathfileName=FilenameUtils.getName(ImagePath);
			pathfileName="ScreenShots/"+pathfileName;
			extentTest.log(LogStatus.INFO, "Step Snapshot :"+extentTest.addScreenCapture(pathfileName));

		} catch (Exception e) {
			ImagePath=ImageCapture(driver);
			pathfileName=FilenameUtils.getName(ImagePath);
			pathfileName="ScreenShots/"+pathfileName;
			extentTest.log(LogStatus.INFO, "Step Snapshot :"+extentTest.addScreenCapture(pathfileName));
			e.printStackTrace();
		}
		return Value;
	}
	public void SelectTextBoxValueRoboClass() {
		try {
			Thread.sleep(3000);
			StringSelection stringSelection=new StringSelection("");
			Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
			String result=(String)clipboard.getData(DataFlavor.stringFlavor);
			System.out.println("result:"+result);
			//Transferable contents=clipboard.getContents(null);
			clipboard.setContents(stringSelection, null);
			//Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			//String result=(String)contents.getTransferData(DataFlavor.stringFlavor);
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void ButtonClick(WebDriver driver,By locator,ExtentTest extenTest) {
		try {
			WebElement WebButton=driver.findElement(locator);
			WebButton.click();
			Thread.sleep(2000);
			ImagePath=ImageCapture(driver);
			pathfileName=FilenameUtils.getName(ImagePath);
			pathfileName="ScreenShots/"+pathfileName;
			extenTest.log(LogStatus.INFO, "Step Snapshot :"+extenTest.addScreenCapture(pathfileName));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void ClickNavigationLink(WebDriver driver,String Xpath,String ItemToClick,ExtentTest extentTest) throws IOException {
		Actions ClickAction=new Actions(driver);
		ClickAction.moveToElement(driver.findElement(By.xpath(Xpath))).build().perform();
		WebElement EntityLink=driver.findElement(By.linkText(ItemToClick));
		ImagePath=ImageCapture(driver);
		pathfileName=FilenameUtils.getName(ImagePath);
		pathfileName="ScreenShots/"+pathfileName;
		extentTest.log(LogStatus.INFO, "Step Snapshot : "+extentTest.addScreenCapture(pathfileName));
		ClickAction.moveToElement(EntityLink).click(EntityLink).build().perform();
	}
	public String SelectDropDownList(WebDriver driver,String name,String value,ExtentTest extentTest) throws IOException {
		try {
			Select SelectBox=new Select(driver.findElement(By.name(name)));
			SelectBox.selectByVisibleText(value);
			ImagePath=ImageCapture(driver);
			pathfileName=FilenameUtils.getName(ImagePath);
			pathfileName="ScreenShots/"+pathfileName;
			extentTest.log(LogStatus.INFO, "Step Snapshot :"+extentTest.addScreenCapture(pathfileName));
			return value;
		} catch (Exception e) {
			ImagePath=ImageCapture(driver);
			pathfileName=FilenameUtils.getName(ImagePath);
			pathfileName="ScreenShots/"+pathfileName;
			extentTest.log(LogStatus.INFO, "Step Snapshots :"+extentTest.addScreenCapture(pathfileName));
		}
		return value;

	}
	public void SelectRadioButton(WebDriver driver,String XPath,ExtentTest extentTest) throws IOException {
		try {
			WebElement WebRadioButton=driver.findElement(By.xpath(XPath));
			ImagePath=ImageCapture(driver);
			pathfileName=FilenameUtils.getName(ImagePath);
			pathfileName="ScreenShots/"+pathfileName;
			extentTest.log(LogStatus.INFO, "Step Snapshots :"+extentTest.addScreenCapture(pathfileName));
			WebRadioButton.click();

		} catch (Exception e) {
			ImagePath=ImageCapture(driver);
			extentTest.log(LogStatus.INFO, "Step Snapshot :"+extentTest.addScreenCapture(pathfileName));
		}
	}
	public void SelectCheckBox(WebDriver driver,By locator,ExtentTest extenTest) throws IOException {
		try {
			WebElement WebCheckBox=driver.findElement(locator);
			WebCheckBox.click();
			Thread.sleep(2000);
			ImagePath=ImageCapture(driver);
			pathfileName=FilenameUtils.getName(ImagePath);
			pathfileName="ScreenShots/"+pathfileName;
			extenTest.log(LogStatus.INFO, "Step Snapshots :" +extenTest.addScreenCapture(pathfileName));
		} catch (Exception e) {
			ImagePath=ImageCapture(driver);
			extenTest.log(LogStatus.INFO, "Step Snapshot :" +extenTest.addScreenCapture(ImagePath));
		}
	}
	public String ImageCapture(WebDriver driver) throws IOException {
		File ScrImageScreenshotFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			SystemTimeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			//TargetScreenshotName=new File(System.getProperty("user.dir")+"//..//TestReports//ScreenShots//"+SystemTimeStamp+".png");
			TargetScreenshotName=new File(System.getProperty("user.dir")+"//TestReports//ScreenShots//"+SystemTimeStamp+".png");
			org.apache.commons.io.FileUtils.copyFile(ScrImageScreenshotFile, TargetScreenshotName);
			String filePath=TargetScreenshotName.toString();
			return filePath;
		} 
		catch (EmptyFileException e) {
			SystemTimeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			TargetScreenshotName=new File(System.getProperty("user.dir")+"//TestReports//ScreenShots//"+SystemTimeStamp+".png");
			org.apache.commons.io.FileUtils.copyFile(ScrImageScreenshotFile, TargetScreenshotName);
			String filePath=TargetScreenshotName.toString();
			return filePath;
		}
	}
	public void ScrollToElement(WebDriver driver, By locator,ExtentTest extentTest) throws Exception {
		try {
			WebElement element=driver.findElement(locator);
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			ImagePath=ImageCapture(driver);
			pathfileName=FilenameUtils.getName(ImagePath);
			pathfileName="ScreenShots/"+pathfileName;
			extentTest.log(LogStatus.INFO, "Step Snapshots :"+extentTest.addScreenCapture(pathfileName));
		} catch (Exception e) {
			ImagePath=ImageCapture(driver);
			pathfileName=FilenameUtils.getName(ImagePath);
			pathfileName="ScreenShots/"+pathfileName;
			extentTest.log(LogStatus.INFO, "Step Snapshots :"+extentTest.addScreenCapture(pathfileName));
		}
	}
}
