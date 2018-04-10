package TC_Driver;

import constants.*;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import pages.*;
import utils.*;

import org.testng.Reporter;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;


public class stockApp_Flow implements ApplicationConstants,PerfectoConstants {
	
	private IOSDriver<WebElement> driver;
	homePage home;
	stockPage stockPa;
	public String firstQuote,secondQuote;
	
	
	//Using Data  Provider
	 @DataProvider(name = "SelectDevice")
	  public static Object[][] credentials() {
		 return new Object[][] {
				new Object[] {"Yes bank","Ashok Leyland"},
		    	new Object[] {"Punj Lloyd","GVK Power"}};
		 }

	@Parameters({ "mobileOS","OSVersion","deviceModel","persona"}) 
	@BeforeMethod
	public void beforeMethod(String mobileOS,String OSVersion,String deviceModel,String persona)throws IOException {
	
		driver = Utilities.getAppiumDriver(OSVersion,deviceModel, mobileOS,persona);
		home = new homePage(driver);
		stockPa = new stockPage(driver);
		/*listings  = new listings_directory();
		stock = new stockPage();*/
		driver.get(APP_URL);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
	}
	
	@Test(dataProvider = "SelectDevice")
	public void RetriveStockPrice(String firstCompany,String secondCompany) throws Exception {
		firstQuote = firstCompany;
		secondQuote=secondCompany;
		
		//Verify if the page has loaded or not
		home.homesScreenchk();
		home.QuoteBoxSetData(firstQuote);
		stockPa.pageLoadchk(firstQuote);
		String CompanyLastStockPrice = stockPa.CompanyLastStockPrice();
		System.out.println(CompanyLastStockPrice);
		String Fifytwo_weekLowPrice = stockPa.Company52weekLowPrice();
		String Fifytwo_weekHighPrice = stockPa.Company52weekHighPrice();
		
		stockPa.StockPercentageCompare(CompanyLastStockPrice, Fifytwo_weekHighPrice, Fifytwo_weekLowPrice);
		double epsForCompanyone=stockPa.CompanyEPS_value();
		home.QuoteBoxSetData(secondQuote);
		stockPa.pageLoadchk(secondQuote);
		double epsForCompanytwo=stockPa.CompanyEPS_value();
		
		if(epsForCompanyone > epsForCompanytwo){
			System.out.println("EPS for company1 ("+firstQuote+") is "+epsForCompanyone+"  which is higher than company2 ("+secondQuote+") which is "+epsForCompanytwo+". " );
			Reporter.log("EPS for company1 ("+firstQuote+") is "+epsForCompanyone+"  which is higher than company2 ("+secondQuote+") which is "+epsForCompanytwo+". " );
		}else{
			System.out.println("EPS for company2 ("+secondQuote+") is "+epsForCompanytwo+"  which is higher than company1 ("+firstQuote+") which is "+epsForCompanytwo+". " );
			Reporter.log("EPS for company2 ("+secondQuote+") is "+epsForCompanytwo+"  which is higher than company1 ("+firstQuote+") which is "+epsForCompanytwo+". " );
		}		
		
		
		
  }
 
  @AfterMethod
	public void afterTest() throws IOException {
		driver.close();
		Calendar c = new GregorianCalendar();
		c.setTime(new Date());
		String file_name = "PerfectoReport_for_"+firstQuote+"-"+secondQuote+"-"+ c.get(Calendar.DATE)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.YEAR)+"_"+c.get(Calendar.HOUR_OF_DAY)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.SECOND);
		PerfectoLabUtils.downloadReport(driver, "pdf", Perfecto_Report+file_name+".pdf");
		String reportURL = (String)(driver.getCapabilities().getCapability("windTunnelReportUrl"));
		System.out.println("WindTunnel Report for device - " +firstQuote + " - " +reportURL);
		driver.quit();	
	}

}
