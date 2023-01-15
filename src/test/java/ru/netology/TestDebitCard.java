package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DBHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.page.HomePage;
import ru.netology.page.DebitPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DBHelper.deleteAllDB;
import static ru.netology.data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.*;


public class TestDebitCard {

    @BeforeAll
    static void addListenerAndHeadless() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @AfterAll
    static void removeListener() {
        SelenideLogger.removeListener("AllureSelenide");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
        new HomePage().checkHomePageIsOpened();
    }

    @AfterEach
    void setDown() {
        deleteAllDB();
    }


    @Test
    @DisplayName("Должен успешно оплатить с одобренной дебетовой картой в форме оплаты")
    void shouldSuccessPayWithApprovedDebitCard() {
        successDebitPage().enterValidUserWithApprovedCard();
        assertEquals("APPROVED", DBHelper.getStatusDebitCard());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с отклоненной дебетовой картой в форме оплаты")
    void shouldReturnFailWithDeclinedDebitCard() {
        successDebitPage().enterValidUserWithIncorrectCard(validUser(getDeclinedCard()));
        assertEquals("DECLINED", DBHelper.getStatusDebitCard());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с неизвестной дебетовой картой")
    void shouldReturnErrorWithUnknownCardDebit() {
        successDebitPage().enterValidUserWithIncorrectCard(validUser(getUnknownCard()));
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми пустыми полями в форме оплаты")
    void shouldReturnErrorsWithEmptyAllDebit() {
        successDebitPage().enterInputs(emptyUser());
        errorsDisplayDebit();
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустой дебетовой картой в форме оплаты")
    void shouldReturnErrorWithEmptyDebitCard() {
        successDebitPage().enterIncorrectCardInput(userWithEmptyFieldCard(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением поля Карты до лимита в форме оплаты")
    void shouldReturnErrorWithCardUnderLimitDebit() {
        successDebitPage().enterIncorrectCardInput(userWithCardUnderLimit(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем месяца в форме оплаты")
    void shouldReturnErrorWithEmptyMonthDebit() {
        successDebitPage().enterIncorrectMonthInput(userWithAnEmptyMonthField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением '2' поля Месяца в форме оплаты")
    void shouldReturnErrorsWithSingleDigitInMonthFieldDebit() {
        successDebitPage().enterIncorrectMonthInput(userWithSingleDigitInMonthField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем месяца в форме оплаты")
    void shouldReturnErrorWithInvalidMonthDebit() {
        successDebitPage().enterIncorrectMonthInput(userWithNumberInMonthFieldMoreTwelve(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с нулевым месяцем в форме оплаты")
    void shouldReturnErrorWithMonthZeroDebit() {
        successDebitPage().enterIncorrectMonthInput(userWithMonthZero(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем года в форме оплаты")
    void shouldReturnErrorWithEmptyYearDebit() {
        successDebitPage().enterIncorrectYearInput(userWithAnEmptyYearField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем года в форме оплаты")
    void shouldReturnErrorWithInvalidYearDebit() {
        successDebitPage().enterIncorrectYearInput(userWithSingleDigitInYearField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с ушедшим годом в форме оплаты")
    void shouldReturnErrorWithYearGoneDebit() {
        successDebitPage().enterIncorrectYearInput(userWithAnOldDateInYearField(), "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением '00' поля Года в форме оплаты")
    void shouldReturnErrorsWithYearZeroInputsDebit() {
        successDebitPage().enterIncorrectYearInput(userWithYearZeroInField(), "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем имени в форме оплаты")
    void shouldReturnErrorWithEmptyNameDebit() {
        successDebitPage().enterIncorrectNameInput(userWithAnEmptyOwnerField(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с одним словом при заполнении поля Владелец в форме оплаты")
    void shouldReturnErrorUserWithOneWordInOwnerFieldDebit() {
        successDebitPage().enterIncorrectNameInput(userWithOneWordInOwnerField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с тремя словами при заполнении поля Владелец в форме оплаты")
    void shouldReturnErrorUserWithThreeWordsInOwnerFieldDebit() {
        successDebitPage().enterIncorrectNameInput(userWithThreeWordsInOwnerField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с именем на русском языке в форме оплаты")
    void shouldReturnErrorWithNameInRussianInOwnerFieldDebit() {
        successDebitPage().enterIncorrectNameInput(userWithNameInRussianInOwnerField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с номером в поле владельца в форме оплаты")
    void shouldReturnFailWithNumbersInOwnerFieldDebit() {
        successDebitPage().enterIncorrectNameInput(userWithNumbersInOwnerField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении спец.символами в поле владельца в форме оплаты")
    void shouldReturnFailWithCharactersInOwnerFieldDebit() {
        successDebitPage().enterIncorrectNameInput(userWithCharactersInOwnerField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем CVC в форме оплаты")
    void shouldReturnErrorWithEmptyCodeDebit() {
        successDebitPage().enterIncorrectCodeInput(userWithAnEmptyInFieldCvc(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении одной цифрой в поле CVC в форме оплаты")
    void shouldReturnErrorWithSingleDigitInFieldCvcDebit() {
        successDebitPage().enterIncorrectCodeInput(userWithSingleDigitInFieldCvc(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении двумя цифрами в поле CVC в форме оплаты")
    void shouldReturnErrorWithTwoDigitsInFieldCvcDebit() {
        successDebitPage().enterIncorrectCodeInput(userWithTwoDigitsInFieldCvc(), "Неверный формат");
    }

    private DebitPage successDebitPage() {
        new HomePage().openDebitForm().successOpenPage();
        return new DebitPage();
    }

    private void errorsDisplayDebit() {
        new DebitPage().errorsDisplay("Неверный формат", "Неверный формат", "Неверный формат", "Поле обязательно для заполнения", "Неверный формат");
    }
}


