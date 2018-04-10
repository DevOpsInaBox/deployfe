package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import io.appium.java_client.ios.IOSDriver;
import utils.Utilities;

public class stockPage extends BasePages {
	
	By companystockpath = By.xpath("//span[@id='b_last_value']");
	By Company52WeekHigh = By.id("bse_in_9");
	By Company52WeekLow = By.id("bse_in_8");
	By CompanyEPS_Value = By.xpath("//*[@id='pg_Stocks']/div[24]/div[2]/div[2]//strong");
	By Verify_StockPage = By.xpath("//*[@id='stocks_bse']/div[1]/div[1]/a/strong");
	
	public stockPage(IOSDriver<WebElement> driver) {
		super(driver);
	}
	
	
	public void pageLoadchk(String companyName) throws Exception
	{
		//By Verify_StockPage = By.xpath("//div[@id='stocks_bse']//a/strong[contains(text(),'"+companyName+"')]");
		
		Thread.sleep(5000);
		if(Utilities.waitForElement(driver,Verify_StockPage )){
			System.out.println("Stock page loaded sucessfully");
			Reporter.log("Stock page loaded sucessfully");
		}		
	}
	
	public String CompanyLastStockPrice() throws Exception
	{
		String companylastStockValue=null;
		if(Utilities.waitForElement(driver, companystockpath)){
			companylastStockValue=driver.findElement(companystockpath).getText();
			companylastStockValue = companylastStockValue.replace(",", "");
			System.out.println("Company's current stock price is  :"+companylastStockValue);
			Reporter.log("Company's last quote value is: "+companylastStockValue);	
		}
		return companylastStockValue;
		
	}	
	
	public String Company52weekHighPrice() throws Exception
	{
		String company52HighValue=null;
		//driver.scrollTo("1d");
		//Thread.sleep(5000);
		
		//driver.scrollToExact("1d");
		company52HighValue=driver.findElement(Company52WeekHigh).getText();
		company52HighValue = company52HighValue.replace(",", "");
		System.out.println("Company's 52week high stock price is  :"+company52HighValue);
		Reporter.log("Company's 52week high stock price is: "+company52HighValue);	
		
		return company52HighValue;
		
	}	
	
	public String Company52weekLowPrice() throws Exception
	{
		String company52LowValue=null;
		Utilities.usePerfectoScrollSwipeUp(driver, "52 Wk Low");
		company52LowValue=driver.findElement(Company52WeekLow).getText();
		company52LowValue = company52LowValue.replace(",", "");
		System.out.println("Company's 52week Low stock price is  :"+company52LowValue);
		Reporter.log("Company's 52week Low stock price is: "+company52LowValue);	
		
		return company52LowValue;
		
	}	
	
		
	public double CompanyEPS_value() throws Exception
	{
		String companyCurrentEPS=null;
		double epsForCompany=0;
		if(Utilities.waitForElement(driver, CompanyEPS_Value)){
			Utilities.usePerfectoScrollSwipeUp(driver, "Deliverables (%)");
			companyCurrentEPS=driver.findElement(CompanyEPS_Value).getText();
			epsForCompany=Double.parseDouble(companyCurrentEPS);
			System.out.println("Company's current EPS price is  :"+epsForCompany);
		}
		return epsForCompany;
		
	}	
	
	
	public double showFiftyTwoWeekLowPercentage(String LastStockQuote,String fiftyTwoWeekLowPrice){
		double latestStockPrice=Double.parseDouble(LastStockQuote.replace(",",""));
		double fiftyTwoWeekLow=Double.parseDouble(fiftyTwoWeekLowPrice);
		
		double fiftyTwoWeekLowPercentage=((latestStockPrice-fiftyTwoWeekLow)*100)/fiftyTwoWeekLow;
		System.out.println(fiftyTwoWeekLowPercentage);
		return fiftyTwoWeekLowPercentage;
		
	}
	
	public double showFiftyTwoWeekHighPercentage(String LastStockQuote,String fiftyTwoWeekHighPrice){
		double latestStockPrice=Double.parseDouble(LastStockQuote.replace(",",""));
		double fiftyTwoWeekHigh=Double.parseDouble(fiftyTwoWeekHighPrice);
		
		double fiftyTwoWeekHighPercentage=((fiftyTwoWeekHigh-latestStockPrice)*100)/fiftyTwoWeekHigh;
		System.out.println(fiftyTwoWeekHighPercentage);
		return fiftyTwoWeekHighPercentage;
		
	}
	
	public void StockPercentageCompare(String LastStockQuote, String fiftyTwoWeekHighPrice, String fiftyTwoWeekLowPrice) throws Exception
	{
		
		double fiftyTwoWeekLowPercentage = showFiftyTwoWeekLowPercentage(LastStockQuote,fiftyTwoWeekLowPrice);
		double fiftyTwoWeekHighPercentage = showFiftyTwoWeekHighPercentage(LastStockQuote,fiftyTwoWeekHighPrice);
		System.out.println("Today’s price of <"+LastStockQuote+"> is "+fiftyTwoWeekHighPercentage+"% lower than the 52 week high and "+fiftyTwoWeekLowPercentage+"% higher than the 52 week low.");
		Reporter.log("Today’s price of <"+LastStockQuote+"> is "+fiftyTwoWeekHighPercentage+"% lower than the 52 week high and "+fiftyTwoWeekLowPercentage+"% higher than the 52 week low.");
	
	}
	
	
	
	
	
	
	


}
