package CommonFunLibrary;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SupplierPage {
WebDriver driver;
WebDriverWait wait;
public SupplierPage(WebDriver driver)
{
	this.driver=driver;
}
@FindBy(linkText="Suppliers")
WebElement clicksupplier;
@FindBy(xpath="//body/div[2]/div[3]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/a[1]/span[1]")
WebElement clickAddIcon;
@FindBy(name="x_Supplier_Number")
WebElement suppliernumber;
@FindBy(name="x_Supplier_Name")
WebElement suppliername;
@FindBy(name="x_Address")
WebElement address;
@FindBy(name="x_City")
WebElement city;
@FindBy(name="x_Country")
WebElement country; 
@FindBy(name="x_Contact_Person")
WebElement cperson;
@FindBy(name="x_Phone_Number")
WebElement pnumber;
@FindBy(name="x__Email")
WebElement email;
@FindBy(name="x_Mobile_Number")
WebElement mnumber;
@FindBy(name="x_Notes")
WebElement notes;
@FindBy(name="btnAction")
WebElement clickAddbtn;
@FindBy(xpath="(//button[contains(text(),'OK!')])[1]")
WebElement clickokconfirnbtn;
@FindBy(xpath="(//button[text()='OK'])[6]")
WebElement clickAalertokbtn;
@FindBy(xpath="//body/div[2]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/button[1]/span[1]")
WebElement clicksearchpanel;
@FindBy(name="psearch")
WebElement clicksearctextbox;
@FindBy(name="btnsubmit")
WebElement clicksearcbtn;
@FindBy(xpath="//table[@id='tbl_a_supplierslist']")
WebElement stable;
public void verifyaddsupplier(String sname,String address,String city,String country,
		String cperson,String pnumber,String email,String mnumber,String notes)throws Throwable
{
wait= new WebDriverWait(driver, 30);
wait.until(ExpectedConditions.elementToBeClickable(clicksupplier));
this.clicksupplier.click();
wait.until(ExpectedConditions.visibilityOf(clickAddIcon));
this.clickAddIcon.click();
wait.until(ExpectedConditions.visibilityOf(suppliernumber));
String expectedsnumber=this.suppliernumber.getAttribute("value");
this.suppliername.sendKeys(sname);
this.address.sendKeys(address);
this.city.sendKeys(city);
this.country.sendKeys(country);
this.cperson.sendKeys(cperson);
this.pnumber.sendKeys(pnumber);
this.email.sendKeys(email);
this.mnumber.sendKeys(mnumber);
this.notes.sendKeys(notes);
this.clickAddbtn.sendKeys(Keys.ENTER);
wait.until(ExpectedConditions.visibilityOf(clickokconfirnbtn));
//Thread.sleep(4000);
this.clickokconfirnbtn.click();
wait.until(ExpectedConditions.elementToBeClickable(clickAalertokbtn));
this.clickAalertokbtn.click();
if(!clicksearctextbox.isDisplayed())
//click on search panel
this.clicksearchpanel.click();
wait.until(ExpectedConditions.visibilityOf(clicksearctextbox));
this.clicksearctextbox.clear();
this.clicksearctextbox.sendKeys(expectedsnumber);
this.clicksearcbtn.click();
wait.until(ExpectedConditions.visibilityOf(stable));
//count no of rows in a table
List<WebElement> rows=stable.findElements(By.tagName("tr"));
System.out.println("No of rows are::"+rows.size());
String actualsnumber=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr/td[6]/div/span/span")).getText();
Assert.assertEquals(actualsnumber, expectedsnumber,"Supplier number Not Matching with table");
System.out.println(actualsnumber+"      "+expectedsnumber);

}
}
