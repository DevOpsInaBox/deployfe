package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.perfectomobile.selenium.util.EclipseConnector;

import constants.ApplicationConstants;
import constants.PerfectoConstants;

public class Utilities implements PerfectoConstants,ApplicationConstants{
	//AppiumDriver<WebElement> driver;
	public static String REPORT_LIB = Perfecto_Report;
	
	public static IOSDriver<WebElement> getAppiumDriver(String OSVersion,String deviceModel,String platform,String persona ) throws IOException {

		IOSDriver<WebElement> driver = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("user", PERFECTO_USRNAME);
		capabilities.setCapability("password", PERFECTO_PWD);
		capabilities.setCapability("platformName", platform);
		capabilities.setCapability("platformVersion", OSVersion);
		capabilities.setCapability("model", deviceModel);
		
		capabilities.setCapability("automationName", "appium");
		setExecutionIdCapability(capabilities, PERFECTO_URL);
		
		// One liner to set persona
		capabilities.setCapability("windTunnelPersona", persona);
		
		//Creating Android Driver
		try {
			driver = new IOSDriver<WebElement>(
					new URL("https://"+PERFECTO_URL + "/nexperience/perfectomobile/wd/hub"),capabilities);
		} catch (Exception e) {
			String ErrToRep = e.getMessage().substring(0, e.getMessage().indexOf("Command duration") - 1);
			System.out.println(ErrToRep);
			return (null);
		}
		return driver;
	}
	
	
	private static void setExecutionIdCapability(DesiredCapabilities capabilities, String host) throws IOException  {
        EclipseConnector connector = new EclipseConnector();
        String eclipseHost = connector.getHost();
        if ((eclipseHost == null) || (eclipseHost.equalsIgnoreCase(host))) {
            String executionId = connector.getExecutionId();
            capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
        }
    }

	
	public static boolean waitForElement(IOSDriver<WebElement> driver,By objPath) throws Exception{
		try{
			WebDriverWait wait = new WebDriverWait(driver, 1000);
    		wait.until(ExpectedConditions.visibilityOfElementLocated(objPath));
    		return true;
		}
		catch(Exception e){
			Reporter.log("Error occured on waiting for the element to appear  - "+ e);
			throw new Exception ("Error occured on waiting for the element to appear  - "+ e);
            
		}
	}
	
	public static boolean waitForText(IOSDriver<WebElement> driver,String text) throws Exception{
		try{
			Map<String, Object> params = new HashMap<>();
			params.put("content", text);
			params.put("timeout", "40");
			driver.executeScript("mobile:checkpoint:text", params);
			return true;
		}
		catch(Exception e){
			Reporter.log("Error occured on waiting for the text to appear  - "+ e);
			throw new Exception ("Error occured on waiting for the text to appear  - "+ e);
            
		}
	}
	
	public static void enterAndSelectText(IOSDriver<WebElement> driver,String serachText){
		Map<String, Object> params = new HashMap<>();
		params.put("label", serachText);
		params.put("timeout", "20");
		params.put("index", "2");
		driver.executeScript("mobile:button-text:click", params);
	}
	
	/*public static void usePerfectoScrollSwipeUp(IOSDriver<WebElement> driver,String searchItem){
		 Map<String, Object> params = new HashMap<String, Object>();
        params.put("content", searchItem);
        params.put("scrolling", "scroll");
        //params.put("maxscroll", "10");
        params.put("next", "SWIPE_UP");
        driver.executeScript("mobile: scroll", params);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("direction", "up");
		params.put("element", searchItem);
		driver.executeScript("mobile: scroll", params);
	}*/
	
	public static void usePerfectoScrollSwipeUp(IOSDriver<WebElement> driver,String searchItem){
		 Map<String, Object> params = new HashMap<>();
       params.put("content", searchItem);
       params.put("scrolling", "scroll");
       //params.put("maxscroll", "10");
       params.put("next", "SWIPE_UP");
       driver.executeScript("mobile:text:find", params);
	}
	
	public static void usePerfectoScrollSwipeDown(IOSDriver<WebElement> driver,String searchItem){
		 Map<String, Object> params = new HashMap<>();
      params.put("content", searchItem);
      params.put("scrolling", "scroll");
      //params.put("maxscroll", "10");
      params.put("next", "SWIPE_DOWN");
      driver.executeScript("mobile:text:find", params);
	}
	
	

	public static void downloadReport(AndroidDriver<WebElement> driver, String type, String fileName)
			throws IOException {
		try {
			String command = "mobile:report:download";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "html");
			String report = (String) driver.executeScript(command, params);
			File reportFile = new File(getReprtName(fileName, true));
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(reportFile));
			byte[] reportBytes = OutputType.BYTES.convertFromBase64Png(report);
			output.write(reportBytes);
			output.close();
		} catch (Exception ex) {
			System.out.println("Got exception " + ex);
		}
	}

	public static String getReprtName(String repID, boolean withPath) {
		if (withPath) {
			return REPORT_LIB + "/rep_" + repID + ".html";
		} else {
			return "/rep_" + repID + ".html";
		}
	}
	
}
