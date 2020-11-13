package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {
public static WebDriver driver;
public static WebDriver startBrowser(WebDriver driver)throws Throwable
{
	if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
	{
		Reporter.log("Executing on Chrome browser",true);
	System.setProperty("webdriver.chrome.driver", "E:\\mrngOjt\\ERP_StockAccounting\\CommonDrivers\\chromedriver.exe");	
	driver= new ChromeDriver();
	}
	else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
	{
		Reporter.log("Executing on Firefox browser",true);
System.setProperty("webdriver.gecko.driver", "E:\\mrngOjt\\ERP_StockAccounting\\CommonDrivers\\geckodriver.exe");
driver= new FirefoxDriver();
	}
	else
	{
		Reporter.log("Browser value Not matching",true);
	}
	return driver;
	}
//method for launch url
public static void openApplication(WebDriver driver)throws Throwable
{
	System.out.println("Executing openApplication method");
	driver.get(PropertyFileUtil.getValueForKey("Url"));
	driver.manage().window().maximize();
}
//method for wait for element
public static void waitForElement(WebDriver driver,String locatortype,String locatorvalue,String wait)
{
	System.out.println("Executing waitForElement method");
WebDriverWait mywait= new WebDriverWait(driver, Integer.parseInt(wait));
if(locatortype.equalsIgnoreCase("name"))
{
mywait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(locatorvalue)));
}
else if(locatortype.equalsIgnoreCase("id"))
{
mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
}
else if(locatortype.equalsIgnoreCase("xpath"))
{
mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
}
else
{
	System.out.println("wait for element unable to execute");
}
}
//method for typeAction
public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata)
{
	System.out.println("Executing typeAction method");
if(locatortype.equalsIgnoreCase("xpath"))
{
	driver.findElement(By.xpath(locatorvalue)).clear();
	driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
}
else if(locatortype.equalsIgnoreCase("id"))
{
	driver.findElement(By.id(locatorvalue)).clear();
	driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
}
else if(locatortype.equalsIgnoreCase("name"))
{
	driver.findElement(By.name(locatorvalue)).clear();
	driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
}
else
{
	System.out.println("Type action unable Execute");
}
}
//method for clickaction
public static void clickAction(WebDriver driver,String locatortype,String locatorvalue)
{
	System.out.println("Executing clickAction method");
if(locatortype.equalsIgnoreCase("xpath"))
{
	driver.findElement(By.xpath(locatorvalue)).click();
}
else if(locatortype.equalsIgnoreCase("id"))
{
	driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
}
else if(locatortype.equalsIgnoreCase("name"))
{
	driver.findElement(By.name(locatorvalue)).click();
}
else
{
	System.out.println("Unable to Execute clickAction Method");
}
}
public static void closeBrowser(WebDriver driver)
{
	driver.close();
}
//method for capture data into notepad
public static void captureData(WebDriver driver,String locatortype,String locatorvalue)
throws Throwable{
	String exp_snumber="";
	if(locatortype.equalsIgnoreCase("id"))
	{
	 exp_snumber=driver.findElement(By.id(locatorvalue)).getAttribute("value");
	}
	else if(locatortype.equalsIgnoreCase("xpath"))
	{
	exp_snumber=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
	}
	FileWriter fw= new FileWriter("E:\\mrngOjt\\ERP_StockAccounting\\CaptureData\\suppler.txt");
	BufferedWriter bw= new BufferedWriter(fw);
	bw.write(exp_snumber);
	bw.flush();
	bw.close();
}
//method for supplier table validation
public static void stableValidation(WebDriver driver,String testdata)throws Throwable
{
	FileReader fr= new FileReader("E:\\mrngOjt\\ERP_StockAccounting\\CaptureData\\suppler.txt");
	BufferedReader br= new BufferedReader(fr);
	String snumber=br.readLine();
	////convert testdata into integer
	int column=Integer.parseInt(testdata);
	//if search textbox already exist then enter expdata into search textbox
if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox"))).isDisplayed())
driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchpanel"))).click();
Thread.sleep(5000);
driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox"))).clear();
driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox"))).sendKeys(snumber);
driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchbtn"))).click();
Thread.sleep(5000);
WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("stable")));
List<WebElement> rows=table.findElements(By.tagName("tr"));
for(int i=1;i<rows.size();i++)
{
String act_snumber=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
Assert.assertEquals(act_snumber, snumber,"Supplier Number Not Matching");
Reporter.log(act_snumber+"   "+snumber,true);
break;
}
}
//method for mouseclick
public static void stockCategories(WebDriver driver)throws Throwable
{
	Actions ac= new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("//body/div[2]/div[2]/div[1]/div[1]/ul[1]/li[2]/a[1]"))).perform();
	ac.moveToElement(driver.findElement(By.xpath("//body/div[2]/div[2]/div[1]/div[1]/ul[1]/li[2]/ul[1]/li[1]/a[1]"))).click().perform();
}
public static void sttableValidation(WebDriver driver,String testdata)throws Throwable
{
if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox1"))).isDisplayed())
	//if search panel is not displayed click on search panel
driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchpanel1"))).click();
driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox1"))).clear();
Thread.sleep(5000);
driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox1"))).sendKeys(testdata);
driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchbtn1"))).click();
Thread.sleep(5000);
WebElement table1=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("stable1")));
List<WebElement> rows=table1.findElements(By.tagName("tr"));
for(int i=1;i<rows.size();i++)
{
String tabaledata=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
Thread.sleep(5000);
Assert.assertEquals(tabaledata, testdata,"Category Name is Not Matching");
Reporter.log(tabaledata+"    "+testdata,true);
break;
}
}
//method date generate
	public static String generateDate()
	{
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
		return sdf.format(d);
	}
	public void verifylogin()
	{
		
	}
}







