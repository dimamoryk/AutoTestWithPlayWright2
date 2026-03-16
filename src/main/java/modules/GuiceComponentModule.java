package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.microsoft.playwright.Page;
import component.CommonComponents;

public class GuiceComponentModule extends AbstractModule {

    private final Page page;

    public GuiceComponentModule(Page page) {
        this.page = page;
    }

    @Provides
    @Singleton
    public CommonComponents provideCommonComponents() {
        return new CommonComponents(page);
    }

    @Override
    protected void configure() {
    }
}