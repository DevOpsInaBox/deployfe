package pageObjects;

import io.appium.java_client.ios.IOSDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utils.Utilities;

public class HomeScreenPageObject extends BasePages {
	
	By dummyPopUPWindow=By.xpath("//span[text()='dummy']");
	
	By searchTextBox = By.xpath("//input[contains(@id,'sb-searchBox')]");
	
	By homesScreenLoadChkFinanceImage = By.xpath("//img[@alt='FINANCE']");
	
	String text = "FINANCE";
	

	public HomeScreenPageObject(IOSDriver<WebElement> driver) {
		super(driver);
	}
	
	public void dummyPopUpWindowClick(){
		driver.findElement(dummyPopUPWindow).click();
	}
	
	
	public void homesScreenLoadChkFinanceImageVerify() throws Exception
	{
		if(Utilities.waitForElement(driver, homesScreenLoadChkFinanceImage)){
			System.out.println("Yahoo finnance page loaded sucessfully");
		}		
	}
	
	public void homesScreenLoadChkFinanceTextVerify() throws Exception
	{
		if(Utilities.waitForText(driver, text)){
			System.out.println("Finnance text loaded & appeared sucessfully");
		}
		
	}
	
	public void searchTextBoxSetData(String searchText) throws Exception
	{	Utilities.waitForElement(driver, searchTextBox);
		driver.findElement(searchTextBox).sendKeys(searchText);
		Utilities.enterAndSelectText(driver, searchText);
	}
	
}
