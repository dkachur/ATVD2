import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.testng.Assert;
import org.testng.annotations.*;


public class WikidepiaTest {
    private WebDriver chromeDriver;
    private static final String baseUrl = "https://www.wikipedia.org/";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().browserVersion("132").setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-fullscreen");
        this.chromeDriver = new ChromeDriver(chromeOptions);
    }

    @BeforeMethod
    public void preconditions() {
        chromeDriver.get(baseUrl);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        chromeDriver.quit();
    }

    @Test
    public void testWikipediaSearch() {
        WebElement searchField = chromeDriver.findElement(By.name("search"));
        Assert.assertNotNull(searchField);

        String query = "Selenium (software)";
        searchField.sendKeys(query);

        Assert.assertEquals(searchField.getAttribute("value"), query);

        WebElement searchButton = chromeDriver.findElement(By.cssSelector("button.pure-button.pure-button-primary-progressive"));
        Assert.assertNotNull(searchButton);
        searchButton.click();

        Assert.assertTrue(chromeDriver.getTitle().contains("Selenium (software)"));
    }
}


