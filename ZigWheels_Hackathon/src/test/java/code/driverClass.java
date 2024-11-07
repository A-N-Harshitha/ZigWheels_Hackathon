package code;

import java.io.FileInputStream;
//import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

 
public class driverClass
{
	public WebDriver driver;
	public static Properties prop;
	
	// Method to set up the WebDriver instance based on the browser specified in the configuration Properties file
	public WebDriver driverSetup()
	{
		prop=new Properties();
		try 
		{
			prop.load(new FileInputStream("C:\\Users\\2361570\\eclipse-workspace\\ZigWheels_Hackathon\\src\\test\\java\\code\\config.properties"));
		}	
		 catch (Exception e) 
		{
			e.printStackTrace();
		}
		System.out.println(prop.getProperty("browser"));
       // Checking which browser is specified in the configuration file and creating the corresponding WebDriver instance
		if(prop.getProperty("browser").equalsIgnoreCase("edge")) 
		{
			EdgeOptions options = new EdgeOptions();
			options.addArguments("disable-notifications");
			driver = new EdgeDriver();
		}
		else if(prop.getProperty("browser").equalsIgnoreCase("chrome"))
		{
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-notifications");
			driver = new ChromeDriver(options);
		}

		// Maximizing the window
		driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		// Navigating to the URL specified in the static variable
		driver.get(prop.getProperty("url"));
		return driver;
	}
	// Method to quit the browser
	public void quitBrowser()
	{
		driver.quit();
	}
 
}
 
