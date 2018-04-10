package pageObjects;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePages {

	protected final IOSDriver<WebElement> driver;
	public BasePages(IOSDriver<WebElement> driver) {
		this.driver = driver;
	}
	

	
}
