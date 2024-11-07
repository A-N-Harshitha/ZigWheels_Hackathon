package code;
 
import java.text.NumberFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
 
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import UtilityFiles.CaptureScreenshot;
import UtilityFiles.outputExcel;
public class Bike
{

	public WebDriver driver;
	Actions action;
	WebDriverWait myWait;
	
    public String filePath = null;
	CaptureScreenshot cs =new CaptureScreenshot();
	
	ArrayList<String> nameList;
	ArrayList<String> dateList;
	ArrayList<String> priceList;
	ArrayList<String> upcomingBikes;


	//Constructor to initialize WebElements
	public Bike(WebDriver driver) 
	{
		    this.driver = driver;
		    PageFactory.initElements(driver, this);
	}

	@FindBy(xpath= "//a[@title='New Bikes']")
	WebElement newBikesElement;
	
	@FindBy(xpath= "//li[normalize-space()='Upcoming']")
	WebElement upcomingBikesElement;

	@FindBy(xpath= "//a[@title='All Upcoming Bikes']")
	WebElement allBikesElement;
	
	@FindBy(xpath= "//select[@id='makeId']")
	WebElement dropDownEle;
	
	@FindBy(xpath="//span[@class='zw-cmn-loadMore']")
	WebElement viewMoreElement;

	@FindBy(xpath="//ul[@id='modelList']")
	WebElement resultBikesElement;
	
	public void newBikes()
	{
		newBikesElement.click();
		filePath =System.getProperty("user.dir")+ "/Screenshots/Bikes_SS/NewBikes.png";
		cs.captureTestScreenshot(driver, filePath);
	}
	
	public void upcomingBikes() throws InterruptedException
	{
		//upcomingBikesElement.click();
		action = new Actions(driver);
		Thread.sleep(2000);
		action.moveToElement(upcomingBikesElement).click().perform();
		filePath =System.getProperty("user.dir")+ "/Screenshots/Bikes_SS/UpcomingBikes.png";
		cs.captureTestScreenshot(driver, filePath);
	}
	
	public void allBikes() throws InterruptedException
	{
		//allBikesElement.click();
		action = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].scrollIntoView(true);",allBikesElement );
		action.moveToElement(allBikesElement).click().perform();
		
		filePath =System.getProperty("user.dir")+ "/Screenshots/Bikes_SS/AllBikes.png";
		cs.captureTestScreenshot(driver, filePath);
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//	    js.executeScript("arguments[0].scrollIntoView(true);",allBikesElement );
////		
////		
////		JavascriptExecutor js = (JavascriptExecutor) driver;
////		js.executeScript("window.scrollTo(0, arguments[0].getBoundingClientRect().top + window.scrollY - 10);", allBikesElement);
//		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//		 
//		 wait.until(ExpectedConditions.elementToBeClickable(allBikesElement));
//		 
//		 Thread.sleep(3000);
//		 
//		 allBikesElement.click();
	}
	public void selectHonda()
	{
		myWait = new WebDriverWait(driver,Duration.ofSeconds(5));
		myWait.until(ExpectedConditions.visibilityOf(dropDownEle));
		Select mySelect = new Select(dropDownEle);
		mySelect.selectByVisibleText("Honda");
		
		filePath =System.getProperty("user.dir")+ "/Screenshots/Bikes_SS/Honda.png";
		cs.captureTestScreenshot(driver, filePath);
	}
	public void viewMore()
	{
		action = new Actions(driver);
		action.moveToElement(viewMoreElement).click().build().perform();
	}
	public String resultBikes()
	{
		filePath =System.getProperty("user.dir")+ "/Screenshots/Bikes_SS/resultBikes.png";
		cs.captureTestScreenshot(driver, filePath);
		return resultBikesElement.getText();
		
	}
	public void bikesUnderFourLakhs() throws Exception
	{
		ArrayList<String> bikeModelsElements = new ArrayList<String>();
		String bikesData =  resultBikes();
		Collections.addAll(bikeModelsElements, bikesData.split("\n"));
		// Initialize ArrayLists to store names, launch dates and prices of bikes
		nameList = new ArrayList<String>();
		dateList = new ArrayList<String>();
		priceList = new ArrayList<String>();
		String[] arr = null;
		// Iterate through the list of bike models and extract the required information
		for (int i = 0; i < bikeModelsElements.size(); i++) 
		{
			String s = bikeModelsElements.get(i);
			if (s.contains("Honda")) 
			{
				nameList.add(s);
			}
			if (s.contains("Rs. ")) 
			{
				arr = s.split(" ");
				if(arr.length<3) 
				{
					String replacedValue="";
					if(arr[1].contains(",")) 
					{
						replacedValue = arr[1].replace(",","");
					}
					double price = Double.parseDouble(replacedValue);
					price = price/100000;
					arr[1] = ""+price+"";
				}
				priceList.add(arr[1]);
			}
			if (s.contains("Expected Launch : ") || s.contains("Expected Launch Date :")) {
				dateList.add(s);
			}
		}
		// Wait for page elements to load
		try 
		{
			Thread.sleep(3000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		// Initialize an ArrayList to store upcoming bikes with price less than 4 Lakhs
		upcomingBikes = new ArrayList<String>();
//		System.out.println(nameList.size());
//		System.out.println(priceList.size());
//		System.out.println(dateList.size());
		if(nameList.size()>0) 
		{
			for (int i = 0; i < nameList.size(); i++) 
			{
				String temp = nameList.get(i);
				// Convert bike price to a double value
				NumberFormat format = NumberFormat.getInstance(Locale.FRANCE); // parse numbers in French-style format
				Number number = format.parse(priceList.get(i));
				double price = number.doubleValue();
				// Combine bike name, price and launch date to a single string
				String info = temp + "  " + priceList.get(i) + " Lakh  " + dateList.get(i);
				// Check if bike name is present in the string and price is less than 4 Lakhs
				if (info.contains(temp)) 
				{
					if (price < 4.0) 
						//Double.compare(price, 4d)
					{
						upcomingBikes.add(info);
	    			}

				}
		    }
		}
		// Print the list of upcoming bikes to the console
		System.out.println("Upcoming Honda Bikes Below 4 Lakhs are as follows:");
		for (int i = 0; i < upcomingBikes.size(); i++) 
		{
			System.out.println(upcomingBikes.get(i));
		}
	}

	public void bikesExcel() throws Throwable
	{
		   outputExcel.getBikeExcel(upcomingBikes);
	}
}

