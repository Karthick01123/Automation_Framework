package Locators1;

import java.time.Duration;
//import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Locators1Demo {

	// METHOD TO TAKE SCREENSHOT
	public static void takeScreenshot(WebDriver driver, String fileName) {

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		File dest = new File("screenshots/" + fileName + ".png");

		try {

			dest.getParentFile().mkdirs();

			Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

			System.out.println("Screenshot saved: " + dest.getAbsolutePath());

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {

		// LAUNCH BROWSER
		WebDriver driver = new ChromeDriver();
		driver.get("https://app.papirfly.com/mars/external.enter?p_com_id=14232");
		driver.manage().window().maximize();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		
		// LOGIN USING USERNAME AND PASSWORD
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("p_user_name")))
				.sendKeys("kaaylabs+kelly@papirfly.com");

		driver.findElement(By.name("p_password")).sendKeys("TFFbTFgpJJz9wELaR6aD");

		driver.findElement(By.id("LOGIN-panel--login")).click();

		Thread.sleep(5000);

		takeScreenshot(driver, "After_Login");

		// ACCEPT COOKIES
		try {
			WebElement cookieHost = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("cookie-banner")));

			SearchContext cookieShadow = cookieHost.getShadowRoot();

			WebElement acceptBtn = wait.until(d -> cookieShadow.findElement(By.cssSelector("#accept-all-btn")));

			try {
				acceptBtn.click();
			} catch (Exception e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", acceptBtn);
			}
		} catch (Exception e) {
		}
		
		
		// SKIP TOUR IF PRESENT
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Skip tour']")))
					.click();
		} catch (Exception e) {
		}


		// SHADOW HOST
		WebElement toolbarHost = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("web-toolbar[placeholder='true']")));

		SearchContext shadow = toolbarHost.getShadowRoot();

		// CLICK PRODUCT MENU
		WebElement productMenu = wait.until(d -> shadow.findElement(By.cssSelector("#product-menu")));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", productMenu);

		Thread.sleep(3000);

		// CLICK PLACE
		WebElement place = wait.until(d -> shadow.findElement(By.cssSelector("button[data-product='Place']")));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", place);

		System.out.println("PLACE CLICKED");

		// OPEN KELLY EDUCATION

		WebElement education = shadow.findElement(By.cssSelector("button[data-item-id*='Kelly Education']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", education);

		Thread.sleep(3000);

		// OPEN EDUCATION ASSETS

		List<WebElement> labels = shadow.findElements(By.cssSelector(

				"span.c-navigation__label"));

		for (WebElement label : labels) {

			String text = label.getText().trim();

			System.out.println(text);

			if (text.equalsIgnoreCase("Education Assets")) {

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", label);

				break;
			}
		}

		Thread.sleep(3000);
		
		//SKIP IF ANY POPUP APPEARS

		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Skip']"))).click();
		} catch (Exception e) {
		}

		// CLICK ON SEARCH BOX AND ENTER TEMPLATE NAME
		try {

			WebElement searchBox = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector("input[placeholder='Type your search…']")));

			searchBox.clear();

			searchBox.sendKeys("KE Military Veterans Flier");

			searchBox.sendKeys(Keys.ENTER);

			System.out.println("Template searched successfully");

		} catch (Exception e) {

			System.out.println("Failed to search template");

			e.printStackTrace();

			takeScreenshot(driver, "Template_Search_Failed");
		}

		// CLICK THE TEMPLATE CHECKBOX

		try {

			// Locate template by title
			WebElement templateCard = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='KE Military Veterans Flier']")));

			// Hover over template
			new Actions(driver).moveToElement(templateCard).pause(Duration.ofSeconds(2)).perform();

			takeScreenshot(driver, "After_Hover");

			// Locate checkbox after hover
			WebElement checkbox = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//input[contains(@data-testid,'place-dam-asset-card-checkbox')]")));

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);

			System.out.println("Template selected successfully");

		} catch (Exception e) {

			System.out.println("Failed to select template");

			e.printStackTrace();

			takeScreenshot(driver, "Template_Select_Failed");

			Assert.fail("Template selection failed: " + e.getMessage());
		}
		
		// CLICK ON PLUS SYMBOL
		try {

		    WebElement plusButton = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("//button[@data-testid='place-dam-archive-sidebar-button-create-new-chili-template']")));

		    ((JavascriptExecutor) driver)
		            .executeScript("arguments[0].click();", plusButton);

		    System.out.println("Plus symbol clicked successfully");

		} catch (Exception e) {

		    System.out.println("Failed to click plus symbol");

		    e.printStackTrace();

		    takeScreenshot(driver, "Plus_Symbol_Click_Failed");

		    Assert.fail("Unable to click plus symbol: " + e.getMessage());
		}
		
		//CLICK ON CONTINUE IN IFRAME
		
		try {

		    // Wait until iframe appears
		    wait.until(driver1 ->
		            driver.findElements(By.tagName("iframe")).size() > 0);

		    List<WebElement> iframes =
		            driver.findElements(By.tagName("iframe"));

		    System.out.println("Iframe count: " + iframes.size());

		    driver.switchTo().frame(iframes.get(0));

		    WebElement continueBtn = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("//button[normalize-space()='Continue']")));

		    ((JavascriptExecutor) driver)
		            .executeScript("arguments[0].click();", continueBtn);

		    System.out.println("Continue clicked successfully");

		    driver.switchTo().defaultContent();

		} catch (Exception e) {

		    System.out.println("Failed to click continue");

		    e.printStackTrace();

		    takeScreenshot(driver, "Continue_Click_Failed");

		    Assert.fail("Unable to click continue: " + e.getMessage());
		}
		
		//CLICK ON DOWNLOAD BUTTON
		
		try {

		    WebElement downloadBtn = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.cssSelector(
		                            "button.c-button.c-button--tertiary.c-button--expandable.c-button--expanded")));

		    ((JavascriptExecutor) driver)
		            .executeScript("arguments[0].click();", downloadBtn);

		    System.out.println("Download button clicked successfully");

		} catch (Exception e) {

		    System.out.println("Failed to click download button");

		    e.printStackTrace();

		    takeScreenshot(driver, "Download_Button_Click_Failed");

		    Assert.fail("Unable to click download button: "
		            + e.getMessage());
		}
		
		//CLICK ON PDF OR PNG OPTION
		
		try {

		    WebElement downloadOption = wait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("//span[contains(text(),'.pdf') or contains(text(),'.png')]")));

		    ((JavascriptExecutor) driver)
		            .executeScript("arguments[0].click();", downloadOption);

		    System.out.println("Download option selected successfully");

		} catch (Exception e) {

		    System.out.println("Failed to select download option");

		    e.printStackTrace();

		    takeScreenshot(driver, "Download_Option_Failed");

		    Assert.fail("Unable to select download option: "
		            + e.getMessage());
		}
	}
}