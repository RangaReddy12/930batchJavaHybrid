package CommonFunLibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
WebDriver driver;
public LoginPage(WebDriver driver)
{
	this.driver=driver;
}
@FindBy(name="username")
WebElement username;
@FindBy(name="password")
WebElement password;
@FindBy(name="btnsubmit")
WebElement clicklogin;
@FindBy(name="btnreset")
WebElement clickreset;
public void verifyLogin(String username,String password)
{
	this.clickreset.click();
	this.username.sendKeys(username);
	this.password.sendKeys(password);
	this.clicklogin.click();
}
}
