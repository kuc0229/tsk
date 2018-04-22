package tsk.cviceni5_v2;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.hamcrest.CoreMatchers;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Test3 {
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
  public void test3() throws Exception {
    driver.get(baseUrl + "/shop2/");
    driver.findElement(By.linkText("DVD Movies->")).click();
    driver.findElement(By.partialLinkText("|_ Action")).click();
    driver.findElement(By.cssSelector("img.listingProductImage")).click();
    driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
    driver.findElement(By.cssSelector("img[alt=\"Checkout\"]")).click();
    driver.findElement(By.id("gender-male")).click();
    driver.findElement(By.id("firstname")).clear();
    driver.findElement(By.id("firstname")).sendKeys("David");
    driver.findElement(By.id("lastname")).clear();
    driver.findElement(By.id("lastname")).sendKeys("Ježek");
    driver.findElement(By.id("street-address")).clear();
    driver.findElement(By.id("street-address")).sendKeys("ulice 1");
    driver.findElement(By.id("city")).clear();
    driver.findElement(By.id("city")).sendKeys("Ostrava - Poruba");
    driver.findElement(By.id("state")).clear();
    driver.findElement(By.id("state")).sendKeys("czech republic");
    driver.findElement(By.id("postcode")).clear();
    driver.findElement(By.id("postcode")).sendKeys("70800");
    new Select(driver.findElement(By.id("country"))).selectByVisibleText("Czech Republic");
    driver.findElement(By.id("telephone")).clear();
    driver.findElement(By.id("telephone")).sendKeys("123456789");
    driver.findElement(By.id("dob")).clear();
    driver.findElement(By.id("dob")).sendKeys("05/21/1970");
    driver.findElement(By.id("email-address")).clear();
    driver.findElement(By.id("email-address")).sendKeys("david" + new Random().nextInt(100000) + "@vsb.cz");
    driver.findElement(By.id("password-new")).clear();
    driver.findElement(By.id("password-new")).sendKeys("1234567");
    driver.findElement(By.id("password-confirm")).clear();
    driver.findElement(By.id("password-confirm")).sendKeys("1234567");
    driver.findElement(By.id("newsletter-checkbox")).click();
    driver.findElement(By.cssSelector("#createAccountForm > div.buttonRow.forward > input[type=\"image\"]")).click();
//    assertEquals("aaa", driver.findElement(By.cssSelector("address")).getText());
    assertEquals(true, driver.findElement(
    		By.cssSelector("address")).getText().
    		contains("David"));
    assertThat(driver.findElement(
    		By.cssSelector("address")).getText(),
    		CoreMatchers.allOf(
    				CoreMatchers.containsString("David"),
    				CoreMatchers.containsString("Jezek"),
    				CoreMatchers.containsString("70800"),
    				CoreMatchers.containsString("ulice 1"),
    				CoreMatchers.containsString("Czech Republic")));
    assertEquals(true, driver.findElement(
    		By.cssSelector("address")).getText().
    		contains("Jezek"));
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
