package BuildVerificationTest;
 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import code.Bike;
import code.UsedCars;
import code.driverClass;
import code.signInPage;
 

public class RegressionTest
{
	WebDriver driver = null;
	JavascriptExecutor js;
	Properties prop = new Properties();
	driverClass dc = new driverClass();
	Bike bk;
	UsedCars uc;
	signInPage sp;
 
	
  // Setting up the driver before each test class
	@BeforeClass
	public void setup() throws FileNotFoundException
	{
		driver = dc.driverSetup();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		bk = new Bike(driver);
		uc = new UsedCars(driver);
		sp = new signInPage(driver); 
	}
	
	@Test(priority=0)
	public void upComingBikes() throws Throwable
	{
		bk.newBikes(); // Clicks on the New Bikes menu
		bk.upcomingBikes(); // Clicks on the Upcoming Bikes submenu
		bk.allBikes();
		bk.selectHonda();
		bk.viewMore();
		bk.resultBikes();
		bk.bikesUnderFourLakhs();
 
		System.out.println("--------Bikes test Completed----------");
	}
	
    @Test(priority=1)
    public void testUsedCarsSearch() throws Throwable {
    	
        uc.usedCarsMenu();
        uc.selectRequiredCity();
        uc.printPopularModels();
        
        System.out.println("--------Cars Test Completed----------");
    }
	
	@Test(priority=2)
	public void signInTests() throws InterruptedException, IOException
	{
		sp.homePage();
		sp.signUp();
		sp.SignIn();
		sp.email("harshi@h,mail.com");
		sp.next();
		
		System.out.println("---------SignIn test Completed-------, Error message is: "+sp.errorMessage());
 
	}
	
	@AfterClass
	public void closeDriver() throws IOException
	{
		dc.quitBrowser();
	}
 
}