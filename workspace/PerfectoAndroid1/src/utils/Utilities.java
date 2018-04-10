package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

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
	//public static String SCREENSHOTS_LIB = SCREENSHOTS_PATH;
	
	public static AndroidDriver<WebElement> getAppiumDriver(String OSVersion,String app,String deviceModel,String platform,String persona ) throws IOException {

		AndroidDriver<WebElement> driver = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("user", PERFECTO_USRNAME);
		capabilities.setCapability("password", PERFECTO_PWD);
		capabilities.setCapability("platformName", platform);
		//capabilities.setCapability("deviceName", "1DAA7935");
		capabilities.setCapability("platformVersion", OSVersion);
		capabilities.setCapability("model", deviceModel);
		
		capabilities.setCapability("automationName", "appium");
		setExecutionIdCapability(capabilities, PERFECTO_URL);
		capabilities.setCapability("appPackage", app);
		
		//Installing Aplication
		capabilities.setCapability("app", APK_PERFECTO_REPO_PATH);
		capabilities.setCapability("autoInstrument", true);
		capabilities.setCapability("fullReset",true); 
		capabilities.setCapability("appPackage", APP_PACKAGE_NAME);
		
		// One liner to set persona
		capabilities.setCapability("windTunnelPersona", persona);
		
		//Creating Android Driver
		try {
			driver = new AndroidDriver<WebElement>(
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

	public static void startApp(String appName, AppiumDriver<WebElement> driver) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", appName);
		driver.executeScript("mobile:application:open", params);
	}

	public static void stopApp(String appName, AppiumDriver<WebElement> d) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", appName);
		d.executeScript("mobile:application:close", params);
	}

	
	public static boolean waitForElement(AppiumDriver<WebElement> driver,By objPath) throws Exception{
		try{
			WebDriverWait wait = new WebDriverWait(driver, 40);
    		wait.until(ExpectedConditions.visibilityOfElementLocated(objPath));
    		return true;
		}
		catch(Exception e){
			Reporter.log("Error occured on waiting for the element to appear  - "+ e);
			throw new Exception ("Error occured on waiting for the element to appear  - "+ e);
            
		}
	}
	
	@SuppressWarnings("unchecked")
	/*public static String getScreenShot(AppiumDriver<WebElement> driver, String name, String deviceID) {
		String screenShotName = SCREENSHOTS_LIB + name + "_" + deviceID + ".png";
		driver = (AppiumDriver<WebElement>) new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(screenShotName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenShotName;
	}*/
	
	public static void usePerfectoScrollSwipeUp(AppiumDriver<WebElement> driver,String searchItem){
		 Map<String, Object> params = new HashMap<>();
        params.put("content", searchItem);
        params.put("scrolling", "scroll");
        //params.put("maxscroll", "10");
        params.put("next", "SWIPE_UP");
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
