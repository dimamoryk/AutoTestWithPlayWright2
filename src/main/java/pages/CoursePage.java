package pages;

import annotations.PathTemplete;
import com.google.inject.Inject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

@PathTemplete("/catalog/courses/")
public class CoursePage extends AbsBasePage {

    private final Locator architectureCourse;
    private final Locator showMoreButton;
    protected Page page;

    @Inject
    public CoursePage(Page page) {
        super(page);
        this.page = page;

        architectureCourse = page.locator("div:text-is('Архитектура корпорации. TOGAF 10')");
        showMoreButton = page.locator("button:text-is('Показать ещё 20')");
    }

    public Page getPage() {
        return page;
    }

    public void changeCategoryToArchitecture() {
        selectOption("select#category-filter", "Архитектура");
    }

    public boolean onlyArchitectureCoursesShown() {
        return page.querySelectorAll(".course-item").stream()
                .allMatch(course -> course.getAttribute("data-category").equals("Архитектура"));
    }

    public void resetFilters() {
        page.click("#reset-filters-btn");
    }

    public boolean filtersAreReset() {
        return page.locator("#filter-direction").innerText().equals("Все направления") &&
                page.locator("#filter-level").innerText().equals("Любой уровень сложности");
    }

    public void selectOption(String selector, String value) {
        page.selectOption(selector, value);
    }
}