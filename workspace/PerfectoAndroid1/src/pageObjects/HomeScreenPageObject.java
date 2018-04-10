package pageObjects;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import utils.Utilities;

public class HomeScreenPageObject extends BasePages {
	
	By homesScreenLoadChkImage = By.xpath("//*[contains(@resource-id,'/fragment_picture_image')]");
	By homeSearchBtn = By.xpath("//*[contains(@resource-id,'menu')]");   //menu_home_search
	By goBtn = By.xpath("//*[contains(@resource-id,'search_go_text')]");
	By homeSearchTextBox = By.xpath("//*[contains(@resource-id,'search_text')]");
	By selectFirstDisplayedProduct = By.xpath("(//*[contains(@resource-id,'subcategory_list_page_listview_item_trending_title')])[1]");
	
	public HomeScreenPageObject(IOSDriver<WebElement> driver) {
		super(driver);
		try {
			driver.findElement(homeSearchBtn);
		} catch (NoSuchElementException e) {
			Assert.fail("Home Screen Page did not get displayed");
		}
	}
	
	private HomeScreenPageObject homeSearchBtnClick() throws Exception
	{
		if(Utilities.waitForElement(driver, homeSearchBtn)){
			driver.findElement(homeSearchBtn).click();
		}
		return this;
	}
	
	private HomeScreenPageObject goBtnClick()
	{
		driver.findElement(goBtn).click();
		return this;
	}
	
	private HomeScreenPageObject homeSearchTextBoxSetData(String searchText) throws Exception
	{
		if(Utilities.waitForElement(driver, homeSearchTextBox)){
			driver.findElement(homeSearchTextBox).sendKeys(searchText);
		}
		return this;
	}
	
	private ProductDisplayPageObject selectFirstDisplayedProductClick() throws Exception
	{
		if(Utilities.waitForElement(driver, selectFirstDisplayedProduct)){
			driver.findElement(selectFirstDisplayedProduct).click();
		}
		return new ProductDisplayPageObject(driver);
	}
	
	public ProductDisplayPageObject searchAndSelectDevice(String deviceName) throws Exception{
		homeSearchBtnClick();
		homeSearchTextBoxSetData(deviceName);
		goBtnClick();
		return this.selectFirstDisplayedProductClick();
	}
	
	
	
}
