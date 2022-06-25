package md.jamaddar.clearcapitalexercise;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IkeaWebdriverTest {
	WebDriver driver;
	@Test
	public void invalidCuponErrorCode() {
		setWebdriverImplicitWaitMazSizeGetPageHandleCookie("https://www.ikea.com/us/en");
		// search "sofa" type product and select 1st product from list
	    searchItemWithText("sofa");
	    addToCartWithPosition(1);
	    waitForToastTextToInvisible();
	    
	    // search "table" type product and select 3rd product from list
	    scrollToTop();
	    this.driver.findElement(By.id("clear-input")).click();
	    searchItemWithText("table");
	    addToCartWithPosition(3);
	    waitForToastTextToInvisible();
	
	    // shopping cart page and validate selected product's size
	    scrollToTop();
	    this.driver.findElement(By.cssSelector(".js-shopping-cart-icon")).click();
	    List<WebElement> selectedProduct = this.driver.findElements(By.cssSelector("div[itemscope]"));
	    Assert.assertEquals(selectedProduct.size(), 2, "Selected product size did not match");
	    
	    // type invalid coupon code and validate coupon error message
	    this.driver.findElement(By.className("cart-ingka-accordion-item-header__title")).click();
	    this.driver.findElement(By.id("discountCode")).sendKeys("a1s2d3f4g5h6j7k");
	    this.driver.findElement(By.cssSelector("button[type='submit']")).click();
	    String actualMessage = this.driver.findElement(By.cssSelector(".cart-ingka-form-field__message")).getText();
	    Assert.assertTrue(actualMessage.contains("invalid coupon code"), "Actual error message does not have expected text.");
	    
	    this.driver.quit();
	}

	public void scrollToTop() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("scroll(250, 0)");
	}
	
	public void addToCartWithPosition(int position) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver)
				.withTimeout(Duration.ofSeconds(20))
				.pollingEvery(Duration.ofSeconds(5))
			    .ignoring(NoSuchElementException.class)
			    .ignoring(StaleElementReferenceException.class);
		
		List<WebElement> addToCardButtons = wait.until(new Function<WebDriver, List<WebElement>>() {
		    public List<WebElement> apply(WebDriver driver) {
		    return driver.findElements(By.cssSelector(".button__add-to-cart"));
		}});
		addToCardButtons.get(position - 1).click();
	}
	
	public void waitForSearchResults(){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search-results"))); 
	}
	
	public void waitForToastTextToInvisible(){
		WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".hnf-toast__text")));
	}
	
	public void searchItemWithText(String text) {
		WebElement searchBox = this.driver.findElement(By.cssSelector("input[name='q']"));
		searchBox.clear();
	    searchBox.sendKeys(text);
	    this.driver.findElement(By.id("search-box__searchbutton")).click();
	    waitForSearchResults();
	}
	
	public void setWebdriverImplicitWaitMazSizeGetPageHandleCookie(String url) {
		String path = System.getProperty("user.dir");
		String os_name = System.getProperty("os.name");
		setPropertyBasedOnOperatingSystems(os_name, path);
		 
		this.driver = new ChromeDriver();
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    this.driver.manage().window().maximize();
	    this.driver.get(url);
	    this.driver.findElement(By.cssSelector("div #onetrust-accept-btn-handler")).click();
	}
	
	public void setPropertyBasedOnOperatingSystems(String os_name, String path) {
		if (os_name.startsWith("Windows")) {
			System.setProperty("webdriver.chrome.driver", path + "\\resources\\chromedriver.exe");
		} else {
			System.setProperty("webdriver.chrome.driver", path + "/resources/chromedriver");
		}
	}
}
