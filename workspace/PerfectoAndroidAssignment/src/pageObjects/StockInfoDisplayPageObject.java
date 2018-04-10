package pageObjects;

import java.util.List;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import utils.Utilities;

public class StockInfoDisplayPageObject  extends BasePages {
	
	

	By companyCurrentStock = By.xpath("(//span[@class='title']/strong)[1]");
	
	By weekHighLowValue = By.xpath("(//div[text()='52wk Range:']/../../td/div[@class='small uic'])[2]");
	
	By getEPS = By.xpath("//div[contains(text(),'EPS')]/../../td[@class='value']/div");
	
	
	public StockInfoDisplayPageObject(IOSDriver<WebElement> driver) {
		super(driver);
	}
	
	
	public void companyStockInfoDisplayConfirmation(String companyName) throws Exception
	{	
		System.out.println("//div[@id='stocks_bse']//a/strong[contains(text(),'"+companyName+"')]");
		List<WebElement>allElement = driver.findElements(By.xpath("//span[contains(text(),'"+companyName+"')]"));
		int totalElements = allElement.size();
		System.out.println("Total Size is : "+totalElements);
		if(totalElements!=0){
			System.out.println("Do nothing");			
		}else{
			throw new Exception ("Company name did not appear in the page.Page could not be loaded with COmpany Stock details");
		}	
	}	
	
	public String fetchCompanyCurrentStockPrice() throws Exception
	{
		String companyCurrentStockValue=null;
		if(Utilities.waitForElement(driver, companyCurrentStock)){
			companyCurrentStockValue=driver.findElement(companyCurrentStock).getText();
			System.out.println("Company's current stock price is  :"+companyCurrentStockValue);
		}
		return companyCurrentStockValue;
		
	}	
	
	
	public void fetchFiftyTwoWeekHighLowValuePrice(String currentStockP) throws Exception
	{
		String currentStockPrice=currentStockP;
		String fiftyTwoWeekLowPrice = null;
		String fiftyTwoWeekHighPrice= null;
		if(Utilities.waitForElement(driver, weekHighLowValue)){
			String companyFiftyTwoWeekHighLow=driver.findElement(weekHighLowValue).getText();
			String splittedPrice[] = companyFiftyTwoWeekHighLow.split("-");
			fiftyTwoWeekLowPrice=(splittedPrice[0].trim());
			fiftyTwoWeekHighPrice=(splittedPrice[1].trim());
			System.out.println("Company's 52 week low price is  :"+fiftyTwoWeekLowPrice+ "  and 52 week high price is : "+fiftyTwoWeekHighPrice);
			Reporter.log("Company's 52 week low price is  :"+fiftyTwoWeekLowPrice+ "  and 52 week high price is : "+fiftyTwoWeekHighPrice);
		}
		double fiftyTwoWeekLowPercentage = showFiftyTwoWeekLowPercentage(currentStockPrice,fiftyTwoWeekLowPrice);
		double fiftyTwoWeekHighPercentage = showFiftyTwoWeekHighPercentage(currentStockPrice,fiftyTwoWeekHighPrice);
		System.out.println("Today’s price of <"+currentStockPrice+"> is "+fiftyTwoWeekHighPercentage+"% lower than the 52 week high and "+fiftyTwoWeekLowPercentage+"% higher than the 52 week low.");
		Reporter.log("Today’s price of <"+currentStockPrice+"> is "+fiftyTwoWeekHighPercentage+"% lower than the 52 week high and "+fiftyTwoWeekLowPercentage+"% higher than the 52 week low.");
		
		
	}	
	
	public double showFiftyTwoWeekLowPercentage(String currentStockPrice,String fiftyTwoWeekLowPrice){
		double latestStockPrice=Double.parseDouble(currentStockPrice);
		double fiftyTwoWeekLow=Double.parseDouble(fiftyTwoWeekLowPrice);
		
		double fiftyTwoWeekLowPercentage=((latestStockPrice-fiftyTwoWeekLow)*100)/fiftyTwoWeekLow;
		System.out.println(fiftyTwoWeekLowPercentage);
		return fiftyTwoWeekLowPercentage;
		
	}
	
	public double showFiftyTwoWeekHighPercentage(String currentStockPrice,String fiftyTwoWeekHighPrice){
		double latestStockPrice=Double.parseDouble(currentStockPrice);
		double fiftyTwoWeekHigh=Double.parseDouble(fiftyTwoWeekHighPrice);
		
		double fiftyTwoWeekHighPercentage=((fiftyTwoWeekHigh-latestStockPrice)*100)/fiftyTwoWeekHigh;
		System.out.println(fiftyTwoWeekHighPercentage);
		return fiftyTwoWeekHighPercentage;
		
	}
	
	public double getEPSStockPrice() throws Exception
	{
		String companyCurrentEPS=null;
		double epsForCompany=0;
		if(Utilities.waitForElement(driver, getEPS)){
			companyCurrentEPS=driver.findElement(getEPS).getText();
			epsForCompany=Double.parseDouble(companyCurrentEPS);
			System.out.println("Company's current EPS price is  :"+epsForCompany);
		}
		return epsForCompany;
		
	}	
	
}
