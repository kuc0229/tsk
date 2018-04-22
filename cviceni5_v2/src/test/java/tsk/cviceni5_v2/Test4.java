package tsk.cviceni5_v2;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Test4 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://pcfeia406a.vsb.cz/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void test4() throws Exception {
    driver.get(baseUrl + "/shop2/");
    driver.findElement(By.linkText("Hardware->")).click();
    driver.findElement(By.cssSelector("img[alt=\"Graphics Cards\"]")).click();
    driver.findElement(By.linkText("Matrox G200 MMS")).click();
    driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
    driver.findElement(By.cssSelector("img[alt=\"Checkout\"]")).click();
    driver.findElement(By.id("firstname")).clear();
    driver.findElement(By.id("firstname")).sendKeys("david");
    driver.findElement(By.cssSelector("#createAccountForm > div.buttonRow.forward > input[type=\"image\"]")).click();
    String text = closeAlertAndGetItsText();
    assertTrue(closeAlertAndGetItsText().matches("^Errors have occurred during the processing of your form\\.\n\nPlease make the following corrections:\n\n[\\s\\S]* Please choose a salutation\\.\n[\\s\\S]* Is your last name correct[\\s\\S] Our system requires a minimum of 2 characters\\. Please try again\\.\n[\\s\\S]* Is your birth date correct[\\s\\S] Our system requires the date in this format: MM/DD/YYYY \\(eg 05/21/1970\\)\n[\\s\\S]* Is your email address correct[\\s\\S] It should contain at least 6 characters\\. Please try again\\.\n[\\s\\S]* Your Street Address must contain a minimum of 5 characters\\.\n[\\s\\S]* Your Post/ZIP Code must contain a minimum of 4 characters\\.\n[\\s\\S]* Your City must contain a minimum of 2 characters\\.\n[\\s\\S]* Your State must contain a minimum of 2 characters\\.\n[\\s\\S]* Your Telephone Number must contain a minimum of 3 characters\\.\n[\\s\\S]* Your Password must contain a minimum of 7 characters\\.\n$"));
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
