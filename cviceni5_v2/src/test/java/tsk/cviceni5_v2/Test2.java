package tsk.cviceni5_v2;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Test2 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://pcfeia406a.vsb.cz/";
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void test2() throws Exception {
    driver.get(baseUrl + "/shop2/");
    driver.findElement(By.linkText("DVD Movies->")).click();
    driver.findElement(By.partialLinkText("|_ Action")).click();
    driver.findElement(By.cssSelector("h3.itemTitle > a")).click();
    driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
    assertEquals("1", driver.findElement(By.name("cart_quantity[]")).getAttribute("value"));
    driver.findElement(By.linkText("Hardware->")).click();
    driver.findElement(By.partialLinkText("|_ Graphics Cards")).click();
    driver.findElement(By.linkText("Matrox G200 MMS")).click();
    driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
    driver.findElement(By.xpath("(//a[contains(text(),'Big Linked')])[2]")).click();
    driver.findElement(By.linkText("A Bug's Life \"Multi Pak\" Special 2003 Collectors Edition")).click();
    driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
    
    int size = driver.findElement(
    		By.id("cartContentsDisplay")).
    		findElements(By.tagName("tr")).size();
    
    for(int i =1; i<size; i++){
    	WebElement e = driver.findElement(
    		By.id("cartContentsDisplay")).
    		findElements(By.tagName("tr")).get(i);
    	
    	WebElement policko = e.findElement(By.name("cart_quantity[]"));
    	policko.clear();
    	policko.sendKeys("2");
    	e.findElement(
    			By.cssSelector("input[type=\"image\"]")).
    			click();
    	e = driver.findElement(
        		By.id("cartContentsDisplay")).
        		findElements(By.tagName("tr")).get(i);
    	double unitPrice = Double.parseDouble(e.findElement(By.className("cartUnitDisplay")).getText().replace("€", ""));
    	double totalPrice = Double.parseDouble(e.findElement(By.className("cartTotalDisplay")).getText().replace("€", ""));
    	assertEquals(2*unitPrice, totalPrice, 0.00001);
    }
    
    while(isElementPresent(By.id("cartContentsDisplay"))){
    	driver.findElement(By.cssSelector("img[alt=\"Delete this item from the cart by clicking this icon.\"]")).click();
    }
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
