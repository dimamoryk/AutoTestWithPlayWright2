package component;

import com.google.inject.Inject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import common.AbsCommon;

import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CommonComponents extends AbsCommon {

    private final Locator directionLabel;
    private final Locator levelLabel;
    protected Page page;

    @Inject
    public CommonComponents(Page page) {
        super(page);
        this.page = page;

        directionLabel = page.locator("label:text-is('Все направления')");
        levelLabel = page.locator("label:text-is('Любой уровень')");

    }

    public void waitForPageLoad() {
        page.waitForLoadState();
    }

    public void checkInitialFilters() {
        assertThat(directionLabel).hasText("Все направления");
        assertThat(levelLabel).hasText("Любой уровень сложности");
    }

    public void setDurationFilter(int minMonths, int maxMonths) {
        page.fill("#min-duration", "" + minMonths);
        page.fill("#max-duration", "" + maxMonths);
    }

    public boolean haveCoursesWithinDuration(int minMonths, int maxMonths) {
        List<String> durations = page.querySelectorAll(".course-item").stream()
                .map(course -> course.getAttribute("data-duration"))
                .collect(Collectors.toList());

        return durations.stream()
                .mapToInt(durationStr -> Integer.parseInt(durationStr))
                .anyMatch(duration -> duration >= minMonths && duration <= maxMonths);
    }
}