package pageObjects;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import utils.Utilities;

public class ProductDisplayPageObject  extends BasePages {
	
	

	By productDisplayConfrmation = By.xpath("//*[contains(@resource-id,'id/image')]");
	
	By reviewShowAllBtn = By.xpath("//*[contains(@resource-id,'fragment_product_description_user_reviews_show_all')]");
	
	By sortPriceBtn = By.xpath("//*[contains(@resource-id,'fragment_product_description_other_sellers_sort_button')]");
	
	By selectSortType = By.xpath("//*[@text='Price - low to high']");
	
	By fetchThirdLowestPrice = By.xpath("(//*[contains(@resource-id,'other_sellers_list_item_best_price_value')])[3]");
	
	
	public ProductDisplayPageObject(AndroidDriver<WebElement> driver) throws Exception {
		super(driver);
		try {
			driver.findElement(productDisplayConfrmation);
		} catch (NoSuchElementException e) {
			Assert.fail("Element not found in the Product Display page");
		}
	}
	
	public ProductReviewPageObject reviewShowAllBtnClick()
	{	Utilities.usePerfectoScrollSwipeUp(driver, "USER REVIEWS");
		driver.findElement(reviewShowAllBtn).click();
		return new ProductReviewPageObject(driver);
	}	
	
	private void sortPriceBtnClick() throws Exception
	{
		if(Utilities.waitForElement(driver, sortPriceBtn)){
			driver.findElement(sortPriceBtn).click();
		}
		
	}	
	
	private void selectSortTypeClick() throws Exception
	{
		if(Utilities.waitForElement(driver, selectSortType)){
			driver.findElement(selectSortType).click();
		}
	}	
	
	private String fetchThirdLowestPriceText() throws Exception
	{
		String thirdLowestPrice=null;
		if(Utilities.waitForElement(driver, fetchThirdLowestPrice)){
			thirdLowestPrice=driver.findElement(fetchThirdLowestPrice).getText();
			System.out.println("Third Lowest Price is :"+thirdLowestPrice);
			Reporter.log("Third Lowest Price is :"+thirdLowestPrice);
		}
		return thirdLowestPrice;
		
	}	
	
	public String sortAndSelectThirdLowVal() throws Exception{
		sortPriceBtnClick();
		selectSortTypeClick();
		return fetchThirdLowestPriceText();
		
	}
	
}
