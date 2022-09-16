import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTest {

    private WebDriver driver;


    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver=null;
    }

    @Test
    void testFieldsCorrect() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Петров Алексей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278345612");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",text.trim());
    }

    @Test
    void testNameWrong() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Petrov Alex");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278345612");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",text.trim());
    }

    @Test
    void testEmptyName() {
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278345612");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения",text.trim());
    }

    @Test
    void testPhoneWrong() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Петров Алексей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("89278345612");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",text.trim());
    }

    @Test
    void testEmptyPhone() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Петров Алексей");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения",text.trim());
    }

    @Test
    void testMissedCheckbox() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Петров Алексей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278345612");
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",text.trim());
    }
}
