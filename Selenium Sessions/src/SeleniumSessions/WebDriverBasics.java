package SeleniumSessions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverBasics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*	System.setProperty("webdriver.gecko.driver", "C:\\Users\\arvinlml\\Downloads\\geckodriver-v0.21.0-win64\\geckodriver.exe");
		WebDriver fdriver = new FirefoxDriver();
		fdriver.get("https://reg.ebay.com/reg/PartialReg?ru=https%3A%2F%2Fwww.ebay.com%2F"); */
		
		
	 System.setProperty("webdriver.chrome.driver","C:\\Users\\arvinlml\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver cdriver = new ChromeDriver();
		cdriver.get("https://reg.ebay.com/reg/PartialReg?ru=https%3A%2F%2Fwww.ebay.com%2F");
		String Title = cdriver.getTitle();
		System.out.println("Title of the page is " +Title);
		cdriver.findElement(By.id("firstname")).sendKeys("Saluja");
		cdriver.findElement(By.xpath("//*[@id='lastname']")).sendKeys("Aravindh");
		cdriver.findElement(By.xpath("//*[@name='email']")).sendKeys("reachsaluja@gmail.com");
		cdriver.findElement(By.cssSelector("#PASSWORD")).sendKeys("Salu0178");
		cdriver.findElement(By.id("ppaFormSbtBtn")).click();
				/*if (Title.equals("Google")) {
			System.out.println("Correct Page is launched");
		}
			else {
				System.out.println("Correct page is not launched");
			}
		 			
		//System.out.println("Successfully Launched"); */
	} 
		

}
