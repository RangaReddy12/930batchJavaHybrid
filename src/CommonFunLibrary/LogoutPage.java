package CommonFunLibrary;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogoutPage {
@FindBy(id="mi_logout")
WebElement clicklogout;
public void verifyLogout()
{
	clicklogout.click();
}
}
