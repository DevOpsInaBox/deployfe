package test;


import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.HomeScreenPageObject;
import pageObjects.StockInfoDisplayPageObject;
import utils.PerfectoLabUtils;
import utils.Utilities;
import constants.ApplicationConstants;
import constants.PerfectoConstants;

public class StockExchangeTest implements ApplicationConstants,PerfectoConstants{

	private IOSDriver<WebElement> driver;
	HomeScreenPageObject homeScreenPageObject;
	StockInfoDisplayPageObject stockInfoDisplayPageObject;
	public String firstCompanyToBeSearched,secondCompanyToBeSearched;
	
	
	//Using Data  Provider
	 @DataProvider(name = "SelectDevice")
	  public static Object[][] credentials() {
		 return new Object[][] {
				new Object[] {"TCS","WIPRO"},
		    	new Object[] {"INFY","IBM"},
		    	new Object[] {"CTS","GOOGL"}};
		 }
	  
	 
	 @Parameters({ "mobileOS","OSVersion","deviceModel","persona"})
	 @BeforeMethod
		public void beforeTest(String mobileOS,String OSVersion,String deviceModel,String persona) throws IOException {
			//Creating Driver,installing Appp and using Georgia Persona
			driver = Utilities.getAppiumDriver(OSVersion,deviceModel, mobileOS,persona);
			homeScreenPageObject = new HomeScreenPageObject(driver);
			stockInfoDisplayPageObject =new StockInfoDisplayPageObject(driver);
			driver.get("https://in.finance.yahoo.com/");
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			
		
		} 

	@Test(dataProvider = "SelectDevice")
	public void selectSuitableDevices(String firstCompany,String secondCompany) throws Exception {
		firstCompanyToBeSearched = firstCompany;
		secondCompanyToBeSearched=secondCompany;
		
		// Dummy Check for pop up		
		try {
			homeScreenPageObject.dummyPopUpWindowClick();
		} catch (Exception NoSuchElementException) {
			//Do nothing
		}	
		
		try {
			homeScreenPageObject.dummyPopUpWindowClick();
		} catch (Exception NoSuchElementException) {
			//DO nothing
		}	

		//Verify if the page has loaded or not
		homeScreenPageObject.homesScreenLoadChkFinanceImageVerify();
		
		homeScreenPageObject.homesScreenLoadChkFinanceTextVerify();

		// Enter the searched term in the search box
		homeScreenPageObject.searchTextBoxSetData(firstCompanyToBeSearched);

		stockInfoDisplayPageObject.companyStockInfoDisplayConfirmation(firstCompanyToBeSearched);
		
		String currentStockPrice = stockInfoDisplayPageObject.fetchCompanyCurrentStockPrice();
		System.out.println(currentStockPrice);
		
		stockInfoDisplayPageObject.fetchFiftyTwoWeekHighLowValuePrice(currentStockPrice);
		
		double epsForCompany1=stockInfoDisplayPageObject.getEPSStockPrice();
		
		homeScreenPageObject.searchTextBoxSetData(secondCompanyToBeSearched);

		stockInfoDisplayPageObject.companyStockInfoDisplayConfirmation(secondCompanyToBeSearched);
		
		double epsForCompany2=stockInfoDisplayPageObject.getEPSStockPrice();
		
		if(epsForCompany1 > epsForCompany2){
			System.out.println("EPS for company1 ("+firstCompanyToBeSearched+") is "+epsForCompany1+"  which is higher than company2 ("+secondCompanyToBeSearched+") which is "+epsForCompany2+". " );
			Reporter.log("EPS for company1 ("+firstCompanyToBeSearched+") is "+epsForCompany1+"  which is higher than company2 ("+secondCompanyToBeSearched+") which is "+epsForCompany2+". " );
		}else{
			System.out.println("EPS for company2 ("+secondCompanyToBeSearched+") is "+epsForCompany2+"  which is higher than company1 ("+firstCompanyToBeSearched+") which is "+epsForCompany1+". " );
			Reporter.log("EPS for company2 ("+secondCompanyToBeSearched+") is "+epsForCompany2+"  which is higher than company1 ("+firstCompanyToBeSearched+") which is "+epsForCompany1+". " );
		}			
		
		
	}

	

	
	@AfterMethod
	public void afterTest() throws IOException {
		driver.close();
		Calendar c = new GregorianCalendar();
		c.setTime(new Date());
		String file_name = "PerfectoReport_for_"+firstCompanyToBeSearched+"-"+secondCompanyToBeSearched+"-"+ c.get(Calendar.DATE)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.YEAR)+"_"+c.get(Calendar.HOUR_OF_DAY)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.SECOND);
		PerfectoLabUtils.downloadReport(driver, "pdf", Perfecto_Report+file_name+".pdf");
		String reportURL = (String)(driver.getCapabilities().getCapability("windTunnelReportUrl"));
		System.out.println("WindTunnel Report for device - " +firstCompanyToBeSearched + " - " +reportURL);
		driver.quit();	
	}
	
	  
}
