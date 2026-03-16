package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.microsoft.playwright.Page;
import pages.*;

public class GuicePageModule extends AbstractModule {

    private final Page page;

    public GuicePageModule(Page page) {
        this.page = page;
    }

    @Provides
    @Singleton
    public LessonsPage provideLessonsPage() {
        return new LessonsPage(page);
    }

    @Provides
    @Singleton
    public BusinessServicesPage provideBusinessServicesPage() {
        return new BusinessServicesPage(page);
    }

    @Provides
    @Singleton
    public CoursePage provideCoursePage() {
        return new CoursePage(page);
    }

    @Provides
    @Singleton
    public MainPage provideMainPage() {
        return new MainPage(page);
    }

    @Provides
    @Singleton
    public SubscriptionPage provideSubscriptionPage() {
        return new SubscriptionPage(page);
    }

    @Override
    protected void configure() {
    }
}