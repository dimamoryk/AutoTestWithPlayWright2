package pages;

import annotations.Path;
import com.google.inject.Inject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Path("/")
public class MainPage extends AbsBasePage {

    private final Locator infoTabLocator;
    private final Locator teachersBlockLocator;
    protected Page page;

    @Inject
    public MainPage(Page page) {
        super(page);
        this.page = page;
        infoTabLocator = page.locator("span[title='Информация']");
        teachersBlockLocator = page.locator("a[href='/instructors']");
    }

    public void waitForInfoTab() {
        infoTabLocator.waitFor();
    }

    public void checkInfoTabVisibility() {
        assertThat(infoTabLocator).isVisible();
    }

    public void clickInfoTab() {
        infoTabLocator.click();
    }

    public void checkTeachersAreVisible() {
        assertThat(teachersBlockLocator).isVisible();
    }
}
