package pages;

import annotations.PathTemplete;
import com.google.inject.Inject;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@PathTemplete("/subscription")
public class SubscriptionPage extends AbsBasePage {

    @Inject
    public SubscriptionPage(Page page) {
        super(page);
        this.page = page;
    }

    public void checkSubscriptionsPresence() {
        assertThat(page.locator("[href^='/subscription']")).isVisible();
    }

    public void expandSubscriptionDetails() {
        page.click("text='Подробнее'");
    }

    public void checkExpandedDetails() {
        assertThat(page.locator("text='Свернуть'")).
                isVisible();
    }

    public void collapseSubscriptionDetails() {
        page.click("text='Свернуть'");
    }

    public void checkCollapsedDetails() {
        assertThat(page.locator("text='Подробнее'")).
                isVisible();
    }

    public void buySubscription() {
        page.click("text='Купить'");
    }

    public void checkPaymentPage() {
        assertThat(page).hasURL("*/payment*");
    }

    public void selectTrialSubscription() {
        page.selectOption("#subscription-type", "trial");
    }

    public void checkPriceAndDurationChanged() {
        assertThat(page.locator("#price")).hasText("Новая цена");
        assertThat(page.locator("#duration")).hasText("Новый срок");
    }
}
