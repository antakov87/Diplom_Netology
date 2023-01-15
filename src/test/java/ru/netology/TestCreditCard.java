package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DBHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.page.HomePage;
import ru.netology.page.CreditPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DBHelper.deleteAllDB;
import static ru.netology.data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.*;


public class TestCreditCard {

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
    @DisplayName("Должен успешно оплатить с одобренной кредитной картой в форме кредита")
    void shouldSuccessPayWithApprovedCreditCard() {
        successCreditPage().enterValidUserWithApprovedCard();
        assertEquals("APPROVED", DBHelper.getStatusCreditCard());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с отклоненной кредитной картой в форме кредита")
    void shouldReturnFailWithDeclinedCreditCard() {
        successCreditPage().enterValidUserWithIncorrectCard(validUser(getDeclinedCard()));
        assertEquals("DECLINED", DBHelper.getStatusCreditCard());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с неизвестной кредитной картой")
    void shouldReturnErrorWithUnknownCreditCard() {
        successCreditPage().enterValidUserWithIncorrectCard(validUser(getUnknownCard()));
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми пустыми полями в форме кредита")
    void shouldReturnErrorsWithEmptyAllCredit() {
        successCreditPage().enterInputs(emptyUser());
        errorsDisplayCredit();
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем карты в форме кредита")
    void shouldReturnErrorWithEmptyCreditCard() {
        successCreditPage().enterIncorrectCardInput(userWithEmptyFieldCard(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением поля Карты до лимита в форме кредита")
    void shouldReturnErrorWithCardUnderLimitCredit() {
        successCreditPage().enterIncorrectCardInput(userWithCardUnderLimit(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем месяца в форме оплаты кредитной картой")
    void shouldReturnErrorWithEmptyMonthCreditCard() {
        successCreditPage().enterIncorrectMonthInput(userWithAnEmptyMonthField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением '2' поля Месяца в форме оплаты кредитной картой")
    void shouldReturnErrorsWithSingleDigitInMonthFieldCreditCard() {
        successCreditPage().enterIncorrectMonthInput(userWithSingleDigitInMonthField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем месяца в форме оплаты кредитной картой")
    void shouldReturnErrorWithInvalidMonthCreditCard() {
        successCreditPage().enterIncorrectMonthInput(userWithNumberInMonthFieldMoreTwelve(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с нулевым месяцем в форме оплаты кредитной картой")
    void shouldReturnErrorWithMonthZeroCreditCard() {
        successCreditPage().enterIncorrectMonthInput(userWithMonthZero(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем года в форме оплаты кредитной картой")
    void shouldReturnErrorWithEmptyYearCreditCard() {
        successCreditPage().enterIncorrectYearInput(userWithAnEmptyYearField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем года в форме оплаты кредитной картой")
    void shouldReturnErrorWithInvalidYearCreditCard() {
        successCreditPage().enterIncorrectYearInput(userWithSingleDigitInYearField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с ушедшим годом в форме оплаты кредитной картой")
    void shouldReturnErrorWithYearGoneCreditCard() {
        successCreditPage().enterIncorrectYearInput(userWithAnOldDateInYearField(), "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением '00' поля Года в форме оплаты кредитной картой")
    void shouldReturnErrorsWithYearZeroInputsCreditCard() {
        successCreditPage().enterIncorrectYearInput(userWithYearZeroInField(), "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем имени в форме оплаты кредитной картой")
    void shouldReturnErrorWithEmptyNameCreditCard() {
        successCreditPage().enterIncorrectNameInput(userWithAnEmptyOwnerField(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с одним словом при заполнении поля Владелец в форме оплаты кредитной картой")
    void shouldReturnErrorUserWithOneWordInOwnerFieldCreditCard() {
        successCreditPage().enterIncorrectNameInput(userWithOneWordInOwnerField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с тремя словами при заполнении поля Владелец в форме оплаты кредитной картой")
    void shouldReturnErrorUserWithThreeWordsInOwnerFieldCreditCard() {
        successCreditPage().enterIncorrectNameInput(userWithThreeWordsInOwnerField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с именем на русском языке в форме оплаты кредитной картой")
    void shouldReturnErrorWithNameInRussianInOwnerFieldCreditCard() {
        successCreditPage().enterIncorrectNameInput(userWithNameInRussianInOwnerField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с номером в поле владельца в форме оплаты кредитной картой")
    void shouldReturnFailWithNumbersInOwnerFieldDCreditCard() {
        successCreditPage().enterIncorrectNameInput(userWithNumbersInOwnerField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении спец.символами в поле владельца в форме оплаты кредитной картой")
    void shouldReturnFailWithCharactersInOwnerFieldDCreditCard() {
        successCreditPage().enterIncorrectNameInput(userWithCharactersInOwnerField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем CVC в форме оплаты кредитной картой")
    void shouldReturnErrorWithEmptyCodeCreditCard() {
        successCreditPage().enterIncorrectCodeInput(userWithAnEmptyInFieldCvc(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении одной цифрой в поле CVC в форме оплаты кредитной картой")
    void shouldReturnErrorWithSingleDigitInFieldCvcCreditCard() {
        successCreditPage().enterIncorrectCodeInput(userWithSingleDigitInFieldCvc(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении двумя цифрами в поле CVC в форме оплаты кредитной картой")
    void shouldReturnErrorWithTwoDigitsInFieldCvcDCreditCard() {
        successCreditPage().enterIncorrectCodeInput(userWithTwoDigitsInFieldCvc(), "Неверный формат");
    }

    private CreditPage successCreditPage() {
        new HomePage().openCreditForm().successOpenPage();
        return new CreditPage();
    }

    private void errorsDisplayCredit() {
        new CreditPage().errorsDisplay("Неверный формат", "Неверный формат", "Неверный формат", "Поле обязательно для заполнения", "Неверный формат");
    }

}

