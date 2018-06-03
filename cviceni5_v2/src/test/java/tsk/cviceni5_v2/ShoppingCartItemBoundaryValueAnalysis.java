package tsk.cviceni5_v2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ShoppingCartItemBoundaryValueAnalysis {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\kucabpet\\Downloads\\geckodriver-v0.20.1-win64\\geckodriver.exe");

        driver = new FirefoxDriver();
        baseUrl = "http://pcfeia406a.vsb.cz/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /**
     * ID: 1
     * Jmeno: testNegativeValue
     * Popis: Pri zadavani poctu polozek do kosiku zkusi zadat zapornou hodnotu
     * Podminky:
     *     1, Existuje Hardware polozka v menu
     *     2, Existuje Graphics Cards polozka v menu
     *     3, Existuje zbozi Graphic Card Matrox G200 MMS
     * Akce:
     *     1, Vybere zbozi Matrox G200 MMS Graphic Card
     *     2, Zada -1 jako pocet polozek
     *     3, Prida zbozi do kosiku
     * Ocekavane vysledky:
     *     1, Zbozi se neprida do kosiku
     *     2, Zobrazi se chybova hlaska "Quantity Units errors"
     */
    @Test
    public void testNegativeValue() {

        final String changeQuantityTitle = " Change your quantity by highlighting the number in the box, correcting the quantity and clicking this button. ";
        final String errorMessage = "... Quantity Units errors - ";


        driver.get(baseUrl + "/shop2/");
        driver.findElement(By.linkText("Hardware->")).click();
        driver.findElement(By.cssSelector("img[alt=\"Graphics Cards\"]")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'Matrox G200 MMS')])[2]")).click();
        driver.findElement(By.xpath("//input[@value='Add to Cart']")).click();
        driver.findElement(By.name("cart_quantity[]")).click();
        // clear field
        driver.findElement(By.name("cart_quantity[]")).clear();
        // set 0 quantity
        driver.findElement(By.name("cart_quantity[]")).sendKeys("-1");

        final List<WebElement> elements = driver.findElements(By.xpath("//input[@title='" + changeQuantityTitle + "']"));

        if (!(elements.size() > 0)) {
            fail("Could not update quantity.");
        }
        elements.get(0).click();

        try {
            final WebElement errorElement = driver.findElement(By.className("messageStackCaution"));
            Assert.assertTrue("Expected error message", errorElement.getText().contains(errorMessage));
        } catch (NoSuchElementException e) {
            fail("Could not find error message");
        }
    }

    /**
     * ID: 2
     * Jmeno: testZeroValue
     * Popis: Pri zadavani poctu polozek do kosiku zkusi zadat nulovou hodnotu
     * Podminky:
     *     1, Existuje Hardware polozka v menu
     *     2, Existuje Graphics Cards polozka v menu
     *     3, Existuje zbozi Graphic Card Matrox G200 MMS
     * Akce:
     *     1, Vybere zbozi Matrox G200 MMS Graphic Card
     *     2, Zada 0 jako pocet polozek
     *     3, Prida zbozi do kosiku
     * Ocekavane vysledky:
     *     1, Kosik zustane beze zmeny
     */
    @Test
    public void testZeroValue() {

        final String changeQuantityTitle = " Change your quantity by highlighting the number in the box, correcting the quantity and clicking this button. ";
        final String emptyShoppingCartText = "Your Shopping Cart is empty.";


        driver.get(baseUrl + "/shop2/");
        driver.findElement(By.linkText("Hardware->")).click();
        driver.findElement(By.cssSelector("img[alt=\"Graphics Cards\"]")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'Matrox G200 MMS')])[2]")).click();
        driver.findElement(By.xpath("//input[@value='Add to Cart']")).click();
        driver.findElement(By.name("cart_quantity[]")).click();
        // clear field
        driver.findElement(By.name("cart_quantity[]")).clear();
        // set 0 quantity
        driver.findElement(By.name("cart_quantity[]")).sendKeys("0");

        final List<WebElement> elements = driver.findElements(By.xpath("//input[@title='" + changeQuantityTitle + "']"));

        if (!(elements.size() > 0)) {
            fail("Could not update quantity.");
        }
        elements.get(0).click();

        Assert.assertEquals("Expected empty shopping cart", emptyShoppingCartText, driver.findElement(By.id("cartEmptyText")).getText());
    }

    /**
     * ID: 3
     * Jmeno: testMaxValue
     * Popis: Pri zadavani poctu polozek do kosiku zkusi maximalni dostupnout hodnotu
     * Podminky:
     *     1, Existuje Hardware polozka v menu
     *     2, Existuje Graphics Cards polozka v menu
     *     3, Existuje zbozi Graphic Card Matrox G200 MMS a lze je pridat do kosiku
     *     4, Na sklade je alespon jeden kus
     * Akce:
     *     1, Vybere zbozi Matrox G200 MMS Graphic Card
     *     2, Zada max pocet polozek dostupnych na sklade
     *     3, Prida zbozi do kosiku
     * Ocekavane vysledky:
     *     1, Do kosiku se prida nove zbozi
     *     2, Pocet polozek v kosiku bude odpovidat poctu max poctu kusu Graphic Card Matrox G200 MMS  na sklade
     *     3, Zobrazi se informacni zprava o zmene obsahu kosiku
     */
    @Test
    public void testMaxValue() {

        final String changeQuantityTitle = " Change your quantity by highlighting the number in the box, correcting the quantity and clicking this button. ";


        driver.get(baseUrl + "/shop2/");
        driver.findElement(By.linkText("Hardware->")).click();
        driver.findElement(By.cssSelector("img[alt=\"Graphics Cards\"]")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'Matrox G200 MMS')])[2]")).click();
        driver.findElement(By.xpath("//input[@value='Add to Cart']")).click();

        final String[] cartQuantityText = driver.findElement(By.className("cartQuantity")).getText().split("Max: ");

        if (!(cartQuantityText.length > 1)) {
            fail("Could not find max quantity");
        }
        final int maxValue = Integer.parseInt(cartQuantityText[1]);

        driver.findElement(By.name("cart_quantity[]")).click();
        // clear field
        driver.findElement(By.name("cart_quantity[]")).clear();
        // set 0 quantity
        driver.findElement(By.name("cart_quantity[]")).sendKeys("" + maxValue);

        final List<WebElement> elements = driver.findElements(By.xpath("//input[@title='" + changeQuantityTitle + "']"));

        if (!(elements.size() > 0)) {
            fail("Could not update quantity.");
        }
        elements.get(0).click();

        final String[] cartTotalsDisplays = driver.findElement(By.className("cartTotalsDisplay")).getText().split(" ");

        if (!(cartTotalsDisplays.length > 0)) {
            fail("Could not get cart total display");
        }

//        final String[] totalItem = cartTotalsDisplays[0].split("Total Items: ");
//        if (!(totalItem.length > 0)) {
//            fail("Could not get Total items value");
//        }
        final int totalItemValue = Integer.parseInt(cartTotalsDisplays[2]);

        assertEquals("Expected equals quantity and totel item", maxValue, totalItemValue);
    }

    /**
     * ID: 4
     * Jmeno: testExceededMaxValue
     * Popis: Pri zadavani poctu polozek do kosiku zkusi zadat maximalni dostupnout hodnotu + 1
     * Podminky:
     *     1, Existuje Hardware polozka v menu
     *     2, Existuje Graphics Cards polozka v menu
     *     3, Existuje zbozi Graphic Card Matrox G200 MMS a lze je pridat do kosiku
     * Akce:
     *     1, Vybere zbozi Matrox G200 MMS Graphic Card
     *     2, Zada max pocet polozek dostupnych na sklade + 1
     *     3, Prida zbozi do kosiku
     * Ocekavane vysledky:
     *     1, Obsah kosiku se nezmeni
     *     2, Zobrazi se chybova zprava: "The quantity added to your cart has been adjusted because of a restriction on maximum you are allowed."
     */
    @Test
    public void testExceededMaxValue() {

        final String changeQuantityTitle = " Change your quantity by highlighting the number in the box, correcting the quantity and clicking this button. ";
        String errorMessage = "The quantity added to your cart has been adjusted because of a restriction on maximum you are allowed. See this item:";

        driver.get(baseUrl + "/shop2/");
        driver.findElement(By.linkText("Hardware->")).click();
        driver.findElement(By.cssSelector("img[alt=\"Graphics Cards\"]")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'Matrox G200 MMS')])[2]")).click();
        driver.findElement(By.xpath("//input[@value='Add to Cart']")).click();

        final String[] cartQuantityText = driver.findElement(By.className("cartQuantity")).getText().split("Max: ");

        if (!(cartQuantityText.length > 1)) {
            fail("Could not find max quantity");
        }
        final int maxValue = Integer.parseInt(cartQuantityText[1]) + 1;

        driver.findElement(By.name("cart_quantity[]")).click();
        // clear field
        driver.findElement(By.name("cart_quantity[]")).clear();
        // set 0 quantity
        driver.findElement(By.name("cart_quantity[]")).sendKeys("" + maxValue);

        final List<WebElement> elements = driver.findElements(By.xpath("//input[@title='" + changeQuantityTitle + "']"));

        if (!(elements.size() > 0)) {
            fail("Could not update quantity.");
        }
        elements.get(0).click();

        try {
            final WebElement errorElement = driver.findElement(By.className("messageStackCaution"));
            Assert.assertTrue("Expected error message", errorElement.getText().contains(errorMessage));
        } catch (NoSuchElementException e) {
            fail("Could not find error message");
        }
    }

    @After
    public void tearDown() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
