package pages;

import annotations.PathTemplete;
import com.google.inject.Inject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@PathTemplete("/lessons/clickhouse/")
public class LessonsPage extends AbsBasePage {

    @Inject
    public LessonsPage(Page page) {
        super(page);
        this.page = page;
    }
    public Page getPage() {
        return page;
    }
    public void checkTeachersBlock() {
        Locator locators = page.locator("div[data-testid='teachers-block']");
        assertThat(locators).isVisible();
    }

    public void scrollTeachersBlock() {
        page.evaluate("document.querySelector('div[data-testid='teachers-block']').scrollLeft += window.innerWidth;");
    }

    public void checkScrollHappened() {
        assertTrue((Boolean) page.locator("div[data-testid='teachers-block']").evaluate("el => el.scrollLeft > 0"));
    }

    public void clickFirstTeacher() {
        page.click("a[href^='/instructors'][class]:first-of-type");
    }

    public void clickArrowRight() {
        page.click("button[data-testid='arrow-right']");
    }

    public void clickArrowLeft() {
        page.click("button[data-testid='arrow-left']");
    }

    public void checkCardSwitched(int index) {
        String activeDataIndex = page.locator(".teacher-card.active").getAttribute("data-index");
        assertTrue(activeDataIndex.equals(Integer.toString(index)), "Карточка преподавателя не переключилась");
    }
}