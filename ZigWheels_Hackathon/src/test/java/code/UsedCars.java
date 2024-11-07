package code;
 
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import UtilityFiles.CaptureScreenshot;
import UtilityFiles.outputExcel;
 
public class UsedCars
{
	public WebDriver driver;
	JavascriptExecutor js;
    public Actions action;
    
    public String filePath = null;
	CaptureScreenshot cs =new CaptureScreenshot();

    
    public UsedCars(WebDriver driver) 
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }
    
	@FindBy(xpath="//img[@src='https://images.zigcdn.com/images/revamp/zigwheels-logo-black.svg']")
	WebElement homePageElement;

    @FindBy(xpath ="//a[@title='Used Cars']")
    WebElement usedCar_Element;

    By text_cityName_Element = By.xpath("//input[@placeholder ='City Name']");
   
    //ul[@id='ui-id-3']/li/a
   // @FindBy(xpath = "a[@id='ui-id-336']")
    //a[@id='ui-id-336']
    @FindBy(xpath= "//ul//li[@class='ui-menu-item']/a[text()='Chennai']")
    WebElement text_Chennai_Element;
    
    @FindBy(xpath = "//ul[@class='zw-sr-secLev usedCarMakeModelList popularModels ml-20 mt-10']/li")
	public static List<WebElement> popular_car_models;


	public void scroll() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0,-2000);");
	}
    
    public static WebElement expWait(WebDriver driver, By locator, int time) {
        WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(time));
        return myWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitListExplicit(int sec, List<WebElement> elements ,WebDriver driver) {
	    WebDriverWait waits = new WebDriverWait(driver, Duration.ofSeconds(sec));
	    waits.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

    public void usedCarsMenu() throws Throwable
    {
    	
	    js.executeScript("window.scrollBy(0,-2000);");
    	
    	js.executeScript("arguments[0].click();",homePageElement);
		homePageElement.click();
 
        action = new Actions(driver);
        action.moveToElement(usedCar_Element).click().perform();
        
		filePath =System.getProperty("user.dir")+ "/Screenshots/Cars_SS/UsedCars.png";
		cs.captureTestScreenshot(driver, filePath);
		
    }

    public void selectRequiredCity() throws InterruptedException {
        action = new Actions(driver);
        Thread.sleep(2000);
        WebElement inputEle = expWait(driver, text_cityName_Element, 20); // Increase wait time
        inputEle.clear();
        inputEle.sendKeys("chennai");
        Thread.sleep(1000);

        text_Chennai_Element.click();
		Thread.sleep(1000);
		
		filePath =System.getProperty("user.dir")+ "/Screenshots/Cars_SS/Chennai.png";
		cs.captureTestScreenshot(driver, filePath);
		
		inputEle.sendKeys(Keys.ENTER);
    }


	public void printPopularModels() 
	{
    	js = (JavascriptExecutor)driver;
	    js.executeScript("scroll(0, 500)");
		waitListExplicit(30, popular_car_models, driver);
		
		filePath =System.getProperty("user.dir")+ "/Screenshots/Cars_SS/popularModels.png";
		cs.captureTestScreenshot(driver, filePath);
		
	    // Display the list of popular models on the console
		 System.out.println("Following is the list of Popular models:");
			for (int i = 0; i < popular_car_models.size(); i++) {
				System.out.println(popular_car_models.get(i).getText());
			}
    }

	public void carsExcel() throws Throwable
	{
		   outputExcel.getUsedCar(popular_car_models);
	}

 
}
