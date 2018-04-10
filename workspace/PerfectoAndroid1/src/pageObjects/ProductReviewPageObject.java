package pageObjects;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import utils.Utilities;

public class ProductReviewPageObject extends BasePages {
	

	By userReviewNumberText = By.xpath("//*[contains(@resource-id,'id/fragment_user_reviews_list_rating')]");
	
	By appBackBtn = By.xpath("//*[@content-desc='Navigate up']");
	
	public ProductReviewPageObject(AndroidDriver<WebElement> driver) {
		super(driver);
		try {
			driver.findElement(userReviewNumberText);
			} catch (NoSuchElementException e) {
				Assert.fail("Element not found in the Product review page");
			}
	}
	

	public String userReviewNumberTextGetText() throws Exception
	{
		String reviewRating = null;
		if(Utilities.waitForElement(driver, userReviewNumberText)){
			reviewRating=driver.findElement(userReviewNumberText).getText();
			System.out.println("Rating is :"+reviewRating);			
			Reporter.log("Rating is :"+reviewRating);
		}
		return reviewRating;
		
	}	
	
	public ProductDisplayPageObject appBackBtnClick() throws Exception
	{	
		driver.findElement(appBackBtn).click();
		return new ProductDisplayPageObject(driver);
	}	
	
	
}
