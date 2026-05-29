package Locators;

import java.time.Duration;
import java.util.ArrayList;
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

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
public class LocatorsDemo {
	
	//METHOD TO TAKE SCREENSHOT
	public static void takeScreenshot(WebDriver driver, String fileName) {

	    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

	    File dest = new File("screenshots/" + fileName + ".png");

	    try {

	        dest.getParentFile().mkdirs();

	        Files.copy(src.toPath(), dest.toPath(),
	                StandardCopyOption.REPLACE_EXISTING);

	        System.out.println("Screenshot saved: " + dest.getAbsolutePath());

	    } catch (IOException e) {

	        e.printStackTrace();
	    }
	}
	
    public static void main(String[] args) throws InterruptedException {
    	
    	//LAUNCH BROWSER
        WebDriver driver = new ChromeDriver();
        driver.get("https://app.papirfly.com/mars/external.enter?p_com_id=14232");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        //LOGIN USING USERNAME AND PASSWORD    
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("p_user_name")))
        .sendKeys("kaaylabs+kelly@papirfly.com");

        driver.findElement(By.name("p_password"))
        .sendKeys("TFFbTFgpJJz9wELaR6aD");

        driver.findElement(By.id("LOGIN-panel--login")).click();

        Thread.sleep(5000);

        takeScreenshot(driver, "After_Login");
        
        //ACCEPT COOKIES
        try {
            WebElement cookieHost = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("cookie-banner"))
            );

            SearchContext cookieShadow = cookieHost.getShadowRoot();

            WebElement acceptBtn = wait.until(d ->
                cookieShadow.findElement(By.cssSelector("#accept-all-btn"))
            );

            try {
                acceptBtn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", acceptBtn);
            }
        } catch (Exception e) {
                    }
        //SKIP TOUR IF PRESENT
        try {
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Skip tour']")))
                    .click();
        } catch (Exception e) {}
 
        //CLICK ON PRODUCT MENU 
        WebElement toolbarHost = wait.until(
            ExpectedConditions.presenceOfElementLocated(  
                By.cssSelector("web-toolbar[placeholder='true']")
            )
        );

        SearchContext shadow1 = toolbarHost.getShadowRoot();

       
        WebElement productMenu = wait.until(d ->
            shadow1.findElement(By.cssSelector("#product-menu"))
        );

        try {
            productMenu.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", productMenu);
        }

        //CLICK ON PRODUCE
        WebElement produce = wait.until(d ->
            shadow1.findElement(By.cssSelector("a[href*='produce']"))
        );

        try {
            produce.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", produce);
        }
        //CLICK ON TEMPLATES
        WebElement templates = wait.until(
        	    ExpectedConditions.elementToBeClickable(
        	        By.xpath("//a[contains(@data-testid,'navigation-link-templates')]")
        	    )
        	);

        	((JavascriptExecutor)driver)
        	    .executeScript("arguments[0].click();", templates);


        	// CLICK ON SEARCH BOX AND ENTER TEMPLATE NAME
        	WebElement searchBox = wait.until(
        	    ExpectedConditions.visibilityOfElementLocated(
        	        By.cssSelector("input[placeholder='Start searching...']")
        	    )
        	);

        	searchBox.clear();
        	searchBox.sendKeys("KE Military Veterans Flier");
        	

			Thread.sleep(3000);
			
			takeScreenshot(driver, "After_Template_Search");
        
        
        //CLICK THE TEMPLATE CHECKBOX
        try {
        	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@data-testid,'select-template-checkbox-id-97027')]")))
        	.click();
        } catch (Exception e) {}
        
		
		//CLICK ON NEW DOCUMENT BUTTON
        try {

            WebElement newDocumentBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='new-document-button']")));

            Actions act = new Actions(driver);

            act.keyDown(Keys.CONTROL)
               .click(newDocumentBtn)
               .keyUp(Keys.CONTROL)                                                                                              
               .build()
               .perform();

        } catch (Exception e) {
        }
		//CLICK ON SELECT
	      try {
	            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class='c-button c-button--primary']"))).click();
	        } catch (Exception e) {}
	      
	      // SWITCH TO NEW TAB
	      try {

	    	    Thread.sleep(5000);

 	    	    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

	    	    driver.switchTo().window(tabs.get(1));

	    	} catch (Exception e) {

	    	    e.printStackTrace();
	    	}
	      //CLICK ON SAVE

	    	try {

	    	    Thread.sleep(3000);

	    	    WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@aria-label,'Save')]")));

	    	    JavascriptExecutor js =(JavascriptExecutor) driver;

	    	    js.executeScript("arguments[0].scrollIntoView(true);",saveBtn);

	    	    Thread.sleep(2000);

	    	    js.executeScript("arguments[0].click();",saveBtn);

	    	    System.out.println("Save button clicked successfully");

	    	} catch (Exception e) {

	    	    e.printStackTrace();
	    	}
	    	//ENTER DOCUMENT NAME
	    	 try {
	         	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Document name']")))
	         .sendKeys("KarthickQA1");
	         } catch (Exception e) {}
	    	 //CLICK ON SAVE AND CLOSE
	    	 try {
	            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@aria-label,'Save and close')]"))).click();
	           } catch (Exception e) {}
	    	 
	    	 try {

	    		    Thread.sleep(5000);

	    		    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

	    		    System.out.println("Total Tabs = " + tabs.size());

	    		    // SWITCH TO LAST TAB
	    		    driver.switchTo().window(tabs.get(tabs.size() - 1));

	    		    System.out.println("Switched to latest tab");

	    		    Thread.sleep(8000);

	    		    JavascriptExecutor js = (JavascriptExecutor) driver;

	    		    System.out.println(driver.getTitle());

	    		    System.out.println(driver.getCurrentUrl());

	    		    // CLICK ON DOWNLOAD BUTTON
	    		    List<WebElement> buttons = driver.findElements(
	    		            By.xpath("//button[contains(@data-testid,'download-button')]"));

	    		    System.out.println("Buttons Found = " + buttons.size());

	    		    for (WebElement btn : buttons) {

	    		        try {

	    		            js.executeScript("arguments[0].scrollIntoView({block:'center'});",btn);

	    		            Thread.sleep(2000);

	    		            js.executeScript("arguments[0].click();", btn);

	    		            System.out.println("DOWNLOAD CLICKED");
	    		            
	    		            Thread.sleep(5000);

	    		            takeScreenshot(driver, "After_Download");

	    		            break;

	    		        } catch (Exception ex) {

	    		            ex.printStackTrace();
	    		        }
	    		    }

	    		}
	    		catch (Exception e) {

	    		    System.out.println("DOWNLOAD FAILED");
	    		    e.printStackTrace();
	    		}
	    	 
				
				    }
    
        
    }
