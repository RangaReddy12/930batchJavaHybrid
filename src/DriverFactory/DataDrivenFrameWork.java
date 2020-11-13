package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.LoginPage;
import CommonFunLibrary.LogoutPage;
import CommonFunLibrary.SupplierPage;
import Utilities.ExcelFileUtil;

public class DataDrivenFrameWork {
WebDriver driver;
File screen;
ExtentReports report;
ExtentTest test;
String inputpath="E:\\mrngOjt\\ERP_StockAccounting\\TestInput\\TestData.xlsx";
String outputpath="E:\\mrngOjt\\ERP_StockAccounting\\TestOutput\\datadriven.xlsx";
@BeforeTest
public void setUp()
{
	report= new ExtentReports("./ExtentReports/Datadriven.html");
	System.setProperty("webdriver.chrome.driver", "E:\\mrngOjt\\ERP_StockAccounting\\CommonDrivers\\chromedriver.exe");
	driver = new ChromeDriver();
	driver.get("http://projects.qedgetech.com/webapp");
	driver.manage().window().maximize();
	LoginPage login=PageFactory.initElements(driver, LoginPage.class);	
	login.verifyLogin("admin", "master");
}
@Test
public void verifysupplier()throws Throwable
{
	//call suppler class
	SupplierPage addsupplier=PageFactory.initElements(driver, SupplierPage.class);	
	//create object for excel methods
ExcelFileUtil	xl= new ExcelFileUtil(inputpath);
//count no of rows in a sheet
int rc=xl.rowCount("supplier");
//count no of cells in a row
int cc=xl.cellCount("supplier");
Reporter.log("No of rows::"+rc+"   "+"No of cells in first row::"+cc,true);
for(int i=1; i<=rc; i++)
{
test=report.startTest("SupplierCreation");
test.assignAuthor("Ranga");
test.assignCategory("DataDriven");
String suppliername=xl.getCellData("supplier", i, 0);
String address=xl.getCellData("supplier", i, 1);
String city=xl.getCellData("supplier", i, 2);
String country=xl.getCellData("supplier", i, 3);
String contactperson=xl.getCellData("supplier", i, 4);
String phonenumber=xl.getCellData("supplier", i, 5);
String email=xl.getCellData("supplier", i, 6);
String mobilenumber=xl.getCellData("supplier", i, 7);
String notes=xl.getCellData("supplier", i, 8);
addsupplier.verifyaddsupplier(suppliername, address, city, country, contactperson, phonenumber, email, mobilenumber	, notes);
String expected="supplierslist";
String actual=driver.getCurrentUrl();
if(actual.contains(expected))
{
Reporter.log("Supplier added Success",true);
xl.setCellData("supplier", i, 9, "Pass", outputpath);
test.log(LogStatus.PASS, "Supplier added Success");
}
else
{
//take screen shot
screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(screen, new File("./Screens/Iteration/"+i+"supplier.png"));
Reporter.log("Supplier added Fail",true);
xl.setCellData("supplier", i, 9, "Fail", outputpath);
test.log(LogStatus.FAIL, "Supplier added Fail");
}
report.endTest(test);
report.flush();
}
}
@AfterTest
public void tearDown()
{
	LogoutPage logout=PageFactory.initElements(driver, LogoutPage.class);
	logout.verifyLogout();
	driver.close();
}
}
