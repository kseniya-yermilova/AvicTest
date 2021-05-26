package avic;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

    @Test(priority = 1)
    public void checkThatUrlContainsSearchQuerty() {
        driver.findElement(By.xpath("//input[@id='input_search']")).clear();
        driver.findElement(By.xpath("//input[@id='input_search']")).sendKeys("iPhone11", Keys.ENTER);
        Assert.assertTrue(driver.getCurrentUrl().contains("query=iPhone"));
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
