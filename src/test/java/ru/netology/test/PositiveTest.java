package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLunits;
import ru.netology.page.TourPage;

import static com.codeborne.selenide.Selenide.open;

public class PositiveTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void openChrome() {
        open("http://localhost:8080/");
    }

    @DisplayName("Successful card purchase.")
    @Test
    public void shouldConfirmPaymentApprovedCard() {
        var tourPage = new TourPage();
        var payCard = tourPage.payCard();
        var approvedCardInformation = DataHelper.getValidCard();
        payCard.enterCardData(approvedCardInformation);
        payCard.successfulCardPayment();

        var paymentId = SQLunits.getPaymentId();
        var statusPayment = SQLunits.getStatusPayment(paymentId);
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    @DisplayName("Successful credit purchase")
    @Test
    public void shouldConfirmCreditApprovedCard() {
        var tourPage = new TourPage();
        var buyCredit = tourPage.buyCredit();
        var approvedCardInformation = DataHelper.getValidCard();
        buyCredit.enterCreditCardData(approvedCardInformation);
        buyCredit.successfulCreditCardPayment();

        var paymentId = SQLunits.getPaymentId();
        var statusPayment = SQLunits.getStatusCredit(paymentId);
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    @DisplayName("Successful card purchase with current M and Y.")
    @Test
    public void shouldConfirmPaymentCurrentMonthAndYear() {
        var tourPage = new TourPage();
        var payCard = tourPage.payCard();
        var validCardInformation = DataHelper.getCurrentMonthAndYear();
        payCard.enterCardData(validCardInformation);
        payCard.successfulCardPayment();

        var paymentId = SQLunits.getPaymentId();
        var statusPayment = SQLunits.getStatusPayment(paymentId);
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    @DisplayName("Successful credit purchase with current M and Y.")
    @Test
    public void shouldConfirmCreditWithCurrentMonthAndYear() {
        var tourPage = new TourPage();
        var buyCredit = tourPage.buyCredit();
        var validCardInformation = DataHelper.getCurrentMonthAndYear();
        buyCredit.enterCreditCardData(validCardInformation);
        buyCredit.successfulCreditCardPayment();

        var paymentId = SQLunits.getPaymentId();
        var statusPayment = SQLunits.getStatusCredit(paymentId);
        Assertions.assertEquals("APPROVED", statusPayment);
    }

}