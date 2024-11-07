package ExtentReportTest;
 
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
 
import code.driverClass;
import code.Bike;
import code.UsedCars;
import code.signInPage;
import UtilityFiles.outputExcel;

 
public class ExtentAllTestCases {
 
 
	public  WebDriver driver;
	public static ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/ExtentReport/extentReportFile.html");
	public static ExtentReports extent = new ExtentReports();
 
	@BeforeClass
	public void setup() throws FileNotFoundException {
		driverClass ds = new driverClass();
 
		driver = ds.driverSetup();
		extent.attachReporter(reporter);
		reporter.config().setDocumentTitle("Automation Result");
		reporter.config().setReportName("Indentify New Bikes Automation Test");
		reporter.config().setTheme(Theme.DARK);
		ExtentTest logger = extent.createTest("Driver Test");
		logger.log(Status.INFO, "Browser launched");
		logger.log(Status.INFO, "Navigated to https://www.zigwheels.com/");
		
		outputExcel.openExcel();
	}
	@Test(priority = 0)
	public void upComingHondaBikes() throws Throwable{
		Bike up = new Bike(driver);
		up.newBikes();
		extent.attachReporter(reporter);
		ExtentTest logger = extent.createTest("Upcoming Bikes Test");
		logger.log(Status.INFO, "Mouse Hover on 'New Bikes'");
		up.upcomingBikes();
		logger.log(Status.INFO, "Upcoming Bikes is selected");
		up.allBikes();
		logger.log(Status.INFO,"All upcoming bikes are displayed");
		up.selectHonda();
		logger.log(Status.INFO, "Manufacturer name is selected as 'Honda'");
		up.viewMore();
		logger.log(Status.INFO, "View More button is clicked");
		up.resultBikes();
		logger.log(Status.INFO, "All the available bikes will be displayed");
		up.bikesUnderFourLakhs();
		logger.log(Status.INFO, "Upcoming Bikes under 4 lac is displayed on console ");
 
		logger.log(Status.PASS, "Upcoming Bikes test is passed");
		
		up.bikesExcel();
		extent.flush();
	}
	
	@Test(priority = 1)
	public void usedCars() throws Throwable {
		UsedCars cars= new UsedCars(driver);
		
		cars.usedCarsMenu();
		extent.attachReporter(reporter);
		ExtentTest logger = extent.createTest("Used Cars in Chennai Test ");
		logger.log(Status.INFO, "User cars is selected");
		cars.selectRequiredCity();
		logger.log(Status.INFO, "Available cars in chennai is displayed ");
		cars.printPopularModels();
		logger.log(Status.INFO, "Popular Models List is Displayed on console");
 
		logger.log(Status.PASS, "Used cars in chennai test is passed");
		
		cars.carsExcel();
		extent.flush();
 
	}
 
	@Test(priority = 2)
	public void signUp() throws InterruptedException, IOException {
		signInPage signin = new signInPage(driver);
		
		signin.homePage();
		extent.attachReporter(reporter);
		ExtentTest logger = extent.createTest("Google Sign In Test");
		logger.log(Status.INFO, "Home Page is opened");
		signin.signUp();
		logger.log(Status.INFO, "CLick on Sign Up");
		signin.SignIn();
		logger.log(Status.INFO, "Enter email to signIn");
		signin.email("harshi@hmail.com");
		logger.log(Status.INFO, "An invalid email id is entered into Email field");
		signin.next();
		logger.log(Status.INFO,"Next button is clicked after entering the invalid email id");
		System.out.println("Error message displayed:"+signin.errorMessage());
		logger.log(Status.INFO, "Error message is captured successfully and displayed on console");
		logger.log(Status.PASS, "Google Sign In test is passed");
 
		

		signin.signInExcel();
		extent.flush();
	}
	
	@AfterClass
	public void closeDriver() throws IOException 
	{
            driver.quit(); 
            outputExcel.closeExcel();
	}
 
}