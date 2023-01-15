package ru.netology.data;

import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {
    private static final String approvedCard = "4444 4444 4444 4441";
    private static final String declinedCard = "4444 4444 4444 4442";
    private static final String unknownCard = "1234 4321 1234 4321";
    private static final String holder = "Antakov Sergey";
    private static final String cvc = "123";

    @Value
    public static class CardInfo {
        String number;
        String year;
        String month;
        String holder;
        String cvc;
    }



    //валидный пользователь
    public static CardInfo validUser(String cardType) {
        return new CardInfo(
                cardType, // approvedCard, declinedCard, unknownCard
                getGenerateDateYear(), // Год
                getGenerateDateMonth(), // месяц
                getHolder(),
                getCvc()
        );
    }

    // Пользователь с пустыми значениями полей.
    public static CardInfo emptyUser() {
        return new CardInfo(
                "",
                "",
                "",
                "",
                ""
        );
    }

    //пользователь с невалидным номером карты менее 16 цифр
    public static CardInfo userWithCardUnderLimit() {
        return new CardInfo(
                "4444 4444 4444 444",
                getGenerateDateYear(),
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //пользователь с невалидным номером карты при пустом поле
    public static CardInfo userWithEmptyFieldCard() {
        return new CardInfo(
                "",
                getGenerateDateYear(),
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //пользователь с невалидным месяцем при пустом поле
    public static CardInfo userWithAnEmptyMonthField() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                "",
                getHolder(),
                getCvc()
        );
    }

    //пользователь с невалидным месяцем при одной цифре
    public static CardInfo userWithSingleDigitInMonthField() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                "2",
                getHolder(),
                getCvc()
        );
    }

    //пользователь с невалидным месяцем при цифре более 12
    public static CardInfo userWithNumberInMonthFieldMoreTwelve() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                "14",
                getHolder(),
                getCvc()
        );
    }

    //пользователь с невалидным месяцем при цифре 00
    public static CardInfo userWithMonthZero() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                "00",
                getHolder(),
                getCvc()
        );
    }

    //пользователь с невалидным годом при пустом поле
    public static CardInfo userWithAnEmptyYearField() {
        return new CardInfo(
                getApprovedCard(),
                "",
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //пользователь с невалидным годом при заполнении поля одной цифрой
    public static CardInfo userWithSingleDigitInYearField() {
        return new CardInfo(
                getApprovedCard(),
                "2",
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //пользователь с невалидным годом при заполнении старой датой
    public static CardInfo userWithAnOldDateInYearField() {
        return new CardInfo(
                getApprovedCard(),
                "21",
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //пользователь с невалидным нулевым годом
    public static CardInfo userWithYearZeroInField() {
        return new CardInfo(
                getApprovedCard(),
                "00",
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //пользователь с невалидным владелецем при пустом поле
    public static CardInfo userWithAnEmptyOwnerField() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "",
                getCvc()
        );
    }

    //пользователь с невалидным владелецем при заполнении Antakov
    public static CardInfo userWithOneWordInOwnerField() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "Antakov",
                getCvc()
        );
    }

    //пользователь с невалидным владелецем при заполнении Antakov Sergey Andreevich
    public static CardInfo userWithThreeWordsInOwnerField() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "Antakov Sergey Andreevich",
                getCvc()
        );
    }

    //пользователь с невалидным владелецем при заполнении Антаков
    public static CardInfo userWithNameInRussianInOwnerField() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "Антаков",
                getCvc()
        );
    }

    //пользователь с невалидным владелецем при заполнении цифрами
    public static CardInfo userWithNumbersInOwnerField() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "456321",
                getCvc()
        );
    }

    //пользователь с невалидным владелецем при заполнении спец. символами
    public static CardInfo userWithCharactersInOwnerField() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "#$%@",
                getCvc()
        );
    }

    //пользователь с невалидным cvc при пустом поле
    public static CardInfo userWithAnEmptyInFieldCvc() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                getHolder(),
                ""
        );
    }

    //пользователь с невалидным cvc при заполнении одной цифрой
    public static CardInfo userWithSingleDigitInFieldCvc() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                getHolder(),
                "1"
        );
    }

    //пользователь с невалидным cvc при заполнении двумя цифрами
    public static CardInfo userWithTwoDigitsInFieldCvc() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                getHolder(),
                "12"
        );
    }

    private static String getGenerateDateYear() {
        return LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getGenerateDateMonth() {
        return LocalDate.now().plusMonths(5).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getApprovedCard() {

        return approvedCard;
    }

    public static String getDeclinedCard() {

        return declinedCard;
    }

    public static String getUnknownCard() {

        return unknownCard;
    }

    public static String getHolder() {

        return holder;
    }

    public static String getCvc() {
        return cvc;
    }
}






