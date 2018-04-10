package pageObjects;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class PopUps extends BasePages {
	
	By nextBtn = By.xpath("//*[contains(@resource-id,'id/enable_button_text')]");
	By mobTabTextLink = By.xpath("//*[contains(@text,'Mobile, Tablets & Accessories')]");
	

	public PopUps(AndroidDriver<WebElement> driver) {
		super(driver);
	}
	
	private void nextBtnClick()
	{	
		try{
			driver.findElement(nextBtn).click();
			Reporter.log("***Next Btn pop up appeared in the page");
		}catch(Exception e){
			Reporter.log("***Next Btn pop up did not appear in the page");
		}
	}	
	
	private void mobTabTextLinkClick()
	{
		try{
			driver.findElement(mobTabTextLink).click();
			Reporter.log("***Mobile,Tablet Text Btn appeared in the page");
		}catch(Exception e){
			Reporter.log("***Mobile,Tablet Text btn did not appear in the page");
		}
	}	
	
	public HomeScreenPageObject checkForPopUp(){
		mobTabTextLinkClick();
		nextBtnClick();
		return new HomeScreenPageObject(driver);
		
	}
}
