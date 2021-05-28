package avic;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertTrue;

public class AvicTests {
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test
    public void checkThatUrlContainsSearchQuerty() {
        driver.findElement(By.xpath("//input[@id='input_search']")).clear();
        driver.findElement(By.xpath("//input[@id='input_search']")).sendKeys("iPhone11", Keys.ENTER);
        Assert.assertTrue(driver.getCurrentUrl().contains("query=iPhone"));
    }

    @Test
    public void checkThatLoginIsNotPossibleWithoutPassword() {
        driver.findElement(By.xpath("//div[@class='header-bottom__login flex-wrap middle-xs']//i[@class='icon icon-user-big']")).click();//клик на значок входа
        driver.findElement(By.xpath("//div[@class='width-auto']//input[@type='text' and @name='login']")).sendKeys("lalala@gmail.com");//ввод имейл
        driver.findElement(By.xpath("//button[@class='button-reset main-btn submit main-btn--green']")).click();//клик на кнопку войти
        assertTrue(driver.findElement(By.xpath("//div[@class='form-field input-field flex error']")).isDisplayed());

    }

    @Test
    public void checkThatLoginFieldDoesNotAcceptInvalidData() {
        driver.findElement(By.xpath("//div[@class='header-bottom__login flex-wrap middle-xs']//i[@class='icon icon-user-big']")).click();//клик на значок входа
        driver.findElement(By.xpath("//div[@class='width-auto']//input[@type='text' and @name='login']")).sendKeys("lalala.com");
        //    driver.findElement(By.xpath("//*[@id=\"mm-0\"]/main/div/div[2]/form/div[1]/div/input")).sendKeys("lalala.com");//ввод имейл
        driver.findElement(By.xpath("//button[@class='button-reset main-btn submit main-btn--green']")).click();//клик на кнопку войти
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("qwerty");//ввод пароля
        assertTrue(driver.findElement(By.xpath("//div[@class='form-field input-field flex error']")).isDisplayed());
    }

    @Test
    public void checkThatSaleProductsHavePercentIcon() {
        driver.findElement(By.xpath("//span[@class='sidebar-item']")).click();//клик на каталог товаров
        WebElement saleButton = driver.findElement(By.xpath("//span[text()='Уцененные товары']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", saleButton);//прокрутка страницы до элемента
        saleButton.click();//переход на уцененные товары

        WebElement macbookButton = driver.findElement(By.xpath("//ul[@class='category-box__list']//a[text()='MacBook']"));
        js.executeScript("arguments[0].scrollIntoView();", macbookButton);
        macbookButton.click();//macbook
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));//wait for page loading
        List<WebElement> elementsList = driver.findElements(xpath("//div[@class='container-main']"));

        for (WebElement webElement : elementsList) { //прошлись циклом и проверили что каждый элемент листа содержит значок скидки
            assertTrue(webElement.findElement(By.xpath("//img[@class=' ls-is-cached lazyloaded' and @title='Уценка']"))
                    .isDisplayed());
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
