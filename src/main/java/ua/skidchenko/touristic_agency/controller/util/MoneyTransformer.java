package ua.skidchenko.touristic_agency.controller.util;

import ua.skidchenko.touristic_agency.dto.TourDTO;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;

public class MoneyTransformer {

    private static final String PRICE = "price";
    private static MoneyTransformer moneyTransformer;
    private static final String COOKIE_LOCALE_NAME = "language";
    private static final String ENG_LOCALE = "en-gb";
    private static final String UKR_LOCALE = "uk-ua";
    private static final String MONEY_PATTERN = "#0.00";

    private static final double DOLLAR_COURSE = 25.8;

    public static MoneyTransformer getInstance() {
        if (moneyTransformer == null) {
            synchronized (MoneyTransformer.class) {
                if (moneyTransformer == null) {
                    moneyTransformer = new MoneyTransformer();
                }
            }
        }
        return moneyTransformer;
    }

    public String transformToKopecks(double priceInDouble,HttpServletRequest request) {
        String langCookie = getLangCookieFromRequest(request);
        switch (langCookie) {
            case (ENG_LOCALE):
                return String.valueOf((int) (priceInDouble * DOLLAR_COURSE * 100));
            case (UKR_LOCALE):
                return String.valueOf((int) (priceInDouble * 100));
            default:
                throw new RuntimeException("Unsupported locale in request. " +
                        "Cannot transform \"price\" to presented locale view.");
        }
    }

    public String transformToCurrency(int priceInInteger, HttpServletRequest request) {
        String langCookie = getLangCookieFromRequest(request);
        switch (langCookie) {
            case (ENG_LOCALE):
                return new DecimalFormat(MONEY_PATTERN).format(priceInInteger / DOLLAR_COURSE / 100);
            case (UKR_LOCALE):
                return new DecimalFormat(MONEY_PATTERN).format((double) priceInInteger / 100);
            default:
                throw new RuntimeException("Unsupported locale in request. " +
                        "Cannot transform \"price\" to presented locale view.");
        }
    }

    private String getLangCookieFromRequest(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(n -> n.getName().equals(COOKIE_LOCALE_NAME))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no lang cookie in  request!"))
                .getValue();
    }
}