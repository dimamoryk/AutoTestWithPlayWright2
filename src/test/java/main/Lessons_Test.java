package main;

import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import component.CommonComponents;
import extensions.PlaywrightUIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(PlaywrightUIExtension.class)
public class Lessons_Test {

    @Inject
    private MainPage mainPage;

    @Inject
    private LessonsPage lessonsPage;

    @Inject
    private CommonComponents commonComponents;

    @Inject
    private CoursePage coursePage;

    @Inject
    private BusinessServicesPage businessServicesPage;

    @Inject
    private SubscriptionPage subscriptionPage;

    @Inject
    private Page page;

    @Test
    public void testTeachersAppearInInfoTab() {

        mainPage.open();
        mainPage.waitForInfoTab();
        mainPage.checkInfoTabVisibility();
        mainPage.clickInfoTab();
        mainPage.checkTeachersAreVisible();
    }

    @Test
    public void testLessonsClickHouse() {

        lessonsPage.open();
        commonComponents.waitForPageLoad();
        lessonsPage.checkTeachersBlock();
        lessonsPage.scrollTeachersBlock();
        lessonsPage.checkScrollHappened();
        lessonsPage.clickFirstTeacher();
        lessonsPage.clickArrowRight();
        lessonsPage.checkCardSwitched(1);
        lessonsPage.clickArrowLeft();
        lessonsPage.checkCardSwitched(0);
    }

    @Test
    public void testCourseFilter() {
        coursePage.open();
        commonComponents.checkInitialFilters();

        commonComponents.setDurationFilter(3, 10);
        assertTrue(commonComponents.haveCoursesWithinDuration(3, 10), "Курсы нужной длительности отсутствуют");

        coursePage.changeCategoryToArchitecture();
        assertTrue(coursePage.onlyArchitectureCoursesShown(), "Курсы не переключились на нужную категорию");

        coursePage.resetFilters();
        assertTrue(coursePage.filtersAreReset(), "Фильтры не были успешно сброшены");
    }

    @Test
    public void testCustomCourseDevelopment() {
        businessServicesPage.open();
        businessServicesPage.clickDetailsButton();
        businessServicesPage.checkBusinessCourseDevelopmentPageOpened();
        businessServicesPage.checkLearningDirectionsPresent();
        businessServicesPage.clickOnFirstLearningDirection();
        businessServicesPage.checkCatalogOpenedWithSelectedCategory();
    }

    @Test
    public void testSubscriptionFlow() {
        subscriptionPage.open();
        subscriptionPage.checkSubscriptionsPresence();

        subscriptionPage.expandSubscriptionDetails();
        subscriptionPage.checkExpandedDetails();

        subscriptionPage.collapseSubscriptionDetails();
        subscriptionPage.checkCollapsedDetails();

        subscriptionPage.buySubscription();
        subscriptionPage.checkPaymentPage();

        subscriptionPage.selectTrialSubscription();
        subscriptionPage.checkPriceAndDurationChanged();
    }

    @Test
    public void testCookiesOnPage() {
        String cookieName = "testCookie";
        String cookieValue = "testValue";
        page.addInitScript("localStorage setItem('testLocalStorage', 'lala')");
        page.addInitScript(String.format("document.cookie =  '%s=%s; path/;'", cookieName, cookieValue));

        mainPage.open();

        String localValue = (String) page.evaluate("localStorage getItem('testLocalStorage')");
        String actualCookieValue = (String) page.evaluate("document.cookie.split(';').find(item => item.trim() === testCookie)");

        assertEquals(localValue, "lala");
        assertEquals(actualCookieValue, String.format("%s=%s", cookieName, cookieValue));
    }
}
