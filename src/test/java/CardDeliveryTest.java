import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.*;

public class CardDeliveryTest{
    String generateDate (int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }
    @AfterEach
    void tearDown() {
        clearBrowserCookies();
        closeWebDriver();
    }
    @Test
    void CardDelivery(){
        String date = generateDate(3);
        $("[data-test-id= city] input").setValue("Москва");
        $("[data-test-id= date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id= date] input").setValue(date);
        $("[data-test-id= name] input").setValue("Иванов Иван");
        $("[data-test-id= phone] input").setValue("+79992223344");
        $("[data-test-id= agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id= notification]").shouldHave(Condition.exactText("Успешно! " + "встреча успешно забронирована на " + date), Duration.ofSeconds(15)).shouldBe(exist);
    }
}