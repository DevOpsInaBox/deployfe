package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import io.appium.java_client.ios.IOSDriver;
import utils.Utilities;


public class homePage extends BasePages {
	
		By Verify_HomeScreen = By.xpath("//*[contains(text(),'Market Action')]");   //menu_home_search
		By QuoteSearchBox  = By.xpath("//input[@class='search_text_bx']");
		By SearchBtn = By.xpath("//input[@class='ser_btn']");
		By MC_homeBtn = By.xpath("//a[@class='mclogo']");
		public homePage(IOSDriver<WebElement> driver) {
			super(driver);
		}
		
		
		public void homesScreenchk() throws Exception
		{
			if(Utilities.waitForElement(driver, Verify_HomeScreen)){
				System.out.println("Money Control home page loaded sucessfully");
				Reporter.log("Money Control home page loaded sucessfully");
			}		
		}
		
		
		
		
		public void QuoteBoxSetData(String searchText) throws Exception
		{	//Utilities.waitForElement(driver, QuoteSearchBox);
			driver.findElement(MC_homeBtn).click();
			driver.findElement(QuoteSearchBox).sendKeys(searchText);
			Reporter.log("Company Name Entered is : "+searchText);
			Thread.sleep(5000);
			//driver.findElement(SearchBtn).click();
			Utilities.enterAndSelectText(driver, searchText);
		}
		
		
}
