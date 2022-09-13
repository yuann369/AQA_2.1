import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTest {

    private WebDriver driver;


    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testFieldsCorrect() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Петров Алексей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278345612");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",text.trim());
    }

    @Test
    void testNameWrong() {
        driver.get("http://localhost:9999");
        List<WebElement> element = driver.findElements(By.className("input__sub"));
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Petrov Alex");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278345612");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = element.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",text.trim());
    }

    @Test
    void testPhoneWrong() {
        driver.get("http://localhost:9999");
        List<WebElement> element = driver.findElements(By.className("input__sub"));
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Петров Алексей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("89278345612");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = element.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",text.trim());
    }

    @Test
    void testMissedCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Петров Алексей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79278345612");
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.className("input_invalid")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",text.trim());
    }
}
