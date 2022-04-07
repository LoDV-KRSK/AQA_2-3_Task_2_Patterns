package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;

public class AuthTest {


@Test
    void shouldSuccessfullyLogIn(){
    var registered = getRegisteredUser("active");
    Configuration.holdBrowserOpen = false;
    Selenide.open("http://0.0.0.0:9999/");
    $("[data-test-id=login] input").setValue(registered.getLogin());
    $("[data-test-id=password] input").setValue(registered.getPassword());
    $("[data-test-id='action-login']").click();
    $(withText("Личный кабинет")).shouldBe(visible);
}
    @Test
    void ShouldBlockUser() {
        var blocked = getRegisteredUser("blocked");
        Configuration.holdBrowserOpen = false;
        Selenide.open("http://0.0.0.0:9999/");
        $("[data-test-id=login] input").setValue(blocked.getLogin());
        $("[data-test-id=password] input").setValue(blocked.getPassword());
        $("[data-test-id='action-login']").click();
        $(withText("Пользователь заблокирован")).shouldBe(visible);
    }

        @Test
        void shouldShowErrorBecauseUserIsNotLoggedIn() {
            var notRegistered = DataGenerator.Registration.getUser("active");
            Configuration.holdBrowserOpen = false;
            Selenide.open("http://0.0.0.0:9999/");
            $("[data-test-id=login] input").setValue(notRegistered.getLogin());
            $("[data-test-id=password] input").setValue(notRegistered.getPassword());
            $("[data-test-id='action-login']").click();
            $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible);
        }

    @Test
    void shouldErrorIfWrongLogin() {
        var registered = getRegisteredUser("active");
        var incorrectLogin = DataGenerator.Registration.getUser("active");
        Configuration.holdBrowserOpen = false;
        Selenide.open("http://0.0.0.0:9999/");
        $("[data-test-id=login] input").setValue(incorrectLogin.getLogin());
        $("[data-test-id=password] input").setValue(registered.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldErrorIfWrongPassword() {
        var registered = getRegisteredUser("active");
        var incorrectPassword  = DataGenerator.Registration.getUser("active");
        Configuration.holdBrowserOpen = false;
        Selenide.open("http://0.0.0.0:9999/");
        $("[data-test-id=login] input").setValue(registered.getLogin());
        $("[data-test-id=password] input").setValue(incorrectPassword.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible);
    }

}


