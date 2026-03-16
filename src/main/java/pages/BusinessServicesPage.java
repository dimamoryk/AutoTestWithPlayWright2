package pages;

import annotations.PathTemplete;
import com.google.inject.Inject;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@PathTemplete("/uslugi-kompaniyam")
public class BusinessServicesPage extends AbsBasePage {

    @Inject
    public BusinessServicesPage(Page page) {
        super(page);
        this.page = page;
    }

    public void clickDetailsButton() {
        page.click("text='Подробнее'", new Page.ClickOptions().setTimeout(5000));
    }

    public void checkBusinessCourseDevelopmentPageOpened() {
        assertThat(page).hasURL("*/razrabotka-kursov/*");
    }

    public void checkLearningDirectionsPresent() {
        assertThat(page.locator(".learning-directions")).isVisible();
    }


    public void clickOnFirstLearningDirection() {
        page.click(".learning-directions li:first-of-type");
    }

    public void checkCatalogOpenedWithSelectedCategory() {
        assertThat(page.locator(".course-catalog")).isVisible();
        assertThat(page.locator(".selected-category")).hasText("Название категории");
    }
}
