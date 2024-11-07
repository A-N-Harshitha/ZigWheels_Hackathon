package code;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import UtilityFiles.CaptureScreenshot;
import UtilityFiles.outputExcel;
public class signInPage
{
	WebDriver driver;
    JavascriptExecutor js;
	CaptureScreenshot cs = new CaptureScreenshot();
	WebDriverWait myWait;

	public String filePath = null;
	String error;
 

	//Constructor to initialize WebElements
	public signInPage(WebDriver driver)
	{
		this.driver = driver;
        PageFactory.initElements(driver, this);      // Ensure WebDriver is set before initializing elements
		js = (JavascriptExecutor)driver;
	}
 
	//img[@alt='ZW_DIWALI_LOGO'])[1]
		//	https://stimg.cardekho.com/pwa/img/ZW-Black-Diwali-Logo-2024.svg
	
	//https://images.zigcdn.com/images/revamp/zigwheels-logo-black.svg
	@FindBy(xpath="//img[@src='https://images.zigcdn.com/images/revamp/zigwheels-logo-black.svg']")
	WebElement homePageElement;
	
	@FindBy(xpath="//div[@id='des_lIcon']")
	WebElement signUpElement;
	
	@FindBy(xpath="//div[@class='lgn-sc c-p txt-l pl-30 pr-30 googleSignIn']")
	WebElement signInElement;
	
	@FindBy(xpath="//input[@type='email']")
	WebElement emailElement;

	@FindBy(xpath="//span[normalize-space()='Next']")
	WebElement nextElement;
	@FindBy(xpath="(//div[@class='Ekjuhf Jj6Lae'])[1]")
	WebElement errorMsgElement;
 
	public void homePage()
	{
	    js.executeScript("window.scrollBy(0,-2000);");
		homePageElement.click();
 
	}
	public void signUp() throws InterruptedException
	{
		Thread.sleep(2000);
		signUpElement.click();
		filePath =System.getProperty("user.dir")+ "/Screenshots/GoogleSignIn_SS/SignUp.png";
		cs.captureTestScreenshot(driver, filePath);
	}
	public void SignIn() throws InterruptedException
	{
		
		try {
			Thread.sleep(3000);
			signInElement.click();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		filePath =System.getProperty("user.dir")+ "/Screenshots/GoogleSignIn_SS/SignIn.png";
		cs.captureTestScreenshot(driver, filePath);
	}
	public void email(String emailId) throws InterruptedException
	{
		String parentId = driver.getWindowHandle();
		Set<String> windowIds = driver.getWindowHandles();
		List<String> listIds = new ArrayList<>(windowIds);
		for(String id:listIds)
		{
			if(!id.equals(parentId))
			{
				driver.switchTo().window(id);
			}
		}
		myWait = new WebDriverWait(driver,Duration.ofSeconds(20));
//		Thread.sleep(3000);
		emailElement.sendKeys(emailId);
		
		Thread.sleep(3000);
		filePath =System.getProperty("user.dir")+ "/Screenshots/GoogleSignIn_SS/email.png";
		cs.captureTestScreenshot(driver, filePath);
	}
	public void next() throws InterruptedException
	{
		nextElement.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public String errorMessage() throws InterruptedException
	{
		Thread.sleep(2000);
		error = errorMsgElement.getText();
//		System.out.println("Error message displayed:"+error);
		filePath =System.getProperty("user.dir")+ "/Screenshots/GoogleSignIn_SS/ErrorMessage.png";
		cs.captureTestScreenshot(driver, filePath);
		return error;
	}
	public void signInExcel() throws IOException, InterruptedException
	{
		outputExcel.getSignInExcel(errorMessage());
	}

 
}
