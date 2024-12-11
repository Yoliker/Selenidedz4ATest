package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDeliveryTest {
    private String setDate(long daysToAdd, String pattern) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldCheckPositiveCase() {

        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        String newDate = setDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(newDate);
        $("[data-test-id=name] input").setValue("Иванов Игорь");
        $("[data-test-id=phone] input").setValue("+78230000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Успешно! \n Встреча успешно забронирована на " + newDate));

    }
}
