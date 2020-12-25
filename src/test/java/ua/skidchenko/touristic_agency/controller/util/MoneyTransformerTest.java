package ua.skidchenko.touristic_agency.controller.util;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RunWith(JUnit4.class)
public class MoneyTransformerTest extends TestCase {
    private HttpServletRequest requestMock;
    private Cookie[] cookieList;

    @Before
    public void init() {
        requestMock = Mockito.mock(HttpServletRequest.class);

    }

    @Test
    public void testTransformToKopecksEngLocale() {
        cookieList = new Cookie[]{new Cookie("language", "en_GB")};
        Mockito.when(requestMock.getCookies()).thenReturn(cookieList);
        double moneyInDouble = 255.49;
        String s = MoneyTransformer.getInstance().transformToKopecks(255.49,requestMock);
        Assert.assertEquals("Not equals", "658983", s);
    }

    @Test
    public void testTransformToKopecksUkrLocale() {
        cookieList = new Cookie[]{new Cookie("language", "uk_UA")};
        Mockito.when(requestMock.getCookies()).thenReturn(cookieList);
        double moneyInDouble = 255.49;
        String s = MoneyTransformer.getInstance().transformToKopecks(255.49,requestMock);
        Assert.assertEquals("Not equals", "25549", s);
    }

    @Test
    public void testTransformToCurrencyEngLocale() {
        cookieList = new Cookie[]{new Cookie("language", "en_GB")};
        Mockito.when(requestMock.getCookies()).thenReturn(cookieList);
        int moneyInInt = 25549;
        String s = MoneyTransformer.getInstance().transformToCurrency(moneyInInt,requestMock);
        Assert.assertEquals("Not equals", "9,90", s);
    }

    @Test
    public void testTransformToCurrencyUkrLocale() {
        cookieList = new Cookie[]{new Cookie("language", "uk_UA")};
        Mockito.when(requestMock.getCookies()).thenReturn(cookieList);
        int moneyInInt = 25549;
        String s = MoneyTransformer.getInstance().transformToCurrency(moneyInInt,requestMock);
        Assert.assertEquals("Not equals", "255,42", s);
    }
}