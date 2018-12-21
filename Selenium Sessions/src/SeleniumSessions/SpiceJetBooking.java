package SeleniumSessions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SpiceJetBooking {

	

	public static void main(String[] args) throws InterruptedException {
		
		
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\arvinlml\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		/*
		 * cdriver.manage().window().maximize();
		 * cdriver.get("https://www.spicejet.com/");
		 * cdriver.findElement(By.xpath("//input[@id='ctl00_mainContent_rbtnl_Trip_1']")
		 * ).click();
		 * cdriver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).click
		 * (); Thread.sleep(2000);
		 * //cdriver.findElement(By.linkText(" Surat (STV)")).click();
		 * cdriver.findElement(By.partialLinkText("Surat")).click(); Thread.sleep(2000);
		 * cdriver.findElement(By.id("ctl00$mainContent$ddl_destinationStation1")).click
		 * (); Thread.sleep(2000);
		 * cdriver.findElement(By.partialLinkText("Kochi")).click();
		 */

		driver.manage().window().maximize();
		driver.get("https://www.spicejet.com/");
		
		PageObject pageobject = new PageObject();
		driver.findElement(pageobject.rbtriptype).click();
		driver.findElement(pageobject.byOrigin).click();
		
		driver.findElement(By.linkText("Jabalpur (JLR)")).click();
		driver.findElement(By.id("ctl00_mainContent_ddl_destinationStation1_CTXT")).click();
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='India'])[2]/following::a[18]")).click();
		driver.findElement(By.linkText("18")).click();
//driver.close();

	}

}
